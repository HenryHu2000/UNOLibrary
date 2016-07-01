/**
 * 
 */
package net.mcplugin.mineuno.game.deck;

/**
 * @author Henry Hu
 *
 */
public class ActionCard extends Card {
	private final ActionType action;

	/**
	 * @param color
	 * @param point
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

}
