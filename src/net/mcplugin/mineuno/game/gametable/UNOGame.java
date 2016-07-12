/**
 * 
 */
package net.mcplugin.mineuno.game.gametable;

import java.util.ArrayList;

/**
 * @author Henry Hu
 *
 */
public class UNOGame {
	private GameStage stage = GameStage.PREPARING;
	private ArrayList<GamePlayer> playerList = new ArrayList<GamePlayer>();
	private CardPile pile = new CardPile();
	private GamePlayer currentPlayer;
	private boolean clockwise;

	/**
	 * 
	 */
	public UNOGame() {
		// TODO Auto-generated constructor stub
		// Wait to complete

	}

	/**
	 * 
	 * @param playerList
	 *            involves all players who join the card game
	 * @param firstPlayer
	 *            to play card
	 */
	public UNOGame(ArrayList<GamePlayer> playerList, GamePlayer firstPlayer) {
		// TODO Auto-generated constructor stub
		// Wait to complete

	}

	/**
	 * 
	 * This constructor automatically {@link #launch()} the game
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
	public UNOGame(ArrayList<GamePlayer> playerList, CardPile pile, GamePlayer currentPlayer, boolean direction) {
		// TODO Auto-generated constructor stub
		this.setPlayerList(playerList);
		this.setPile(pile);
		this.setCurrentPlayer(currentPlayer);
		this.setClockwise(direction);
		stage = GameStage.ONGOING;
		// Wait to complete

	}

	public boolean launch() {
		boolean successful = false;

		return successful;
	}

	/**
	 * @return the game stage
	 */
	public GameStage getStage() {
		return stage;
	}

	/**
	 * @return the playerList
	 */
	public ArrayList<GamePlayer> getPlayerList() {
		return playerList;
	}

	/**
	 * @param playerList
	 *            the playerList to set
	 */
	public void setPlayerList(ArrayList<GamePlayer> playerList) {
		if (playerList.size() != 0) {
			this.playerList = playerList;
		}

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
		return currentPlayer;
	}

	/**
	 * @param currentPlayer
	 *            the currentPlayer to set
	 * @return false if there isn't such a player in the player list
	 */
	public boolean setCurrentPlayer(GamePlayer currentPlayer) {
		if (playerList.contains(currentPlayer)) {
			this.currentPlayer = currentPlayer;
			return true;
		} else
			return false;

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

}
