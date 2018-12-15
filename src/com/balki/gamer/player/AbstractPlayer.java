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
	private final String gameId;
	private String[] finalPoints;
	private final String logFile;
	private int moveCount;

	public AbstractPlayer(PlayerType type, String id, String gameId) {
		super();
		this.type = type;
		this.id = id;
		this.gameId = gameId;
		this.logFile = gameId + "_moves" + id + ".txt";
		this.moveCount = 0;
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

	public String getGameId() {
		return gameId;
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
