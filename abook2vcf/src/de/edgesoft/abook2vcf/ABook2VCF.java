package de.edgesoft.abook2vcf;

import java.io.File;
import java.io.FileInputStream;
import java.text.MessageFormat;

import mozilla.thunderbird.AddressBook;
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
	private final static CommandOption OPT_ABOOK = new CommandOption("i", "input", true, "inputfile (default: abook.mab)", false);
	
	/** Argument output file. */
	private final static CommandOption OPT_OUTFILE = new CommandOption("o", "outputfile", true, "output file (default: abook.vcf)", false);
	
	/** Argument output directory. */
	private final static CommandOption OPT_OUTDIR = new CommandOption("d", "outputdir", true, "output directory (default: vcards)", false);
	
	/** Argument vcard count. */
	private final static CommandOption OPT_VCFCOUNT = new CommandOption("c", "count", true, "number of vcards per file (default: 0 = unlimited)", false);
	
	/**
	 * Main method, called from command line.
	 * 
	 * @param args command line arguments
	 * 
	 * @version 0.1
	 * @since 0.1
	 */
	public static void main(String[] args) {
		printMessage("start.");
		
		addCommandOption(OPT_ABOOK);
		addCommandOption(OPT_OUTFILE);
		addCommandOption(OPT_OUTDIR);
		addCommandOption(OPT_VCFCOUNT);
		
		init(args, ABook2VCF.class);
		
		try {
			String sInFile = (getOptionValue(OPT_ABOOK) == null) ? "abook.mab" : getOptionValue(OPT_ABOOK);
			String sOutFile = (getOptionValue(OPT_OUTFILE) == null) ? "abook.vcf" : getOptionValue(OPT_OUTFILE);
			String sOutDir = (getOptionValue(OPT_OUTDIR) == null) ? "vcards" : getOptionValue(OPT_OUTDIR);
			int iVCFCount = 0;
			try {
				iVCFCount = Integer.parseInt(getOptionValue(OPT_VCFCOUNT));
			} catch (Exception e) {
				// do nothing, iVCFCount remains 0
			}
			if (iVCFCount < 0) {
				iVCFCount = 0;
			}
			
			convertABook(sInFile, sOutFile, sOutDir, iVCFCount);
			
		} catch (Exception e) {
			printError("");
			printError(getUsage());
			printError("");
			printError(e);
			System.exit(1);
		}
		
		printMessage("end.");
	}
	
	/**
	 * Converts abook to vcf file(s).
	 * 
	 * @param theInFile input file
	 * @param theOutFile output file
	 * @param theOutDir output directory
	 * @param theVCFCount max vcard count3
	 * 
	 * @throws ABookException if an error occured during execution
	 * 
	 * @version 0.1
	 * @since 0.1
	 */
	public static void convertABook(String theInFile, String theOutFile, String theOutDir, int theVCFCount) throws ABookException {
		File fleABook = new File(theInFile);
		
		if (!fleABook.exists()) {
			throw new ABookException(MessageFormat.format("File ''{0}'' does not exist.", theInFile));
		}
		if (!fleABook.canRead()) {
			throw new ABookException(MessageFormat.format("File ''{0}'' is not readable.", theInFile));
		}
		if (!fleABook.isFile()) {
			throw new ABookException(MessageFormat.format("File ''{0}'' is no file (maybe a directory?)", theInFile));
		}
		
		try {
			FileInputStream stmABook = null;
			
			AddressBook theAddressBook = new AddressBook();
			
			try {
				stmABook = new FileInputStream(fleABook);
				
				printMessage(MessageFormat.format("reading abook file: ''{0}''", fleABook.getAbsoluteFile()));
				
				theAddressBook.load(stmABook);
				
			} finally {
				if (stmABook != null) {
					printMessage(MessageFormat.format("closing abook file: ''{0}''", fleABook.getAbsoluteFile()));
					stmABook.close();
				}
			}
			
			printMessage(MessageFormat.format("address count: {0, number}", theAddressBook.getAddresses().size()));
			
			
		} catch (Exception e) {
			throw new ABookException(e.getLocalizedMessage());
		}
		
	}
	
}

/* EOF */
