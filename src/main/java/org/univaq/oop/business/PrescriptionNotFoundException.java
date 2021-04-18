package org.univaq.oop.business;

public class PrescriptionNotFoundException extends BusinessException {
    public PrescriptionNotFoundException() {
    }

    public PrescriptionNotFoundException(String message, Throwable cause, boolean enableSuppression,
                                         boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public PrescriptionNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public PrescriptionNotFoundException(String message) {
        super(message);
    }

    public PrescriptionNotFoundException(Throwable cause) {
        super(cause);
    }
}
