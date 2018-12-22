package com.balki.gamer.rl.actionselection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
public class EpsilonGreedyActionSelectionStrategy extends AbstractActionSelectionStrategy {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6472877921785988117L;

	public static final String EPSILON = "epsilon";
	private Random random = new Random();

	@Override
	public Object clone() {
		EpsilonGreedyActionSelectionStrategy clone = new EpsilonGreedyActionSelectionStrategy();
		clone.copy(this);
		return clone;
	}

	public void copy(EpsilonGreedyActionSelectionStrategy rhs) {
		random = rhs.random;
		for (Map.Entry<String, String> entry : rhs.attributes.entrySet()) {
			attributes.put(entry.getKey(), entry.getValue());
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (obj != null && obj instanceof EpsilonGreedyActionSelectionStrategy) {
			EpsilonGreedyActionSelectionStrategy rhs = (EpsilonGreedyActionSelectionStrategy) obj;
			if (epsilon() != rhs.epsilon())
				return false;
			// if(!random.equals(rhs.random)) return false;
			return true;
		}
		return false;
	}

	private double epsilon() {
		return Double.parseDouble(attributes.get(EPSILON));
	}

	public EpsilonGreedyActionSelectionStrategy() {
		epsilon(0.1);
	}

	public EpsilonGreedyActionSelectionStrategy(HashMap<String, String> attributes) {
		super(attributes);
	}

	private void epsilon(double value) {
		attributes.put(EPSILON, "" + value);
	}

	public EpsilonGreedyActionSelectionStrategy(Random random) {
		this.random = random;
		epsilon(0.1);
	}

	@Override
	public IndexValue selectAction(int stateId, QModel model, Set<Integer> actionsAtState) {
		if (random.nextDouble() < 1 - epsilon()) {
			return model.actionWithMaxQAtState(stateId, actionsAtState);
		} else {
			int actionId;
			if (actionsAtState != null && !actionsAtState.isEmpty()) {
				List<Integer> actions = new ArrayList<>(actionsAtState);
				actionId = actions.get(random.nextInt(actions.size()));
			} else {
				actionId = random.nextInt(model.getActionCount());
			}

			double Q = model.getQ(stateId, actionId);
			return new IndexValue(actionId, Q);
		}
	}
}
