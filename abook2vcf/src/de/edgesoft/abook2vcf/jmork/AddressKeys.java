package de.edgesoft.abook2vcf.jmork;


/**
 * All available keys for addresses.
 * 
 * @author Ekkart Kleinod
 * @version 0.1
 * @since 0.1
 */
public enum AddressKeys {

	FIRST_NAME,
	LAST_NAME,
	PRIMARY_EMAIL,
	DISPLAY_NAME,
	COMPANY,
	LIST_NAME,
	LIST_NICK_NAME,
	LIST_DESCRIPTION,
	LIST_TOTAL_ADDRESSES,
	LOWERCASE_LIST_NAME,
	NS_ADDRBK_SB_TABLE_KIND_DELETED("ns:addrbk:db:table:kind:deleted"),
	NS_ADDRBK_SB_ROW_SCOPE_CARD_ALL("ns:addrbk:db:row:scope:card:all"),
	NS_ADDRBK_SB_ROW_SCOPE_LIST_ALL("ns:addrbk:db:row:scope:list:all"),
	NS_ADDRBK_SB_ROW_SCOPE_DATA_ALL("ns:addrbk:db:row:scope:data:all"),
	PHONETIC_FIRST_NAME,
	PHONETIC_LAST_NAME,
	NICK_NAME,
	LOWERCASE_PRIMARY_EMAIL,
	SECOND_EMAIL,
	DEFAULT_EMAIL,
	CARD_TYPE,
	PREFER_MAIL_FORMAT,
	POPULARITY_INDEX,
	WORK_PHONE,
	HOME_PHONE,
	FAX_NUMBER,
	PAGER_NUMBER,
	CELLULAR_NUMBER,
	WORK_PHONE_TYPE,
	HOME_PHONE_TYPE,
	FAX_NUMBER_TYPE,
	PAGER_NUMBER_TYPE,
	CELLULAR_NUMBER_TYPE,
	HOME_ADDRESS,
	HOME_ADDRESS2,
	HOME_CITY,
	HOME_STATE,
	HOME_ZIP_CODE,
	HOME_COUNTRY,
	WORK_ADDRESS,
	WORK_ADDRESS2,
	WORK_CITY,
	WORK_STATE,
	WORK_ZIP_CODE,
	WORK_COUNTRY,
	JOB_TITLE,
	DEPARTMENT,
	_AIMSCREENNAME("_AimScreenName"),
	ANNIVERSARY_YEAR,
	ANNIVERSARY_MONTH,
	ANNIVERSARY_DAY,
	SPOUSE_NAME,
	FAMILY_NAME,
	DEFAULT_ADDRESS,
	CATEGORY,
	WEB_PAGE1,
	WEB_PAGE2,
	BIRTH_YEAR,
	BIRTH_MONTH,
	BIRTH_DAY;
	
	/** Storage of key. */
	private String sKey;
	
	/**
	 * Constructor computing key.
	 * 
	 * @param newSuffix
	 */
	private AddressKeys() {
		StringBuffer sbKey = new StringBuffer(toString().toLowerCase());
		
		int iIndex = 0;
		sbKey.setCharAt(iIndex, Character.toUpperCase(sbKey.charAt(iIndex)));
		
		while (sbKey.indexOf("_") >= 0) {
			iIndex = sbKey.indexOf("_");
			sbKey.deleteCharAt(iIndex);
			sbKey.setCharAt(iIndex, Character.toUpperCase(sbKey.charAt(iIndex)));
		}
		
		sKey = sbKey.toString();
	}
	
	/**
	 * Constructor setting key.
	 * 
	 * @param newSuffix
	 */
	private AddressKeys(String newKey) {
		sKey = newKey;
	}
	
	/**
	 * Returns key.
	 * 
	 * @return key
	 */
	public String getKey() {
		return sKey;
	}
	
}

/* EOF */
