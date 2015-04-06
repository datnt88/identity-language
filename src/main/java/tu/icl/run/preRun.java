/**
 * 
 */
package tu.icl.run;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.FileUtils;

import com.google.common.io.Files;

/**
 * @author nguyentiendat
 * 
 */
public class preRun {
	public static void main(String args[]) throws IOException {
		String text = Files.toString(new File("./dataset/cs.corpus"),
				StandardCharsets.UTF_8);

		String tmp = text;

		tmp = tmp.replaceAll("\\!", " ! ");
		tmp = tmp.replaceAll("\\@", " @ ");
		tmp = tmp.replaceAll("\\#", " # ");
		tmp = tmp.replaceAll("\\$", " $ ");
		tmp = tmp.replaceAll("\\%", " % ");
		tmp = tmp.replaceAll("\\^", " ^ ");
		tmp = tmp.replaceAll("\\&", " & ");
		tmp = tmp.replaceAll("\\*", " * ");
		tmp = tmp.replaceAll("\\(", " ( ");
		tmp = tmp.replaceAll("\\)", " ) ");
		tmp = tmp.replaceAll("\\_", " _ ");
		tmp = tmp.replaceAll("\\,", " , ");
		tmp = tmp.replaceAll("\\+", " + ");
		tmp = tmp.replaceAll("\\=", " = ");
		tmp = tmp.replaceAll("\\-", " - ");
		tmp = tmp.replaceAll("\\{", " { ");
		tmp = tmp.replaceAll("\\}", " } ");
		tmp = tmp.replaceAll("\\[", " [ ");
		tmp = tmp.replaceAll("\\]", " ] ");
		tmp = tmp.replaceAll("\\;", " ; ");
		tmp = tmp.replaceAll("\\'", " ' ");
		tmp = tmp.replaceAll("\\:", " : ");
		tmp = tmp.replaceAll("\\,", " , ");
		tmp = tmp.replaceAll("\\.", " . ");
		tmp = tmp.replaceAll("\\/", " / ");
		tmp = tmp.replaceAll("\\?", " ? ");
		tmp = tmp.replaceAll("\\>", " > ");
		tmp = tmp.replaceAll("\\<", " < ");
		tmp = tmp.replaceAll("\\`", " ` ");
		tmp = tmp.replaceAll("\\~", " ~ ");

		tmp = tmp.replaceAll("\n", " ");
		tmp = tmp.replaceAll("\\s+", " ");

		String xx = tmp.substring(0, 1000000);

		FileUtils.writeStringToFile(new File("./dataset/cs.train"), xx);

		System.out.println("Done..!");
	}
}
