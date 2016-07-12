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
public class CardPile {
	private Stack<Card> stack = new Stack<Card>();

	/**
	 * @return the stack to store the cards in the card pile
	 */
	public Stack<Card> getStack() {
		return stack;
	}

	/**
	 * @param stack
	 *            to store the cards in the card pile
	 */
	public void setStack(Stack<Card> stack) {
		this.stack = stack;
	}

	public CardPile() {
		// Wait for completing
	}

	public CardPile(Stack<Card> stack) {
		setStack(stack);
	}

	/**
	 * Restore the card pile to standard order of UNO
	 */
	public void initialize() {
		stack.clear();
		for (Color color : Color.values()) {
			// Add card number zero
			stack.push(new NumberCard(color, 0));

			for (int i = 0; i < 2; i++) {
				// Add other number cards
				for (int j = 1; j < 10; j++) {
					stack.push(new NumberCard(color, j));
				}
				// Add action cards
				for (ActionType action : ActionType.values()) {
					stack.push(new ActionCard(color, action));
				}

			}
			// Add wild cards
			stack.push(new WildCard(false));
			stack.push(new WildCard(true));
		}

	}

}
