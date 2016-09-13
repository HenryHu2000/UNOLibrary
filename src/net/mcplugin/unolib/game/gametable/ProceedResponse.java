/**
 * 
 */
package net.mcplugin.unolib.game.gametable;

/**
 * @author Henry Hu
 *
 */
public enum ProceedResponse {
	PLAYED, NOT_EXIST, MISMATCHED, BEYOND_RESTRICTION, /**
														 * The player choose to
														 * draw a card instead
														 * of playing one.
														 */
	DREW, /**
			 * The player's turn is skipped because the card he drew didn't
			 * match the previous card.
			 */
	SKIPPED, COLOR_UNSPECIFIED, WRONG_PLAYER, ENDED, UNKNOWN;
}
