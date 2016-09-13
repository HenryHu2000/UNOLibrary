package net.mcplugin.unolib.control;

import java.util.ArrayList;

import net.mcplugin.unolib.game.gametable.CardPlayer;

public class UNOMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ArrayList<CardPlayer> playerList = new ArrayList<CardPlayer>();

		playerList.add(new CardPlayer("Computer"));
		playerList.add(new CardPlayer("Greensky"));
		playerList.add(new CardPlayer("Lilia"));
		UNOController controller = new UNOController(new DefaultControllerHandler(), playerList);

	}

	public UNOMain() {
		// TODO Auto-generated constructor stub
	}

}
