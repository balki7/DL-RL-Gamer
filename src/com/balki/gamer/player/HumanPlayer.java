package com.balki.gamer.player;

import com.balki.gamer.game.Game;

/**
 * 
 * @author Balki
 * @since 15/12/2018
 *
 */
public class HumanPlayer extends AbstractPlayer{

	public HumanPlayer(String id) {
		super(PlayerType.HUMAN, id);
	}
	
	public HumanPlayer(String id, String[] finalPoints, String logFile, int moveCount) {
		super(PlayerType.HUMAN, id, finalPoints, logFile, moveCount);
	}

	@Override
	public void setTurn(Game game) {}


}
