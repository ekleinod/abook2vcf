package de.edgesoft.utilities.logging;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.logging.Handler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.logging.StreamHandler;


/**
 * Logging functionality.
 * 
 * @author Ekkart Kleinod
 * @version 0.2
 * @since 0.2
 */
public class Log {
	
	/** Logger. */
	private static Logger logger = null;

	/** Logger's handler. */
	private static Handler logHandler = null;
	
	/** Calling class. */
	private static Class<?> clsLogging = null;
	
	/** Logging stream. */
	private static OutputStream stmLogging = null;
	
	/**
	 * Initialize Logger with class.
	 * 
	 * @param theClass calling class
	 * 
	 * @version 0.2
	 * @since 0.2
	 */
	public static void init(Class<?> theClass) {
		init(theClass, null);
	}

	/**
	 * Initialize Logger with class and logging stream.
	 * 
	 * @param theClass calling class
	 * @param theOutputStream output stream
	 * 
	 * @version 0.2
	 * @since 0.2
	 */
	public static void init(Class<?> theClass, OutputStream theOutputStream) {
		clsLogging = theClass;
		stmLogging = theOutputStream;
	}

	/** 
	 * Returns the log information.
	 * 
	 * @return log information
	 *  @retval null if an error occurred or no {@link ByteArrayOutputStream} was specified
	 */
	public static String getLogMessage() {
		if ((logHandler instanceof StreamHandler) && (stmLogging != null) && (stmLogging instanceof ByteArrayOutputStream)) {
			logHandler.flush();
			try {
				return ((ByteArrayOutputStream) stmLogging).toString(StandardCharsets.UTF_8.name());
			} catch (UnsupportedEncodingException e) {
				// use standard encoding
				return stmLogging.toString();
			}
		}
		return null;
	}
	
	/**
	 * Returns the logger.
	 * 
	 * @return logger
	 */
	public static Logger getLgr() {
		if (logger == null) {
			if (clsLogging == null) {
				logger = Logger.getLogger(Log.class.getSimpleName());
			} else {
				logger = Logger.getLogger(clsLogging.getSimpleName());
			}
			if (stmLogging != null) {
				logger.setUseParentHandlers(false);
				logHandler = new StreamHandler(stmLogging, new SimpleFormatter());
				try {
					logHandler.setEncoding(StandardCharsets.UTF_8.name());
				} catch (SecurityException | UnsupportedEncodingException e) {
					// do nothing, stay with default encoding
				}
				logger.addHandler(logHandler);
			}
		}
		
		return logger;
	}
	
}

/* EOF */
