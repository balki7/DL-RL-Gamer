package com.balki.gamer.move;


/**
 * 
 * @author Balkı
 * @since 16/12/2018
 *
 */
public class Direction {
	private final int x;
	private final int y;

	public Direction(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	@Override
	public boolean equals(Object obj) {
		Direction m = (Direction) obj;
		return m.getX() == this.getX() && m.getY() == this.getY();
	}

}
