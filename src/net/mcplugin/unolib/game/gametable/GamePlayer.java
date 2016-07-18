/**
 * 
 */
package net.mcplugin.unolib.game.gametable;

import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

import net.mcplugin.unolib.game.deck.AbstractCard;
import net.mcplugin.unolib.game.deck.Colorable;

/**
 * A class to represent players involved in the card game.
 * 
 * @author Henry Hu
 *
 */
public class GamePlayer {
	private final String name;
	private TreeSet<AbstractCard> hand = new TreeSet<AbstractCard>();
	private UNOGame game = null;

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
	public GamePlayer(String name, TreeSet<AbstractCard> pile) {
		// TODO Auto-generated constructor stub
		this.name = name;
		this.hand = pile;
	}

	/**
	 * Add a card to player's hand.
	 * 
	 * @param card
	 *            to be added to player's hand
	 */
	public void addCard(AbstractCard card) {
		hand.add(card);
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
	 * Draw a card from the given card pile.
	 * 
	 * @param pile
	 *            to draw card from
	 */
	public void draw(CardPile pile) {
		this.addCard(pile.pop());
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
			this.addCard(pile.pop());
		}
	}

	/**
	 * 
	 * @return the names of all cards in hand
	 */
	public Set<String> getCardNames() {
		TreeSet<String> output = new TreeSet<String>();
		for (AbstractCard card : hand) {
			output.add(card.toString());
		}

		return output;

	}

	/**
	 * @return the game
	 */
	public UNOGame getGame() {
		return game;
	}

	/**
	 * @return the cards in hand
	 */
	public TreeSet<AbstractCard> getHand() {
		return hand;
	}

	/**
	 * Find a set of cards in player's hand that can match a given card
	 * 
	 * @param card
	 *            to be matched
	 * @return the set of cards in player's hand that can match the given card
	 */
	public TreeSet<AbstractCard> getMatchesCards(AbstractCard card) {
		TreeSet<AbstractCard> matchesCards = new TreeSet<AbstractCard>();
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
	 * 
	 * @param playerList
	 *            to be added to
	 */
	public void joinGame(ArrayList<GamePlayer> playerList) {
		playerList.add(this);
	}

	/**
	 * Play the card given.
	 * 
	 * @param card
	 *            to be played
	 * @return true if the card is successfully played
	 */
	public boolean playCard(Colorable card) {
		if (game.getCurrentPlayer() == this && hand.contains(card)) {
			return game.makePlayCard(card);
		}
		return false;
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
	 * @param game
	 *            the game to set
	 */
	public void setGame(UNOGame game) {
		this.game = game;
	}

	/**
	 * @param hand
	 *            the cards in hand to set
	 */
	public void setHand(TreeSet<AbstractCard> pile) {
		this.hand = pile;
	}
}
