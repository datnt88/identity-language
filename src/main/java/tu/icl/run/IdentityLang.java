/**
 * 
 */
package tu.icl.run;

import static javax.script.ScriptEngine.NAME;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;

import tu.icl.internal.LangModel;
import tu.icl.internal.iTokenizer;
import tu.icl.internal.iUtil;

import com.google.common.io.Files;



/**
 * @author nguyentiendat
 * file to run the task of language identity 
 * pick the language which most sentences in the document are written in.
 */
public class IdentityLang {
	
	public List<LangModel> loadModels() {
		String dir = "./model/";
		if(!new File(dir).exists()){
			System.out.println("Non exists model folder..! Please try build-model.jar first");
			return null;
		}
		
		List<LangModel> listOfModels = new ArrayList<LangModel>();
		try {
			
			
			File[] files = new File(dir).listFiles();
			String filename = "";
			for (File f : files) {
				filename = f.getName();
				//System.out.println(filename);
			    if (f.isFile() && filename.contains(".ser")) {
			    	FileInputStream fileIn = new FileInputStream(dir + filename);
			    	ObjectInputStream in = new ObjectInputStream(fileIn);
			    	listOfModels.add((LangModel) in.readObject());
			    	in.close();
					fileIn.close();
			    }
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listOfModels; 
	}
	
	public void run(String args[]){
		System.out.println("\n========== Starting Language Identity task =======");
		CommandLine cl = parseArguments02(args);
		String tech = cl.getOptionValue("t");
		String filename = cl.getOptionValue("d");
		
		
		//reading document
		String text = "";
		try {
			text = Files.toString(new File(filename), StandardCharsets.UTF_8);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//pre process remove \n and long white spaces
		text = text.replaceAll("\n", "");
		text = text.replaceAll("\\s+", " ");
		String[] sentences = text.split("\\.+");
		int n = sentences.length;
		
		iTokenizer it = new iTokenizer();
		List<LangModel> listOfModels = new ArrayList<LangModel>(); 
		listOfModels = loadModels();
		int nModel = listOfModels.size();
		
		Map<String, Double> result = new HashMap<String, Double>();
		
		if(tech.equals("ngram")){
			for(int i =0; i < n; i++){
				Map<String, Double> lex_sen = it.ngram_tokenize(sentences[i]);
				double max_prob = 0.0;
				String lang = "";
				for(int j =0; j<nModel; j++){
					LangModel model = listOfModels.get(j);
					double prob = iUtil.computeProbability(lex_sen, model.getNGram_dict());
					if(prob>max_prob){
						max_prob = prob;
						lang = model.getLanguage();
					}
				}
				if(max_prob >0){
					iUtil.updateMap(lang, result);
				}
			}
			
		}
		else{
			for(int i =0; i < n; i++){
				Map<String, Double> lex_sen = it.lex_tokenize(sentences[i]);
				double max_prob = 0.0;
				String lang = "";
				for(int j =0; j<nModel; j++){
					LangModel model = listOfModels.get(j);
					double prob = iUtil.computeProbability(lex_sen, model.getWord_dict());
					if(prob>max_prob){
						max_prob = prob;
						lang = model.getLanguage();
					}
				}
				if(max_prob >0){
					iUtil.updateMap(lang, result);
				}
			}
		}
		
		if(tech.equals("ngram")){
			System.out.println("Techinique: Trigram ...!");
		}
		else{
			System.out.println("Techinique: Small Word ...!");
		}
		System.out.println("Document: " + new File(filename).getName());
		
		DecimalFormat df = new DecimalFormat("#.##");
		for (Map.Entry<String, Double> entry : result.entrySet()) {
			System.out.println(" * Has: " + df.format(entry.getValue()) + "\t sentences in " + entry.getKey());
		}
		//print the most detected language
		System.out.println("--------------------------------------------------");
		System.out.println("The main language: " + iUtil.getMaxByValue(result));
		System.out.println("==================================================");
	}
	
	@SuppressWarnings({ "static-access", "restriction" })
    public CommandLine parseArguments02(String[] args) {
        Options options = new Options();
        Option d
                = OptionBuilder
                .withArgName("testing document")
                .hasArg()
                .withDescription(
                        "file containing testing document")
                .withLongOpt("document").isRequired().create("d");
        Option t
        		= OptionBuilder.withArgName("technique").hasArg()
        		.withDescription("techique to run ngam or smallword").withLongOpt("technique")
        		.isRequired().create('t');
        
        options.addOption(d);
        options.addOption(t);

        CommandLineParser parser = new GnuParser();
        CommandLine line = null;
        try {
            line = parser.parse(options, args);
        } catch (Exception e) {
            System.err.println("ERROR: " + e.getMessage() + "\n");
            new HelpFormatter().printHelp(NAME, options, true);
            System.exit(-1);
        }
        return line;
    }
	
	public static void main(String args[]){
		IdentityLang r = new IdentityLang();
		r.run(args);
	}
}
