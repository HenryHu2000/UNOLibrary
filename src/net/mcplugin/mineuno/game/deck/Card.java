/**
 * 
 */
package net.mcplugin.mineuno.game.deck;

/**
 * @author Henry Hu
 *
 */
public abstract class Card {
	protected final Color color;
	protected final int point;

	/**
	 * @param color
	 * @param point
	 */
	public Card(Color color, int point) {
		this.color = color;
		this.point = point;
	}

	/**
	 * @return the color
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * @return the point
	 */
	public int getPoint() {
		return point;
	}
}
