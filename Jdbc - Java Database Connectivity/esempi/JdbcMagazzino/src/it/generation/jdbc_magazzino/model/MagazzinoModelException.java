
package it.generation.jdbc_magazzino.model;


public class MagazzinoModelException extends Exception {

    public static final String magazzinoModelExceptionLabel = "MagazzinoModelException | ";
    
    public MagazzinoModelException(String exceptionMessage) {
        super(magazzinoModelExceptionLabel + exceptionMessage);
    }
    
    
}
