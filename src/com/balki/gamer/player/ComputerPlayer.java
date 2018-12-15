package com.balki.gamer.player;

import com.balki.gamer.game.Game;
import com.balki.gamer.move.Mover;

/**
 * 
 * @author Balki
 * @since 15/12/2018
 *
 */
public class ComputerPlayer extends AbstractPlayer {

	public ComputerPlayer(String id, String gameId) {
		super(PlayerType.COMPUTER, id, gameId);
	}

	@Override
	public void setTurn(Game game) {
		class MoverThread extends Thread {
			public void run() {
				game.move(Mover.getMove(game.getBoard(), game.getCurrentPlayer()));
			}
		}
		
		MoverThread moverThread = new MoverThread();
		moverThread.start();
	}

}
