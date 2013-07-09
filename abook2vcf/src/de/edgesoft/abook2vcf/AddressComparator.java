package de.edgesoft.abook2vcf;

import java.util.Comparator;

import mozilla.thunderbird.Address;

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
 * @author Ekkart Kleinod
 * @author ibauersachs
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
		
		if (o1.getLastName() != null && o2.getLastName() != null) {
			iComparison = o1.getLastName().compareToIgnoreCase(o2.getLastName());
			if (iComparison != 0) {
				return iComparison;
			}
		}
		
		if (o1.getFirstName() != null && o2.getFirstName() != null) {
			iComparison = o1.getFirstName().compareToIgnoreCase(o2.getFirstName());
			if (iComparison != 0) {
				return iComparison;
			}
		}
		
		if (o1.getDisplayName() != null && o2.getDisplayName() != null) {
			iComparison = o1.getDisplayName().compareToIgnoreCase(o2.getDisplayName());
			if (iComparison != 0) {
				return iComparison;
			}
		}
		
		if (o1.getPrimaryEmail() != null && o2.getPrimaryEmail() != null) {
			iComparison = o1.getPrimaryEmail().compareToIgnoreCase(o2.getPrimaryEmail());
			if (iComparison != 0) {
				return iComparison;
			}
		}
		
		return 0;
	}

}

/* EOF */
