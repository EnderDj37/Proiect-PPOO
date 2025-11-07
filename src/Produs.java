import java.io.Serializable;

/**
 * Modelul de date pentru Produs
 * Implementeaza {@link Serializable} pentru persistenta in fisiere binare
 * @author Mihaita Eduard-Mihai
 */
public class Produs implements Serializable {

    /**
     * ID pentru controlul serializarii
     */
    private static final long serialVersionUID = 1L;

    private int id;
    private String nume;
    private double pret;
    private int stoc;

    /**
     * Constructor fara parametrii
     */
    public Produs() {
    }

    /**
     * Constructor cu toti parametrii
     * @param id ID-ul unic al fiecarui produs, generat de {@link ProdusService}
     * @param nume Numele produsului
     * @param pret Pretul produsului
     * @param stoc Stocul produsului
     */
    public Produs(int id, String nume, double pret, int stoc) {
        this.id = id;
        this.nume = nume;
        this.pret = pret;
        this.stoc = stoc;
    }

    /**
     * @return ID-ul produsului
     */
    public int getId() {
        return id;
    }

    /**
     * @return Numele produsului
     */
    public String getNume() {
        return nume;
    }

    /**
     * @param nume Noul nume al produsului
     */
    public void setNume(String nume) {
        this.nume = nume;
    }

    /**
     * @return Pretul produsului
     */
    public double getPret() {
        return pret;
    }

    /**
     * @param pret Noul pret al produsului
     */
    public void setPret(double pret) {
        this.pret = pret;
    }

    /**
     * @return Stocul produsului
     */
    public int getStoc() {
        return stoc;
    }

    /**
     * @param stoc Noul stoc al produsului
     */
    public void setStoc(int stoc) {
        this.stoc = stoc;
    }

    /**
     * Afiseaza textul in consola
     * @return Un string de forma ID: , Nume: , Pret: , Stoc:
     */
    @Override
    public String toString() {
        return "ID: " + id + ", Nume: " + nume + ", Pret: " + pret + ", Stoc: " + stoc;
    }
}