package org.univaq.oop.business;

public class FarmacoNonTrovato extends BusinessException {
    public FarmacoNonTrovato() {
        super();
    }

    public FarmacoNonTrovato(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public FarmacoNonTrovato(String message, Throwable cause) {
        super(message, cause);
    }

    public FarmacoNonTrovato(String message) {
        super(message);
    }

    public FarmacoNonTrovato(Throwable cause) {
        super(cause);
    }


}
