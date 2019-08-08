package com.cct.beans.exceptions;

public class DataViolationException extends RuntimeException {
	public DataViolationException() {
        super();
    }

    public DataViolationException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public DataViolationException(final String message) {
        super(message);
    }

    public DataViolationException(final Throwable cause) {
        super(cause);
    }

}
