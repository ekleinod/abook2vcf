package de.edgesoft.abook2vcf.jmork;


/**
 * All available keys for addresses.
 * 
 * Addresses only, no lists.
 * 
 * @author Ekkart Kleinod
 * @version 0.1
 * @since 0.1
 */
public enum AddressKeys {

	ALLOW_REMOTE_CONTENT,
	ANNIVERSARY_DAY,
	ANNIVERSARY_MONTH,
	ANNIVERSARY_YEAR,
	BIRTH_DAY,
	BIRTH_MONTH,
	BIRTH_YEAR,
	CARD_TYPE,
	CATEGORY,
	CELLULAR_NUMBER,
	CELLULAR_NUMBER_TYPE,
	COMPANY,
	CUSTOM1,
	CUSTOM2,
	CUSTOM3,
	CUSTOM4,
	DB_ROW_I_D,
	DEFAULT_ADDRESS,
	DEFAULT_EMAIL,
	DEPARTMENT,
	DISPLAY_NAME,
	FAMILY_NAME,
	FAX_NUMBER,
	FAX_NUMBER_TYPE,
	FIRST_NAME,
	HOME_ADDRESS,
	HOME_ADDRESS2,
	HOME_CITY,
	HOME_COUNTRY,
	HOME_PHONE,
	HOME_PHONE_TYPE,
	HOME_STATE,
	HOME_ZIP_CODE,
	JOB_TITLE,
	LAST_MODIFIED_DATE,
	LAST_NAME,
	LAST_RECORD_KEY,
	LOWERCASE_PRIMARY_EMAIL,
	NICK_NAME,
	NOTES,
	PAGER_NUMBER,
	PAGER_NUMBER_TYPE,
	PHONETIC_FIRST_NAME,
	PHONETIC_LAST_NAME,
	PHOTO_NAME,
	PHOTO_TYPE,
	PHOTO_U_R_I,
	POPULARITY_INDEX,
	PREFER_DISPLAY_NAME,
	PREFER_MAIL_FORMAT,
	PRIMARY_EMAIL,
	RECORD_KEY,
	SECOND_EMAIL,
	SPOUSE_NAME,
	T_B_ID,
	WEB_PAGE1,
	WEB_PAGE2,
	WORK_ADDRESS,
	WORK_ADDRESS2,
	WORK_CITY,
	WORK_COUNTRY,
	WORK_PHONE,
	WORK_PHONE_TYPE,
	WORK_STATE,
	WORK_ZIP_CODE,
	_AIMSCREENNAME("_AimScreenName"),
	_GOOGLETALK("_GoogleTalk"),
	_ICQ("_ICQ"),
	_ID("_ID"),
	_JABBERID("_JabberIs"),
	_MSN("_MSN"),
	_QQ("_QQ"),
	_SKYPE("_Skype"),
	_YAHOO("_Yahoo");
	
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
