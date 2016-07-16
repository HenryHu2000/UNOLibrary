/**
 * 
 */
package net.mcplugin.unolib.game.deck;

/**
 * Player declares next color to be matched. If draw four is enabled, next
 * player in sequence draws four cards and loses a turn. May be legally played
 * only if the player has no cards of the current color, not counting wild
 * cards; cards in a color different from the current color do not count even if
 * they have the with the same number or symbol.
 * 
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

	/**
	 * {@inheritDoc}
	 */
	public boolean matches(Colorable card) {

		// Wild cards can match any other cards.
		return true;
	}

	@Override
	public String toString() {
		if (!isDrawfour())
			return "WILD";
		else
			return "WILD DRAWFOUR";
	}
}
