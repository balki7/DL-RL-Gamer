package com.balki.gamer.move;

import java.util.List;

import com.balki.gamer.player.Player;

/**
 * 
 * @author Balkı
 * @since 15/12/2018
 *
 */
public class SubMove {
	private final Point startPoint;
	private final Point endPoint;
	private final Direction direction;
	private final boolean jump;

	public SubMove(Point startPoint, Point endPoint, Direction direction, boolean jump) {
		super();
		this.startPoint = startPoint;
		this.endPoint = endPoint;
		this.jump = jump;
		this.direction = direction;
	}

	public Point getStartPoint() {
		return startPoint;
	}

	public Point getEndPoint() {
		return endPoint;
	}

	public boolean isJump() {
		return jump;
	}

	public Direction getDirection() {
		return direction;
	}

	@Override
	public boolean equals(Object obj) {
		SubMove m = (SubMove) obj;
		return m.getStartPoint().equals(this.getStartPoint()) && m.getEndPoint().equals(this.getEndPoint())
				&& m.isJump() == this.isJump() && m.getDirection().equals(this.getDirection());
	}

}
