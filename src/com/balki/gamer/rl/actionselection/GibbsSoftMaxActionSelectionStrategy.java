package com.balki.gamer.rl.actionselection;

import java.util.ArrayList;
import java.util.List;
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
public class GibbsSoftMaxActionSelectionStrategy extends AbstractActionSelectionStrategy {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6712552186318389770L;

	private Random random = null;

	public GibbsSoftMaxActionSelectionStrategy() {
		random = new Random();
	}

	public GibbsSoftMaxActionSelectionStrategy(Random random) {
		this.random = random;
	}

	@Override
	public Object clone() {
		GibbsSoftMaxActionSelectionStrategy clone = new GibbsSoftMaxActionSelectionStrategy();
		return clone;
	}

	@Override
	public IndexValue selectAction(int stateId, QModel model, Set<Integer> actionsAtState) {
		List<Integer> actions = new ArrayList<Integer>();
		if (actionsAtState == null) {
			for (int i = 0; i < model.getActionCount(); ++i) {
				actions.add(i);
			}
		} else {
			for (Integer actionId : actionsAtState) {
				actions.add(actionId);
			}
		}

		double sum = 0;
		List<Double> plist = new ArrayList<Double>();
		for (int i = 0; i < actions.size(); ++i) {
			int actionId = actions.get(i);
			double p = Math.exp(model.getQ(stateId, actionId));
			sum += p;
			plist.add(sum);
		}

		IndexValue iv = new IndexValue();
		iv.setIndex(-1);
		iv.setValue(Double.NEGATIVE_INFINITY);

		double r = sum * random.nextDouble();
		for (int i = 0; i < actions.size(); ++i) {

			if (plist.get(i) >= r) {
				int actionId = actions.get(i);
				iv.setValue(model.getQ(stateId, actionId));
				iv.setIndex(actionId);
				break;
			}
		}

		return iv;
	}
}
