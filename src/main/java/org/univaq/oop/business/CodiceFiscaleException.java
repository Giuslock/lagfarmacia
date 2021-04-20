package org.univaq.oop.business;

public class CodiceFiscaleException extends BusinessException{
    public CodiceFiscaleException() {
        super();
    }

    public CodiceFiscaleException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public CodiceFiscaleException(String message, Throwable cause) {
        super(message, cause);
    }

    public CodiceFiscaleException(String message) {
        super(message);
    }

    public CodiceFiscaleException(Throwable cause) {
        super(cause);
    }
}
