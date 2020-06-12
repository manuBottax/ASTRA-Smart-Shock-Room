package utils;

import java.util.Comparator;

import org.json.JSONObject;

public class PriorityComparator implements Comparator<JSONObject>{

	public int compare(JSONObject o1, JSONObject o2) {
		if (o1.getInt("priority") < o1.getInt("priority"))
			return -1;
		else if (o1.getInt("priority") == o2.getInt("priority"))
			return 0; 
		else 
			return 1;
	}

}
