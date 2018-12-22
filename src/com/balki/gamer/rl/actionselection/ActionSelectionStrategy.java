package com.balki.gamer.rl.actionselection;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

import com.balki.gamer.rl.models.QModel;
import com.balki.gamer.rl.models.UtilityModel;
import com.balki.gamer.rl.utils.IndexValue;

/**
 * 
 * @author Balki
 * @since 21/12/2018
 *
 */
public interface ActionSelectionStrategy extends Serializable, Cloneable {
	IndexValue selectAction(int stateId, QModel model, Set<Integer> actionsAtState);

	IndexValue selectAction(int stateId, UtilityModel model, Set<Integer> actionsAtState);

	String getPrototype();

	Map<String, String> getAttributes();
}
