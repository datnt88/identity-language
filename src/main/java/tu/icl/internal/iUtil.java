/**
 * 
 */
package tu.icl.internal;

import java.util.Collections;
import java.util.Map;

/**
 * @author nguyentiendat
 * 
 */
public class iUtil {
	public static void updateMap(String key, Map<String, Double> map) {
		if (!map.containsKey(key)) {
			map.put(key, 1.0);
		} else {
			Double v = map.get(key);
			map.put(key, v + 1);
		}
	}

	public static String getMaxByValue(Map<String, Double> map) {
		double maxValue = (Collections.max(map.values()));
		for (Map.Entry<String, Double> entry : map.entrySet()) {
			if (entry.getValue() == maxValue) {
				return entry.getKey();
			}
		}
		return null;
	}

	public static double getMinValue(Map<String, Double> map) {
		return Collections.min(map.values());
	}

	public static double computeProbability(Map<String, Double> lex_test,
			Map<String, Double> lex_train) {
		double prob = 1.0;
		// get minimum prob of a word which is not in training dict
		double minProb = getMinValue(lex_train);

		for (Map.Entry<String, Double> entry : lex_test.entrySet()) {
			String word = entry.getKey();
			double count = entry.getValue();

			if (!lex_train.containsKey(word)) {
				prob *= minProb;
			} else {
				prob *= count * lex_train.get(word);
			}
		}
		return prob;
	}

}
