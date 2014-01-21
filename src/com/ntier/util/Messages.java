package com.ntier.util;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

// TODO: Auto-generated Javadoc
/**
 * The Class Messages.
 *
 * @author JD
 */
public class Messages {
	
	/** The Constant BUNDLE_NAME. */
	private static final String			BUNDLE_NAME		= "com.ntier.util.messages";				//$NON-NLS-1$

	/** The Constant RESOURCE_BUNDLE. */
	private static final ResourceBundle	RESOURCE_BUNDLE	= ResourceBundle.getBundle(BUNDLE_NAME);

	/**
	 * Instantiates a new messages.
	 */
	private Messages() {}

	/**
	 * Gets the string.
	 *
	 * @param key the key
	 * @return the string
	 */
	public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}
}
//class Messages
