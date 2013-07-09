package de.edgesoft.abook2vcf;

import java.util.Map;


/**
 * An address.
 * 
 * Reimplemented existing jmork implementation in order to access all values of addresses.
 * 
 * @author Ekkart Kleinod
 * @author jmork programmers
 * @version 0.1
 * @since 0.1
 */
public class Address {

	/** Storage for values. */
	private final Map<String, String> mapValues;
	
	/**
	 * Constructor setting values.
	 * 
	 * @param theValues map of values
	 *  
	 * @version 0.1
	 * @since 0.1
	 */
	public Address(Map<String, String> theValues) {
		mapValues = theValues;
	}

	/**
	 * Returns value for the given key.
	 * 
	 * @param theKey key of value
	 * @return value for key
	 *  @retval null if no value for the key exists
	 *  
	 * @version 0.1
	 * @since 0.1
	 */
	public String getValue(String theKey) {
		return mapValues.get(theKey);
	}

}

/* EOF */
