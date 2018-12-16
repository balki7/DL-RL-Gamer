package com.balki.gamer.move;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.balki.gamer.board.Board;
import com.balki.gamer.game.Game;
import com.balki.gamer.player.Player;

/**
 * 
 * @author Balkı
 * @since 15/12/2018
 *
 */
public class Mover {
	public static Move getMove(Game game, Player player) {
		List<Move> validMoves = getAllMoves(game.getBoard(), player);

		if (validMoves.isEmpty()) {
			return null;
		}

		Map<Move, Double> moves = new HashMap<Move, Double>();
		for (Move m : validMoves) {
			Board board = (Board) game.getBoard().clone();
			board.put(m.getStartPoint().getId(), null);
			board.put(m.getEndPoint().getId(), player);
			double score = calculateScore(board, player);
			moves.put(m, score);
		}

		moves = sortByComparator(moves, true);

		Iterator<Move> it = moves.keySet().iterator();
		while (it.hasNext()) {
			return it.next();
		}

		return null;
	}

	private static Map<Move, Double> sortByComparator(Map<Move, Double> unsortMap, final boolean order) {
		List<Entry<Move, Double>> list = new LinkedList<Entry<Move, Double>>(unsortMap.entrySet());

		Collections.sort(list, new Comparator<Entry<Move, Double>>() {
			public int compare(Entry<Move, Double> o1, Entry<Move, Double> o2) {
				if (order) {
					return o1.getValue().compareTo(o2.getValue());
				} else {
					return o2.getValue().compareTo(o1.getValue());

				}
			}
		});

		Map<Move, Double> sortedMap = new LinkedHashMap<Move, Double>();
		for (Entry<Move, Double> entry : list) {
			sortedMap.put(entry.getKey(), entry.getValue());
		}

		return sortedMap;
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

		for (Point point : board.getPoints(player)) {
			getMoves(board, player, point, validMoves, null);
		}
		return validMoves;
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
		if (move.getSubMoves().size() == 1) {
			// This is a single move
			double distance = calculateDistance(move.getStartPoint(), move.getEndPoint());
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

				Point middle = getMiddle(move.getSubMoves().get(move.getSubMoves().size() - 1));
				if (board.getState(middle) != null) {
					return true;
				}
				return false;
			} else {
				return false;
			}
		} else {
			// This is a skip move
			SubMove subMove = move.getSubMoves().get(move.getSubMoves().size() - 1);
			SubMove lastSubMove = move.getSubMoves().get(move.getSubMoves().size() - 2);

			if (subMove.getDirection().getX() != 0 && lastSubMove.getDirection().getX() != 0) {
				return false;
			}

			if (subMove.getDirection().getY() != 0 && lastSubMove.getDirection().getY() != 0) {
				return false;
			}

			if (board.getState(move.getEndPoint()) != null) {
				return false;
			}

			Point middle = getMiddle(subMove);
			if (board.getState(middle) == null) {
				return false;
			}
			
			Set<Point> startPoints = new HashSet<Point>();
			Set<Point> endPoints = new HashSet<Point>();
			for(SubMove s : move.getSubMoves()) {
				if(endPoints.contains(s.getEndPoint())){
					return false;
				}
				
				if(startPoints.contains(s.getEndPoint())) {
					return false;
				}
				
				endPoints.add(s.getEndPoint());
				startPoints.add(s.getStartPoint());
			}
			
			return false;
		}
	}

	private static Point getMiddle(SubMove move) {
		return Pointer.getPoint((int) ((move.getStartPoint().getX() + move.getEndPoint().getX()) / 2),
				(int) ((move.getStartPoint().getY() + move.getEndPoint().getY()) / 2));
	}

	private static double calculateDistance(Point p1, Point p2) {
		return Math.sqrt(Math.pow((p2.getY() - p1.getY()), 2) + Math.pow((p2.getX() - p1.getX()), 2));
	}

	public static List<Move> getAllMoves(Board board, Player player, Point point) {
		List<Move> validMoves = new ArrayList<Move>();

		getMoves(board, player, point, validMoves, null);

		return validMoves;
	}

	private static void getMoves(Board board, Player player, Point p, List<Move> validMoves, Move move) {
		Set<Move> moves = new HashSet<Move>();

		if (move == null) {
			if (p.getX() + 1 < 8) {
				moves.add(generateMove(player, p, new Direction(1, 0)));
			}
			if (p.getX() - 1 >= 0) {
				moves.add(generateMove(player, p, new Direction(-1, 0)));
			}
			if (p.getY() + 1 < 8) {
				moves.add(generateMove(player, p, new Direction(0, 1)));
			}
			if (p.getY() - 1 >= 0) {
				moves.add(generateMove(player, p, new Direction(0, -1)));
			}

			if (p.getX() + 2 < 8) {
				moves.add(generateMove(player, p, new Direction(2, 0)));
			}
			if (p.getX() - 2 >= 0) {
				moves.add(generateMove(player, p, new Direction(-2, 0)));
			}
			if (p.getY() + 2 < 8) {
				moves.add(generateMove(player, p, new Direction(0, 2)));
			}
			if (p.getY() - 2 >= 0) {
				moves.add(generateMove(player, p, new Direction(0, -2)));
			}
		} else {
			if (move.getSubMoves().get(move.getSubMoves().size() - 1).isJump()) {
				if (p.getX() + 2 < 8) {
					List<SubMove> subMoveList = new ArrayList<SubMove>();
					for (SubMove s : move.getSubMoves()) {
						subMoveList.add(s);
					}
					Move m = new Move(player, subMoveList);
					addSubMove(m, new Direction(2, 0));
					moves.add(m);
				}

				if (p.getX() - 2 >= 0) {
					List<SubMove> subMoveList = new ArrayList<SubMove>();
					for (SubMove s : move.getSubMoves()) {
						subMoveList.add(s);
					}
					Move m = new Move(player, subMoveList);
					addSubMove(m, new Direction(-2, 0));
					moves.add(m);
				}

				if (p.getY() + 2 < 8) {
					List<SubMove> subMoveList = new ArrayList<SubMove>();
					for (SubMove s : move.getSubMoves()) {
						subMoveList.add(s);
					}
					Move m = new Move(player, subMoveList);
					addSubMove(m, new Direction(0, 2));
					moves.add(m);
				}

				if (p.getY() - 2 >= 0) {
					List<SubMove> subMoveList = new ArrayList<SubMove>();
					for (SubMove s : move.getSubMoves()) {
						subMoveList.add(s);
					}
					Move m = new Move(player, subMoveList);
					addSubMove(m, new Direction(0, -2));
					moves.add(m);
				}
			}
		}

		Set<Move> newValidMoves = getValidMoves(board, player, moves);
		validMoves.addAll(newValidMoves);

		for (Move m : newValidMoves) {
			getMoves(board, player, m.getEndPoint(), validMoves, m);
		}
	}

	private static void addSubMove(Move move, Direction direction) {
		Point endPoint = Pointer.getPoint(move.getEndPoint().getX() + direction.getX(),
				move.getEndPoint().getY() + direction.getY());
		boolean jump = calculateDistance(move.getEndPoint(), endPoint) == 2;
		SubMove subMove = new SubMove(move.getEndPoint(), endPoint, direction, jump);
		move.getSubMoves().add(subMove);
	}

	private static double calculateScore(Board board, Player player) {
		Set<Point> points = board.getPoints(player);

		List<Point> inFinalPoints = new ArrayList<Point>();
		List<Point> notInFinalPoints = new ArrayList<Point>();
		for (Point point : points) {
			if (isFinalPoint(player, point)) {
				inFinalPoints.add(point);
			} else {
				notInFinalPoints.add(point);
			}
		}

		List<Point> emptyFinalPoints = new ArrayList<Point>();
		List<Point> notEmptyFinalPoints = new ArrayList<Point>();
		String[] finalPoints = player.getFinalPoints();
		for (String f : finalPoints) {
			Point point = Pointer.getPoint(f);
			Player owner = board.getState(point);
			if (owner == null) {
				emptyFinalPoints.add(point);
			} else {
				notEmptyFinalPoints.add(point);
			}
		}

		Map<Point, Point> finalPointMapper = new HashMap<Point, Point>();

		while (finalPointMapper.size() != finalPoints.length) {
			findFinalPoint(finalPointMapper, notInFinalPoints, inFinalPoints, emptyFinalPoints, notEmptyFinalPoints);
		}

		double totalDistance = 0;

		for (Point point : finalPointMapper.keySet()) {
			Point finalPoint = finalPointMapper.get(point);
			double distance = calculateDistance(point, finalPoint);
			totalDistance += distance;
		}

		return totalDistance;
	}

	private static void findFinalPoint(Map<Point, Point> finalPointMapper, List<Point> notInFinalPoints,
			List<Point> inFinalPoints, List<Point> emptyFinalPoints, List<Point> notEmptyFinalPoints) {
		double minDistance = 100;
		Point finalPoint = null;
		Point point = null;
		for (Point p : notInFinalPoints) {
			if (finalPointMapper.containsKey(p)) {
				continue;
			}

			for (Point f : emptyFinalPoints) {
				if (finalPointMapper.containsValue(f)) {
					continue;
				}

				double distance = calculateDistance(p, f);
				if (distance < minDistance) {
					minDistance = distance;
					finalPoint = f;
					point = p;
				}
			}
		}

		if (finalPoint != null && point != null) {
			finalPointMapper.put(point, finalPoint);
			return;
		}

		for (Point p : notInFinalPoints) {
			if (finalPointMapper.containsKey(p)) {
				continue;
			}

			for (Point f : notEmptyFinalPoints) {
				if (finalPointMapper.containsValue(f)) {
					continue;
				}

				double distance = calculateDistance(p, f);
				if (distance < minDistance) {
					minDistance = distance;
					finalPoint = f;
					point = p;
				}
			}
		}

		if (finalPoint != null && point != null) {
			finalPointMapper.put(point, finalPoint);
			return;
		}

		for (Point p : inFinalPoints) {
			if (finalPointMapper.containsKey(p)) {
				continue;
			}

			for (Point f : notEmptyFinalPoints) {
				if (finalPointMapper.containsValue(f)) {
					continue;
				}

				double distance = calculateDistance(p, f);
				if (distance < minDistance) {
					minDistance = distance;
					finalPoint = f;
					point = p;
				}
			}
		}

		if (finalPoint != null && point != null) {
			finalPointMapper.put(point, finalPoint);
			return;
		}

		for (Point p : inFinalPoints) {
			if (finalPointMapper.containsKey(p)) {
				continue;
			}

			for (Point f : emptyFinalPoints) {
				if (finalPointMapper.containsValue(f)) {
					continue;
				}

				double distance = calculateDistance(p, f);
				if (distance < minDistance) {
					minDistance = distance;
					finalPoint = f;
					point = p;
				}
			}
		}

		if (finalPoint != null && point != null) {
			finalPointMapper.put(point, finalPoint);
			return;
		}
		return;
	}

	public static boolean isJump(Point point1, Point point2) {
		return calculateDistance(point1, point2) == 2;
	}

	public static Move generateMove(Board board, Player player, Point startPoint, Point endPoint) {
		List<Move> allMoves = getAllMoves(board, player, startPoint);
		for (Move m : allMoves) {
			if (m.getStartPoint().equals(startPoint) && m.getEndPoint().equals(endPoint)) {
				return m;
			}
		}

		return null;
	}

	public static Move generateMove(Player player, Point startPoint, Direction direction) {
		Point endPoint = Pointer.getPoint(startPoint.getX() + direction.getX(), startPoint.getY() + direction.getY());
		boolean jump = calculateDistance(startPoint, endPoint) == 2;
		List<SubMove> subMoves = new ArrayList<SubMove>();
		SubMove subMove = new SubMove(startPoint, endPoint, direction, jump);
		subMoves.add(subMove);
		return new Move(player, subMoves);

	}

}
