/**
 * 
 */
package net.mcplugin.unolib.control;

import net.mcplugin.unolib.game.deck.AbstractCard;

/**
 * @author Henry Hu
 *
 */
public interface GameHandler {
	public void onStart();

	public AbstractCard onTurn();
}
