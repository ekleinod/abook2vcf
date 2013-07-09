package de.edgesoft.utilities.commandline;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
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

	/**
	 * Main method, called from command line.
	 * 
	 * @param args command line arguments
	 * 
	 * @version 0.1
	 * @since 0.1
	 */
	public static void init(String[] args) {
		try {
			// handle commandline options with apache commons cli
			Options theOptions = new Options();
			for (CommandOption theCommandOption : lstCommandOptions) {
				theOptions.addOption(theCommandOption.getOption());
			}
			
			// parse options
			theCommandLine = new PosixParser().parse(theOptions, args);
		} catch (Exception e) {
			System.err.println();
			System.err.println(getUsage(AbstractMainClass.class));
			System.err.println();
			e.printStackTrace();
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
			lstCommandOptions = new ArrayList<>();
		}
		lstCommandOptions.add(theCommandOption);
	}
	
	/**
	 * Returns value of given option.
	 * 
	 * @param theOptionKey key of the option
	 * @return value
	 *  
	 * @version 0.1
	 * @since 0.1
	 */
	public static String getOptionValue(CommandOption theCommandOption) {
		return theCommandLine.getOptionValue(theCommandOption.getOption().getOpt());
	}

	/**
	 * Returns the usage message.
	 * 
	 * @param theClass calling class
	 * @return usage message
	 * 
	 * @version 0.1
	 * @since 0.1
	 */
	public static String getUsage(Class<?> theClass) {
		StringBuffer sbReturn = new StringBuffer();

		sbReturn.append(MessageFormat.format("Call: java -jar {0}.jar{1}",
				theClass.getSimpleName().toLowerCase(),
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
		try {
			wrtOutput = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fleOutput), "UTF-8"));
			wrtOutput.write(theContent);
		} finally {
			if (wrtOutput != null) {
				wrtOutput.close();
			}
		}
		System.out.println(MessageFormat.format("Created file: ''{0}''.", fleOutput.getAbsolutePath()));
	}
	
}

/* EOF */
