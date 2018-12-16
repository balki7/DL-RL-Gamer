package com.balki.gamer.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.UUID;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.balki.gamer.game.Game;
import com.balki.gamer.player.ComputerPlayer;
import com.balki.gamer.player.FilePlayer;
import com.balki.gamer.player.HumanPlayer;
import com.balki.gamer.player.Player;
import com.balki.gamer.player.PlayerType;

/**
 * 
 * @author Balki
 * @since 16/12/2018
 *
 */
public class GameWindow extends JFrame {
	private static final long serialVersionUID = -5415886501992871165L;

	public static final int DEFAULT_WIDTH = 500;
	public static final int DEFAULT_HEIGHT = 600;
	public static final String DEFAULT_TITLE = "DL Term Project - Balkı";
	private CheckerBoard board;
	private OptionPanel opts;
	private JPanel layout;

	private Game game;

	public GameWindow() {
		super(DEFAULT_TITLE);
		super.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		super.setLocationByPlatform(true);

		// Setup the components
		layout = new JPanel(new BorderLayout());
		layout.setBackground(Color.PINK);

		this.opts = new OptionPanel(this);
		layout.add(opts, BorderLayout.NORTH);
		this.add(layout);

		setBackground(Color.PINK);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void startGame(PlayerType player1Type, PlayerType player2Type) {
		String gameId = UUID.randomUUID().toString();

		Player player1 = null;
		switch (player1Type) {
		case COMPUTER: {
			player1 = new ComputerPlayer("1", gameId);
			break;
		}
		case HUMAN: {
			player1 = new HumanPlayer("1", gameId);
			break;
		}
		case FILE: {
			player1 = new FilePlayer("1", gameId);
			break;
		}
		}

		Player player2 = null;
		switch (player1Type) {
		case COMPUTER: {
			player2 = new ComputerPlayer("2", gameId);
			break;
		}
		case HUMAN: {
			player2 = new HumanPlayer("2", gameId);
			break;
		}
		case FILE: {
			player2 = new FilePlayer("2", gameId);
			break;
		}
		}

		game = new Game(this, gameId, player1, player2);
		game.start();

		this.board = new CheckerBoard(this);
		layout.add(board, BorderLayout.CENTER);
	}

	public void pauseGame() {
		game.pause();
	}

	public void stopGame() {
		game = null;
		this.board = null;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public void updateBoard() {
		this.opts.updateButtons();
		this.board.updateBoard();
	}

	public void continueGame() {
		game.cont();
	}
}
