package com.balki.gamer;

import java.util.UUID;

import com.balki.gamer.game.Game;
import com.balki.gamer.player.ComputerPlayer;

public class Main {

	public static void main(String[] args) {
		String gameId = UUID.randomUUID().toString();
		Game game = new Game(gameId, new ComputerPlayer("1", gameId), new ComputerPlayer("2", gameId));
		game.start();
	}

}
