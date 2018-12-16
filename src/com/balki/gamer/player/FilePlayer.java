package com.balki.gamer.player;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.balki.gamer.game.Game;
import com.balki.gamer.move.Move;
import com.balki.gamer.move.Mover;
import com.balki.gamer.move.Pointer;
import com.balki.gamer.util.FileManager;

/**
 * 
 * @author Balki
 * @since 15/12/2018
 *
 */
public class FilePlayer extends AbstractPlayer {

	public FilePlayer(String id) {
		super(PlayerType.FILE, id);
	}

	public FilePlayer(String id, String[] finalPoints, String logFile, int moveCount) {
		super(PlayerType.FILE, id, finalPoints, logFile, moveCount);
	}

	@Override
	public void setTurn(Game game) {
		class MoverThread extends Thread {
			public void run() {
				try {
					int moveCount = game.getCurrentPlayer().getMoveCount();
					moveCount++;
					
					String move = FileManager.read(getLogFile(), moveCount);
					
					Pattern pattern = Pattern.compile("(\\d+)[ ]([a-h]{1}[1-8]{1})[ ]([a-h]{1}[1-8]{1})");
			        Matcher matcher = pattern.matcher(move);
			        while (matcher.find()) {
			            if(moveCount != Integer.parseInt(matcher.group(1))) {
			            	System.out.println("Not OK move count!");
			            }
			            
			            String startPointId = matcher.group(2);
			            String endPointId = matcher.group(3);
			            
						game.move(new Move(game.getCurrentPlayer(), Pointer.getPoint(startPointId), Pointer.getPoint(endPointId)));
			        }
				} catch (IOException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

		MoverThread moverThread = new MoverThread();
		moverThread.start();
	}

}
