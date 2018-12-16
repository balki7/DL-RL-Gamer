package com.balki.gamer.player;

import com.balki.gamer.game.Game;

/**
 * 
 * @author Balki
 * @since 15/12/2018
 *
 */
public class FilePlayer extends AbstractPlayer{

	public FilePlayer(String id) {
		super(PlayerType.FILE, id);
	}
	
	public FilePlayer(String id, String[] finalPoints, String logFile, int moveCount) {
		super(PlayerType.FILE, id, finalPoints, logFile, moveCount);
	}

	@Override
	public void setTurn(Game game) {}

}
