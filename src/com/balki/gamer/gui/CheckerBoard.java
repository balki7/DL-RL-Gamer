package com.balki.gamer.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;

import com.balki.gamer.board.Board;
import com.balki.gamer.game.Game;
import com.balki.gamer.move.Move;
import com.balki.gamer.move.Mover;
import com.balki.gamer.move.Pointer;
import com.balki.gamer.player.Player;
import com.balki.gamer.player.PlayerType;

/**
 * 
 * @author Balki
 * @since 16/12/2018
 *
 */
public class CheckerBoard extends JButton {

	private static final long serialVersionUID = -247784232912895322L;

	private static final int PADDING = 16;

	private GameWindow gameWindow;

	private Color lightTile;
	private Color darkTile;
	
	private Point startPoint = null;

	public CheckerBoard(GameWindow gameWindow) {
		this.gameWindow = gameWindow;

		super.setBorderPainted(false);
		super.setFocusPainted(false);
		super.setContentAreaFilled(false);
		super.setBackground(Color.GRAY);
		this.addActionListener(new ClickListener());

		this.lightTile = Color.WHITE;
		this.darkTile = Color.LIGHT_GRAY;
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);

		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		Game game = gameWindow.getGame();
		Board board = game.getBoard();

		// Perform calculations
		final int BOX_PADDING = 4;
		final int W = getWidth(), H = getHeight();
		final int DIM = W < H ? W : H, BOX_SIZE = (DIM - 2 * PADDING) / 8;
		final int OFFSET_X = (W - BOX_SIZE * 8) / 2;
		final int OFFSET_Y = (H - BOX_SIZE * 8) / 2;
		final int CHECKER_SIZE = Math.max(0, BOX_SIZE - 2 * BOX_PADDING);

		// Draw checker board
		g.setColor(Color.BLACK);
		g.drawRect(OFFSET_X - 1, OFFSET_Y - 1, BOX_SIZE * 8 + 1, BOX_SIZE * 8 + 1);
		g.setColor(lightTile);
		g.fillRect(OFFSET_X, OFFSET_Y, BOX_SIZE * 8, BOX_SIZE * 8);
		g.setColor(darkTile);
		for (int y = 0; y < 8; y++) {
			for (int x = (y + 1) % 2; x < 8; x += 2) {
				g.fillRect(OFFSET_X + x * BOX_SIZE, OFFSET_Y + y * BOX_SIZE, BOX_SIZE, BOX_SIZE);
			}
		}
		
		g.setColor(Color.BLACK);
		for (int x = 0; x < 8; x++) {
			g.drawString(String.valueOf(Pointer.X_LABELS[x]), (BOX_SIZE + 5) + x * BOX_SIZE, 10);
		}
		
		g.setColor(Color.BLACK);
		for (int y = 0; y < 8; y++) {
			g.drawString(Pointer.Y_LABELS[y], 20, (BOX_SIZE - 5) + y * BOX_SIZE);
		}


		// Highlight the selected tile if valid
		if (startPoint != null) {
			g.setColor(Color.GREEN);
			g.fillRect(OFFSET_X + startPoint.x * BOX_SIZE, OFFSET_Y + startPoint.y * BOX_SIZE, BOX_SIZE, BOX_SIZE);
			
			List<Move> moves = Mover.getAllMoves(board, game.getCurrentPlayer(), Pointer.getPoint(startPoint.x, startPoint.y));
			for(Move m : moves) {
				g.setColor(Color.BLUE);
				g.fillRect(OFFSET_X + m.getEndPoint().getX() * BOX_SIZE, OFFSET_Y + m.getEndPoint().getY() * BOX_SIZE, BOX_SIZE, BOX_SIZE);
			}
		}

		// Draw the checkers
		for (int y = 0; y < 8; y++) {
			int cy = OFFSET_Y + y * BOX_SIZE + BOX_PADDING;
			for (int x = 0; x < 8; x++) {
				Player player = board.getState(Pointer.getPoint(x, y));

				// Empty, just skip
				if (player == null) {
					continue;
				}

				int cx = OFFSET_X + x * BOX_SIZE + BOX_PADDING;

				// Black checker
				if (player.getId().equals("1")) {
					g.setColor(Color.DARK_GRAY);
					g.fillOval(cx + 1, cy + 2, CHECKER_SIZE, CHECKER_SIZE);
					g.setColor(Color.LIGHT_GRAY);
					g.drawOval(cx + 1, cy + 2, CHECKER_SIZE, CHECKER_SIZE);
					g.setColor(Color.BLACK);
					g.fillOval(cx, cy, CHECKER_SIZE, CHECKER_SIZE);
					g.setColor(Color.LIGHT_GRAY);
					g.drawOval(cx, cy, CHECKER_SIZE, CHECKER_SIZE);
				}

				// White checker
				else if (player.getId().equals("2")) {
					g.setColor(Color.LIGHT_GRAY);
					g.fillOval(cx + 1, cy + 2, CHECKER_SIZE, CHECKER_SIZE);
					g.setColor(Color.DARK_GRAY);
					g.drawOval(cx + 1, cy + 2, CHECKER_SIZE, CHECKER_SIZE);
					g.setColor(Color.PINK);
					g.fillOval(cx, cy, CHECKER_SIZE, CHECKER_SIZE);
					g.setColor(Color.DARK_GRAY);
					g.drawOval(cx, cy, CHECKER_SIZE, CHECKER_SIZE);
				}
			}
		}

		// Draw the player turn sign
		String msg = "Player " + game.getCurrentPlayer().getId() + "'s turn";
		int width = g.getFontMetrics().stringWidth(msg);
		Color back = "1".equals(game.getCurrentPlayer().getId()) ? Color.BLACK : Color.WHITE;
		Color front = "1".equals(game.getCurrentPlayer().getId()) ? Color.WHITE : Color.BLACK;
		g.setColor(back);
		g.fillRect(W / 2 - width / 2 - 5, OFFSET_Y + 8 * BOX_SIZE + 2, width + 10, 15);
		g.setColor(front);
		g.drawString(msg, W / 2 - width / 2, OFFSET_Y + 8 * BOX_SIZE + 2 + 11);

		// Draw a game over sign
		if (game.isGameOver()) {
			g.setFont(new Font("Arial", Font.BOLD, 20));
			msg = "Game Over! Winner is Player " + game.getCurrentPlayer().getId();
			width = g.getFontMetrics().stringWidth(msg);
			g.setColor(new Color(240, 240, 255));
			g.fillRoundRect(W / 2 - width / 2 - 5, OFFSET_Y + BOX_SIZE * 4 - 16, width + 10, 30, 10, 10);
			g.setColor(Color.RED);
			g.drawString(msg, W / 2 - width / 2, OFFSET_Y + BOX_SIZE * 4 + 7);
		}
	}

	public void setLightTile(Color lightTile) {
		this.lightTile = (lightTile == null) ? Color.WHITE : lightTile;
	}

	public void setDarkTile(Color darkTile) {
		this.darkTile = (darkTile == null) ? Color.BLACK : darkTile;
	}

	private void handleClick(int x, int y) {
		Game game = gameWindow.getGame();
		
		if (game == null || game.isPaused() || game.isGameOver() || !PlayerType.HUMAN.equals(game.getCurrentPlayer().getType())) {
			return;
		}
		
		Board board = game.getBoard();

		// Determine what square (if any) was selected
		final int W = getWidth(), H = getHeight();
		final int DIM = W < H ? W : H, BOX_SIZE = (DIM - 2 * PADDING) / 8;
		final int OFFSET_X = (W - BOX_SIZE * 8) / 2;
		final int OFFSET_Y = (H - BOX_SIZE * 8) / 2;
		x = (x - OFFSET_X) / BOX_SIZE;
		y = (y - OFFSET_Y) / BOX_SIZE;
		
		Player player = board.getState(Pointer.getPoint(x, y));
		if(startPoint == null) {
			if(player == null || !player.equals(game.getCurrentPlayer())) {
				return;
			}	
			
			startPoint = new Point(x, y);
		}
		else {
			if(player != null && player.equals(game.getCurrentPlayer())) {
				startPoint = new Point(x, y);
				return;
			}		
			
			Move move = new Move(game.getCurrentPlayer(), Pointer.getPoint(startPoint.x, startPoint.y), Pointer.getPoint(x, y));
			if(Mover.isValid(board, game.getCurrentPlayer(), move)) {
				game.move(move);
			}
		}
	}

	private class ClickListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			Point m = CheckerBoard.this.getMousePosition();
			if (m != null) {
				handleClick(m.x, m.y);
			}
		}
	}

	public void updateBoard() {
		this.startPoint = null;
		
		this.repaint();
	}

}
