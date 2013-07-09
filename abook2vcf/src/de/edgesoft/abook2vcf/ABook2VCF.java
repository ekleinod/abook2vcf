package de.edgesoft.abook2vcf;

import java.io.File;
import java.io.FileInputStream;
import java.text.MessageFormat;

import mozilla.thunderbird.Address;
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
	
	/** Argument vcard count. */
	private final static CommandOption OPT_VCFCOUNT = new CommandOption("c", "count", true, "number of vcards per file (default: 0 = unlimited)", false);
	
	/** Vcard file extension. */
	private final static String VCF_FILE_EXTENSION = ".vcf";
	
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
		addCommandOption(OPT_VCFCOUNT);
		
		init(args, ABook2VCF.class);
		
		try {
			String sInFile = (getOptionValue(OPT_ABOOK) == null) ? "abook.mab" : getOptionValue(OPT_ABOOK);
			String sOutFile = (getOptionValue(OPT_OUTFILE) == null) ? "abook.vcf" : getOptionValue(OPT_OUTFILE);
			int iVCFCount = 0;
			try {
				iVCFCount = Integer.parseInt(getOptionValue(OPT_VCFCOUNT));
			} catch (Exception e) {
				// do nothing, iVCFCount remains 0
			}
			if (iVCFCount < 0) {
				iVCFCount = 0;
			}
			
			convertABook(sInFile, sOutFile, iVCFCount);
			
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
	 * @param theVCFCount max vcard count
	 * 
	 * @throws ABookException if an error occurred during execution
	 * 
	 * @version 0.1
	 * @since 0.1
	 */
	public static void convertABook(String theInFile, String theOutFile, int theVCFCount) throws ABookException {
		
		try {
			AddressBook theAddressBook = loadABook(theInFile);
			printMessage(MessageFormat.format("address count: {0, number}", theAddressBook.getAddresses().size()));

			int iMaxFileCount = 1;
			if (theVCFCount != 0) {
				iMaxFileCount = theAddressBook.getAddresses().size() / theVCFCount;
				if ((theAddressBook.getAddresses().size() % theVCFCount) > 0) {
					iMaxFileCount++;
				}
			}
			printMessage(MessageFormat.format("file count: {0, number}", iMaxFileCount));
			
			String sOutFilePattern = theOutFile;
			if (!sOutFilePattern.endsWith(VCF_FILE_EXTENSION)) {
				sOutFilePattern += VCF_FILE_EXTENSION;
			}
			sOutFilePattern = sOutFilePattern.replace(VCF_FILE_EXTENSION, String.format("%%0%dd%s", String.valueOf(iMaxFileCount).length(), VCF_FILE_EXTENSION));
			
			int iFileCount = 1;
			int iAddressesInFile = 0;
			
			for (Address theAddress : theAddressBook.getAddresses()) {
				printMessage(MessageFormat.format("file: {0}", String.format(sOutFilePattern, iFileCount)));
				iAddressesInFile++;
			}
			
			
		} catch (Exception e) {
			throw new ABookException(e.getLocalizedMessage());
		}
		
	}
	
	/**
	 * Loads the address book.
	 * 
	 * @param theInFile input file
	 * 
	 * @throws ABookException if an error occurred during execution
	 * 
	 * @version 0.1
	 * @since 0.1
	 */
	private static AddressBook loadABook(String theInFile) throws ABookException {
		AddressBook theAddressBook = new AddressBook();
		
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
			
		} catch (Exception e) {
			throw new ABookException(e.getLocalizedMessage());
		}
		
		return theAddressBook;
		
	}
	
}

/* EOF */
