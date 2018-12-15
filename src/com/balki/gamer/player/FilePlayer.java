package com.balki.gamer.player;

import com.balki.gamer.game.Game;

/**
 * 
 * @author Balki
 * @since 15/12/2018
 *
 */
public class FilePlayer extends AbstractPlayer{

	public FilePlayer(String id, String gameId) {
		super(PlayerType.FILE, id, gameId);
	}

	@Override
	public void setTurn(Game game) {
		// TODO Auto-generated method stub
		
	}

}
