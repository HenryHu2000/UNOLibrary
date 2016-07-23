/**
 * 
 */
package net.mcplugin.unolib.control;

import net.mcplugin.unolib.game.deck.AbstractCard;
import net.mcplugin.unolib.game.deck.ActionCard;
import net.mcplugin.unolib.game.deck.Color;
import net.mcplugin.unolib.game.gametable.GamePlayer;
import net.mcplugin.unolib.game.gametable.UNOGame;

/**
 * @author Henry Hu
 *
 */
public interface GameHandler {
	/**
	 * This method is called in the beginning of the game.
	 * 
	 * @param game
	 *            TODO
	 * @param firstPlayer
	 * @param firstCard
	 */
	public void onStart(UNOGame game, GamePlayer firstPlayer, AbstractCard firstCard);

	/**
	 * This method is called when a player's turn starts.
	 * 
	 * @param player
	 * @return the card to play in this turn, null for drawing a card
	 */
	public AbstractCard onTurn(GamePlayer player, AbstractCard currentCard);

	public AbstractCard onMismatch(GamePlayer player, AbstractCard currentCard);

	public AbstractCard onCardNotExist(GamePlayer player, AbstractCard currentCard);

	public AbstractCard onUndeclaredColor(GamePlayer player, AbstractCard currentCard);

	public Color onChooseColor(GamePlayer player);

	/**
	 * This method is called when a player successfully plays a card.
	 * 
	 * @param player
	 * @param afterDrawn
	 *            tells if the event of playing a card is after he drew a card
	 */
	public void onPlay(GamePlayer player, boolean afterDrawn, AbstractCard playedCard);

	public boolean onDrawInTurn(GamePlayer player, AbstractCard cardDrawn);

	public void onSkipAfterDrawn(GamePlayer player, AbstractCard currentCard);

	public void onCardChange(GamePlayer player, int change, int totalCards);

	public void onDraw(GamePlayer player, int cardsNumber);

	public void onAction(ActionCard card, GamePlayer invoker);

	public void onEnd(GamePlayer winner);

}
