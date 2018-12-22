package com.balki.gamer.rl.learning.rlearn;

import java.io.Serializable;
import java.util.Set;

import com.balki.gamer.rl.actionselection.AbstractActionSelectionStrategy;
import com.balki.gamer.rl.actionselection.ActionSelectionStrategy;
import com.balki.gamer.rl.actionselection.ActionSelectionStrategyFactory;
import com.balki.gamer.rl.actionselection.EpsilonGreedyActionSelectionStrategy;
import com.balki.gamer.rl.models.QModel;
import com.balki.gamer.rl.utils.IndexValue;
import com.balki.gamer.util.JSONManager;

/**
 * 
 * @author Balki
 * @since 21/12/2018
 *
 */
public class RLearner implements Serializable, Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2029847117936782788L;

	private QModel model;
	private ActionSelectionStrategy actionSelectionStrategy;
	private double rho;
	private double beta;

	public String toJson() {
		return JSONManager.toJson(this);
	}

	public static RLearner fromJson(String json) {
		return JSONManager.fromJson(RLearner.class, json);
	}

	public RLearner makeCopy() {
		RLearner clone = new RLearner();
		clone.copy(this);
		return clone;
	}

	public void copy(RLearner rhs) {
		model = rhs.model.makeCopy();
		actionSelectionStrategy = (ActionSelectionStrategy) ((AbstractActionSelectionStrategy) rhs.actionSelectionStrategy)
				.clone();
		rho = rhs.rho;
		beta = rhs.beta;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj != null && obj instanceof RLearner) {
			RLearner rhs = (RLearner) obj;
			if (!model.equals(rhs.model))
				return false;
			if (!actionSelectionStrategy.equals(rhs.actionSelectionStrategy))
				return false;
			if (rho != rhs.rho)
				return false;
			return beta == rhs.beta;
		}
		return false;
	}

	public RLearner() {

	}

	public double getRho() {
		return rho;
	}

	public void setRho(double rho) {
		this.rho = rho;
	}

	public double getBeta() {
		return beta;
	}

	public void setBeta(double beta) {
		this.beta = beta;
	}

	public QModel getModel() {
		return model;

	}

	public void setModel(QModel model) {
		this.model = model;
	}

	public String getActionSelection() {
		return ActionSelectionStrategyFactory.serialize(actionSelectionStrategy);
	}

	public void setActionSelection(String conf) {
		this.actionSelectionStrategy = ActionSelectionStrategyFactory.deserialize(conf);
	}

	public RLearner(int stateCount, int actionCount) {
		this(stateCount, actionCount, 0.1, 0.1, 0.7, 0.1);
	}

	public RLearner(int state_count, int action_count, double alpha, double beta, double rho, double initial_Q) {
		model = new QModel(state_count, action_count, initial_Q);
		model.setAlpha(alpha);

		this.rho = rho;
		this.beta = beta;

		actionSelectionStrategy = new EpsilonGreedyActionSelectionStrategy();
	}

	private double maxQAtState(int stateId, Set<Integer> actionsAtState) {
		IndexValue iv = model.actionWithMaxQAtState(stateId, actionsAtState);
		double maxQ = iv.getValue();
		return maxQ;
	}

	public void update(int currentState, int actionTaken, int newState, Set<Integer> actionsAtNextStateId,
			double immediate_reward) {
		double oldQ = model.getQ(currentState, actionTaken);

		double alpha = model.getAlpha(currentState, actionTaken); // learning rate;

		double maxQ = maxQAtState(newState, actionsAtNextStateId);

		double newQ = oldQ + alpha * (immediate_reward - rho + maxQ - oldQ);

		double maxQAtCurrentState = maxQAtState(currentState, null);
		if (newQ == maxQAtCurrentState) {
			rho = rho + beta * (immediate_reward - rho + maxQ - maxQAtCurrentState);
		}

		model.setQ(currentState, actionTaken, newQ);
	}

	public IndexValue selectAction(int stateId, Set<Integer> actionsAtState) {
		return actionSelectionStrategy.selectAction(stateId, model, actionsAtState);
	}
}
