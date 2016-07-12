/**
 * 
 */
package net.mcplugin.mineuno.game.gametable;

import java.util.Stack;

import net.mcplugin.mineuno.game.deck.*;

/**
 * @author Henry Hu
 *
 */
public class CardPile extends Stack<AbstractCard> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7412631325157467969L;

	public CardPile() {
		// Wait to complete
	}

	public CardPile(Stack<AbstractCard> stack) {
		this.addAll(stack);
	}

	/**
	 * Restore the card pile to standard order of UNO
	 */
	public void initialize() {
		clear();
		for (Color color : Color.values()) {
			// Add card number zero
			push(new NumberCard(color, 0));

			for (int i = 0; i < 2; i++) {
				// Add other number cards
				for (int j = 1; j < 10; j++) {
					push(new NumberCard(color, j));
				}
				// Add action cards
				for (ActionType action : ActionType.values()) {
					push(new ActionCard(color, action));
				}

			}
			// Add wild cards
			push(new WildCard(false));
			push(new WildCard(true));
		}

	}

}
