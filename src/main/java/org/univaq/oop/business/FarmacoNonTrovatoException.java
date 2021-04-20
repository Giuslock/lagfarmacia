package org.univaq.oop.business;

public class FarmacoNonTrovatoException extends BusinessException {
    public FarmacoNonTrovatoException() {
        super();
    }

    public FarmacoNonTrovatoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public FarmacoNonTrovatoException(String message, Throwable cause) {
        super(message, cause);
    }

    public FarmacoNonTrovatoException(String message) {
        super(message);
    }

    public FarmacoNonTrovatoException(Throwable cause) {
        super(cause);
    }


}
