package com.balki.gamer;

import com.balki.gamer.game.Game;
import com.balki.gamer.player.ComputerPlayer;

public class Main {

	public static void main(String[] args) {
		Game game = new Game(new ComputerPlayer("1"), new ComputerPlayer("2"));
		game.start();
	}

}
