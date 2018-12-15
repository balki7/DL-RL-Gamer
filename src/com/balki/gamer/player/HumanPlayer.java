package com.balki.gamer.player;

import com.balki.gamer.game.Game;

/**
 * 
 * @author Balki
 * @since 15/12/2018
 *
 */
public class HumanPlayer extends AbstractPlayer{

	public HumanPlayer(String id, String gameId) {
		super(PlayerType.HUMAN, id, gameId);
	}

	@Override
	public void setTurn(Game game) {
		// TODO Auto-generated method stub
		
	}


}
