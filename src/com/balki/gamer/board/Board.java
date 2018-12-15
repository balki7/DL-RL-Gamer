package com.balki.gamer.board;

import com.balki.gamer.move.Point;
import com.balki.gamer.move.Pointer;
import com.balki.gamer.player.Player;

/**
 * 
 * @author Balkı
 * @since 15/12/2018
 *
 */
public class Board {

	private Player[][] state = null;

	public Board() {
		state = new Player[8][8];
	}

	public void init() {
		for (int y = 0; y < 8; y++) {
			for (int x = 0; x < 8; x++) {
				state[y][x] = null;
			}
		}
	}

	public void put(String id, Player player) {
		Point p = Pointer.getPoint(id);
		state[p.getY()][p.getX()] = player;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int y = 0; y < 8; y++) {
			for (int x = 0; x < 8; x++) {
				sb.append((state[y][x] == null) ? "-" : state[y][x].getId());
				sb.append("\t");
			}
			sb.append("\n");
		}
		return sb.toString();
	}

}
