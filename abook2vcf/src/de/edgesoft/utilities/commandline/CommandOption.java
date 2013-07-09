package de.edgesoft.utilities.commandline;

import java.text.MessageFormat;

import org.apache.commons.cli.Option;



/**
 * Wrapper for all data of a apache command line option.
 * 
 * @author Ekkart Kleinod
 * @version 0.1
 * @since 0.1
 */
public class CommandOption {

	/** Apache option. */
	private Option optOption = null;
	
	/**
	 * Constructor, initializing the apache option object
	 *  
	 * @version 0.1
	 * @since 0.1
	 */
	public CommandOption(String opt, String longOpt, boolean hasArg, String description, boolean isRequired) {
		optOption = new Option(opt, longOpt, hasArg, description);
		optOption.setRequired(isRequired);
	}
	
	/**
	 * Returns apache option object.
	 * 
	 * @return apache option object
	 *  
	 * @version 0.1
	 * @since 0.1
	 */
	public Option getOption() {
		return optOption;
	}
	
	/**
	 * Returns usage formatted string.
	 * 
	 * @return usage formatted string
	 *  
	 * @version 0.1
	 * @since 0.1
	 */
	public String getUsage() {
		return MessageFormat.format((getOption().isRequired()) ? "{0}" : "[{0}]", 
				MessageFormat.format("[-{0}|-{1}] {2}",
						getOption().getOpt(),
						getOption().getLongOpt(),
						(getOption().hasArg()) ? getOption().getDescription() : ""));
	}
	
}

/* EOF */
