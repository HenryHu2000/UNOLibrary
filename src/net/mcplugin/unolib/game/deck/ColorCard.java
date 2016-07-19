/**
 * 
 */
package net.mcplugin.unolib.game.deck;

/**
 * Any class which implements this interface has a color
 * 
 * @author Henry Hu
 *
 */
public abstract class ColorCard extends AbstractCard {
	protected final Color color;

	public ColorCard(Color color, int point) {
		super(point);
		this.color = color;
		// TODO Auto-generated constructor stub
	}

	/**
	 * Get the color of the colored card
	 * 
	 * @return the color
	 */
	public Color getColor() {
		return color;
	}
}
