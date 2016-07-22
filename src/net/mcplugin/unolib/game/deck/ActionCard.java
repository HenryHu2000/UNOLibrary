/**
 * 
 */
package net.mcplugin.unolib.game.deck;

/**
 * Action card is a type of cards that can invoke an action.
 * 
 * @author Henry Hu
 *
 */
public class ActionCard extends ColorCard {
	private final ActionType action;

	/**
	 * @param color
	 *            of the card
	 * @param action
	 *            of the card
	 */
	public ActionCard(Color color, ActionType action) {
		super(color, 20);
		// TODO Auto-generated constructor stub
		this.action = action;
	}

	/**
	 * @return the action of the card
	 */
	public ActionType getAction() {
		return action;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean matches(AbstractCard card) {
		if (card instanceof ColorCard) {
			ColorCard coloredCard = (ColorCard) card;
			if (color == coloredCard.getColor()) {
				return true;
			} else if (coloredCard instanceof ActionCard) {
				ActionCard actionCard = (ActionCard) coloredCard;
				if (this.getAction() == actionCard.getAction())
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
		return (color.toString() + " " + action.toString());
	}
}
