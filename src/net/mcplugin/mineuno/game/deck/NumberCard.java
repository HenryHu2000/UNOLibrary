/**
 * 
 */
package net.mcplugin.mineuno.game.deck;

/**
 * @author Henry Hu
 *
 */
public class NumberCard extends AbstractCard implements Colorable {
	private final Color color;

	/**
	 * @param color
	 *            of the card
	 * @param point
	 *            of the card
	 */
	public NumberCard(Color color, int point) {
		super(point);
		// TODO Auto-generated constructor stub
		this.color = color;
	}

	/**
	 * {@inheritDoc}
	 */
	public Color getColor() {
		return color;
	}

	@Override
	public String toString() {
		return (color.toString() + point);
	}
}
