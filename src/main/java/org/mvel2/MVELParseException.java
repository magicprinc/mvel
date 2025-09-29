package org.mvel2;

/**
 @see  */
public class MVELParseException extends RuntimeException {
	public MVELParseException (String message) {
		super(message);
	}

	public MVELParseException (String message, Throwable cause) {
		super(message, cause);
	}

	public MVELParseException (Throwable cause) {
		super(cause);
	}

	public static MVELParseException fmt (String format, Object... args) {
		return new MVELParseException(String.format(format, args));
	}
}