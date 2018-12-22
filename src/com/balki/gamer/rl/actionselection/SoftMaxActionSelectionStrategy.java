package com.balki.gamer.rl.actionselection;

import java.util.Random;
import java.util.Set;

import com.balki.gamer.rl.models.QModel;
import com.balki.gamer.rl.utils.IndexValue;

/**
 * 
 * @author Balki
 * @since 21/12/2018
 *
 */
public class SoftMaxActionSelectionStrategy extends AbstractActionSelectionStrategy {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5183164119005949171L;

	private Random random = new Random();

	@Override
	public Object clone() {
		SoftMaxActionSelectionStrategy clone = new SoftMaxActionSelectionStrategy(random);
		return clone;
	}

	@Override
	public boolean equals(Object obj) {
		return obj != null && obj instanceof SoftMaxActionSelectionStrategy;
	}

	public SoftMaxActionSelectionStrategy() {

	}

	public SoftMaxActionSelectionStrategy(Random random) {
		this.random = random;
	}

	@Override
	public IndexValue selectAction(int stateId, QModel model, Set<Integer> actionsAtState) {
		return model.actionWithSoftMaxQAtState(stateId, actionsAtState, random);
	}
}
