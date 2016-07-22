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
	private int currentPlayerID = 0;
	private boolean clockwise = true; // of order of play

	/**
	 * Create an UNO game with a given list of players
	 * 
	 * @param playerList
	 *            involves all players who join the card game
	 */
	public UNOGame(ArrayList<GamePlayer> playerList) {
		// TODO Auto-generated constructor stub
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

	/**
	 * @param card
	 *            to be forced to play
	 * @param gotoNext
	 *            if need to move to the next player
	 */
	public void forceProceed(AbstractCard card, boolean gotoNext) {
		// Make the given card as the current card, and then remove it from
		// player's hand to the discard pile.
		this.setCurrentCard(card);
		getCurrentPlayer().discard(card, discardPile);
		if (gotoNext) {
			gotoNextPlayer();
		}
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
		return getPlayerList().get(currentPlayerID);
	}

	/**
	 * @return the currentPlayerID
	 */
	public int getCurrentPlayerID() {
		return currentPlayerID;
	}

	/**
	 * @return the discardPile
	 */
	public CardPile getDiscardPile() {
		return discardPile;
	}

	/**
	 * @param playerID
	 * @return the ID of next player to play card
	 */
	public int getNextPlayerID(int playerID) {
		if (isClockwise()) {
			// Return player ID 0 when the given player is the last player.
			return (playerID >= getPlayerList().size() - 1) ? 0 : (playerID + 1);
		} else {
			// Return the last player when the given player is player ID 0.
			return (playerID <= 0) ? (getPlayerList().size() - 1) : (playerID - 1);
		}

	}

	/**
	 * @return the pile
	 */
	public CardPile getPile() {
		return pile;
	}

	/**
	 * @param playerID
	 * @return
	 */
	public GamePlayer getPlayer(int playerID) {
		return getPlayerList().get(playerID);
	}

	/**
	 * @return the playerList
	 */
	public ArrayList<GamePlayer> getPlayerList() {
		return playerList;
	}

	/**
	 * @return number of players
	 */
	public int getPlayersNumber() {
		return playerList.size();
	}

	/**
	 * @return the game stage
	 */
	public GameStage getStage() {
		return stage;
	}

	public void gotoNextPlayer() {
		// Set the current player ID as the ID of the next player.
		this.setCurrentPlayerID(this.getNextPlayerID(currentPlayerID));
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
			gotoNextPlayer();

			break;
		case REVERSE:
			// Reverse the direction and then move to the next player.
			this.setClockwise(!clockwise);
			gotoNextPlayer();
			break;
		case SKIP:
			// Goto the player after the next player.
			gotoNextPlayer();
			gotoNextPlayer();
			break;
		default:
			break;

		}
	}

	/**
	 * @return response of proceeding
	 */
	public ProceedResponse proceed() {
		switch (stage) {
		case END:
			break;
		case RESTRICTIVE_SUSPEND:
			// If the player has already drawn a card, the game move to the next
			// player.
			this.gotoNextPlayer();
			// Reset the game stage to SUSPEND.
			this.stage = GameStage.SUSPEND;
			return ProceedResponse.SKIPPED;
		case SUSPEND:
			// Restrict the card that the player can play to the card he drew.
			getCurrentPlayer().setRestrictedCard(this.getCurrentPlayer().draw(pile));
			// Set the game stage to RESTRICTIVE_SUSPEND
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
	 * @return response of proceeding
	 */
	public ProceedResponse proceed(ColorCard card) {
		if (!card.matches(currentCard)) { // Return if the given card doesn't
											// match the given card
			return ProceedResponse.MISMATCHED;
		}
		switch (stage) {
		case END:
			return ProceedResponse.ENDED;
		case RESTRICTIVE_SUSPEND:
			if (card == getCurrentPlayer().getRestrictedCard()) {

				if (card instanceof ActionCard) {
					forceProceed(card, false);
					this.performAction(((ActionCard) card).getAction());
				} else
					forceProceed(card, true);
				this.stage = GameStage.SUSPEND; // Reset the game stage to
												// SUSPEND.
				return ProceedResponse.PLAYED;
			} else
				return ProceedResponse.BEYOND_RESTRICTION;

		case SUSPEND:
			if (getCurrentPlayer().getHand().contains(card)) {

				if (card instanceof ActionCard) {
					forceProceed(card, false);
					this.performAction(((ActionCard) card).getAction());
				} else
					forceProceed(card, true);
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
	 * @return response of proceeding
	 */
	public ProceedResponse proceed(WildCard card, Color declaredColor) {
		if (!card.matches(currentCard)) { // Return if the given card doesn't
			// match the given card
			return ProceedResponse.MISMATCHED;
		}
		switch (stage) {
		case END:
			return ProceedResponse.ENDED;

		case RESTRICTIVE_SUSPEND:
			if (card == getCurrentPlayer().getRestrictedCard()) {
				card.declareColor(declaredColor);
				if (card.isDrawFour()) {
					forceProceed(card, false);
					getCurrentPlayer().draw(pile, 4);
				} else
					forceProceed(card, true);
				this.stage = GameStage.SUSPEND;
				return ProceedResponse.PLAYED;
			}
			return ProceedResponse.BEYOND_RESTRICTION;

		case SUSPEND:
			if (getCurrentPlayer().getHand().contains(card)) {
				card.declareColor(declaredColor);
				if (card.isDrawFour()) {
					forceProceed(card, false);
					getCurrentPlayer().draw(pile, 4);
				} else
					forceProceed(card, true);

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
		for (int i = 0; i < getPlayerList().size(); i++) {
			for (int j = 0; j < getPlayerList().size(); j++) {
				if (i != j && getPlayerList().get(i) == getPlayerList().get(j)) {
					getPlayerList().remove(j); // Remove the player if he
												// appeared in any other place
												// of the list
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
	 * @param currentPlayerID
	 *            the currentPlayerID to set
	 */
	public void setCurrentPlayerID(int currentPlayerID) {
		this.currentPlayerID = currentPlayerID;
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
		discardPile.push(currentCard);

		if (currentCard instanceof WildCard) {
			if (((WildCard) currentCard).isDrawFour()) {
				reshuffle();
				this.start();
				return;
			} else {
				// In order that the player can only use the wild card by
				// declaring a color
				getCurrentPlayer().setRestrictedCard(currentCard);

				return;
			}
		} else if (currentCard instanceof ActionCard) {
			this.performAction(((ActionCard) currentCard).getAction());
			return;
		}

	}

	public void reshuffle() {
		pile.addAll(discardPile);
		pile.shuffle();
		discardPile.clear();
	}
}
