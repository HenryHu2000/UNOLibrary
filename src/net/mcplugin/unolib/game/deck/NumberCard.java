/**
 * 
 */
package net.mcplugin.unolib.game.deck;

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

	/**
	 * {@inheritDoc}
	 */
	public boolean matches(AbstractCard card) {
		if (card instanceof Colorable) {
			Colorable coloredCard = (Colorable) card;
			if (this.color == coloredCard.getColor()) {
				return true;
			} else if (coloredCard instanceof NumberCard) {
				NumberCard numCard = (NumberCard) coloredCard;
				if (this.point == numCard.getPoint())
					return true;
			}
		} else if (card instanceof WildCard) {
			WildCard wild = (WildCard) card;
			if (this.color == wild.getDeclaredColor()) {
				return true;
			}
		}
		// more codes needed
		return false;
	}

	@Override
	public String toString() {
		return (color.toString() + " " + point);
	}
}
