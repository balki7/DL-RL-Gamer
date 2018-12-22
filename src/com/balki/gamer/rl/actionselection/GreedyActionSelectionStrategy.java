package com.balki.gamer.rl.actionselection;

import java.util.Set;

import com.balki.gamer.rl.models.QModel;
import com.balki.gamer.rl.utils.IndexValue;

/**
 * 
 * @author Balki
 * @since 21/12/2018
 *
 */
public class GreedyActionSelectionStrategy extends AbstractActionSelectionStrategy {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3035837532843593020L;

	@Override
	public IndexValue selectAction(int stateId, QModel model, Set<Integer> actionsAtState) {
		return model.actionWithMaxQAtState(stateId, actionsAtState);
	}

	@Override
	public Object clone() {
		GreedyActionSelectionStrategy clone = new GreedyActionSelectionStrategy();
		return clone;
	}

	@Override
	public boolean equals(Object obj) {
		return obj != null && obj instanceof GreedyActionSelectionStrategy;
	}
}
