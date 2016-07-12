/**
 * 
 */
package net.mcplugin.mineuno.game.deck;

/**
 * a type of cards that can invoke an action
 * 
 * @author Henry Hu
 *
 */
public class ActionCard extends Card implements Colorable {
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
	 * @return the color
	 */
	public Color getColor() {
		return color;
	}

}
