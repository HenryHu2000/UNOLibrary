/**
 * 
 */
package net.mcplugin.unolib.game.deck;

/**
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
	public boolean matches(AbstractCard card) {
		if (card instanceof ColorCard) {
			ColorCard coloredCard = (ColorCard) card;
			if (color == coloredCard.getColor()) {
				return true;
			} else if (coloredCard instanceof NumberCard) {
				NumberCard numCard = (NumberCard) coloredCard;
				if (this.point == numCard.getPoint())
					return true;
			}
		} else if (card instanceof WildCard) {
			WildCard wild = (WildCard) card;
			if (color == wild.getDeclaredColor()) {
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
