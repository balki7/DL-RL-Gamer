package com.balki.gamer.move;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

import com.balki.gamer.board.Board;
import com.balki.gamer.player.Player;

/**
 * 
 * @author Balkı
 * @since 15/12/2018
 *
 */
public class Mover {
	public static Move getMove(Board board, Player player) {
		Set<Move> validMoves = getAllMoves(board, player);

		int size = validMoves.size();
		if (size > 0) {
			int item = new Random().nextInt(size);
			int i = 0;
			for (Move obj : validMoves) {
				if (i == item)
					return obj;
				i++;
			}
		}

		return null;
	}

	public static Set<Move> getAllMoves(Board board, Player player) {
		Set<Move> validMoves = new HashSet<Move>();

		validMoves.addAll(getMoves(board, player));
		// TODO validMoves.addAll(getSkips(board, player));

		return validMoves;
	}

	private static Set<Move> getMoves(Board board, Player player) {
		Set<Move> moves = new HashSet<Move>();
		Set<Point> currentPoints = board.getPoints(player);

		for (Point p : currentPoints) {
			if (p.getX() + 1 < 8) {
				moves.add(new Move(player, p, Pointer.getPoint(p.getX() + 1, p.getY())));
			}
			if (p.getX() - 1 >= 0) {
				moves.add(new Move(player, p, Pointer.getPoint(p.getX() - 1, p.getY())));
			}
			if (p.getX() + 2 < 8) {
				moves.add(new Move(player, p, Pointer.getPoint(p.getX() + 2, p.getY())));
			}
			if (p.getX() - 2 >= 0) {
				moves.add(new Move(player, p, Pointer.getPoint(p.getX() - 2, p.getY())));
			}
			if (p.getY() + 1 < 8) {
				moves.add(new Move(player, p, Pointer.getPoint(p.getX(), p.getY() + 1)));
			}
			if (p.getY() - 1 >= 0) {
				moves.add(new Move(player, p, Pointer.getPoint(p.getX(), p.getY() - 1)));
			}
			if (p.getY() + 2 < 8) {
				moves.add(new Move(player, p, Pointer.getPoint(p.getX(), p.getY() + 2)));
			}
			if (p.getY() - 2 >= 0) {
				moves.add(new Move(player, p, Pointer.getPoint(p.getX(), p.getY() - 2)));
			}
		}

		return getValidMoves(board, player, moves);
	}

	private static Set<Move> getValidMoves(Board board, Player player, Set<Move> moves) {
		Set<Move> validMoves = new HashSet<Move>();

		Iterator<Move> moveIterator = moves.iterator();
		while (moveIterator.hasNext()) {
			Move move = moveIterator.next();
			if (isValid(board, player, move)) {
				validMoves.add(move);
			}
		}

		return validMoves;
	}

	private static boolean isValid(Board board, Player player, Move move) {
		if (move.getSubPoints() == null) {
			// This is a single move
			double distance = calculateDistance(move);
			if (Math.abs(distance) == 1) {
				// not jump
				if (board.getState(move.getEndPoint()) != null) {
					return false;
				}
				return true;
			} else if (Math.abs(distance) == 2) {
				// jump
				if (board.getState(move.getEndPoint()) != null) {
					return false;
				}

				Point middle = getMiddle(move, distance);
				if (board.getState(middle) != null) {
					return true;
				}
				return false;
			} else {
				return false;
			}
		} else {
			// This is a skip move
			return false;
		}
	}

	private static Point getMiddle(Move move, double distance) {
		if (move.getStartPoint().getX() == move.getEndPoint().getX()) {
			// change y
			return Pointer.getPoint(move.getStartPoint().getX(), move.getStartPoint().getY() + (int) (distance / 2));
		}

		if (move.getStartPoint().getY() == move.getEndPoint().getY()) {
			// change x
			return Pointer.getPoint(move.getStartPoint().getX() + (int) (distance / 2), move.getStartPoint().getY());
		}

		return null;
	}

	private static double calculateDistance(Move move) {
		return (move.getEndPoint().getY() - move.getStartPoint().getY())
				+ (move.getEndPoint().getX() - move.getStartPoint().getX());
	}

	private static List<Move> getSkips(Board board, Player player) {
		// TODO Auto-generated method stub
		return null;
	}

}
