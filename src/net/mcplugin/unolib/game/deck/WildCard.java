/**
 * 
 */
package net.mcplugin.unolib.game.deck;

/**
 * Player declares next color to be matched. If draw four is enabled,
 * next player in sequence draws four cards and loses a turn. May be legally
 * played only if the player has no cards of the current color, not
 * counting wild cards; cards in a declaredColor different from the current
 * color do not count even if they have the with the same number or
 * symbol.
 * 
 * @author Henry Hu
 *
 */
/**
 * @author Henry Hu
 *
 */
public class WildCard extends AbstractCard {
	private final boolean drawFour;
	// A wild card does't really have a color, but you need to declare one for
	// the next player to play card.
	private Color declaredColor = null;

	/**
	 * @param drawFour
	 *            whether the wild card has the effect to make the next player
	 *            draw four cards
	 */
	public WildCard(boolean drawFour) {
		super(50);
		this.drawFour = drawFour;
	}

	/**
	 * @param color
	 *            to declare
	 */
	public void declareColor(Color color) {
		this.declaredColor = color;
	}

	/**
	 * @return the next declaredColor player declares to be matched
	 */
	public Color getDeclaredColor() {
		return declaredColor;
	}

	/**
	 * @return
	 */
	public boolean isDeclared() {
		return (this.getDeclaredColor() != null);
	}

	/**
	 * @return true if the wild card has the effect to make the next player draw
	 *         four cards
	 */
	public boolean isDrawFour() {
		return drawFour;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean matches(AbstractCard card) {

		// Wild cards can match any other cards.
		return true;
	}

	@Override
	public String toString() {
		if (!isDrawFour())
			return "WILD";
		else
			return "WILD DRAW_FOUR";
	}

}
