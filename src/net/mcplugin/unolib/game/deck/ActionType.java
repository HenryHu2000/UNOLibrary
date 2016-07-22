/**
 * 
 */
package net.mcplugin.unolib.game.deck;

/**
 * Three types of actions for action cards.
 * 
 * @author Henry Hu
 *
 */
public enum ActionType {

	/**
	 * Skip the next player.
	 */
	SKIP, /**
			 * Let the next player draw two cards, and then skip him.
			 */
	DRAW_TWO, /**
				 * Reverse the direction of playing card before moving to the
				 * next player.
				 */
	REVERSE;

}
