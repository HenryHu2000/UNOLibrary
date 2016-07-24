package net.mcplugin.unolib.control;

import java.util.Scanner;

import net.mcplugin.unolib.game.deck.AbstractCard;
import net.mcplugin.unolib.game.deck.ActionCard;
import net.mcplugin.unolib.game.deck.Color;
import net.mcplugin.unolib.game.gametable.GamePlayer;
import net.mcplugin.unolib.game.gametable.UNOGame;

public class DefaultGameHandler implements GameHandler {
	private UNOGame game;
	Scanner in = new Scanner(System.in);

	public DefaultGameHandler() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onStart(UNOGame game, GamePlayer firstPlayer, AbstractCard firstCard) {
		this.game = game;
		System.out.println("Players: ");
		for (GamePlayer player : game.getPlayerList()) {
			System.out.print(player + " ");
		}
		System.out.println("\nGame started!");
		System.out.println("First card on the top: " + firstCard.toString());

		// TODO Auto-generated method stub

	}

	@Override
	public AbstractCard onTurn(GamePlayer player, AbstractCard currentCard) {
		System.out.println("\nNo." + game.getCurrentPlayerID() + " " + player + "'s turn");
		System.out.println(player + " need to play a card to match " + currentCard.toString());
		System.out.println("or draw one card.");
		return this.onSuspend(player, currentCard);
	}

	@Override
	public AbstractCard onMismatch(GamePlayer player, AbstractCard currentCard) {
		System.out.print("Current card is " + currentCard.toString() + ", ");
		System.out.println(player + "'s card doesn't match the current card!");
		System.out.println("Please choose another card.");
		// TODO Auto-generated method stub
		return this.onSuspend(player, currentCard);
	}

	@Override
	public AbstractCard onCardNotExist(GamePlayer player, AbstractCard currentCard) {
		System.out.println("The card " + player + " doesn't exist!");
		System.out.println(player + " need to play a card to match " + currentCard.toString());
		System.out.println("or draw one card.");
		return this.onSuspend(player, currentCard);

		// TODO Auto-generated method stub
	}

	@Override
	public AbstractCard onUndeclaredColor(GamePlayer player, AbstractCard currentCard) {
		System.out.println(player + " didn't declare a color for the wild card.");
		System.out.println(player + " need to play a card to match " + currentCard.toString() + " again ");
		System.out.println("or draw one card.");
		return this.onSuspend(player, currentCard);

		// TODO Auto-generated method stub
	}

	@Override
	public void onPlay(GamePlayer player, boolean afterDrawn, AbstractCard playedCard) {
		System.out.println((afterDrawn ? "After he drew a card, " : "") + player + " played " + playedCard);
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onDrawInTurn(GamePlayer player, AbstractCard cardDrawn) {
		System.out.println(player + " drew " + cardDrawn + " instead of playing a card.");
		System.out.println("Play it?(Y/N)");
		String input = in.next().toUpperCase();
		switch (input) {
		case "Y":
			return true;
		case "N":
			return false;
		default:
			System.out.println("Automatically skip.");
		}
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onSkipAfterDrawn(GamePlayer player, AbstractCard currentCard) {
		System.out.println(player + " gives up playing a card.");
		// TODO Auto-generated method stub

	}

	@Override
	public void onCardChange(GamePlayer player, int change, int totalCards) {
		System.out.println(player
				+ ((change >= 0) ? (" drew " + Integer.toString(change)) : (" discarded " + Integer.toString(-change)))
				+ " cards.");
		System.out.println("Card numbers of " + player + "'s: " + Integer.toString(totalCards));
		System.out.println(player + "'s cards:");
		System.out.println(player.getCardNames());
		// TODO Auto-generated method stub

	}

	@Override
	public void onDraw(GamePlayer player, int cardsNumber) {
		System.out.println(player + " drew " + Integer.toString(cardsNumber) + "cards");
		// TODO Auto-generated method stub

	}

	@Override
	public void onAction(ActionCard card, GamePlayer invoker) {
		System.out.println(invoker + " performs " + card.getAction().toString());
		// TODO Auto-generated method stub

	}

	@Override
	public void onEnd(GamePlayer winner) {
		// TODO Auto-generated method stub
		System.out.println("Game ended.");
		System.out.println("Winner: " + winner);

	}

	public AbstractCard onSuspend(GamePlayer player, AbstractCard currentCard) {
		System.out.println(player + "'s cards:");
		for (int i = 0; i < player.getHand().size(); i++) {
			System.out.print(Integer.toString(i + 1) + "." + player.getHand().get(i) + " ");
		}
		System.out.println("Please enter the number of card to play:");

		int input = in.nextInt();
		if (input == 0) {
			return null;
		}
		return player.getHand().get(input - 1);

	}

	@Override
	public Color onChooseColor(GamePlayer player) {
		System.out.println("Please enter a color to change for:");
		System.out.println("(RED/YELLOW/GREEN/BLUE)");
		String input = in.next().toUpperCase();
		while (true) {
			switch (input) {
			case "RED":
			case "YELLOW":
			case "GREEN":
			case "BLUE":
				return Color.valueOf(input);
			default:
				System.out.println("Invalid color! Please enter a correct color.");
			}
		}

		// TODO Auto-generated method stub
	}
}
