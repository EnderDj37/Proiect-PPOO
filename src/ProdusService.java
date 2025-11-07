import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Cerinta 4
 * Clasa ce gestioneaza operatiile CRUD, persistenta si validarea datelor
 * @author Mihaita Eduard-Mihai
 */
public class ProdusService {

    /**
     * Cerinta 3
     * Sursa datelor, folosita pentru salvarea in fisier.
     */
    private List<Produs> listaProduse;
    /**
     * Cerinta 3
     * A2a colectie, un Map folosit ca index pentru cautari rapide dupa ID
     */
    private Map<Integer, Produs> indexProduse;

    /**
     * Contor pentru generarea ID-urilor unice
     */
    private int idContor = 0;
    /**
     * Cerinta 1
     * Numele fisierului binar
     */
    private static final String NUME_FISIER = "produse.dat";

    /**
     * Constructor
     * {@link #incarcaDate()} pentru a incarca fisierul salvat in rularile anterioare (Cerinta 2)
     */
    public ProdusService() {
        this.listaProduse = new ArrayList<>();
        this.indexProduse = new HashMap<>();

        incarcaDate();
        System.out.println("ProdusService inițializat. " + listaProduse.size() + " produse incarcate.");
    }

    /**
     * Incarca datele din fisierul {@value NUME_FISIER}.
     * Populeaza ambele colectii si actualizeaza idContor
     */
    @SuppressWarnings("unchecked")
    private void incarcaDate() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(NUME_FISIER))) {
            this.listaProduse = (List<Produs>) ois.readObject();

            this.indexProduse.clear();
            if (!this.listaProduse.isEmpty()) {
                int maxId = 0;
                for (Produs p : this.listaProduse) {
                    this.indexProduse.put(p.getId(), p);
                    if (p.getId() > maxId) {
                        maxId = p.getId();
                    }
                }
                this.idContor = maxId;
            }
        } catch (IOException | ClassNotFoundException e ) {
            System.out.println("Nu s-a putut incarca fisierul, se incepe cu o lista goala");
            this.listaProduse = new ArrayList<>();
            this.indexProduse = new HashMap<>();
        }
    }

    /**
     * Salveaza datele in fisierul {@value NUME_FISIER}.
     * Apelat la inchiderea aplicatiei (Cerinta 2)
     */
    public void salveazaDate() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(NUME_FISIER))) {
            oos.writeObject(this.listaProduse);
            System.out.println("Datele au fost salvate cu succes in " + NUME_FISIER);
        } catch (IOException e) {
            System.out.println("Eroare la salvarea datelor: " + e.getMessage());
        }
    }

    /**
     * Genereaza un ID unic, prin incrementare
     * @return un ID Numeric nou
     */
    private int genereazaIdNou() {
        this.idContor++;
        return this.idContor;
    }

    /**
     * Adauga un produs nou in memorie si ruleaza validarea
     * @param nume Numele produsului
     * @param pret Pretul produsului
     * @param stoc Stocul produsului
     * @throws ValidareException Daca datele nu trec de validare
     */
    public void adaugaProdus(String nume, double pret, int stoc) throws ValidareException {
        if (nume == null || nume.trim().isEmpty()) {
            throw new ValidareException("Numele produsului nu poate fi gol.");
        }
        if (pret <= 0 ) {
            throw new ValidareException("Pretul nu poate fi un numar negativ.");
        }
        if(stoc <0 ) {
            throw new ValidareException("Stocul nu poate fi negativ");
        }

        int idNou = genereazaIdNou();
        Produs produsNou = new Produs(idNou, nume, pret, stoc);

        listaProduse.add(produsNou);
        indexProduse.put(produsNou.getId(), produsNou);
        System.out.println("Produs adaugat: " + produsNou);
    }

    /**
     * Returneaza lista de produse
     * @return O lista cu toate obiectele {@link Produs}
     */
    public List<Produs> getProduse() {
        return this.listaProduse;
    }

    /**
     * Cauta un produs folosind HashMap-ul pentru eficienta
     * @param id ID-ul produsului cautat
     * @return Obiectul {@link Produs} daca este gasit, altfel eroare
     */
    public Produs getProdusDupaId(int id) {
        return indexProduse.get(id);
    }

    /**
     * Actualizeaza un produs existent si ruleaza validarea
     * @param id ID-ul produsului ce trebuie actualizat
     * @param numeNou Noul nume al produsului
     * @param pretNou Noul pret al produsului
     * @param stocNou Noul stoc al produsului
     * @return true daca reuseste sa actualizeze, false altfel
     * @throws ValidareException Daca datele nu trec de validare
     */
    public boolean updateProdus(int id, String numeNou, double pretNou, int stocNou) throws ValidareException{
        if (numeNou == null || numeNou.trim().isEmpty()) {
            throw new ValidareException("Numele produsului nu poate fi gol.");
        }
        if (pretNou <= 0) {
            throw new ValidareException("Pretul nu poate fi negativ.");
        }
        if (stocNou < 0 ) {
            throw new ValidareException("Stocul trebuie sa fie pozitiv.");
        }
        Produs produsDeActualizat = getProdusDupaId(id);

        if (produsDeActualizat != null) {
            produsDeActualizat.setNume(numeNou);
            produsDeActualizat.setPret(pretNou);
            produsDeActualizat.setStoc(stocNou);
            System.out.println("Produs actualizat:" + produsDeActualizat);
            return true;
        }
        System.out.println("Eroare la actualizarea produsului cu id:"+id);
        return false;
    }

    /**
     * Sterge un produs din memorie
     * @param id ID-ul produsului de sters
     * @return true daca stergerea a reusit, false altfel
     */
    public boolean stergeProdus(int id) {
        Produs produsDeSters = getProdusDupaId(id);

        if (produsDeSters != null) {
            listaProduse.remove(produsDeSters);
            indexProduse.remove(id);
            System.out.println("Produsul cu id-ul: " + id + "a fost sters cu succes!");
            return true;
        }
        System.out.println("Eroare la stergerea produsului cu id-ul: " + id);
        return false;
    }
}