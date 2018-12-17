package com.balki.gamer;

import com.balki.gamer.game.Game;
import com.balki.gamer.move.Move;
import com.balki.gamer.move.Mover;
import com.balki.gamer.player.ComputerPlayer;
import com.balki.gamer.util.FileManager;

/**
 * 
 * @author Balki
 * @since 17/12/2018
 *
 */
public class Test {

	public static void main(String[] args) {
		for (int i = 0; i < 10000; i++) {
			Game game = new Game(null, null, new ComputerPlayer("1"), new ComputerPlayer("2"));
			game.init(true);
			game.setCurrentPlayer((Math.random() < 0.5) ? game.getPlayer1() : game.getPlayer2());
			Move m = Mover.getMove(game, game.getCurrentPlayer());
			FileManager.log("test.txt", game.getBoard() + game.getCurrentPlayer().getId() + m.getStartPoint().getX()
					+ m.getStartPoint().getY() + m.getEndPoint().getX() + m.getEndPoint().getY());
		}
	}

}
