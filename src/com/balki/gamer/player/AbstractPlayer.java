package com.balki.gamer.player;

/**
 * 
 * @author Balki
 * @since 15/12/2018
 *
 */
public abstract class AbstractPlayer implements Player {
	private final String id;
	private final PlayerType type;
	private String[] finalPoints;
	private final String logFile;
	private int moveCount;

	public AbstractPlayer(PlayerType type, String id) {
		this(type, id, null, "moves" + id + ".txt", 0);
	}

	public AbstractPlayer(PlayerType type, String id, String[] finalPoints, String logFile,
			int moveCount) {
		super();
		this.id = id;
		this.type = type;
		this.finalPoints = finalPoints;
		this.logFile = logFile;
		this.moveCount = moveCount;
	}

	@Override
	public PlayerType getType() {
		return type;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public String getLogFile() {
		return logFile;
	}

	@Override
	public int getMoveCount() {
		return moveCount;
	}

	@Override
	public void incrementMoveCount() {
		this.moveCount++;
	}

	public void setMoveCount(int moveCount) {
		this.moveCount = moveCount;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		AbstractPlayer p = (AbstractPlayer) obj;
		return p.getId().equals(this.getId()) && p.getType().equals(this.getType());
	}

	@Override
	public void setFinalPoints(String[] finalPoints) {
		this.finalPoints = finalPoints;
	}

	@Override
	public String[] getFinalPoints() {
		return finalPoints;
	}

}
