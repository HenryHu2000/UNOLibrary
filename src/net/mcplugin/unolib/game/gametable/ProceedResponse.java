/**
 * 
 */
package net.mcplugin.unolib.game.gametable;

/**
 * @author Henry Hu
 *
 */
public enum ProceedResponse {
	PLAYED, /**
			 * The player played the card he drew in the turn.
			 */
	PLAYED_DRAWN, NOT_EXIST, MISMATCHED, BEYOND_RESTRICTION, /**
																 * The player
																 * choose to
																 * draw a card
																 * instead of
																 * playing one.
																 */
	DREW, /**
			 * The player's turn is skipped because the card he drew didn't
			 * match the previous card.
			 */
	SKIPPED, WRONG_PLAYER, ENDED, UNKNOWN;
}
