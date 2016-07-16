/**
 * 
 */
package net.mcplugin.unolib.game.gametable;

import java.util.ArrayList;

/**
 * A class to represent players involved in the card game.
 * 
 * @author Henry Hu
 *
 */
public class GamePlayer {
	private final String name;
	private CardPile pile = new CardPile();
	private UNOGame game = null;

	/**
	 * @param name
	 *            of the player
	 */
	public GamePlayer(String name) {
		// TODO Auto-generated constructor stub
		this.name = name;
	}

	/**
	 * @param name
	 *            of the player
	 * @param pile
	 *            of the player's cards
	 */
	public GamePlayer(String name, CardPile pile) {
		// TODO Auto-generated constructor stub
		this.name = name;
		this.pile = pile;
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
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the game
	 */
	public UNOGame getGame() {
		return game;
	}

	/**
	 * @param game
	 *            the game to set
	 */
	public void setGame(UNOGame game) {
		this.game = game;
	}

	/**
	 * 
	 * @param playerList
	 *            to be added to
	 */
	public void addTo(ArrayList<GamePlayer> playerList) {
		playerList.add(this);
	}

}
