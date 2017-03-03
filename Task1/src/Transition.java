import java.util.HashMap;
import java.util.Map;

public class Transition {

	private Map<Integer, Map<Character, Integer>> transition = new HashMap<>();

	    public void setTransition(Integer startState, Integer goalState, char character) {
	        transition.putIfAbsent(startState, new HashMap<>());
	        transition.get(startState).put(character, goalState);
	    }

	    public Integer getGoalState(Integer startState, char character) {
	        if (!transition.containsKey(startState)) {
	            return null;
	        }

	        return transition.get(startState).get(character);
	    }
	    
}
