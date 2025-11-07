/**
 * Cerinta 5
 * Este throw cand datele nu trec de regulile de validare din {@link ProdusService}.
 * Extinde {@link Exception}.
 * @author Mihaita Eduard-Mihai
 */
public class ValidareException extends Exception {
    public ValidareException(String mesaj) {
        /**
         * Constructor pentru exceptia de validare.
         * @param mesaj Mesajul de eroare care descrie problema de validare.
         */
        super(mesaj);
    }
}
