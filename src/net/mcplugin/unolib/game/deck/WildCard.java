/**
 * 
 */
package net.mcplugin.unolib.game.deck;

/**
 * Player declares next declaredColor to be matched. If draw four is enabled,
 * next player in sequence draws four cards and loses a turn. May be legally
 * played only if the player has no cards of the current declaredColor, not
 * counting wild cards; cards in a declaredColor different from the current
 * declaredColor do not count even if they have the with the same number or
 * symbol.
 * 
 * @author Henry Hu
 *
 */
public class WildCard extends AbstractCard {
	private final boolean drawFour;
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
	 * @return the next declaredColor player declares to be matched
	 */
	public Color getDeclaredColor() {
		return declaredColor;
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
	public boolean matches(AbstractCard card) {

		// Wild cards can match any other cards.
		return true;
	}

	/**
	 * @param declaredColor
	 *            player declares to be matched
	 */
	public void setDeclaredColor(Color color) {
		this.declaredColor = color;
	}

	@Override
	public String toString() {
		if (!isDrawFour())
			return "WILD";
		else
			return "WILD DRAWFOUR";
	}

}
