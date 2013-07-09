package de.edgesoft.abook2vcf;



/**
 * Possible adress keys.
 * 
 * @author Ekkart Kleinod
 * @version 0.1
 * @since 0.1
 */
public enum AddressKeys {
	
	DISPLAY_NAME;
	
	/** 
	 * Returns key of enum value.
	 *  
	 * @return key representation
	 */
	public String getKey() {
		StringBuffer sbKey = new StringBuffer(toString().toLowerCase());
		
		sbKey.setCharAt(0, Character.toUpperCase(sbKey.charAt(0)));
		
		while (sbKey.indexOf("_") >= 0) {
			int iIndex = sbKey.indexOf("_");
			sbKey.deleteCharAt(iIndex);
			sbKey.setCharAt(iIndex, Character.toUpperCase(sbKey.charAt(iIndex)));
		}
		
		return sbKey.toString();
	}
	
}

/* EOF */
