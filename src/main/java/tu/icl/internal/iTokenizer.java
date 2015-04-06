package tu.icl.internal;


import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import com.google.common.io.Files;

public class iTokenizer {
	
	public Map<String, Double> toComonWordDict(String filename) throws IOException{
		
		String	text = Files.toString(new File(filename), StandardCharsets.UTF_8);
		StringTokenizer st = new StringTokenizer(text);
		Map<String, Double> lex_dict = new HashMap<String, Double>();
		Map<String, Double> result = new HashMap<String, Double>();
	
	    while (st.hasMoreTokens()) {
	    	 //update common word dictionary
	    	 iUtil.updateMap(st.nextToken(), lex_dict);
	    }
	    
	    double totalCount = 0.0;
	    //retain small words having more than three counts
	    result.putAll(lex_dict);
	    
	    for(Map.Entry<String, Double> entry: lex_dict.entrySet()){
	    	Double count = entry.getValue();
	    	String word = entry.getKey();
	    	if(count >3.0 && word.length() <=5){
	    		totalCount+=count;
	    	}
	    	else{
	    		result.remove(entry.getKey());
	    	}
	    }
	   
	    // compute probability of each small word
	    for(Map.Entry<String, Double> entry: result.entrySet() ){
	    	String word = entry.getKey();
	    	Double count = entry.getValue();
	    	result.put(word,count/totalCount);
	    }
	    	
	    return result;
	}
	
	///////////////////////////////////////////////////////////////////
	public Map<String, Double> toNGramDict(String filename) throws IOException{
		
		String	text = Files.toString(new File(filename), StandardCharsets.UTF_8);
		int n = text.length();
		Map<String, Double> ngram_dict = new HashMap<String, Double>();
		String _3gram;
		
		for(int i = 0; i < n -3 ; i++){
			_3gram = text.substring(i, i+3);
			iUtil.updateMap(_3gram, ngram_dict);
		}
		
		Map<String, Double> result = new HashMap<String, Double>();
	    double totalCount = 0.0;
	    result.putAll(ngram_dict);
	    
	    for(Map.Entry<String, Double> entry: ngram_dict.entrySet()){
	    	Double count = entry.getValue();
	    	// retain ngram having more than 100 occurrences 
	    	if(count >=100.0){
	    		totalCount+=count;
	    	}
	    	else{
	    		result.remove(entry.getKey());
	    	}
	    }
	    
	    // compute probability of each 3gram
	    for(Map.Entry<String, Double> entry: result.entrySet() ){
	    	String word = entry.getKey();
	    	Double count = entry.getValue();
	    	result.put(word,(double)count/totalCount);
	    }
	    
	    return result;
	}
	
	public Map<String, Double> lex_tokenize(String text){
		Map<String, Double> lex_dict = new HashMap<String, Double>();
		StringTokenizer st = new StringTokenizer(text);
	    while (st.hasMoreTokens()) {	    	 
			 iUtil.updateMap(st.nextToken(), lex_dict);
	    }
	    return lex_dict;
	}
	
	public Map<String, Double> ngram_tokenize(String text){
		int n = text.length();
		Map<String, Double> ngram_dict = new HashMap<String, Double>();
		String _3gram = "";
		for(int i = 0; i < n -3 ; i++){
			_3gram = text.substring(i, i+3);
			iUtil.updateMap(_3gram, ngram_dict);
		}
	    return ngram_dict;
	}

}
