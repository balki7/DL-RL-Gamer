package com.balki.gamer.rl.models;

import java.util.*;

import com.balki.gamer.rl.utils.IndexValue;
import com.balki.gamer.rl.utils.Matrix;
import com.balki.gamer.rl.utils.Vec;

/**
 * 
 * @author Balki
 * @since 21/12/2018
 *
 *        Q is known as the quality of state-action combination, note that it is
 *        different from utility of a state
 */
public class QModel {
	/**
	 * Q value for (state_id, action_id) pair Q is known as the quality of
	 * state-action combination, note that it is different from utility of a state
	 */
	private Matrix Q;
	/**
	 * $\alpha[s, a]$ value for learning rate: alpha(state_id, action_id)
	 */
	private Matrix alphaMatrix;

	/**
	 * discount factor
	 */
	private double gamma = 0.7;

	private int stateCount;
	private int actionCount;

	public QModel(int stateCount, int actionCount, double initialQ) {
		this.stateCount = stateCount;
		this.actionCount = actionCount;
		Q = new Matrix(stateCount, actionCount);
		alphaMatrix = new Matrix(stateCount, actionCount);
		Q.setAll(initialQ);
		alphaMatrix.setAll(0.1);
	}

	public QModel(int stateCount, int actionCount) {
		this(stateCount, actionCount, 0.1);
	}

	public QModel() {

	}

	@Override
	public boolean equals(Object rhs) {
		if (rhs != null && rhs instanceof QModel) {
			QModel rhs2 = (QModel) rhs;

			if (gamma != rhs2.gamma)
				return false;

			if (stateCount != rhs2.stateCount || actionCount != rhs2.actionCount)
				return false;

			if ((Q != null && rhs2.Q == null) || (Q == null && rhs2.Q != null))
				return false;
			if ((alphaMatrix != null && rhs2.alphaMatrix == null) || (alphaMatrix == null && rhs2.alphaMatrix != null))
				return false;

			return !((Q != null && !Q.equals(rhs2.Q))
					|| (alphaMatrix != null && !alphaMatrix.equals(rhs2.alphaMatrix)));

		}
		return false;
	}

	public QModel makeCopy() {
		QModel clone = new QModel();
		clone.copy(this);
		return clone;
	}

	public void copy(QModel rhs) {
		gamma = rhs.gamma;
		stateCount = rhs.stateCount;
		actionCount = rhs.actionCount;
		Q = rhs.Q == null ? null : rhs.Q.makeCopy();
		alphaMatrix = rhs.alphaMatrix == null ? null : rhs.alphaMatrix.makeCopy();
	}

	public Matrix getQ() {
		return Q;
	}

	public void setQ(Matrix q) {
		Q = q;
	}

	public Matrix getAlphaMatrix() {
		return alphaMatrix;
	}

	public void setAlphaMatrix(Matrix alphaMatrix) {
		this.alphaMatrix = alphaMatrix;
	}

	public double getGamma() {
		return gamma;
	}

	public void setGamma(double gamma) {
		this.gamma = gamma;
	}

	public int getStateCount() {
		return stateCount;
	}

	public void setStateCount(int stateCount) {
		this.stateCount = stateCount;
	}

	public int getActionCount() {
		return actionCount;
	}

	public void setActionCount(int actionCount) {
		this.actionCount = actionCount;
	}

	public double getQ(int stateId, int actionId) {
		return Q.get(stateId, actionId);
	}

	public void setQ(int stateId, int actionId, double Qij) {
		Q.set(stateId, actionId, Qij);
	}

	public double getAlpha(int stateId, int actionId) {
		return alphaMatrix.get(stateId, actionId);
	}

	public void setAlpha(double defaultAlpha) {
		this.alphaMatrix.setAll(defaultAlpha);
	}

	public IndexValue actionWithMaxQAtState(int stateId, Set<Integer> actionsAtState) {
		Vec rowVector = Q.rowAt(stateId);
		return rowVector.indexWithMaxValue(actionsAtState);
	}

	public IndexValue actionWithSoftMaxQAtState(int stateId, Set<Integer> actionsAtState, Random random) {
		Vec rowVector = Q.rowAt(stateId);
		double sum = 0;

		if (actionsAtState == null) {
			actionsAtState = new HashSet<>();
			for (int i = 0; i < actionCount; ++i) {
				actionsAtState.add(i);
			}
		}

		List<Integer> actions = new ArrayList<>();
		for (Integer actionId : actionsAtState) {
			actions.add(actionId);
		}

		double[] acc = new double[actions.size()];
		for (int i = 0; i < actions.size(); ++i) {
			sum += rowVector.get(actions.get(i));
			acc[i] = sum;
		}

		double r = random.nextDouble() * sum;

		IndexValue result = new IndexValue();
		for (int i = 0; i < actions.size(); ++i) {
			if (acc[i] >= r) {
				int actionId = actions.get(i);
				result.setIndex(actionId);
				result.setValue(rowVector.get(actionId));
				break;
			}
		}

		return result;
	}
}
