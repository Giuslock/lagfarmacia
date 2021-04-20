package org.univaq.oop.business;

public class PrescrizioneNonTrovataException extends BusinessException {
    public PrescrizioneNonTrovataException() {
    }

    public PrescrizioneNonTrovataException(String message, Throwable cause, boolean enableSuppression,
                                           boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public PrescrizioneNonTrovataException(String message, Throwable cause) {
        super(message, cause);
    }

    public PrescrizioneNonTrovataException(String message) {
        super(message);
    }

    public PrescrizioneNonTrovataException(Throwable cause) {
        super(cause);
    }
}
