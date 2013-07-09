package de.edgesoft.abook2vcf;

/**
 * Special exception for abook.
 * 
 * @author Ekkart Kleinod
 * @version 0.1
 * @since 0.1
 */
public class ABookException extends Exception {
	
	/** Default serial id. */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Default constructor with message.
	 * 
	 * @param s error message
	 */
	public ABookException(String s) {
		super(s);
	}

}

/* EOF */
