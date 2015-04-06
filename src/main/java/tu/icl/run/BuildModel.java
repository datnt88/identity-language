/**
 * 
 */
package tu.icl.run;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.DecimalFormat;
import java.util.Map;

import static javax.script.ScriptEngine.NAME;

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

/**
 * @author nguyentiendat file to run building language model
 * 
 */
public class BuildModel {

	public void run(String[] args) throws IOException {

		String dir = "./model/";
		if (!new File(dir).exists()) {
			new File("./model/").mkdir();
		}

		CommandLine cl = parseArguments01(args);
		String corpusFile = cl.getOptionValue("c");
		String language = cl.getOptionValue("l").toUpperCase();

		System.out.println("\n======= Start building Language Model =====");
		System.out.println("Language: " + language);
		iTokenizer it = new iTokenizer();
		Map<String, Double> word_dict = it.toComonWordDict(corpusFile);
		Map<String, Double> ngram_dict = it.toNGramDict(corpusFile);
		LangModel model = new LangModel(word_dict, ngram_dict, language);

		// print some basic information
		DecimalFormat df = new DecimalFormat("#.#####");
		System.out.println("---------------------------------------------");
		System.out.println("Most frequency words vs probabilities: ");
		String key = "";
		for (int i = 0; i < 10; i++) {
			key = iUtil.getMaxByValue(word_dict);
			System.out
					.println(" " + key + "\t" + df.format(word_dict.get(key)));
			word_dict.remove(key);
		}

		System.out.println("Most frequency 3gram vs probabilities: ");
		for (int i = 0; i < 10; i++) {
			key = iUtil.getMaxByValue(ngram_dict);
			System.out.println(" " + key.replaceAll("\\s+", "_") + "\t"
					+ df.format(ngram_dict.get(key)));
			ngram_dict.remove(key);
		}

		// save model to folder
		FileOutputStream fileOut = new FileOutputStream(dir
				+ language.toUpperCase() + ".ser");
		ObjectOutputStream out = new ObjectOutputStream(fileOut);
		out.writeObject(model);
		System.out.println("\n------ Serialized in folder ./model/ ------");
		out.close();
		fileOut.close();

	}

	@SuppressWarnings({ "static-access" })
	public CommandLine parseArguments01(String[] args) {
		Options options = new Options();
		Option c = OptionBuilder
				.withArgName("document.corpus")
				.hasArg()
				.withDescription(
						"file containing 1 milion charaters of the language")
				.withLongOpt("corpus").isRequired().create('c');
		Option l = OptionBuilder.withArgName("Language").hasArg()
				.withDescription("Language of text corpus")
				.withLongOpt("language").isRequired().create('l');

		options.addOption(c);
		options.addOption(l);

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

	public static void main(String args[]) throws IOException {
		BuildModel build = new BuildModel();
		build.run(args);
	}
}
