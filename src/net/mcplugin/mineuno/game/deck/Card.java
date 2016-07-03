/**
 * 
 */
package net.mcplugin.mineuno.game.deck;

/**
 * @author Henry Hu
 *
 */
public abstract class Card {
	protected final int point;

	/**
	 * @param point
	 */
	public Card(int point) {
		this.point = point;
	}

	/**
	 * @return the point
	 */
	public int getPoint() {
		return point;
	}
}
