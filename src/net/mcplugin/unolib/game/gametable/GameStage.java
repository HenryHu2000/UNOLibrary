/**
 * 
 */
package net.mcplugin.unolib.game.gametable;

/**
 * @author Henry Hu
 *
 */
public enum GameStage {

	/**
	 * In suspend stage, the game is waiting the current player to play a card
	 * in his hand, which matches the previous card.
	 */
	SUSPEND, /**
				 * If in a player's turn, he choose to draw a card instead of
				 * playing a card, he must play the drawn card if it matches the
				 * previous card, but not other cards. The restricted card is
				 * the only card the player can play, in the restrictive suspend
				 * stage.
				 */
	RESTRICTIVE_SUSPEND, /**
							 * If anyone of the players has no cards, the game
							 * moves to the end, and it can't proceed anymore.
							 */
	END;
}
