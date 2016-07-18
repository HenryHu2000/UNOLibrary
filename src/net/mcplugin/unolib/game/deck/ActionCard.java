/**
 * 
 */
package net.mcplugin.unolib.game.deck;

/**
 * a type of cards that can invoke an action
 * 
 * @author Henry Hu
 *
 */
public class ActionCard extends AbstractCard implements Colorable {
	private final Color color;
	private final ActionType action;

	/**
	 * @param color
	 *            of the card
	 * @param action
	 *            of the card
	 */
	public ActionCard(Color color, ActionType action) {
		super(20);
		// TODO Auto-generated constructor stub
		this.color = color;
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
			} else if (coloredCard instanceof ActionCard) {
				ActionCard actionCard = (ActionCard) coloredCard;
				if (this.getAction() == actionCard.getAction())
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
		return (color.toString() + " " + action.toString());
	}
}
