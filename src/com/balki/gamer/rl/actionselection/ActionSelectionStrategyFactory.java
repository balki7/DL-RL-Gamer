package com.balki.gamer.rl.actionselection;

import java.util.HashMap;
import java.util.Map;


/**
 * 
 * @author Balki
 * @since 21/12/2018
 *
 */
public class ActionSelectionStrategyFactory {
    public static ActionSelectionStrategy deserialize(String conf){
        String[] comps = conf.split(";");

        HashMap<String, String> attributes = new HashMap<String, String>();
        for(int i=0; i < comps.length; ++i){
            String comp = comps[i];
            String[] field = comp.split("=");
            if(field.length < 2) continue;
            String fieldname = field[0].trim();
            String fieldvalue = field[1].trim();

            attributes.put(fieldname, fieldvalue);
        }
        if(attributes.isEmpty()){
            attributes.put("prototype", conf);
        }

        String prototype = attributes.get("prototype");
        if(prototype.equals(GreedyActionSelectionStrategy.class.getCanonicalName())){
            return new GreedyActionSelectionStrategy();
        } else if(prototype.equals(SoftMaxActionSelectionStrategy.class.getCanonicalName())){
            return new SoftMaxActionSelectionStrategy();
        } else if(prototype.equals(EpsilonGreedyActionSelectionStrategy.class.getCanonicalName())){
            return new EpsilonGreedyActionSelectionStrategy(attributes);
        } else if(prototype.equals(GibbsSoftMaxActionSelectionStrategy.class.getCanonicalName())){
            return new GibbsSoftMaxActionSelectionStrategy();
        }

        return null;
    }

    public static String serialize(ActionSelectionStrategy strategy){
        Map<String, String> attributes = strategy.getAttributes();
        attributes.put("prototype", strategy.getPrototype());

        StringBuilder sb = new StringBuilder();
        boolean first = true;
        for(Map.Entry<String, String> entry : attributes.entrySet()){
            if(first){
                first = false;
            }
            else{
                sb.append(";");
            }
            sb.append(entry.getKey()+"="+entry.getValue());
        }
        return sb.toString();
    }
}
