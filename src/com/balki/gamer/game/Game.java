package com.balki.gamer.game;

import java.util.Set;

import com.balki.gamer.board.Board;
import com.balki.gamer.gui.GameWindow;
import com.balki.gamer.move.Move;
import com.balki.gamer.move.Mover;
import com.balki.gamer.move.Point;
import com.balki.gamer.move.Pointer;
import com.balki.gamer.player.Player;
import com.balki.gamer.util.FileManager;

/**
 * 
 * @author Balki
 * @since 15/12/2018
 *
 */
public class Game {
	private final GameWindow gameWindow;
	private final String id;

	private Player player1;
	private Player player2;

	private Player currentPlayer;
	private final Board board;

	private final String[] player1FinalPoints = new String[] { "f3", "f4", "f5", "g3", "g4", "g5", "h3", "h4", "h5" };
	private final String[] player2FinalPoints = new String[] { "a3", "a4", "a5", "b3", "b4", "b5", "c3", "c4", "c5" };

	private boolean paused;
	private boolean gameOver;

	public Game(GameWindow gameWindow, String id, Player player1, Player player2) {
		super();
		this.gameWindow = gameWindow;
		this.id = id;
		this.player1 = player1;
		this.player2 = player2;
		this.currentPlayer = null;
		this.board = new Board();
	}

	public GameWindow getGameWindow() {
		return gameWindow;
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

	public boolean isPaused() {
		return paused;
	}

	public void setPaused(boolean paused) {
		this.paused = paused;
	}

	public boolean isGameOver() {
		return gameOver;
	}

	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}

	public void init(boolean random) {
		this.getBoard().init();

		player1.setFinalPoints(player1FinalPoints);
		player2.setFinalPoints(player2FinalPoints);

		if (!random) {
			for (String id : player2FinalPoints) {
				this.getBoard().put(id, player1);
			}

			for (String id : player1FinalPoints) {
				this.getBoard().put(id, player2);
			}
		} else {
			int count = 0;
			while(count < 9) {
				Point randomPoint = Pointer.getPoint(Pointer.getRandomPoint());
				if(getBoard().getState(randomPoint) == null) {
					this.getBoard().put(randomPoint.getId(), player1);
					count++;
				}
			}
			
			count = 0;
			while(count < 9) {
				Point randomPoint = Pointer.getPoint(Pointer.getRandomPoint());
				if(getBoard().getState(randomPoint) == null) {
					this.getBoard().put(randomPoint.getId(), player2);
					count++;
				}
			}
		}
	}

	public void start() {
		this.init(false);
		setTurn(this.getPlayer1());
	}

	public void setTurn(Player player) {
		setCurrentPlayer(player);

		if (!isPaused()) {
			getCurrentPlayer().setTurn(this);
		}
	}

	public void move(Move move) {
		if (move == null) {
			// GAME OVER
			System.out.println("No move left");
		} else {
			if (!Mover.isValid(board, this.getCurrentPlayer(), move)) {
				System.out.println("Invalid move : " + this.getCurrentPlayer().getId() + " : "
						+ move.getStartPoint().getId() + " -> " + move.getEndPoint().getId());
				return;
			}

			this.getBoard().put(move.getStartPoint().getId(), null);
			this.getBoard().put(move.getEndPoint().getId(), getCurrentPlayer());
			getCurrentPlayer().incrementMoveCount();

			FileManager.log(getCurrentPlayer().getLogFile(), getCurrentPlayer().getMoveCount() + " "
					+ move.getStartPoint().getId() + " " + move.getEndPoint().getId());

			if (checkGameOver()) {
				FileManager.log(getCurrentPlayer().getLogFile(), "WIN");
			} else {
				switchTurn();
			}
		}

		getGameWindow().updateBoard();
	}

	private boolean checkGameOver() {
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
				setGameOver(false);
				return false;
			}
		}

		setGameOver(true);
		return true;
	}

	private void switchTurn() {
		if (getCurrentPlayer().equals(getPlayer1())) {
			setTurn(getPlayer2());
		} else {
			setTurn(getPlayer1());
		}
	}

	public void pause() {
		setPaused(true);
	}

	public void cont() {
		setPaused(false);
		setTurn(this.getCurrentPlayer());
	}

	public void setPlayer1(Player player1) {
		this.player1 = player1;
	}

	public void setPlayer2(Player player2) {
		this.player2 = player2;
	}
}
