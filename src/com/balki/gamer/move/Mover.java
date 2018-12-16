package com.balki.gamer.move;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
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
		List<Move> validMoves = getAllMoves(board, player);

		if (validMoves.isEmpty()) {
			return null;
		}

		List<Move> moves = new ArrayList<Move>();
		for (Move m : validMoves) {
			if (isFinalPoint(player, m.getStartPoint())) {
				continue;
			}

			moves.add(m);
		}

		return moves.get(0);
	}

	private static boolean isFinalPoint(Player player, Point startPoint) {
		String[] finalPoints = player.getFinalPoints();

		for (String finalPoint : finalPoints) {
			if (finalPoint.equals(startPoint.getId())) {
				return true;
			}
		}

		return false;
	}

	public static List<Move> getAllMoves(Board board, Player player) {
		List<Move> validMoves = new ArrayList<Move>();

		validMoves.addAll(getMoves(board, player));

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

	public static boolean isValid(Board board, Player player, Move move) {
		if (move.getSubPoints() == null) {
			// This is a single move
			int distance = calculateDistance(move.getStartPoint(), move.getEndPoint());
			if (distance == 1) {
				// not jump
				if (board.getState(move.getEndPoint()) != null) {
					return false;
				}
				return true;
			} else if (distance == 2) {
				// jump
				if (board.getState(move.getEndPoint()) != null) {
					return false;
				}

				Point middle = getMiddle(move);
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

	private static Point getMiddle(Move move) {
		return Pointer.getPoint((int)((move.getStartPoint().getX() + move.getEndPoint().getX()) / 2), (int) ((move.getStartPoint().getY() + move.getEndPoint().getY()) / 2));
	}

	private static int calculateDistance(Point p1, Point p2) {
		return Math.abs(p2.getY() - p1.getY()) + Math.abs(p2.getX() - p1.getX());
	}

	private static List<Move> getSkips(Board board, Player player) {
		// TODO Auto-generated method stub
		return null;
	}

	public static List<Move> getAllMoves(Board board, Player player, Point point) {
		List<Move> validMoves = new ArrayList<Move>();

		validMoves.addAll(getMoves(board, player, point));

		return validMoves;
	}

	private static Set<Move> getMoves(Board board, Player player, Point p) {
		Set<Move> moves = new HashSet<Move>();
		
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
	
		return getValidMoves(board, player, moves);
	}

}
