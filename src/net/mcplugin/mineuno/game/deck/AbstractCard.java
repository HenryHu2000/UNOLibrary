/**
 * 
 */
package net.mcplugin.mineuno.game.deck;

/**
 * UNO cards involved in game.
 * 
 * @author Henry Hu
 *
 */
public abstract class AbstractCard {
	/**
	 * The point of the UNO card.
	 */
	protected final int point;

	/**
	 * @param point
	 *            of the UNO card
	 */
	public AbstractCard(int point) {
		this.point = point;
	}

	/**
	 * @return the point
	 */
	public int getPoint() {
		return point;
	}
}
