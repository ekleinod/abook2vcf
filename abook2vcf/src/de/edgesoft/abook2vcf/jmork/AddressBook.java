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
 */
public class AddressBook {

	/** Internal container for addresses. */
	private final List<Address> addresses = new LinkedList<Address>();
	
	/** Storage of exception handler. */
	private ExceptionHandler exceptionHandler = null;

	/** Internal singleton container for addresses in their last modified form. */
	private List<Address> lstAddressesDBRowID = null;
	
	/** Internal singleton container for addresses in their last modified form without doubles. */
	private List<Address> lstAddressesDBRowIDDoubles = null;
	
	/** Internal singleton container for removed addresses (last modified). */
	private List<Address> lstAddressesDBRowIDRemoved = null;
	
	/** Internal singleton container for removed addresses (last modified and doubles). */
	private List<Address> lstAddressesDBRowIDDoublesRemoved = null;
	
	/**
	 * Default constructor.
	 */
	public AddressBook() {
		lstAddressesDBRowID = new ArrayList<Address>();
		lstAddressesDBRowIDDoubles = new ArrayList<Address>();
		lstAddressesDBRowIDRemoved = new ArrayList<Address>();
		lstAddressesDBRowIDDoublesRemoved = new ArrayList<Address>();
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
		
		// compute modified lists
		Map<String, Address> mapReturn = new HashMap<String, Address>();
		
		// sort for convenience
		lstAddressesDBRowID = new ArrayList<Address>(mapReturn.values());
		Collections.sort(lstAddressesDBRowID, new AddressComparator());
	}

	/**
	 * Returns an unmodifiable list of all {@link Address}es.
	 * 
	 * @return an unmodifiable list of all {@link Address}es, might be empty, never null.
	 *  @retval empty list if no addresses are in the address book
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
	 * This method removes all duplicates of {@link Address}es.
	 * Duplicates means here: addresses with the same DBRowID.
	 * If several addresses contain the same DBRowID, the last modified (LastModifiedDate) address is used.
	 * 
	 * @return an unmodifiable list of DBRowID-unique {@link Address}es, might be empty, never null.
	 *  @retval empty list if no addresses are to be returned
	 */
	public List<Address> getAddressesDBRowID() {
		
		if (lstAddressesDBRowID == null) {
			return Collections.emptyList();
		}
		
		return Collections.unmodifiableList(lstAddressesDBRowID);
	}

	/**
	 * Returns an unmodifiable list of all {@link Address}es in their last modified form without doubles.
	 * 
	 * This method removes all duplicates of {@link Address}es.
	 * Duplicates means here: addresses with the same DBRowID, the same name and email.
	 * If several addresses contain the same DBRowID, the last modified (LastModifiedDate) address is used.
	 * 
	 * @return an unmodifiable list of DBRowID-unique {@link Address}es without doubles, might be empty, never null.
	 *  @retval empty list if no addresses are to be returned
	 */
	public List<Address> getAddressesDBRowIDDoubles() {
		
		if (lstAddressesDBRowIDDoubles == null) {
			return Collections.emptyList();
		}
		
		return Collections.unmodifiableList(lstAddressesDBRowIDDoubles);
	}

	/**
	 * Returns an unmodifiable list of all remaining {@link Address}es of {@link #getAddressesDBRowID()}.
	 * 
	 * @return an unmodifiable list of remaining {@link Address}es of {@link #getAddressesDBRowID()}, might be empty, never null.
	 *  @retval empty list if no addresses are to be returned
	 */
	public List<Address> getAddressesDBRowIDRemoved() {
		
		if (lstAddressesDBRowIDRemoved == null) {
			return Collections.emptyList();
		}
		
		return Collections.unmodifiableList(lstAddressesDBRowIDRemoved);
	}

	/**
	 * Returns an unmodifiable list of all remaining {@link Address}es of {@link #getAddressesDBRowIDDoubles()}.
	 * 
	 * @return an unmodifiable list of remaining {@link Address}es of {@link #getAddressesDBRowIDDoubles()}, might be empty, never null.
	 *  @retval empty list if no addresses are to be returned
	 */
	public List<Address> getAddressesDBRowIDDoublesRemoved() {
		
		if (lstAddressesDBRowIDDoublesRemoved == null) {
			return Collections.emptyList();
		}
		
		return Collections.unmodifiableList(lstAddressesDBRowIDDoublesRemoved);
	}

	/**
	 * Sets the exception handler.
	 * 
	 * @param exceptionHandler new exception handler
	 */
	public void setExceptionHandler(ExceptionHandler exceptionHandler) {
		this.exceptionHandler = exceptionHandler;
	}

}

/* EOF */
