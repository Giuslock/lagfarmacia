package org.univaq.oop.business;

public class UtenteNonTrovato extends BusinessException {

    public UtenteNonTrovato() {
    }

    public UtenteNonTrovato(String message, Throwable cause, boolean enableSuppression,
                            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public UtenteNonTrovato(String message, Throwable cause) {
        super(message, cause);
    }

    public UtenteNonTrovato(String message) {
        super(message);
    }

    public UtenteNonTrovato(Throwable cause) {
        super(cause);
    }

}
