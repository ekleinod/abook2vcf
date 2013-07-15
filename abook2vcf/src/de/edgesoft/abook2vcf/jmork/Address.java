package de.edgesoft.abook2vcf.jmork;

import java.util.Map;

import mork.Alias;

/**
 * Represents a single address in a Mozilla Thunderbird address book.
 * 
 * For a list of properties known to Thunderbird Addresses see {@link AddressKeys}.
 * 
 * @author mhaller
 * @author ibauersachs
 * @author Ekkart Kleinod (ekleinod)
 */
public class Address {

	/** Map of aliases. */
	private final Map<String, Alias> aliases;

	/**
	 * Constructor setting alias map.
	 * 
	 * @param mapAliases alias map
	 */
	public Address(Map<String, Alias> mapAliases) {
		aliases = mapAliases;
	}
	
	/**
	 * Returns value to given property key.
	 * 
	 * @param thePropertyKey property key
	 * @return alias value
	 *  @retval null if property key could not be found
	 */
	public Map<String, Alias> getAliases() {
		return aliases;
	}

	/**
	 * Returns value to given property key.
	 * 
	 * @param thePropertyKey property key
	 * @return alias value
	 *  @retval null if property key could not be found
	 */
	public String get(String thePropertyKey) {
		Alias a = aliases.get(thePropertyKey);
		if (a == null) {
			return null;
		}
		return a.getValue();
	}

	/**
	 * Returns value to given property key.
	 * 
	 * @param thePropertyKey property key
	 * @return alias value
	 *  @retval null if property key could not be found
	 */
	public String get(AddressKeys thePropertyKey) {
		return get(thePropertyKey.getKey());
	}

	/**
	 * Returns value to given property key without null return.
	 * 
	 * @param thePropertyKey property key
	 * @return alias value
	 *  @retval empty string if property key could not be found
	 */
	public String getString(String thePropertyKey) {
		String sReturn = get(thePropertyKey);
		if (sReturn == null) {
			return "";
		}
		return sReturn;
	}

	/**
	 * Returns value to given property key without null return.
	 * 
	 * @param thePropertyKey property key
	 * @return alias value
	 *  @retval empty string if property key could not be found
	 */
	public String getString(AddressKeys thePropertyKey) {
		return getString(thePropertyKey.getKey());
	}

}

/* EOF */
