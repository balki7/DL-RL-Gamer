package com.balki.gamer.player;

import com.balki.gamer.game.Game;

/**
 * 
 * @author Balki
 * @since 15/12/2018
 *
 */
public class RLPlayer extends AbstractPlayer {
	public RLPlayer(String id) {
		super(PlayerType.RL, id);
	}

	public RLPlayer(String id, String[] finalPoints, String logFile, int moveCount) {
		super(PlayerType.RL, id, finalPoints, logFile, moveCount);
	}

	@Override
	public void setTurn(Game game) {
		class MoverThread extends Thread {
			public void run() {
				//policy.getBestAction(game.getBoard().getState());
			}
		}

		MoverThread moverThread = new MoverThread();
		moverThread.start();
	}
}
