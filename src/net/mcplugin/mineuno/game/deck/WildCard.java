/**
 * 
 */
package net.mcplugin.mineuno.game.deck;

/**
 * @author Henry Hu
 *
 */
public class WildCard extends AbstractCard {
	private final boolean drawfour;

	/**
	 * @param drawfour
	 *            whether the wild card has the effect to make the next player
	 *            draw four cards
	 */
	public WildCard(boolean drawfour) {
		super(50);
		this.drawfour = drawfour;
	}

	/**
	 * @return true if the wild card has the effect to make the next player draw
	 *         four cards
	 */
	public boolean isDrawfour() {
		return drawfour;
	}
}
