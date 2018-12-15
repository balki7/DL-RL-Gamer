package com.balki.gamer.move;

/**
 * 
 * @author Balkı
 * @since 15/12/2018
 *
 */
public class Point {
	private final int x;
	private final int y;
	private final int index;
	private final String id;

	public Point(int x, int y, int index, String id) {
		super();
		this.x = x;
		this.y = y;
		this.index = index;
		this.id = id;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getIndex() {
		return index;
	}

	public String getId() {
		return id;
	}

}
