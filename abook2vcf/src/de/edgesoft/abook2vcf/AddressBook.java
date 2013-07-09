package de.edgesoft.abook2vcf;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import mork.MorkDocument;
import mork.Row;
import mork.Table;


/**
 * An address book.
 * 
 * Reimplemented existing jmork implementation in order to access all values of addresses.
 * 
 * @author Ekkart Kleinod
 * @author jmork programmers
 * @version 0.1
 * @since 0.1
 */
public class AddressBook {

	/**
	 * Loads a Mork database and returns the loaded addresses.
	 * 
	 * It loads from the given input and parses it as being a
	 * Mozilla Thunderbird Address Book. The file is usually called abook.mab
	 * and is located in the Thunderbird user profile.
	 * 
	 * If additional address books are loaded into the same Address Book
	 * instance, the addresses get collected into the same address book.
	 * 
	 * @param inputStream the stream to load the address book from.
	 *  
	 * @version 0.1
	 * @since 0.1
	 */
	public static List<Address> load(final InputStream inputStream) {

		List<Address> lstAddresses = new LinkedList<Address>();
		
		if (inputStream == null) {
			throw new IllegalArgumentException("InputStream must not be null");
		}
		
		final MorkDocument morkDocument = new MorkDocument(new InputStreamReader(inputStream));
		
		for (Row row : morkDocument.getRows()) {
			final Address address = new Address(row.getValues());
			lstAddresses.add(address);
		}
		
		for (Table table : morkDocument.getTables()) {
			for (Row row : table.getRows()) {
				if (row.getValue("DisplayName") != null) {
					final Address address = new Address(row.getValues());
					lstAddresses.add(address);
				}
			}
		}
		
		return Collections.unmodifiableList(lstAddresses);
	}

}

/* EOF */
