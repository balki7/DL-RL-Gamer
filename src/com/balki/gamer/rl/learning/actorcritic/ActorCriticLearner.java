package com.balki.gamer.rl.learning.actorcritic;

import java.io.Serializable;
import java.util.Set;
import java.util.function.Function;

import com.balki.gamer.rl.actionselection.AbstractActionSelectionStrategy;
import com.balki.gamer.rl.actionselection.ActionSelectionStrategy;
import com.balki.gamer.rl.actionselection.ActionSelectionStrategyFactory;
import com.balki.gamer.rl.actionselection.GibbsSoftMaxActionSelectionStrategy;
import com.balki.gamer.rl.models.QModel;
import com.balki.gamer.rl.utils.IndexValue;
import com.balki.gamer.util.JSONManager;

/**
 * 
 * @author Balki
 * @since 21/12/2018
 *
 */
public class ActorCriticLearner implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4080618415250269008L;

	protected QModel P;
	protected ActionSelectionStrategy actionSelectionStrategy;

	public String toJson() {
		return JSONManager.toJson(this);
	}

	public static ActorCriticLearner fromJson(String json) {
		return JSONManager.fromJson(ActorCriticLearner.class, json);
	}

	public Object makeCopy() {
		ActorCriticLearner clone = new ActorCriticLearner();
		clone.copy(this);
		return clone;
	}

	public void copy(ActorCriticLearner rhs) {
		P = rhs.P.makeCopy();
		actionSelectionStrategy = (ActionSelectionStrategy) ((AbstractActionSelectionStrategy) rhs.actionSelectionStrategy)
				.clone();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj != null && obj instanceof ActorCriticLearner) {
			ActorCriticLearner rhs = (ActorCriticLearner) obj;
			return P.equals(rhs.P) && getActionSelection().equals(rhs.getActionSelection());
		}
		return false;
	}

	public ActorCriticLearner() {

	}

	public ActorCriticLearner(int stateCount, int actionCount) {
		this(stateCount, actionCount, 1, 0.7, 0.01);
	}

	public int selectAction(int stateId, Set<Integer> actionsAtState) {
		IndexValue iv = actionSelectionStrategy.selectAction(stateId, P, actionsAtState);
		return iv.getIndex();
	}

	public int selectAction(int stateId) {
		return selectAction(stateId, null);
	}

	public ActorCriticLearner(int stateCount, int actionCount, double beta, double gamma, double initialP) {
		P = new QModel(stateCount, actionCount, initialP);
		P.setAlpha(beta);
		P.setGamma(gamma);

		actionSelectionStrategy = new GibbsSoftMaxActionSelectionStrategy();
	}

	public void update(int currentStateId, int currentActionId, int newStateId, double immediateReward,
			Function<Integer, Double> V) {
		update(currentStateId, currentActionId, newStateId, null, immediateReward, V);
	}

	public void update(int currentStateId, int currentActionId, int newStateId, Set<Integer> actionsAtNewState,
			double immediateReward, Function<Integer, Double> V) {
		double td_error = immediateReward + V.apply(newStateId) - V.apply(currentStateId);

		double oldP = P.getQ(currentStateId, currentActionId);
		double beta = P.getAlpha(currentStateId, currentActionId);
		double newP = oldP + beta * td_error;
		P.setQ(currentStateId, currentActionId, newP);
	}

	public String getActionSelection() {
		return ActionSelectionStrategyFactory.serialize(actionSelectionStrategy);
	}

	public void setActionSelection(String conf) {
		this.actionSelectionStrategy = ActionSelectionStrategyFactory.deserialize(conf);
	}

	public QModel getP() {
		return P;
	}

	public void setP(QModel p) {
		P = p;
	}
}
