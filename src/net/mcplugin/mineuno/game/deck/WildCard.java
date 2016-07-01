/**
 * 
 */
package net.mcplugin.mineuno.game.deck;

/**
 * @author Henry Hu
 *
 */
public class WildCard extends Card {
	private final boolean drawfour;

	/**
	 * @param point
	 */
	public WildCard(boolean drawfour) {
		super(Color.BLACK, 50);
		this.drawfour = drawfour;
	}

	/**
	 * @return the draw four
	 */
	public boolean isDrawfour() {
		return drawfour;
	}
}
