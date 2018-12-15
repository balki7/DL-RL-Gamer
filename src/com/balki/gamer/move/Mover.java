package com.balki.gamer.move;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Balkı
 * @since 15/12/2018
 *
 */
public class Mover {
	private List<Move> previousMoves = null;

	public Mover() {
		this.previousMoves = new ArrayList<Move>();
	}

	public List<Move> getPreviousMoves() {
		return previousMoves;
	}

	public void init() {
		// TODO Auto-generated method stub

	}

}
