package com.easy.registration.exceptions;

public class PasswordsDontMatchException extends RuntimeException {
    public PasswordsDontMatchException() {
        super("Passwords don't match exception!");
    }

    public PasswordsDontMatchException(String message) {
        super(message);
    }

    public PasswordsDontMatchException(String message, Throwable cause) {
        super(message, cause);
    }

    public PasswordsDontMatchException(Throwable cause) {
        super(cause);
    }

    public PasswordsDontMatchException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
