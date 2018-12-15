package com.balki.gamer.move;

/**
 * 
 * @author Balkı
 * @since 15/12/2018
 *
 */
public class Point {
	private final int x;
	private final String y;
	private final int index;

	public Point(int x, String y, int index) {
		super();
		this.x = x;
		this.y = y;
		this.index = index;
	}

	public int getX() {
		return x;
	}

	public String getY() {
		return y;
	}

	public int getIndex() {
		return index;
	}

	public String getId() {
		return getY() + getX();
	}

}
