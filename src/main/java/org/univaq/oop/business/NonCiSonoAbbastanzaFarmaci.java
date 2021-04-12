package org.univaq.oop.business;

public class NonCiSonoAbbastanzaFarmaci extends Exception{

    public NonCiSonoAbbastanzaFarmaci() {
        super();
    }

    public NonCiSonoAbbastanzaFarmaci(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public NonCiSonoAbbastanzaFarmaci(String message, Throwable cause) {
        super(message, cause);
    }

    public NonCiSonoAbbastanzaFarmaci(String message) {
        super(message);
    }

    public NonCiSonoAbbastanzaFarmaci(Throwable cause) {
        super(cause);
    }
}
