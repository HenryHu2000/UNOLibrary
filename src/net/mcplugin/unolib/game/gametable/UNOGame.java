/**
 * 
 */
package net.mcplugin.unolib.game.gametable;

import java.util.ArrayList;

import net.mcplugin.unolib.game.deck.AbstractCard;

/**
 * Main class of the UNO game.
 * 
 * @author Henry Hu
 *
 */
public class UNOGame {
	private boolean ended = false;
	private final ArrayList<GamePlayer> playerList;
	private CardPile pile = new CardPile();
	private AbstractCard currentCard;
	private int currentPlayerNumber = 0;

	/**
	 * @return the currentPlayerNumber
	 */
	public int getCurrentPlayerNumber() {
		return currentPlayerNumber;
	}

	/**
	 * @param currentPlayerNumber
	 *            the currentPlayerNumber to set
	 */
	public void setCurrentPlayerNumber(int currentPlayerNumber) {
		this.currentPlayerNumber = currentPlayerNumber;
	}

	private boolean clockwise;

	/**
	 * 
	 * @param playerList
	 *            involves all players who join the card game
	 * @param firstPlayer
	 *            to play card
	 */
	public UNOGame(ArrayList<GamePlayer> playerList) {
		// TODO Auto-generated constructor stub
		for (GamePlayer eachPlayer : playerList) {
			eachPlayer.setGame(this);
		}
		this.playerList = playerList;

		pile.initialize(); // Initialize the card pile
		this.setClockwise(true);

		// Wait to complete

	}

	/**
	 * 
	 * 
	 * 
	 * @param playerList
	 *            involves all players who join the card game
	 * @param pile
	 *            of cards used in game
	 * @param currentPlayer
	 *            to play
	 * @param direction
	 *            of order of play
	 */
	public UNOGame(ArrayList<GamePlayer> playerList, CardPile pile, AbstractCard currentCard, int currentPlayerNumber,
			boolean direction) {
		// TODO Auto-generated constructor stub
		for (GamePlayer eachPlayer : playerList) {
			eachPlayer.setGame(this);
		}
		this.playerList = playerList;
		this.setPile(pile);
		this.setCurrentCard(currentCard);

		this.setCurrentPlayerNumber(currentPlayerNumber);
		this.setClockwise(direction);
		// Wait to complete

	}

	/**
	 * @return the playerList
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<GamePlayer> getPlayerList() {
		return (ArrayList<GamePlayer>) playerList.clone();
	}

	/**
	 * @return the pile
	 */
	public CardPile getPile() {
		return pile;
	}

	/**
	 * @param pile
	 *            the pile to set
	 */
	public void setPile(CardPile pile) {
		this.pile = pile;
	}

	/**
	 * @return the currentPlayer
	 */
	public GamePlayer getCurrentPlayer() {
		return playerList.get(currentPlayerNumber);
	}

	public GamePlayer getNextPlayer() {
		if (isClockwise()) {

		}
		return null;
	}

	/**
	 * @return true if the game order is clockwise
	 */
	public boolean isClockwise() {
		return clockwise;
	}

	/**
	 * @param clockwise
	 *            the clockwise to set
	 */
	public void setClockwise(boolean clockwise) {
		this.clockwise = clockwise;
	}

	/**
	 * @return the ended
	 */
	public boolean isEnded() {
		return ended;
	}

	/**
	 * @return the currentCard
	 */
	public AbstractCard getCurrentCard() {
		return currentCard;
	}

	/**
	 * @param currentCard
	 *            the currentCard to set
	 */
	public void setCurrentCard(AbstractCard currentCard) {
		this.currentCard = currentCard;
	}

}
