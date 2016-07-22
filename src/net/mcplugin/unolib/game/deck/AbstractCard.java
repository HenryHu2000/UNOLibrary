/**
 * 
 */
package net.mcplugin.unolib.game.deck;

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

	/**
	 * Check if this colored card can be drawn after the given card
	 * 
	 * @param card
	 *            with color to match with
	 * @return true if this card can be drawn after the given card
	 */
	public abstract boolean matches(AbstractCard card);

}
