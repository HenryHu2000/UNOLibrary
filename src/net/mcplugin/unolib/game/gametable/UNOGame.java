/**
 * 
 */
package net.mcplugin.unolib.game.gametable;

import java.util.ArrayList;

import net.mcplugin.unolib.game.deck.AbstractCard;
import net.mcplugin.unolib.game.deck.ActionCard;
import net.mcplugin.unolib.game.deck.ActionType;
import net.mcplugin.unolib.game.deck.Colorable;
import net.mcplugin.unolib.game.deck.WildCard;

/**
 * Main class of the UNO game.
 * 
 * @author Henry Hu
 *
 */
public class UNOGame {
	private GameStage stage = GameStage.SUSPEND;
	private final ArrayList<GamePlayer> playerList; // involves all players who
	// join the card game
	private CardPile pile = new CardPile(); // of cards used in game
	private CardPile discardPile = new CardPile();
	private AbstractCard currentCard;
	private int currentPlayerNumber = 0;
	private boolean clockwise = true; // of order of play

	/**
	 * Create an UNO game with a given list of players
	 * 
	 * @param playerList
	 *            involves all players who join the card game
	 */
	public UNOGame(ArrayList<GamePlayer> playerList) {
		// TODO Auto-generated constructor stub
		for (GamePlayer eachPlayer : playerList) {
			eachPlayer.setGame(this);
		}
		this.playerList = playerList;

		pile.initialize(); // Initialize the card pile
		for (int i = 0; i < 7; i++) {
			for (int playerNum = 0; playerNum < playerList.size(); playerNum++) {
				this.getPlayer(playerNum).draw(pile);
			}
		}
		this.start();

	}

	/**
	 * Get the number of the player after the next player to play card. Usually
	 * called when the next player is skipped.
	 * 
	 * @return the number of the player after the next player to play card
	 */
	public int getAfterNextPlayerNumber(int playerNumber) {
		return this.getNextPlayerNumber(this.getNextPlayerNumber(playerNumber));
	}

	/**
	 * @return the currentCard
	 */
	public AbstractCard getCurrentCard() {
		return currentCard;
	}

	/**
	 * @return the currentPlayer
	 */
	public GamePlayer getCurrentPlayer() {
		return playerList.get(currentPlayerNumber);
	}

	/**
	 * @return the currentPlayerNumber
	 */
	public int getCurrentPlayerNumber() {
		return currentPlayerNumber;
	}

	/**
	 * @return the discardPile
	 */
	public CardPile getDiscardPile() {
		return discardPile;
	}

	/**
	 * @param playerNumber
	 * @return the next player to play card
	 */
	public GamePlayer getNextPlayer(int playerNumber) {
		return this.getPlayer(getNextPlayerNumber(playerNumber));

	}

	/**
	 * @param playerNumber
	 * @return the number of next player to play card
	 */
	public int getNextPlayerNumber(int playerNumber) {
		if (isClockwise()) {
			return (playerNumber >= playerList.size() - 1) ? 0 : (playerNumber + 1);
		} else {
			return (playerNumber <= 0) ? (playerList.size() - 1) : (playerNumber - 1);
		}

	}

	/**
	 * @return the pile
	 */
	public CardPile getPile() {
		return pile;
	}

	/**
	 * @param playerNumber
	 * @return
	 */
	public GamePlayer getPlayer(int playerNumber) {
		return playerList.get(playerNumber);
	}

	/**
	 * @return the game stage
	 */
	public GameStage getStage() {
		return stage;
	}

	/**
	 * @return true if the game order is clockwise
	 */
	public boolean isClockwise() {
		return clockwise;
	}

	/**
	 * Make the current player play a given card if he has it
	 * 
	 * @param card
	 *            to play
	 * @return true if the card is successfully played
	 */
	public boolean makePlayCard(Colorable card) {
		if (card instanceof AbstractCard && getCurrentPlayer().getHand().contains(card)
				&& (((AbstractCard) card).matches(currentCard))) {
			this.setCurrentCard((AbstractCard) card);
			getCurrentPlayer().discard((AbstractCard) card, discardPile);
			this.setCurrentPlayerNumber(this.getNextPlayerNumber(getCurrentPlayerNumber()));
			if (card instanceof ActionCard) {
				this.performAction(((ActionCard) card).getAction());
			}
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @param action
	 *            to perform
	 */
	public void performAction(ActionType action) {
		switch (action) {
		case DRAW_TWO:
			this.getCurrentPlayer().draw(discardPile, 2);
			this.setCurrentPlayerNumber(this.getNextPlayerNumber(currentPlayerNumber));
			break;
		case REVERSE:
			this.setClockwise(false);
			this.setCurrentPlayerNumber(this.getAfterNextPlayerNumber(currentPlayerNumber));

			break;
		case SKIP:
			this.setCurrentPlayerNumber(this.getNextPlayerNumber(currentPlayerNumber));
			break;
		default:
			break;

		}
		// TODO wait to finish
	}

	/**
	 * @param clockwise
	 *            the clockwise to set
	 */
	public void setClockwise(boolean clockwise) {
		this.clockwise = clockwise;
	}

	/**
	 * @param currentCard
	 *            the currentCard to set
	 */
	public void setCurrentCard(AbstractCard currentCard) {
		this.currentCard = currentCard;
	}

	/**
	 * @param currentPlayerNumber
	 *            the currentPlayerNumber to set
	 */
	public void setCurrentPlayerNumber(int currentPlayerNumber) {
		this.currentPlayerNumber = currentPlayerNumber;
	}

	/**
	 * @param pile
	 *            the pile to set
	 */
	public void setPile(CardPile pile) {
		this.pile = pile;
	}

	/**
	 * Start the game by discarding the top card on the pile.
	 */
	public void start() {

		currentCard = pile.pop();
		if (currentCard instanceof WildCard && ((WildCard) currentCard).isDrawFour()) {
			pile.push(currentCard);
			pile.shuffle();
			this.start();
		} else {
			discardPile.push(currentCard);
			if (currentCard instanceof ActionCard) {
				this.performAction(((ActionCard) currentCard).getAction());
			} else if (currentCard instanceof WildCard && ((WildCard) currentCard).isDrawFour()) {
				// TODO finish this part
			}
		}
	}

}
