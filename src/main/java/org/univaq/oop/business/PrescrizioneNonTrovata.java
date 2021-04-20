package org.univaq.oop.business;

public class PrescrizioneNonTrovata extends BusinessException {
    public PrescrizioneNonTrovata() {
    }

    public PrescrizioneNonTrovata(String message, Throwable cause, boolean enableSuppression,
                                  boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public PrescrizioneNonTrovata(String message, Throwable cause) {
        super(message, cause);
    }

    public PrescrizioneNonTrovata(String message) {
        super(message);
    }

    public PrescrizioneNonTrovata(Throwable cause) {
        super(cause);
    }
}
