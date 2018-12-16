package com.balki.gamer.move;

import java.util.List;

import com.balki.gamer.player.Player;

/**
 * 
 * @author Balkı
 * @since 15/12/2018
 *
 */
public class Move {
	private final Player player;
	private final List<SubMove> subMoves;

	public Move(Player player, List<SubMove> subMoves) {
		super();
		this.player = player;
		this.subMoves = subMoves;
	}

	public Player getPlayer() {
		return player;
	}

	public List<SubMove> getSubMoves() {
		return subMoves;
	}
	
	public Point getStartPoint() {
		return subMoves.get(0).getStartPoint();
	}
	
	public Point getEndPoint() {
		return subMoves.get(subMoves.size()-1).getEndPoint();
	}

	@Override
	public boolean equals(Object obj) {
		Move m = (Move) obj;
		return m.getPlayer().equals(this.getPlayer()) && m.getSubMoves().equals(this.getSubMoves());
	}

}
