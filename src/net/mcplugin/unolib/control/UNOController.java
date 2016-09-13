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
import net.mcplugin.unolib.game.gametable.CardPlayer;
import net.mcplugin.unolib.game.gametable.GameStage;
import net.mcplugin.unolib.game.gametable.ProceedResponse;
import net.mcplugin.unolib.game.gametable.UNOGame;

/**
 * @author Henry Hu
 *
 */
public class UNOController {
	private UNOGame game;
	private ControllerHandler controllerHandler;
	AbstractCard cardChosen = null;

	/**
	 * 
	 */
	public UNOController(ControllerHandler controllerHandler, ArrayList<CardPlayer> playerList) {
		game = new UNOGame(null, playerList);
		this.controllerHandler = controllerHandler;
		this.controllerHandler.onStart(game, game.getCurrentPlayer(), game.getCurrentCard());
		if (game.getCurrentCard() instanceof WildCard) {
			((WildCard) game.getCurrentCard()).declareColor(controllerHandler.onChooseColor(game.getCurrentPlayer()));
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
				cardChosen = controllerHandler.onTurn(game.getCurrentPlayer(), game.getCurrentCard());
			if (handleResponse(game.getCurrentPlayer(), this.proceed(cardChosen))) {
				cardChosen = null;
			}
			break;
		default:
			break;

		}
		return null;
	}

	/**
	 * @param player
	 *            TODO
	 * @param response
	 *            to handle
	 * @return true if a new turn may start
	 */
	private boolean handleResponse(CardPlayer player, ProceedResponse response) {
		switch (response) {
		case DREW:
			controllerHandler.onCardChange(player, 1, player.getCardsNumber());
		case BEYOND_RESTRICTION:
			// A player draws a card and then he may use it.
			if (controllerHandler.onDrawInTurn(player, player.getRestrictedCard())) {
				if (this.proceed(player.getRestrictedCard()) != ProceedResponse.PLAYED) {
					this.proceed(null); // If the card he drew isn't
										// successfully played, the game
										// automatically proceeds
					// TODO fix this part
					controllerHandler.onSkipAfterDrawn(player, game.getCurrentCard());
				}

			} else {
				this.proceed(null);
			}
			break;

		case MISMATCHED:
			cardChosen = controllerHandler.onMismatch(player, game.getCurrentCard());
			break;

		case NOT_EXIST:
			cardChosen = controllerHandler.onCardNotExist(player, game.getCurrentCard());
			break;

		case PLAYED:
			controllerHandler.onPlay(player, false, game.getCurrentCard());
			controllerHandler.onCardChange(player, -1, player.getCardsNumber());
			cardChosen = null;
			return true;
		case SKIPPED:
			controllerHandler.onSkipAfterDrawn(player, game.getCurrentCard());

			cardChosen = null;
			return true;

		case WRONG_PLAYER:
			// Not possible to have this response
			break;

		case ENDED:
			CardPlayer succeededPlayer = null;
			for (CardPlayer eachPlayer : game.getPlayerList()) {
				if (eachPlayer.getCardsNumber() == 0) {
					succeededPlayer = eachPlayer;
					break;
				}
			}
			controllerHandler.onEnd(succeededPlayer);
			break;

		case UNKNOWN:
			break;

		default:
			break;

		}

		return false;

	}

	private ProceedResponse proceed(AbstractCard cardChosen) {
		CardPlayer player = game.getCurrentPlayer();
		if (cardChosen instanceof ColorCard) {
			ProceedResponse response = game.proceed((ColorCard) cardChosen);
			if (response == ProceedResponse.PLAYED) {
				if (cardChosen instanceof ActionCard) {
					controllerHandler.onAction(((ActionCard) cardChosen), player);
					if (((ActionCard) cardChosen).getAction() == ActionType.DRAW_TWO) {
						controllerHandler.onCardChange(game.getPlayer(game.getNextPlayerID(game.getPlayerID(player))),
								2, game.getCurrentPlayer().getCardsNumber());
					}
				}
			}
			return response;
		} else if (cardChosen instanceof WildCard) {

			ProceedResponse response = game.proceed((WildCard) cardChosen, controllerHandler.onChooseColor(player));
			if (response == ProceedResponse.PLAYED) {
				if (((WildCard) cardChosen).isDrawFour()) {
					controllerHandler.onCardChange(game.getCurrentPlayer(), 4,
							game.getCurrentPlayer().getCardsNumber());

				}
			}
			return response;

		} else if (cardChosen == null) {
			return game.proceed();
		}
		return ProceedResponse.UNKNOWN;
	}
}
