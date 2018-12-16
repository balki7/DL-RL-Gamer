package com.balki.gamer.move;

import java.util.List;

import com.balki.gamer.player.Player;

/**
 * 
 * @author Balkı
 * @since 15/12/2018
 *
 */
public class Move{
	private final Player player;
	private final Point startPoint;
	private final Point endPoint;
	private final List<Point> subPoints;

	public Move(Player player, Point startPoint, Point endPoint) {
		this(player, startPoint, endPoint, null);
	}

	public Move(Player player, Point startPoint, Point endPoint, List<Point> subPoints) {
		super();
		this.player = player;
		this.startPoint = startPoint;
		this.endPoint = endPoint;
		this.subPoints = subPoints;
	}

	public Player getPlayer() {
		return player;
	}

	public Point getStartPoint() {
		return startPoint;
	}

	public Point getEndPoint() {
		return endPoint;
	}

	public List<Point> getSubPoints() {
		return subPoints;
	}

	@Override
	public boolean equals(Object obj) {
		Move m = (Move) obj;
		return m.getPlayer().equals(this.getPlayer()) && m.getStartPoint().equals(this.getStartPoint())
				&& m.getEndPoint().equals(this.getEndPoint()) && m.getSubPoints().equals(this.getSubPoints());
	}

}
