/**
 * 
 */
package net.mcplugin.unolib.control;

import java.util.ArrayList;

import net.mcplugin.unolib.game.deck.AbstractCard;
import net.mcplugin.unolib.game.deck.ActionCard;
import net.mcplugin.unolib.game.deck.ActionType;
import net.mcplugin.unolib.game.deck.ColorCard;
import net.mcplugin.unolib.game.deck.WildCard;
import net.mcplugin.unolib.game.gametable.GamePlayer;
import net.mcplugin.unolib.game.gametable.GameStage;
import net.mcplugin.unolib.game.gametable.ProceedResponse;
import net.mcplugin.unolib.game.gametable.UNOGame;

/**
 * @author Henry Hu
 *
 */
public class UNOController {
	private UNOGame game;
	private GameHandler gameHandler;
	AbstractCard cardChosen = null;

	/**
	 * 
	 */
	public UNOController(GameHandler gameHandler, ArrayList<GamePlayer> playerList) {
		game = new UNOGame(playerList);
		this.gameHandler = gameHandler;
		this.gameHandler.onStart(game, game.getCurrentPlayer(), game.getCurrentCard());
		if (game.getCurrentCard() instanceof WildCard) {
			((WildCard) game.getCurrentCard()).declareColor(gameHandler.onChooseColor(game.getCurrentPlayer()));
		}
		while (game.getStage() != GameStage.END) {
			cardChosen = this.attempt(cardChosen);
		}
		// TODO Auto-generated constructor stub
	}

	private AbstractCard attempt(AbstractCard cardChosen) {
		switch (game.getStage()) {
		case END:
			return null;
		case RESTRICTIVE_SUSPEND:
			if (handleResponse(game.getCurrentPlayer(), this.proceed(cardChosen))) {
				cardChosen = null;
			}
			break;
		case SUSPEND:
			if (cardChosen == null)
				cardChosen = gameHandler.onTurn(game.getCurrentPlayer(), game.getCurrentCard());
			if (handleResponse(game.getCurrentPlayer(), this.proceed(cardChosen))) {
				cardChosen = null;
			}
			break;
		default:
			break;

		}
		return null;
	}

	private ProceedResponse proceed(AbstractCard cardChosen) {
		GamePlayer player = game.getCurrentPlayer();
		if (cardChosen instanceof ColorCard) {
			ProceedResponse response = game.proceed((ColorCard) cardChosen);
			if (response == ProceedResponse.PLAYED || response == ProceedResponse.PLAYED_DRAWN) {
				if (cardChosen instanceof ActionCard) {
					gameHandler.onAction(((ActionCard) cardChosen), player);
					if (((ActionCard) cardChosen).getAction() == ActionType.DRAW_TWO) {
						gameHandler.onCardChange(game.getPlayer(game.getNextPlayerID(game.getPlayerID(player))), 2,
								game.getCurrentPlayer().getCardsNumber());
					}
				}
			}
			return response;
		} else if (cardChosen instanceof WildCard) {

			ProceedResponse response = game.proceed((WildCard) cardChosen, gameHandler.onChooseColor(player));
			if (response == ProceedResponse.PLAYED || response == ProceedResponse.PLAYED_DRAWN) {
				if (((WildCard) cardChosen).isDrawFour()) {
					gameHandler.onCardChange(game.getCurrentPlayer(), 4, game.getCurrentPlayer().getCardsNumber());

				}
			}
			return response;

		} else if (cardChosen == null) {
			return game.proceed();
		}
		return ProceedResponse.UNKNOWN;
	}

	/**
	 * @param player
	 *            TODO
	 * @param response
	 *            to handle
	 * @return true if a new turn may start
	 */
	private boolean handleResponse(GamePlayer player, ProceedResponse response) {
		switch (response) {
		case DREW:
			gameHandler.onCardChange(player, 1, player.getCardsNumber());
		case BEYOND_RESTRICTION:
			// A player draws a card and then he may use it.
			if (gameHandler.onDrawInTurn(player, player.getRestrictedCard())) {
				if (this.proceed(player.getRestrictedCard()) != ProceedResponse.PLAYED) {
					this.proceed(null); // If the card he drew isn't
										// successfully played, the game
										// automatically proceeds
					// TODO fix this part
					gameHandler.onSkipAfterDrawn(player, game.getCurrentCard());
				}

			} else {
				this.proceed(null);
			}
			break;

		case MISMATCHED:
			cardChosen = gameHandler.onMismatch(player, game.getCurrentCard());
			break;

		case NOT_EXIST:
			cardChosen = gameHandler.onCardNotExist(player, game.getCurrentCard());
			break;

		case PLAYED:
			gameHandler.onPlay(player, false, game.getCurrentCard());
			gameHandler.onCardChange(player, -1, player.getCardsNumber());
			cardChosen = null;
			return true;
		case PLAYED_DRAWN:
			gameHandler.onPlay(player, true, game.getCurrentCard());
			gameHandler.onCardChange(player, -1, player.getCardsNumber());
			cardChosen = null;
			return true;
		case SKIPPED:
			gameHandler.onSkipAfterDrawn(player, game.getCurrentCard());

			cardChosen = null;
			return true;

		case WRONG_PLAYER:
			// Not possible to have this response
			break;

		case ENDED:
			GamePlayer succeededPlayer = null;
			for (GamePlayer eachPlayer : game.getPlayerList()) {
				if (eachPlayer.getCardsNumber() == 0) {
					succeededPlayer = eachPlayer;
					break;
				}
			}
			gameHandler.onEnd(succeededPlayer);
			break;

		case UNKNOWN:
			break;

		default:
			break;

		}

		return false;

	}
}
