/**
 * 
 */
package net.mcplugin.unolib.game.deck;

/**
 * Number cards are simple cards with colors and points, but no further effects
 * to the game.
 * 
 * @author Henry Hu
 *
 */
public class NumberCard extends ColorCard {

	/**
	 * @param color
	 *            of the card
	 * @param point
	 *            of the card
	 */
	public NumberCard(Color color, int point) {
		super(color, point);
		// TODO Auto-generated constructor stub
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean matches(AbstractCard card) {
		if (card instanceof ColorCard) {
			ColorCard coloredCard = (ColorCard) card;
			if (color == coloredCard.getColor()) {
				// This card can be played after the given colored card, because
				// they have the same color.

				return true;
			} else if (coloredCard instanceof NumberCard) {
				NumberCard numCard = (NumberCard) coloredCard;
				// This card can be played after the given number card, because
				// they have the same number.
				if (this.point == numCard.getPoint())
					return true;
			}
		} else if (card instanceof WildCard) {
			WildCard wild = (WildCard) card;
			if (color == wild.getDeclaredColor()) {
				// This card can be played after the given wild card, because
				// they have the same color.
				return true;
			}
		}
		return false;
	}

	@Override
	public String toString() {
		return (color.toString() + " " + point);
	}
}
