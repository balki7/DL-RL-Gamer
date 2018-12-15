package com.balki.gamer.game;

import com.balki.gamer.board.Board;
import com.balki.gamer.move.Mover;
import com.balki.gamer.player.Player;

/**
 * 
 * @author Balki
 * @since 15/12/2018
 *
 */
public class Game {
	private final Player player1;
	private final Player player2;

	private final Player currentPlayer;
	private final Board board;
	private final Mover mover;
	
	public Game(Player player1, Player player2) {
		super();
		this.player1 = player1;
		this.player2 = player2;
		this.currentPlayer = player1;
		this.board = new Board();
		this.mover = new Mover();
	}

	public Player getPlayer1() {
		return player1;
	}

	public Player getPlayer2() {
		return player2;
	}

	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	public Board getBoard() {
		return board;
	}

	public Mover getMover() {
		return mover;
	}
	
	public void init() {
		this.getBoard().init();
		this.getMover().init();
	}
}
