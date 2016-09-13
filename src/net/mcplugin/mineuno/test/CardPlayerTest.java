package net.mcplugin.mineuno.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import net.mcplugin.unolib.game.deck.AbstractCard;
import net.mcplugin.unolib.game.deck.Color;
import net.mcplugin.unolib.game.deck.ColorCard;
import net.mcplugin.unolib.game.deck.NumberCard;
import net.mcplugin.unolib.game.gametable.CardPile;
import net.mcplugin.unolib.game.gametable.CardPlayer;
import net.mcplugin.unolib.game.gametable.UNOGame;

public class CardPlayerTest {
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	CardPlayer player;

	@Before
	public void setUp() throws Exception {
		player = new CardPlayer("Player");
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testAdd() {
		AbstractCard card = new NumberCard(Color.GREEN, 0);
		int cardNumBefore = player.getCardsNumber();
		player.add(card);
		int cardNumAfter = player.getCardsNumber();
		assertEquals("Correct card number", cardNumBefore + 1, cardNumAfter);
		assertTrue(player.has(card));
	}

	@Test
	public void testCardPlayerString() {
		String name = "Player";
		player = new CardPlayer(name);
		assertEquals("Correct player name", name, player.getName());
	}

	@Test
	public void testCardPlayerStringCardPile() {
		String name = "Player";
		CardPile hand = new CardPile();
		player = new CardPlayer(name, hand);
		assertEquals("Correct player name", name, player.getName());
		assertEquals("Correct cards in hand", hand, player.getHand());
	}

	@Test
	public void testDiscard() {
		AbstractCard card = new NumberCard(Color.GREEN, 0);
		CardPile pile = new CardPile();
		player.add(card);

		int cardNumBefore = player.getCardsNumber();
		player.discard(card, pile);
		int cardNumAfter = player.getCardsNumber();
		assertEquals("Correct card number", cardNumBefore - 1, cardNumAfter);
		assertTrue(pile.contains(card));

	}

	@Test
	public void testDrawCardPile() {
		AbstractCard card = new NumberCard(Color.GREEN, 0);
		CardPile pile = new CardPile();
		pile.initialize();
		pile.push(card);
		int cardNumBefore = player.getCardsNumber();
		int pileSizeBefore = pile.size();
		AbstractCard cardDrawn = player.draw(pile);
		int cardNumAfter = player.getCardsNumber();
		int pileSizeAfter = pile.size();
		assertEquals("Correct cards number in hand", cardNumBefore + 1, cardNumAfter);
		assertEquals("Correct cards number in pile", pileSizeBefore - 1, pileSizeAfter);
		assertEquals("Correct card drawn", card, cardDrawn);
		assertTrue(player.has(card));

	}

	@Test
	public void testDrawCardPileInt() {
		CardPile pile = new CardPile();
		pile.initialize();
		int cardNumBefore = player.getCardsNumber();
		int pileSizeBefore = pile.size();
		CardPile cardsDrawn = player.draw(pile, 4);
		int cardNumAfter = player.getCardsNumber();
		int pileSizeAfter = pile.size();
		assertEquals("Correct cards number in hand", cardNumBefore + 4, cardNumAfter);
		assertEquals("Correct cards number in pile", pileSizeBefore - 4, pileSizeAfter);
		for (AbstractCard card : cardsDrawn) {
			assertTrue(player.has(card));
		}
	}

	@Test
	public void testGetMatchesCards() {
		AbstractCard card = new NumberCard(Color.GREEN, 0);
		player.getHand().initialize();
		for (AbstractCard matchedCard : player.getMatchesCards(card)) {
			if (matchedCard instanceof ColorCard)
				if (((ColorCard) matchedCard).getColor() != Color.GREEN && card.getPoint() != 0)
					fail("Not matched");
		}

	}

	@Test
	public void testJoinGame() {
		ArrayList<CardPlayer> playerList = new ArrayList<CardPlayer>();
		int playerNumBefore = playerList.size();
		boolean successful = player.joinGame(playerList);
		int playerNumAfter = playerList.size();
		assertTrue("Player in the list", playerList.contains(player));
		if (successful)
			assertEquals("Player number", playerNumBefore + 1, playerNumAfter);
		playerList.add(new CardPlayer("1"));
		UNOGame game = new UNOGame(playerList);
		assertTrue("Player in the game", game.getPlayerList().contains(player));
	}

	@Test
	public void testPlayCardUNOGame() {
		fail("Not yet implemented");
	}

	@Test
	public void testPlayCardUNOGameColorCard() {
		fail("Not yet implemented");
	}

	@Test
	public void testPlayCardUNOGameWildCardColor() {
		fail("Not yet implemented");
	}

	@Test
	public void testRemove() {
		AbstractCard card = new NumberCard(Color.GREEN, 0);
		player.add(card);
		int cardNumBefore = player.getCardsNumber();
		player.remove(card);
		int cardNumAfter = player.getCardsNumber();
		assertEquals("Correct card number", cardNumBefore - 1, cardNumAfter);

	}

	@Test
	public void testToString() {
		assertEquals("Player's name", player.getName(), player.toString());

	}

}
