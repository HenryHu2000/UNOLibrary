package net.mcplugin.mineuno.test;

import net.mcplugin.mineuno.game.gametable.CardPile;

public class PileMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		CardPile pile = new CardPile();
		pile.initialize();
		System.out.println(pile.getStack().size());

	}

}
