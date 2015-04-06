/**
 * 
 */
package tu.icl.internal;

import java.io.Serializable;
import java.util.Map;

/**
 * @author nguyentiendat
 * 
 */

@SuppressWarnings("serial")
public class LangModel implements Serializable {
	private Map<String, Double> word_dict;
	private Map<String, Double> NGram_dict;
	private String language;

	public LangModel(Map<String, Double> word_dict,
			Map<String, Double> nGram_dict, String language) {
		super();
		this.word_dict = word_dict;
		NGram_dict = nGram_dict;
		this.language = language;
	}

	public Map<String, Double> getWord_dict() {
		return word_dict;
	}

	public void setWord_dict(Map<String, Double> word_dict) {
		this.word_dict = word_dict;
	}

	public Map<String, Double> getNGram_dict() {
		return NGram_dict;
	}

	public void setNGram_dict(Map<String, Double> nGram_dict) {
		NGram_dict = nGram_dict;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

}
