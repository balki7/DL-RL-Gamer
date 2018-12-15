package com.balki.gamer.player;

import com.balki.gamer.game.Game;

/**
 * 
 * @author Balki
 * @since 15/12/2018
 *
 */
public interface Player {

	String getId();
	
	PlayerType getType();

	String[] getFinalPoints();

	void setFinalPoints(String[] finalPoints);

	String getLogFile();

	int getMoveCount();

	void incrementMoveCount();

	void setTurn(Game game);

}
