package de.edgesoft.abook2vcf;

import java.io.File;
import java.io.FileInputStream;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.logging.Level;

import de.edgesoft.abook2vcf.jmork.Address;
import de.edgesoft.abook2vcf.jmork.AddressBook;
import de.edgesoft.abook2vcf.jmork.AddressKeys;
import de.edgesoft.utilities.commandline.AbstractMainClass;
import de.edgesoft.utilities.commandline.CommandOption;
import de.edgesoft.utilities.logging.Log;


/**
 * Exports a thunderbird addressbook to one or several vcard (vcf) files.
 * 
 * @author Ekkart Kleinod
 * @version 0.2
 * @since 0.1
 */
public class ABook2VCF extends AbstractMainClass {

	/** Argument input file. */
	public final static CommandOption OPT_ABOOK = new CommandOption("a", "abook", true, "<abookfile> (default: abook.mab)", false);
	
	/** Argument output file. */
	public final static CommandOption OPT_OUTFILE = new CommandOption("o", "outputfile", true, "<output file> (default: abook.vcf)", false);
	
	/** Argument vcard count. */
	public final static CommandOption OPT_VCFCOUNT = new CommandOption("c", "count", true, "<#> (number of vcards per file, default: 0 = unlimited)", false);
	
	/** Argument vcard version. */
	public final static CommandOption OPT_VERSION = new CommandOption("v", "version", true, "<#.#> (vcard version, default: 3.0)", false);
	
	/** Argument doubles. */
	public final static CommandOption OPT_DOUBLES = new CommandOption("d", "doubles", false, "(write doubles' vcards)", false);
	
	/** Argument text dump. */
	public final static CommandOption OPT_TEXTDUMP = new CommandOption("t", "textdump", false, "(write text dump)", false);
	
	/** Argument csv dump. */
	public final static CommandOption OPT_CSVDUMP = new CommandOption("s", "csvdump", false, "(write csv dump)", false);
	
	/** Vcard file extension. */
	private final static String VCF_FILE_EXTENSION = ".vcf";
	
	/** Vcard version 3.0. */
	public final static String VERSION_3 = "3.0";
	
	/** Vcard version 4.0. */
	public final static String VERSION_4 = "4.0";
	
	/**
	 * Main method, called from command line.
	 * 
	 * @param args command line arguments
	 * 
	 * @version 0.1
	 * @since 0.1
	 */
	public static void main(String[] args) {
		
		addCommandOption(OPT_ABOOK);
		addCommandOption(OPT_OUTFILE);
		addCommandOption(OPT_VCFCOUNT);
		addCommandOption(OPT_VERSION);
		addCommandOption(OPT_DOUBLES);
		addCommandOption(OPT_TEXTDUMP);
		addCommandOption(OPT_CSVDUMP);
		
//		Log.getLgr().setLevel(Level.SEVERE);
		
		init(args, ABook2VCF.class);
		
		Log.getLgr().log(Level.INFO, "start.");
		
		try {
			String sInFile = getOptionValue(OPT_ABOOK);
			String sOutFile = getOptionValue(OPT_OUTFILE);
			
			int iVCFCount = 0;
			try {
				iVCFCount = Integer.parseInt(getOptionValue(OPT_VCFCOUNT));
			} catch (Exception e) {
				// do nothing, iVCFCount remains 0
			}
			
			String sVersion = getOptionValue(OPT_VERSION);
			boolean bWriteDoubles = hasOption(OPT_DOUBLES);
			boolean bWriteTextDump = hasOption(OPT_TEXTDUMP);
			boolean bWriteCsvDump = hasOption(OPT_CSVDUMP);
			
			convertABook(sInFile, sOutFile, iVCFCount, sVersion, bWriteDoubles, bWriteTextDump, bWriteCsvDump);
			
		} catch (Exception e) {
			Log.getLgr().log(Level.SEVERE, "oh no, an exception", e);
			System.err.println(getUsage());
			System.exit(1);
		}
		
		Log.getLgr().log(Level.INFO, "end.");
	}
	
	/**
	 * Converts abook to vcf file(s).
	 * 
	 * @param theInFile input file
	 * @param theOutFile output file
	 * @param theVCFCount max vcard count
	 * @param theVersion vcard version
	 * @param bWriteDoubles write doubles' vcards?
	 * @param bWriteTextDump write text dump?
	 * @param bWriteCsvDump write csv dump?
	 * 
	 * @throws ABookException if an error occurred during execution
	 * 
	 * @version 0.1
	 * @since 0.1
	 */
	public static void convertABook(String theInFile, String theOutFile, int theVCFCount, String theVersion, boolean bWriteDoubles, boolean bWriteTextDump, boolean bWriteCsvDump) throws ABookException {
		
		String sInFile = (theInFile == null) ? "abook.mab" : theInFile;
		String sOutFile = (theOutFile == null) ? "abook.vcf" : theOutFile;
		int iVCFCount = (theVCFCount < 0) ? 0 : theVCFCount;
		String sVersion = (theVersion == null) ? VERSION_3 : theVersion;
		if (!sVersion.equals(VERSION_3) && !sVersion.equals(VERSION_4)) {
			sVersion = VERSION_3;
		}
		
		try {
			AddressBook theAddressBook = loadAdresses(sInFile);
			
			Log.getLgr().log(Level.INFO, "address count: {0}", theAddressBook.getAddressesDBRowID().size());
			Log.getLgr().log(Level.INFO, "double count: {0}", theAddressBook.getAddressesDBRowIDRemoved().size());

			String sOutFilePattern = getOutFilePattern(theAddressBook.getAddressesDBRowID().size(), sOutFile, iVCFCount);

			writeVCards(theAddressBook.getAddressesDBRowID(), sOutFilePattern, iVCFCount, sVersion);

			if (bWriteDoubles) {
				File fleTemp = new File(sOutFilePattern);
				String sDoublePattern = String.format("double.%s", fleTemp.getName());
				if (fleTemp.getParent() != null) {
					sDoublePattern = String.format("%s%s%s", fleTemp.getParent(), System.getProperty("file.separator"), sDoublePattern);
				}
				writeVCards(theAddressBook.getAddressesDBRowIDRemoved(), sDoublePattern, iVCFCount, sVersion);
			}
			
			if (bWriteTextDump) {
				String sFileName = sOutFile;
				if (sFileName.endsWith(VCF_FILE_EXTENSION)) {
					sFileName = sFileName.substring(0, sFileName.length() - VCF_FILE_EXTENSION.length());
				}
				sFileName = String.format("%s.dump.txt", sFileName);
				writeTextDump(theAddressBook.getAddresses(), sFileName, sInFile);
			}
			
			if (bWriteCsvDump) {
				String sFileName = sOutFile;
				if (sFileName.endsWith(VCF_FILE_EXTENSION)) {
					sFileName = sFileName.substring(0, sFileName.length() - VCF_FILE_EXTENSION.length());
				}
				sFileName = String.format("%s.dump.csv", sFileName);
				writeCsvDump(theAddressBook.getAddresses(), sFileName);
			}
			
		} catch (Exception e) {
			throw new ABookException(e.getLocalizedMessage());
		}
		
	}
	
	/**
	 * Write vcf file(s).
	 * 
	 * @param theAddresses list of addresses
	 * @param theOutFilePattern output file pattern
	 * @param theVCFCount max vcard count
	 * @param theVersion vcard version
	 * 
	 * @throws ABookException if an error occurred during execution
	 * 
	 * @version 0.1
	 * @since 0.1
	 */
	private static void writeVCards(Collection<Address> theAddresses, String theOutFilePattern, int theVCFCount, String theVersion) throws ABookException {
		
		try {
			
			int iFileCount = 1;
			int iAddressesInFile = 0;
			StringBuffer sbFileContent = null;
			StringBuffer sbTemp = null;
			
			for (Address theAddress : theAddresses) {
				
				if (iAddressesInFile == 0) {
					sbFileContent = new StringBuffer();
				}
				
				// start
				sbFileContent.append(getLine("BEGIN", "VCARD", "\\n"));
				sbFileContent.append(getLine("VERSION", theVersion, "\\n"));
				
				// home address
				sbTemp = new StringBuffer();
				sbTemp.append(";;"); // PO Box; extended address
				sbTemp.append((theAddress.get("HomeAddress") == null) ? "" : theAddress.get("HomeAddress"));
				sbTemp.append(((theAddress.get("HomeAddress2") == null) || theAddress.get("HomeAddress2").isEmpty()) ? "" : "," + theAddress.get("HomeAddress2"));
				sbTemp.append(";");
				sbTemp.append((theAddress.get("HomeCity") == null) ? "" : theAddress.get("HomeCity"));
				sbTemp.append(";");
				sbTemp.append((theAddress.get("HomeState") == null) ? "" : theAddress.get("HomeState"));
				sbTemp.append(";");
				sbTemp.append((theAddress.get("HomeZipCode") == null) ? "" : theAddress.get("HomeZipCode"));
				sbTemp.append(";");
				sbTemp.append((theAddress.get("HomeCountry") == null) ? "" : theAddress.get("HomeCountry"));
				if (!sbTemp.toString().trim().equals(";;;;;;")) {
					sbFileContent.append(getLine("ADR;TYPE=home", sbTemp.toString(), ","));
				}
				
				// work address
				sbTemp = new StringBuffer();
				sbTemp.append(";;"); // PO Box; extended address
				sbTemp.append((theAddress.get("WorkAddress") == null) ? "" : theAddress.get("WorkAddress"));
				sbTemp.append(((theAddress.get("WorkAddress2") == null) || theAddress.get("WorkAddress2").isEmpty()) ? "" : "," + theAddress.get("WorkAddress2"));
				sbTemp.append(";");
				sbTemp.append((theAddress.get("WorkCity") == null) ? "" : theAddress.get("WorkCity"));
				sbTemp.append(";");
				sbTemp.append((theAddress.get("WorkState") == null) ? "" : theAddress.get("WorkState"));
				sbTemp.append(";");
				sbTemp.append((theAddress.get("WorkZipCode") == null) ? "" : theAddress.get("WorkZipCode"));
				sbTemp.append(";");
				sbTemp.append((theAddress.get("WorkCountry") == null) ? "" : theAddress.get("WorkCountry"));
				if (!sbTemp.toString().trim().equals(";;;;;;")) {
					sbFileContent.append(getLine("ADR;TYPE=work", sbTemp.toString(), ","));
				}
				
				// birthday
				if ((theAddress.get("BirthYear") != null) && !theAddress.get("BirthYear").isEmpty()) {
					sbFileContent.append(getLine("BDAY", String.format("%s%s%s", theAddress.get("BirthYear"), theAddress.get("BirthMonth"), theAddress.get("BirthDay")), "\\n"));
				}
				
				// categories
				sbFileContent.append(getLine("CATEGORIES", theAddress.get("Category"), "\\n"));
				
				// email
				sbFileContent.append(getLine("EMAIL;TYPE=primary", theAddress.get("PrimaryEmail"), "\\n"));
				sbFileContent.append(getLine("EMAIL;TYPE=second", theAddress.get("SecondEmail"), "\\n"));
				sbFileContent.append(getLine("EMAIL;TYPE=default", theAddress.get("DefaultEmail"), "\\n"));
				
				// formatted name
				sbTemp = new StringBuffer();
				if ((theAddress.get("DisplayName") == null) || theAddress.get("DisplayName").isEmpty()) {
					sbTemp.append((theAddress.get("FirstName") == null) ? "" : theAddress.get("FirstName") + " ");
					sbTemp.append((theAddress.get("LastName") == null) ? "" : theAddress.get("LastName"));
				} else {
					sbTemp.append(theAddress.get("DisplayName"));
				}
				if (!sbTemp.toString().trim().isEmpty()) {
					sbFileContent.append(getLine("FN", sbTemp.toString(), ","));
				}
				
				// structured name
				sbTemp = new StringBuffer();
				sbTemp.append((theAddress.get("LastName") == null) ? "" : theAddress.get("LastName"));
				sbTemp.append(";");
				sbTemp.append((theAddress.get("FirstName") == null) ? "" : theAddress.get("FirstName"));
				sbTemp.append(";;"); // additional names
				sbTemp.append((theAddress.get("NickName") == null) ? "" : theAddress.get("NickName"));
				sbTemp.append(";;"); // honorific prefixes and suffixes
				if (!sbTemp.toString().trim().equals(";;;;;")) {
					sbFileContent.append(getLine("N", sbTemp.toString(), ","));
				}

				// nickname
				sbFileContent.append(getLine("NICKNAME", theAddress.get("NickName"), ","));
				
				// organization
				sbTemp = new StringBuffer();
				sbTemp.append((theAddress.get("Company") == null) ? "" : theAddress.get("Company"));
				sbTemp.append(";");
				sbTemp.append((theAddress.get("Department") == null) ? "" : theAddress.get("Department"));
				sbTemp.append(";");
				sbTemp.append((theAddress.get("JobTitle") == null) ? "" : theAddress.get("JobTitle"));
				if (!sbTemp.toString().trim().equals(";;")) {
					sbFileContent.append(getLine("ORG", sbTemp.toString(), ","));
				}
				
				// profile
				if (theVersion.equals(VERSION_3)) {
					sbFileContent.append(getLine("PROFILE", "VCARD", "\\n"));
				}
				
				// telephone
				sbFileContent.append(getLine("TEL;TYPE=work", theAddress.get("WorkPhone"), "\\n"));
				sbFileContent.append(getLine("TEL;TYPE=home", theAddress.get("HomePhone"), "\\n"));
				sbFileContent.append(getLine("TEL;TYPE=fax", theAddress.get("FaxNumber"), "\\n"));
				sbFileContent.append(getLine("TEL;TYPE=pager", theAddress.get("PagerNumber"), "\\n"));
				sbFileContent.append(getLine("TEL;TYPE=cell", theAddress.get("CellularNumber"), "\\n"));
				
				// title
				sbFileContent.append(getLine("TITLE", theAddress.get("JobTitle"), "\\n"));
				
				// url
				sbTemp = new StringBuffer();
				sbTemp.append((theAddress.get("WebPage1") == null) ? "" : theAddress.get("WebPage1"));
				if ((theAddress.get("WebPage2") != null) && !theAddress.get("WebPage2").isEmpty()) {
					if (!sbTemp.toString().isEmpty()) {
						sbTemp.append(",");
					}
					sbTemp.append((theAddress.get("WebPage2") == null) ? "" : theAddress.get("WebPage2"));
				}
				if (!sbTemp.toString().trim().equals(";")) {
					sbFileContent.append(getLine("URL", sbTemp.toString(), ","));
				}
				
				// end
				sbFileContent.append(getLine("END", "VCARD", "\\n"));
				sbFileContent.append("\n");
				iAddressesInFile++;
				
				if (iAddressesInFile == theVCFCount) {
					writeFile(String.format(theOutFilePattern, iFileCount), sbFileContent.toString());
					iAddressesInFile = 0;
					iFileCount++;
				}
			}
			
			if (iAddressesInFile > 0) {
				writeFile(String.format(theOutFilePattern, iFileCount), sbFileContent.toString());
			}
			
		} catch (Exception e) {
			throw new ABookException(e.getLocalizedMessage());
		}
		
	}

	/**
	 * Returns a formatted VCard line.
	 * 
	 * @param theName name of the line
	 * @param theContent content of the line
	 * @param theNewLine new line replacement
	 * 
	 * @return formatted line
	 * 
	 * @version 0.1
	 * @since 0.1
	 */
	private static String getLine(String theName, String theContent, String theNewLine) {
		if ((theContent == null) || theContent.trim().isEmpty()) {
			return "";
		}
		return String.format("%s:%s\n", theName, theContent.trim().replace("\n", theNewLine).replace("\r", ""));
	}
	
	
	/**
	 * Returns the output file pattern.
	 * 
	 * @param theAddressCount number of addresses
	 * @param theOutFile output file
	 * @param theVCFCount max vcard count
	 * 
	 * @throws ABookException if an error occurred during execution
	 * 
	 * @version 0.1
	 * @since 0.1
	 */
	private static String getOutFilePattern(int theAddressCount, String theOutFile, int theVCFCount) {
		
		String sOutFilePattern = theOutFile;
		if (!sOutFilePattern.endsWith(VCF_FILE_EXTENSION)) {
			sOutFilePattern += VCF_FILE_EXTENSION;
		}
		
		if (theVCFCount != 0) {
			int iMaxFileCount = theAddressCount / theVCFCount;
			if ((theAddressCount % theVCFCount) > 0) {
				iMaxFileCount++;
			}
			sOutFilePattern = sOutFilePattern.replace(VCF_FILE_EXTENSION, String.format("%%0%dd%s", String.valueOf(iMaxFileCount).length(), VCF_FILE_EXTENSION));
		}

		return sOutFilePattern;
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
	private static AddressBook loadAdresses(String theInFile) throws ABookException {
		
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
				
				Log.getLgr().log(Level.INFO, "reading abook file: {0}", fleABook.getAbsoluteFile());
				
				theAddressBook.load(stmABook);
				
			} finally {
				if (stmABook != null) {
					Log.getLgr().log(Level.INFO, "closing abook file: {0}", fleABook.getAbsoluteFile());
					stmABook.close();
				}
			}
			
		} catch (Exception e) {
			throw new ABookException(e.getLocalizedMessage());
		}
		
		return theAddressBook;
	}
	
	/**
	 * Write text dump.
	 * 
	 * @param theAddresses list of addresses
	 * @param theFileName file name
	 * @param theABookName address book name
	 * 
	 * @throws ABookException if an error occurred during execution
	 * 
	 * @version 0.1
	 * @since 0.1
	 */
	private static void writeTextDump(Collection<Address> theAddresses, String theFileName, String theABookName) throws ABookException {
		
		try {
			
			StringBuffer sbFileContent = new StringBuffer();
			
			sbFileContent.append(MessageFormat.format("dumping address book: {0}\n\n", theABookName));
			sbFileContent.append(MessageFormat.format("address count: {0, number}\n", theAddresses.size()));
			sbFileContent.append("\n");
			
			for (Address theAddress : theAddresses) {
				
				sbFileContent.append("----- start address -----\n");
				for (AddressKeys theAddressKey : AddressKeys.values()) {
					sbFileContent.append(theAddressKey.getKey());
					sbFileContent.append(": ");
					if (theAddress.get(theAddressKey.getKey()) == null) {
						sbFileContent.append("null");
					} else {
						sbFileContent.append(theAddress.get(theAddressKey.getKey()).trim().replace("\n", "\\n").replace("\r", ""));
					}
					sbFileContent.append("\n");
				}
				sbFileContent.append("----- end address -----\n\n");
				
			}
			
			writeFile(theFileName, sbFileContent.toString());
			
		} catch (Exception e) {
			throw new ABookException(e.getLocalizedMessage());
		}
		
	}

	/**
	 * Write csv dump.
	 * 
	 * @param theAddresses list of addresses
	 * @param theFileName file name
	 * 
	 * @throws ABookException if an error occurred during execution
	 * 
	 * @version 0.1
	 * @since 0.1
	 */
	private static void writeCsvDump(Collection<Address> theAddresses, String theFileName) throws ABookException {
		
		try {
			
			StringBuffer sbFileContent = new StringBuffer();
			
			for (AddressKeys theAddressKey : AddressKeys.values()) {
				sbFileContent.append(String.format("\"%s\";", theAddressKey.getKey()));
			}
			sbFileContent.append("\n");
			
			for (Address theAddress : theAddresses) {
				for (AddressKeys theAddressKey : AddressKeys.values()) {
					sbFileContent.append(String.format("\"%s\";", 
							(theAddress.get(theAddressKey.getKey()) == null) ? "null" : theAddress.get(theAddressKey.getKey()).trim().replace("\n", "\\n").replace("\r", "")));
				}
				sbFileContent.append("\n");
			}
			
			writeFile(theFileName, sbFileContent.toString());
			
		} catch (Exception e) {
			throw new ABookException(e.getLocalizedMessage());
		}
		
	}

}

/* EOF */
