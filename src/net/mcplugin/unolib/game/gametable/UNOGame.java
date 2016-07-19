/**
 * 
 */
package net.mcplugin.unolib.game.gametable;

import java.util.ArrayList;

import net.mcplugin.unolib.game.deck.AbstractCard;
import net.mcplugin.unolib.game.deck.ActionCard;
import net.mcplugin.unolib.game.deck.ActionType;
import net.mcplugin.unolib.game.deck.Color;
import net.mcplugin.unolib.game.deck.ColorCard;
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
		this.removeRepeatPlayer();
		pile.initialize(); // Initialize the card pile
		for (int i = 0; i < 7; i++) { // Deal the cards to each players
			for (int playerNum = 0; playerNum < playerList.size(); playerNum++) {
				this.getPlayer(playerNum).draw(pile);
			}
		}
		this.start();

	}

	public void forceProceed(AbstractCard card) {
		this.setCurrentCard(card);
		getCurrentPlayer().discard(card, discardPile);
		gotoNextPlayer();
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

	public void gotoNextPlayer() {
		this.setCurrentPlayerNumber(this.getNextPlayerNumber(currentPlayerNumber));
	}

	/**
	 * @return true if the game order is clockwise
	 */
	public boolean isClockwise() {
		return clockwise;
	}

	/**
	 * Perform an action. Must be invoked before moving to the next player.
	 * 
	 * @param action
	 *            to perform
	 */
	public void performAction(ActionType action) {
		switch (action) {
		case DRAW_TWO:
			gotoNextPlayer();
			this.getCurrentPlayer().draw(pile, 2);

			break;
		case REVERSE:
			this.setClockwise(!clockwise);

			break;
		case SKIP:
			gotoNextPlayer();
			break;
		default:
			break;

		}
	}

	public ProceedResponse proceed() {
		switch (stage) {
		case END:
			break;
		case RESTRICTIVE_SUSPEND:
			this.gotoNextPlayer();
			this.stage = GameStage.SUSPEND;
			return ProceedResponse.SKIPPED;
		case SUSPEND:
			getCurrentPlayer().setRestrictedCard(this.getCurrentPlayer().draw(pile));
			this.stage = GameStage.RESTRICTIVE_SUSPEND;
			return ProceedResponse.DREW;
		default:
			break;

		}
		return ProceedResponse.UNKNOWN;

	}

	/**
	 * Make the current player play a given card if he has it
	 * 
	 * @param card
	 *            to play
	 * @return true if the card is successfully played
	 */
	public ProceedResponse proceed(ColorCard card) {
		if (card.matches(currentCard)) {
			return ProceedResponse.MISMATCHED;
		}
		switch (stage) {
		case END:
			return ProceedResponse.ENDED;
		case RESTRICTIVE_SUSPEND:
			if (card == getCurrentPlayer().getRestrictedCard()) {

				if (card instanceof ActionCard) {
					this.performAction(((ActionCard) card).getAction());
				}
				forceProceed(card);
				this.stage = GameStage.SUSPEND;
				return ProceedResponse.PLAYED;
			} else
				return ProceedResponse.BEYOND_RESTRICTION;

		case SUSPEND:
			if (getCurrentPlayer().getHand().contains(card)) {

				if (card instanceof ActionCard) {
					this.performAction(((ActionCard) card).getAction());
				}
				forceProceed(card);
				return ProceedResponse.PLAYED;
			} else
				return ProceedResponse.NOT_EXIST;

		default:
			break;
		}

		return ProceedResponse.UNKNOWN;
	}

	/**
	 * Make the current player play a given card if he has it
	 * 
	 * @param card
	 *            to play
	 * @return true if the card is successfully played
	 */
	public ProceedResponse proceed(WildCard card, Color declaredColor) {
		if (card.matches(currentCard)) {
			return ProceedResponse.MISMATCHED;
		}
		switch (stage) {
		case END:
			return ProceedResponse.ENDED;

		case RESTRICTIVE_SUSPEND:
			if (card == getCurrentPlayer().getRestrictedCard()) {
				card.declareColor(declaredColor);
				forceProceed(card);
				this.stage = GameStage.SUSPEND;
				return ProceedResponse.PLAYED;
			}
			return ProceedResponse.BEYOND_RESTRICTION;

		case SUSPEND:
			if (getCurrentPlayer().getHand().contains(card)) {
				card.declareColor(declaredColor);
				forceProceed(card);
				return ProceedResponse.PLAYED;
			}
			return ProceedResponse.NOT_EXIST;

		default:
			break;

		}
		return ProceedResponse.UNKNOWN;

	}

	/**
	 * Check if there're any repeating players in the list
	 */
	public void removeRepeatPlayer() {
		for (int i = 0; i < playerList.size(); i++) {
			for (int j = 0; j < playerList.size(); j++) {
				if (i != j && playerList.get(i) == playerList.get(j)) {
					playerList.remove(j);
				}
			}
		}

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
		if (currentCard instanceof WildCard) {
			if (((WildCard) currentCard).isDrawFour()) {
				pile.push(currentCard);
				pile.shuffle();
				this.start();
				return;
			} else {
				getCurrentPlayer().setRestrictedCard(currentCard);

				return;
			}
		} else if (currentCard instanceof ActionCard) {
			this.performAction(((ActionCard) currentCard).getAction());
			gotoNextPlayer();
			return;
		}
		discardPile.push(currentCard);
	}

}
