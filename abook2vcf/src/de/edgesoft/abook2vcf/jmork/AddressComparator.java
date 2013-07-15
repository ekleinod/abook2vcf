package de.edgesoft.abook2vcf.jmork;

import java.util.Comparator;


/**
 * Compares two addresses.
 * 
 * Two addresses are sorted according to these fields:
 * 1. last name
 * 2. first name
 * 3. display name
 * 4. primary email address
 * 
 * They are equal, if all fields are equal.
 * 
 * Inspired by ibauersachs.
 * 
 * @author ibauersachs
 * @author Ekkart Kleinod (ekleinod)
 * @version 0.1
 * @since 0.1
 */
public class AddressComparator implements Comparator<Address> {

	/**
	 * Compares two addresses.
	 * 
	 * @version 0.1
	 * @since 0.1
	 */
	public int compare(Address o1, Address o2) {
		int iComparison = 0;
		
		String sO1 = o1.get(AddressKeys.LAST_NAME);
		String sO2 = o2.get(AddressKeys.LAST_NAME);
		if ((sO1 != null) && (sO2 != null)) {
			iComparison = sO1.compareToIgnoreCase(sO2);
			if (iComparison != 0) {
				return iComparison;
			}
		}
		
		sO1 = o1.get(AddressKeys.FIRST_NAME);
		sO2 = o2.get(AddressKeys.FIRST_NAME);
		if ((sO1 != null) && (sO2 != null)) {
			iComparison = sO1.compareToIgnoreCase(sO2);
			if (iComparison != 0) {
				return iComparison;
			}
		}
		
		sO1 = o1.get(AddressKeys.DISPLAY_NAME);
		sO2 = o2.get(AddressKeys.DISPLAY_NAME);
		if ((sO1 != null) && (sO2 != null)) {
			iComparison = sO1.compareToIgnoreCase(sO2);
			if (iComparison != 0) {
				return iComparison;
			}
		}
		
		sO1 = o1.get(AddressKeys.PRIMARY_EMAIL);
		sO2 = o2.get(AddressKeys.PRIMARY_EMAIL);
		if ((sO1 != null) && (sO2 != null)) {
			iComparison = sO1.compareToIgnoreCase(sO2);
			if (iComparison != 0) {
				return iComparison;
			}
		}
		
		return 0;
	}

}

/* EOF */
