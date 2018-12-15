package com.balki.gamer.player;

/**
 * 
 * @author Balki
 * @since 15/12/2018
 *
 */
public abstract class AbstractPlayer implements Player {
	private final String id;

	public AbstractPlayer(String id) {
		super();
		this.id = id;
	}

	@Override
	public String getId() {
		return id;
	}

}
