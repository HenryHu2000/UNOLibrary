/**
 * 
 */
package net.mcplugin.mineuno.game.deck;

/**
 * @author Henry Hu
 *
 */
public class NumberCard extends Card implements Colorable {
	private final Color color;

	/**
	 * @param color
	 * @param point
	 */
	public NumberCard(Color color, int point) {
		super(point);
		// TODO Auto-generated constructor stub
		this.color = color;
	}

	/**
	 * @return the color
	 */
	public Color getColor() {
		return color;
	}

}
