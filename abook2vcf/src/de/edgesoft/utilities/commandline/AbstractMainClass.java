package de.edgesoft.utilities.commandline;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.PosixParser;


/**
 * Abstract class for classes with command line call.
 * 
 * @author Ekkart Kleinod
 * @version 0.1
 * @since 0.1
 */
public abstract class AbstractMainClass {
	
	/** Storage for options. */
	private static List<CommandOption> lstCommandOptions = null;
	
	/** Command line values. */
	private static CommandLine theCommandLine = null;
	
	/** Calling class. */
	private static Class<? extends AbstractMainClass> theCallingClass = null;

	/**
	 * Main method, called from command line.
	 * 
	 * @param args command line arguments
	 * @param theClass calling class
	 * 
	 * @version 0.1
	 * @since 0.1
	 */
	public static void init(String[] args, Class<? extends AbstractMainClass> theClass) {
		try {
			// store calling class
			theCallingClass = theClass;
			
			// handle commandline options with apache commons cli
			Options theOptions = new Options();
			for (CommandOption theCommandOption : lstCommandOptions) {
				theOptions.addOption(theCommandOption.getOption());
			}
			
			// parse options
			theCommandLine = new PosixParser().parse(theOptions, args);
		} catch (Exception e) {
			printError("");
			printError(getUsage());
			printError("");
			printError(e);
			System.exit(1);
		}
	}

	/**
	 * Adds a command option.
	 * 
	 * @param theCommandOption new command option
	 *  
	 * @version 0.1
	 * @since 0.1
	 */
	public static void addCommandOption(CommandOption theCommandOption) {
		if (lstCommandOptions == null) {
			lstCommandOptions = new ArrayList<CommandOption>();
		}
		lstCommandOptions.add(theCommandOption);
	}
	
	/**
	 * Returns value of given option.
	 * 
	 * @param theCommandOption option
	 * @return value
	 *  
	 * @version 0.1
	 * @since 0.1
	 */
	public static String getOptionValue(CommandOption theCommandOption) {
		return theCommandLine.getOptionValue(theCommandOption.getOption().getOpt());
	}

	/**
	 * Returns if given option is stated.
	 * 
	 * @param theCommandOption option
	 * @return if option in command line?
	 *  
	 * @version 0.1
	 * @since 0.1
	 */
	public static boolean hasOption(CommandOption theCommandOption) {
		return theCommandLine.hasOption(theCommandOption.getOption().getOpt());
	}

	/**
	 * Returns the usage message.
	 * 
	 * @return usage message
	 * 
	 * @version 0.1
	 * @since 0.1
	 */
	public static String getUsage() {
		StringBuffer sbReturn = new StringBuffer();

		sbReturn.append(MessageFormat.format("Call: java -jar {0}.jar{1}",
				theCallingClass.getSimpleName().toLowerCase(),
				System.getProperty("line.separator")));

		for (CommandOption theCommandOption : lstCommandOptions) {
			sbReturn.append(MessageFormat.format("\t{0}{1}",
					theCommandOption.getUsage(),
					System.getProperty("line.separator")));
		}

		return sbReturn.toString();
	}
	
	/**
	 * Write content to a file.
	 * 
	 * @param theFileName filename
	 * @param theContent file content
	 *  
	 * @version 0.1
	 * @since 0.1
	 */
	public static void writeFile(String theFileName, String theContent) throws Exception {
	        
		BufferedWriter wrtOutput = null;
		File fleOutput = new File(theFileName);
		if (fleOutput.getParentFile() != null) {
			fleOutput.getParentFile().mkdirs();
		}

		if (fleOutput.exists() && !fleOutput.isFile()) {
			throw new IOException(MessageFormat.format("File ''{0}'' is no file (maybe a directory?)", theFileName));
		}
		if (fleOutput.exists() && !fleOutput.canWrite()) {
			throw new IOException(MessageFormat.format("File ''{0}'' is not writeable.", theFileName));
		}
		
		try {
			wrtOutput = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fleOutput), "UTF-8"));
			wrtOutput.write(theContent);
		} finally {
			if (wrtOutput != null) {
				wrtOutput.close();
			}
		}
		printMessage(MessageFormat.format("Created file: ''{0}''.", fleOutput.getAbsolutePath()));
	}
	
	/**
	 * Prints a message.
	 * 
	 * @param theMessage the message
	 */
	public static void printMessage(String theMessage) {
		System.out.println(theMessage);
	}
	
	/**
	 * Prints an error message.
	 * 
	 * @param theError the error message
	 */
	public static void printError(String theError) {
		System.out.println(theError);
	}
	
	/**
	 * Prints an exception.
	 * 
	 * @param theException the exception
	 */
	public static void printError(Exception theException) {
		theException.printStackTrace();
	}
	
}

/* EOF */
