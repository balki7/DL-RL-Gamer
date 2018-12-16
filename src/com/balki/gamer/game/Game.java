package com.balki.gamer.game;

import java.util.Set;

import com.balki.gamer.board.Board;
import com.balki.gamer.move.Move;
import com.balki.gamer.move.Point;
import com.balki.gamer.player.Player;
import com.balki.gamer.util.FileManager;

/**
 * 
 * @author Balki
 * @since 15/12/2018
 *
 */
public class Game {
	private final String id;

	private final Player player1;
	private final Player player2;

	private Player currentPlayer;
	private final Board board;

	private final String[] player1FinalPoints = new String[] { "f3", "f4", "f5", "g3", "g4", "g5", "h3", "h4", "h5" };
	private final String[] player2FinalPoints = new String[] { "a3", "a4", "a5", "b3", "b4", "b5", "c3", "c4", "c5" };

	public Game(String id, Player player1, Player player2) {
		super();
		this.id = id;
		this.player1 = player1;
		this.player2 = player2;
		this.currentPlayer = null;
		this.board = new Board();
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

	public String getId() {
		return id;
	}

	public void setCurrentPlayer(Player currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

	public void init() {
		this.getBoard().init();

		player1.setFinalPoints(player1FinalPoints);
		player2.setFinalPoints(player2FinalPoints);

		for (String id : player2FinalPoints) {
			this.getBoard().put(id, player1);
		}

		for (String id : player1FinalPoints) {
			this.getBoard().put(id, player2);
		}
	}

	public void start() {
		this.init();
		System.out.println(this.getBoard());

		setTurn(this.getPlayer1());
	}

	private void setTurn(Player player) {
		setCurrentPlayer(player);

		getCurrentPlayer().setTurn(this);
	}

	public void move(Move move) {
		if (move == null) {
			// GAME OVER
			System.out.println("No move left");
		} else {
			this.getBoard().put(move.getStartPoint().getId(), null);
			this.getBoard().put(move.getEndPoint().getId(), getCurrentPlayer());
			getCurrentPlayer().incrementMoveCount();

			FileManager.log(getCurrentPlayer().getLogFile(), getCurrentPlayer().getMoveCount() + " "
					+ move.getStartPoint().getId() + " " + move.getEndPoint().getId());
			System.out.println(this.getBoard());

			if (isGameOver()) {
				System.out.println("Game over, winner : " + getCurrentPlayer().getId());
				FileManager.log(getCurrentPlayer().getLogFile(), "WIN");
			} else {
				switchTurn();
			}
		}
	}

	private boolean isGameOver() {
		Set<Point> points = getBoard().getPoints(getCurrentPlayer());

		for (Point p : points) {
			boolean found = false;
			for (String id : getCurrentPlayer().getFinalPoints()) {
				if (id.equals(p.getId())) {
					found = true;
					break;
				}
			}

			if (!found) {
				return false;
			}
		}

		return true;
	}

	private void switchTurn() {
		if (getCurrentPlayer().equals(getPlayer1())) {
			setTurn(getPlayer2());
		} else {
			setTurn(getPlayer1());
		}
	}
}