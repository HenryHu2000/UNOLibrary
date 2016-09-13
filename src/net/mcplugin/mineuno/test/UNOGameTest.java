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
import net.mcplugin.unolib.game.deck.ActionType;
import net.mcplugin.unolib.game.deck.Color;
import net.mcplugin.unolib.game.deck.WildCard;
import net.mcplugin.unolib.game.gametable.CardPlayer;
import net.mcplugin.unolib.game.gametable.UNOGame;

public class UNOGameTest {
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	private UNOGame game;

	private int playersNumber;

	private int totalCardsNumber;

	@Before
	public void setUp() throws Exception {
		playersNumber = 4;
		ArrayList<CardPlayer> playerList = new ArrayList<CardPlayer>();
		for (int i = 0; i < playersNumber; i++) {
			playerList.add(new CardPlayer(Integer.toString(i)));
		}

		game = new UNOGame(playerList);
		totalCardsNumber = game.getPile().size() + game.getDiscardPile().size();
		for (CardPlayer player : game.getPlayerList()) {
			totalCardsNumber += player.getCardsNumber();
		}
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testForceProceed() {
		for (int i = 0; i < playersNumber; i++) {
			game.setCurrentPlayerID(i);
			CardPlayer nextPlayer = game.getPlayer(game.getNextPlayerID(game.getCurrentPlayerID()));
			AbstractCard card = game.getCurrentPlayer().getHand().get(0);
			game.forceProceed(card, true);
			assertEquals("Correct next player", nextPlayer, game.getCurrentPlayer());
			assertEquals("Equal cards", card, game.getCurrentCard());
			game.forceProceed(card, false);
			assertEquals("Correct player", nextPlayer, game.getCurrentPlayer());

		}

	}

	@Test
	public void testGetNextPlayerNumber() {
		for (int i = 0; i < playersNumber; i++) {
			assertTrue("Clockwise", game.isClockwise());
			int playerID = game.getNextPlayerID(i);
			game.setClockwise(!game.isClockwise());
			assertEquals("Current player ID after reversing", i, game.getNextPlayerID(playerID));
			game.setClockwise(true);

		}
	}

	@Test
	public void testGotoNextPlayer() {
		for (int i = 0; i < playersNumber; i++) {
			game.setCurrentPlayerID(i);
			CardPlayer player = game.getCurrentPlayer();
			game.gotoNextPlayer();
			game.gotoNextPlayer();
			game.setClockwise(!game.isClockwise());
			game.gotoNextPlayer();
			game.gotoNextPlayer();
			assertEquals("Current player after reversing", player, game.getCurrentPlayer());
		}
	}

	@Test
	public void testPerformAction() {
		int currentID = game.getCurrentPlayerID();
		game.performAction(ActionType.SKIP);
		assertEquals("Correct after next player", game.getNextPlayerID(game.getNextPlayerID(currentID)),
				game.getCurrentPlayerID());
		currentID = game.getCurrentPlayerID();
		int initialCardsNum = game.getCurrentPlayer().getCardsNumber();
		game.performAction(ActionType.DRAW_TWO);
		assertEquals("Correct after next player", game.getNextPlayerID(game.getNextPlayerID(currentID)),
				game.getCurrentPlayerID());
		game.setClockwise(!game.isClockwise());
		assertEquals(initialCardsNum + 2,
				game.getPlayer(game.getNextPlayerID(game.getCurrentPlayerID())).getCardsNumber());

		totalCardsNumber = game.getPile().size() + game.getDiscardPile().size();
		for (CardPlayer player : game.getPlayerList()) {
			totalCardsNumber += player.getCardsNumber();
		}
		assertEquals("Currect total card number in game", 108, totalCardsNumber);

	}

	@Test
	public void testProceed() {
		fail("Not yet implemented");
	}

	@Test
	public void testProceedColorCard() {
		fail("Not yet implemented");
	}

	@Test
	public void testProceedWildCardColor() {
		fail("Not yet implemented");
	}

	@Test
	public void testRemoveRepeatPlayer() {
		ArrayList<CardPlayer> playerList = new ArrayList<CardPlayer>();
		CardPlayer firstPlayer = new CardPlayer("1");
		CardPlayer secondPlayer = new CardPlayer("2");
		for (int i = 0; i < playersNumber; i++) {
			playerList.add(firstPlayer);
			playerList.add(secondPlayer);
		}
		game = new UNOGame(playerList);
		game.removeRepeatPlayer();
		assertEquals("Correct first player", firstPlayer, game.getPlayerList().get(0));
		assertEquals("Correct second player", secondPlayer, game.getPlayerList().get(1));
		assertEquals("Correct players number", 2, game.getPlayersNumber());

	}

	@Test
	public void testStart() {
		fail("Not yet implemented");
	}

	@Test
	public void testUNOGame() {
		System.out.println("Cards in players' hand:");
		int differentCardsNum = 0;
		for (CardPlayer player : game.getPlayerList()) {
			System.out.println(player.getCardNames());
			assertEquals("Correct number of players", playersNumber, game.getPlayersNumber());
			if (player.getCardsNumber() == 8 || player.getCardsNumber() == 9) {
				differentCardsNum++;
			}
			assertEquals("Correct initial card number", 7,
					(player.getCardsNumber() == 8 || player.getCardsNumber() == 9) ? 7 : player.getCardsNumber());

		}
		if (differentCardsNum > 1) {
			fail("too many players with abnormal number of cards");
		}

		System.out.println(game.getCurrentCard());

		totalCardsNumber = game.getPile().size() + game.getDiscardPile().size();
		for (CardPlayer player : game.getPlayerList()) {
			totalCardsNumber += player.getCardsNumber();
		}
		assertEquals("Currect total card number in game", 108, totalCardsNumber);
		if (game.getCurrentCard() instanceof WildCard) {
			game.proceed((WildCard) game.getCurrentCard(), Color.GREEN);
			for (CardPlayer player : game.getPlayerList()) {
				totalCardsNumber += player.getCardsNumber();
			}
			assertEquals("Currect total card number in game after proceed", 108, totalCardsNumber);

		}
	}

}
