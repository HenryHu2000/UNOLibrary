/**
 * 
 */
package net.mcplugin.unolib.game.gametable;

import java.util.ArrayList;

import net.mcplugin.unolib.game.deck.AbstractCard;
import net.mcplugin.unolib.game.deck.Color;
import net.mcplugin.unolib.game.deck.ColorCard;
import net.mcplugin.unolib.game.deck.WildCard;

/**
 * A class to represent players involved in the card game.
 * 
 * @author Henry Hu
 *
 */
public class GamePlayer {
	private final String name;
	private CardPile hand = new CardPile();
	private AbstractCard restrictedCard = null;

	/**
	 * @param name
	 *            of the player
	 */
	public GamePlayer(String name) {
		// TODO Auto-generated constructor stub
		this.name = name;
	}

	/**
	 * Create a player with a given set of cards in hand
	 * 
	 * @param name
	 *            of the player
	 * @param hand
	 *            of the player's cards
	 */
	public GamePlayer(String name, CardPile pile) {
		// TODO Auto-generated constructor stub
		this.name = name;
		this.hand = pile;
	}

	/**
	 * 
	 * @param card
	 *            to discard
	 * @return true if the card is successfully discarded
	 */
	public boolean discard(AbstractCard card, CardPile discardPile) {
		if (this.remove(card)) {
			discardPile.push(card);
			return true;
		}
		return false;
	}

	/**
	 * @param card
	 * @return
	 */
	public void add(AbstractCard card) {
		hand.add(card);
	}

	/**
	 * Draw a card from the given card pile.
	 * 
	 * @param pile
	 *            to draw card from
	 */
	public AbstractCard draw(CardPile pile) {
		AbstractCard card = pile.pop();
		this.add(card);
		return card;
	}

	/**
	 * Draw a number of cards from the given card pile.
	 * 
	 * @param pile
	 *            to draw card from
	 * @param number
	 *            of cards
	 */
	public void draw(CardPile pile, int number) {
		for (int i = 0; i < number; i++) {
			AbstractCard card = pile.pop();
			this.add(card);
		}
	}

	/**
	 * 
	 * @return the names of all cards in hand
	 */
	public String getCardNames() {
		return hand.toString();
	}

	/**
	 * @return number of cards
	 */
	public int getCardsNumber() {
		return hand.size();
	}

	/**
	 * @return the cards in hand
	 */
	public CardPile getHand() {
		return hand;
	}

	/**
	 * Find a set of cards in player's hand that can match a given card
	 * 
	 * @param card
	 *            to be matched
	 * @return the set of cards in player's hand that can match the given card
	 */
	public CardPile getMatchesCards(AbstractCard card) {
		CardPile matchesCards = new CardPile();
		for (AbstractCard eachCard : hand) {
			if (eachCard.matches(card)) {
				matchesCards.add(eachCard);
			}
		}
		return matchesCards;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the restrictedCard
	 */
	public AbstractCard getRestrictedCard() {
		return restrictedCard;
	}

	/**
	 * 
	 * @param playerList
	 *            to be added to
	 */
	public boolean joinGame(ArrayList<GamePlayer> playerList) {
		if (!playerList.contains(this)) {
			playerList.add(this);
			return true;
		}
		return false;
	}

	/**
	 * Play the card given.
	 * 
	 * @param card
	 *            to be played
	 * @return
	 */
	public ProceedResponse playCard(UNOGame game, ColorCard card) {
		if (game.getCurrentPlayer() == this) {
			if (hand.contains(card)) {
				return game.proceed(card);
			}
			return ProceedResponse.NOT_EXIST;
		}
		return ProceedResponse.WRONG_PLAYER;
	}

	/**
	 * @param game
	 * @param card
	 * @param declaredColor
	 * @return
	 */
	public ProceedResponse playCard(UNOGame game, WildCard card, Color declaredColor) {
		if (game.getCurrentPlayer() == this) {
			if (hand.contains(card)) {
				return game.proceed(card, declaredColor);
			}
			return ProceedResponse.NOT_EXIST;
		}
		return ProceedResponse.WRONG_PLAYER;
	}

	public ProceedResponse playCard(UNOGame game) {
		if (game.getCurrentPlayer() == this) {
			return game.proceed();
		}
		return ProceedResponse.WRONG_PLAYER;
	}

	/**
	 * Remove a card from player's hand.
	 * 
	 * @param card
	 *            to be removed from player's hand
	 */
	public boolean remove(AbstractCard card) {
		if (hand.contains(card)) {
			hand.remove(card);

			return true;
		}
		return false;
	}

	/**
	 * @param hand
	 *            the cards in hand to set
	 */
	public void setHand(CardPile pile) {
		this.hand = pile;
	}

	/**
	 * @param restrictedCard
	 *            the restrictedCard to set
	 */
	public void setRestrictedCard(AbstractCard drewCard) {
		this.restrictedCard = drewCard;
	}
}
