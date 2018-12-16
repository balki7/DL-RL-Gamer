package com.balki.gamer.gui;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.balki.gamer.player.PlayerType;

/**
 * 
 * @author Balki
 * @since 16/12/2018
 *
 */
public class OptionPanel  extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4459825247903276934L;

	private GameWindow gameWindow;
	
	private JComboBox<String> player1Opts;
	private JComboBox<String> player2Opts;
	
	private JButton startBtn;
	private JButton pauseBtn;
	private JButton stopBtn;
	
	public OptionPanel(GameWindow gameWindow) {
		super(new GridLayout(0, 1));
		
		this.gameWindow = gameWindow;
		
		final String[] playerTypeOpts = new String[PlayerType.values().length];
		for(int i=0; i< PlayerType.values().length; i++) {
			PlayerType type = PlayerType.values()[i];
			playerTypeOpts[i] = type.name();
		}
		
		this.player1Opts = new JComboBox<>(playerTypeOpts);
		this.player2Opts = new JComboBox<>(playerTypeOpts);
		
		this.startBtn = new JButton("Start");
		this.pauseBtn = new JButton("Pause");
		this.stopBtn = new JButton("Stop");
		
		this.pauseBtn.setVisible(false);
		this.stopBtn.setVisible(false);
		
		this.startBtn.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {
				startBtn.setVisible(false);
				pauseBtn.setVisible(true);
				stopBtn.setVisible(true);
				gameWindow.startGame(PlayerType.valueOf(PlayerType.class, player1Opts.getSelectedItem().toString()), PlayerType.valueOf(PlayerType.class, player2Opts.getSelectedItem().toString()));
			}
		});
		
		this.pauseBtn.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {
				startBtn.setVisible(true);
				pauseBtn.setVisible(false);
				stopBtn.setVisible(true);
				gameWindow.pauseGame();
			}
		});
		
		this.stopBtn.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {
				startBtn.setVisible(true);
				pauseBtn.setVisible(false);
				stopBtn.setVisible(false);
				gameWindow.stopGame();
			}
		});
		
		JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel middle = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel bottom = new JPanel(new FlowLayout(FlowLayout.CENTER));
		
		top.add(new JLabel("(black) Player 1: "));
		top.add(player1Opts);
		top.setBackground(Color.PINK);
		middle.add(new JLabel("(white) Player 2: "));
		middle.add(player2Opts);
		middle.setBackground(Color.PINK);
		bottom.add(startBtn);
		bottom.add(pauseBtn);
		bottom.add(stopBtn);
		bottom.setBackground(Color.PINK);
		
		this.add(top);
		this.add(middle);
		this.add(bottom);
		
		setBackground(Color.PINK);
	}
}
