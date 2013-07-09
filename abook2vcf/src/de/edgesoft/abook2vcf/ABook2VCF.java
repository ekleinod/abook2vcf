package de.edgesoft.abook2vcf;

import de.edgesoft.utilities.commandline.AbstractMainClass;
import de.edgesoft.utilities.commandline.CommandOption;


/**
 * Exports a thunderbird addressbook to one or several vcard (vcf) files.
 * 
 * @author Ekkart Kleinod
 * @version 0.1
 * @since 0.1
 */
public class ABook2VCF extends AbstractMainClass {

	/** Argument input file. */
	private final static CommandOption OPT_DTD = new CommandOption("i", "input", true, "inputfile (default: abook.mab)", false);
	
	/** Argument output file. */
	private final static CommandOption OPT_OUTFILE = new CommandOption("o", "outputfile", true, "output file (default: abook.vcf)", false);
	
	/** Argument output directory. */
	private final static CommandOption OPT_OUTDIR = new CommandOption("d", "outputdir", true, "output directory (default: vcards)", false);
	
	/** Argument vcard count. */
	private final static CommandOption OPT_VCFCOUNT = new CommandOption("c", "count", true, "number of vcards per file (default: -1 = unlimited)", false);
	
	/**
	 * Main method, called from command line.
	 * 
	 * @param args command line arguments
	 * 
	 * @version 0.1
	 * @since 0.1
	 */
	public static void main(String[] args) {
		System.out.println("start.");
		
		addCommandOption(OPT_DTD);
		addCommandOption(OPT_OUTFILE);
		addCommandOption(OPT_OUTDIR);
		addCommandOption(OPT_VCFCOUNT);
		
		init(args);
		
		try {
			// to do :)
			String sOutFile = (getOptionValue(OPT_OUTFILE) == null) ? "abook.vcf" : getOptionValue(OPT_OUTFILE);
			writeFile(sOutFile, "todo");
			
		} catch (Exception e) {
			System.err.println();
			System.err.println(getUsage(ABook2VCF.class));
			System.err.println();
			e.printStackTrace();
			System.exit(1);
		}
		
		System.out.println("end.");
	}
	
}

/* EOF */
