package de.edgesoft.abook2vcf.jmork;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import mork.ExceptionHandler;
import mork.MorkDocument;
import mork.Row;
import mork.Table;

/**
 * An address book is a container for addresses loaded from a Mozilla
 * Thunderbird address book, which is stored in the Mork file format.
 * 
 * @author mhaller
 * @author ibauersachs
 * @author Ekkart Kleinod (ekleinod)
 * 
 * @version 0.2
 * @since 0.1
 */
public class AddressBook {

	/** Internal container for addresses. */
	private final List<Address> addresses = new LinkedList<>();
	
	/** Storage of exception handler. */
	private ExceptionHandler exceptionHandler = null;

	/** Internal singleton container for addresses in their last modified form. */
	private List<Address> lstAddressesDBRowID = null;
	
	/** Internal singleton container for removed addresses (last modified). */
	private List<Address> lstAddressesDBRowIDRemoved = null;
	
	/**
	 * Default constructor.
	 * 
	 * @version 0.2
	 * @since 0.1
	 */
	public AddressBook() {
		lstAddressesDBRowID = new ArrayList<>();
		lstAddressesDBRowIDRemoved = new ArrayList<>();
	}
	
	/**
	 * Loads a Mork database from the given input and parses it as being a
	 * Mozilla Thunderbird Address Book. 
	 * 
	 * The file is usually called abook.mab
	 * and is located in the Thunderbird user profile.
	 * 
	 * If additional address books are loaded into the same Address Book
	 * instance, the addresses get collected into the same address book.
	 * 
	 * @param inputStream the stream to load the address book from.
	 * 
	 * @version 0.2
	 * @since 0.1
	 */
	public void load(final InputStream inputStream) {
		
		if (inputStream == null) {
			throw new IllegalArgumentException("InputStream must not be null");
		}
		
		final MorkDocument morkDocument = new MorkDocument(new InputStreamReader(inputStream), exceptionHandler);

		// load addresses
		for (Row row : morkDocument.getRows()) {
			final Address address = new Address(row.getAliases());
			addresses.add(address);
		}
		
		for (Table table : morkDocument.getTables()) {
			for (Row row : table.getRows()) {
				if (row.getValue("DisplayName") != null) {
					final Address address = new Address(row.getAliases());
					addresses.add(address);
				}
			}
		}
		
		// DBRowID
		Map<String, Address> mapTemp = new HashMap<>();
		lstAddressesDBRowIDRemoved = new ArrayList<>();
		for (Address theAddress : addresses) {
			if (mapTemp.containsKey(theAddress.getString(AddressKeys.DB_ROW_I_D))) {
				if (theAddress.getString(AddressKeys.LAST_MODIFIED_DATE).compareToIgnoreCase(
						mapTemp.get(theAddress.getString(AddressKeys.DB_ROW_I_D)).getString(AddressKeys.LAST_MODIFIED_DATE)) > 0) {
					lstAddressesDBRowIDRemoved.add(mapTemp.get(theAddress.getString(AddressKeys.DB_ROW_I_D)));
					mapTemp.put(theAddress.getString(AddressKeys.DB_ROW_I_D), theAddress);
				} else {
					lstAddressesDBRowIDRemoved.add(theAddress);
				}
			} else {
				mapTemp.put(theAddress.getString(AddressKeys.DB_ROW_I_D), theAddress);
			}
		}
		
		lstAddressesDBRowID = new ArrayList<>(mapTemp.values());
		Collections.sort(lstAddressesDBRowID, new AddressComparator());
		Collections.sort(lstAddressesDBRowIDRemoved, new AddressComparator());
	}

	/**
	 * Returns an unmodifiable list of all {@link Address}es.
	 * 
	 * @return an unmodifiable list of all {@link Address}es, might be empty, never null.
	 *  @retval empty list if no addresses are in the address book
	 * 
	 * @version 0.1
	 * @since 0.1
	 */
	public List<Address> getAddresses() {
		
		if (addresses == null) {
			return Collections.emptyList();
		}
		
		return Collections.unmodifiableList(addresses);
	}

	/**
	 * Returns an unmodifiable list of all {@link Address}es in their last modified form.
	 * 
	 * The list does not contain duplicates of {@link Address}es.
	 * Duplicates means here: addresses with the same DBRowID.
	 * If several addresses contain the same DBRowID, the last modified (LastModifiedDate) address is used.
	 * 
	 * @return an unmodifiable list of DBRowID-unique {@link Address}es, might be empty, never null.
	 *  @retval empty list if no addresses are to be returned
	 * 
	 * @version 0.1
	 * @since 0.1
	 */
	public List<Address> getAddressesDBRowID() {
		
		if (lstAddressesDBRowID == null) {
			return Collections.emptyList();
		}
		
		return Collections.unmodifiableList(lstAddressesDBRowID);
	}

	/**
	 * Returns an unmodifiable list of all remaining {@link Address}es of {@link #getAddressesDBRowID()}.
	 * 
	 * @return an unmodifiable list of remaining {@link Address}es of {@link #getAddressesDBRowID()}, might be empty, never null.
	 *  @retval empty list if no addresses are to be returned
	 * 
	 * @version 0.1
	 * @since 0.1
	 */
	public List<Address> getAddressesDBRowIDRemoved() {
		
		if (lstAddressesDBRowIDRemoved == null) {
			return Collections.emptyList();
		}
		
		return Collections.unmodifiableList(lstAddressesDBRowIDRemoved);
	}

	/**
	 * Sets the exception handler.
	 * 
	 * @param exceptionHandler new exception handler
	 * 
	 * @version 0.1
	 * @since 0.1
	 */
	public void setExceptionHandler(ExceptionHandler exceptionHandler) {
		this.exceptionHandler = exceptionHandler;
	}

}

/* EOF */
