/**
 * 
 */
package net.mcplugin.unolib.game.gametable;

import java.util.ArrayList;

import net.mcplugin.unolib.control.GameHandler;
import net.mcplugin.unolib.game.deck.AbstractCard;
import net.mcplugin.unolib.game.deck.ActionCard;
import net.mcplugin.unolib.game.deck.ActionType;
import net.mcplugin.unolib.game.deck.Color;
import net.mcplugin.unolib.game.deck.WildCard;

/**
 * Main class of the UNO game.
 * 
 * @author Henry Hu
 *
 */
public class UNOGame {
	private GameStage stage = GameStage.SUSPEND;
	private final ArrayList<CardPlayer> playerList; // involves all players who
	// join the card game
	private CardPile pile = new CardPile(); // of cards used in game
	private CardPile discardPile = new CardPile();
	private AbstractCard currentCard;
	private int currentPlayerID = 0;
	private boolean clockwise = true; // of order of play
	private GameHandler handler;

	/**
	 * Create an UNO game with a given list of players
	 * 
	 * @param handler
	 *            of the game
	 * @param playerList
	 *            involves all players who join the card game
	 */
	public UNOGame(GameHandler handler, ArrayList<CardPlayer> playerList) {
		// TODO Auto-generated constructor stub
		this.handler = handler;
		this.playerList = playerList;
		if (playerList.size() <= 1) {
			throw new IllegalArgumentException("Too few players");
		}
		this.removeRepeatPlayer();
		pile.initialize(); // Initialize the card pile
		this.deal();
		this.discardFirst();
		handler.onStart(this, getCurrentPlayer(), getCurrentCard());

	}

	/**
	 * Create an UNO game with the given variables. This Method won't
	 * automatically start the game. (Please set the card pile and the discard
	 * pile by yourself.)
	 * 
	 * @param handler
	 *            of the game
	 * @param playerList
	 *            involves all players who join the card game
	 * @param currentPlayerID
	 *            of the current player
	 * @param currentCard
	 *            that the next player need play a card to match with
	 * @param clockwise
	 *            of the direction of the game
	 */
	public UNOGame(GameHandler handler, ArrayList<CardPlayer> playerList, int currentPlayerID, AbstractCard currentCard,
			boolean clockwise) {
		this.handler = handler;
		this.playerList = playerList;
		this.removeRepeatPlayer();
		this.currentPlayerID = currentPlayerID;
		this.currentCard = currentCard;
		this.clockwise = clockwise;
	}

	/**
	 * Deal seven cards to each player.
	 */
	public void deal() {
		for (int i = 0; i < 7; i++) { // Deal the cards to each players
			for (int playerNum = 0; playerNum < playerList.size(); playerNum++) {
				this.getPlayer(playerNum).draw(pile);

				handler.onCardsChange(getCurrentPlayer(), 1, getCurrentPlayer().getCardsNumber());
			}
		}

	}

	/**
	 * Start the game by discarding the top card on the pile.
	 */
	public void discardFirst() {
		// Discard a card on the top of the pile.
		currentCard = pile.pop();
		getDiscardPile().push(currentCard);

		if (currentCard instanceof WildCard) {
			if (((WildCard) currentCard).isDrawFour()) {
				reshuffle(); // Shuffle the pile because the card on top is a
								// wild draw four.
				this.discardFirst(); // Do the action of discarding the card on
										// top
				// again.
				return;
			} else {
				// In order that the player can only use the wild card by
				// declaring a color.
				// The player can use the card which he is restricted to in the
				// restrictive suspend stage,
				// although he doesn't really have it.
				getCurrentPlayer().setRestrictedCard(currentCard);
				stage = GameStage.DECLARE_COLOR;
				return;
			}
		} else if (currentCard instanceof ActionCard) {
			this.performAction(((ActionCard) currentCard).getAction());
			stage = GameStage.SUSPEND;

			handler.onTurn(getCurrentPlayer(), getCurrentCard());

			return;
		} else {
			gotoNextPlayer(); // Simply goto the next player if the card is a
			// normal number card.

			handler.onTurn(getCurrentPlayer(), getCurrentCard());

			return;
		}

	}

	/**
	 * This method could be invoke when a card is discarded into the discard
	 * pile.
	 * 
	 * @param card
	 *            to be forced to play
	 * @param gotoNext
	 *            if need to move to the next player
	 */
	public void forceProceed(AbstractCard card, boolean gotoNext) {
		// Make the given card as the current card, and then remove it from
		// player's hand to the discard pile.
		this.setCurrentCard(card);
		getCurrentPlayer().discard(card, getDiscardPile());

		handler.onCardsChange(getCurrentPlayer(), -1, getCurrentPlayer().getCardsNumber());
		if (getCurrentPlayer().getHand().isEmpty()) {
			stage = GameStage.END; // End the game if anyone of the players has
									// no card
			return;
		}
		if (gotoNext) {
			gotoNextPlayer();

			handler.onTurn(getCurrentPlayer(), getCurrentCard());

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
	public CardPlayer getCurrentPlayer() {
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
	public CardPlayer getPlayer(int playerID) {
		return getPlayerList().get(playerID);
	}

	/**
	 * @param player
	 *            to find
	 * @return the ID of the player, return -1 if player is not found.
	 */
	public int getPlayerID(CardPlayer player) {
		for (int i = 0; i < playerList.size(); i++) {
			if (playerList.get(i) == player)
				return i;
		}
		return -1;
	}

	/**
	 * @return the playerList
	 */
	public ArrayList<CardPlayer> getPlayerList() {
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

	/**
	 * @return
	 */
	public CardPlayer getWinner() {
		CardPlayer winner = null;
		for (CardPlayer eachPlayer : getPlayerList()) {
			if (eachPlayer.getCardsNumber() == 0) {
				winner = eachPlayer;
				break;
			}
		}
		return winner;
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
		CardPlayer invoker = getCurrentPlayer();
		switch (action) {
		case DRAW_TWO:
			gotoNextPlayer();
			this.getCurrentPlayer().draw(pile, 2);

			handler.onCardsChange(getCurrentPlayer(), 2, getCurrentPlayer().getCardsNumber());
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

		handler.onAction(action, invoker);
	}

	/**
	 * @return response of proceeding
	 */
	public ProceedResponse proceed() {
		switch (stage) {
		case END:
			handler.onEnd(getWinner());
			return ProceedResponse.ENDED;
		case RESTRICTIVE_SUSPEND:
			// If the player has already drawn a card, the game move to the next
			// player.
			this.gotoNextPlayer();
			// Reset the game stage to SUSPEND.
			this.stage = GameStage.SUSPEND;

			handler.onTurn(getCurrentPlayer(), getCurrentCard());

			return ProceedResponse.SKIPPED;
		case SUSPEND:
			// Restrict the card that the player can play to the card he drew.
			getCurrentPlayer().setRestrictedCard(this.getCurrentPlayer().draw(pile));

			handler.onCardsChange(getCurrentPlayer(), 1, getCurrentPlayer().getCardsNumber());
			// Restrict the card to the drawn card
			this.stage = GameStage.RESTRICTIVE_SUSPEND;
			return ProceedResponse.DREW;
		case DECLARE_COLOR:
			return ProceedResponse.COLOR_UNSPECIFIED;
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
	public ProceedResponse proceed(AbstractCard card) {
		if (card == null) {
			return this.proceed();
		}
		if (!card.matches(currentCard)) { // Return if the given card doesn't
											// match the given card
			return ProceedResponse.MISMATCHED;
		}
		CardPlayer player = getCurrentPlayer();
		switch (stage) {
		case END:
			handler.onEnd(getWinner());
			return ProceedResponse.ENDED;
		case RESTRICTIVE_SUSPEND:
			if (card != getCurrentPlayer().getRestrictedCard()) {
				// Returns if the card is not the card drawn in this turn.
				// Remember a
				// player can only play the same card after he drew one.
				return ProceedResponse.BEYOND_RESTRICTION;
			}
			handler.onPlay(player, card, true);
			return useCard(player, card);

		case SUSPEND:
			if (!getCurrentPlayer().getHand().contains(card)) {
				// Returns because the player doesn't have the card in hand.
				return ProceedResponse.NOT_EXIST;
			}
			handler.onPlay(player, card, false);
			return useCard(player, card);
		case DECLARE_COLOR:
			return ProceedResponse.COLOR_UNSPECIFIED;

		default:
			break;
		}

		return ProceedResponse.UNKNOWN;
	}

	/**
	 * @param color
	 *            to declare
	 * @return response
	 */
	public ProceedResponse proceed(Color color) {
		// TODO Add codes for this method
		if (getCurrentCard() instanceof WildCard && stage == GameStage.DECLARE_COLOR) {
			WildCard wild = (WildCard) getCurrentCard();
			if (!wild.isDeclared()) {

				wild.declareColor(color);
				if (wild.isDrawFour()) {
					// Next player must draw four cards because of the wild draw
					// four.
					forceProceed(wild, false);
					getCurrentPlayer().draw(pile, 4);

					handler.onCardsChange(getCurrentPlayer(), 4, getCurrentPlayer().getCardsNumber());
				} else
					forceProceed(wild, true);
				handler.onTurn(getCurrentPlayer(), getCurrentCard());
			}
			stage = GameStage.SUSPEND;
			return ProceedResponse.PLAYED;
		}
		return ProceedResponse.NOT_EXIST;
	}

	/**
	 * @deprecated As the response of the method is unsafe.
	 */
	@Deprecated
	public ProceedResponse proceed(WildCard card, Color declaredColor) {
		proceed(card);
		return proceed(declaredColor);

	}

	/**
	 * Check if there're any repeating players in the list, and then remove the
	 * repeating players.
	 */
	public void removeRepeatPlayer() {
		for (int i = 0; i < getPlayerList().size(); i++) {
			for (int j = 0; j < getPlayerList().size(); j++) {
				if (i != j && getPlayerList().get(i) == getPlayerList().get(j))
					// Remove the player if he appeared in any other place of
					// the list
					getPlayerList().remove(j);

			}
		}

	}

	/**
	 * Return the cards from the discard pile to the card pile and then shuffle
	 * them.
	 */
	public void reshuffle() {
		pile.addAll(getDiscardPile());
		pile.shuffle();
		getDiscardPile().clear();
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
	 * @param discardPile
	 *            the discardPile to set
	 */
	public void setDiscardPile(CardPile discardPile) {
		this.discardPile = discardPile;
	}

	/**
	 * @param pile
	 *            the pile to set
	 */
	public void setPile(CardPile pile) {
		this.pile = pile;
	}

	/**
	 * @param player
	 * @param card
	 * @return
	 */
	private ProceedResponse useCard(CardPlayer player, AbstractCard card) {
		if (card instanceof ActionCard) {
			forceProceed(card, false);
			this.performAction(((ActionCard) card).getAction());
		} else if (card instanceof WildCard) {
			this.setCurrentCard(card);
			// Every time a player plays a wild card, he must declare a
			// color.

			this.stage = GameStage.DECLARE_COLOR; // Let the player declare
													// a
													// card.
			return ProceedResponse.COLOR_UNSPECIFIED;
		} else
			forceProceed(card, true);
		this.stage = GameStage.SUSPEND; // Reset the game stage to
										// SUSPEND.

		return ProceedResponse.PLAYED;
	}
}
