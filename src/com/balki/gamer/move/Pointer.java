package com.balki.gamer.move;

/**
 * 
 * @author Balkı
 * @since 15/12/2018
 *
 */
public class Pointer {
	private static int[] X_LABELS = new int[] { 1, 2, 3, 4, 5, 6, 7, 8 };
	private static String[] Y_LABELS = new String[] { "h", "g", "f", "e", "d", "c", "b", "a" };
	
	public static Point getPoint(int x, String y) {
		int index = getIndex(x) + getIndex(y) * 8;
		return new Point(x, y, index);
	}
	
	private static int getIndex(String y) {
		for(int i=0; i<X_LABELS.length; i++) {
			if(Y_LABELS[i].equals(y)) {
				return i;
			}
		}
		return -1;
	}

	private static int getIndex(int x) {
		for(int i=0; i<X_LABELS.length; i++) {
			if(X_LABELS[i] == x) {
				return i;
			}
		}
		return -1;
	}

	public static Point getPoint(int index) {
		int y = index % 8;
		int x = index - (y * 8);
		return new Point(X_LABELS[x], Y_LABELS[y], index);
	}
}
