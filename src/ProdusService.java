import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class ProdusService {

    private List<Produs> listaProduse;
    private int idContor = 0;
    private static final String NUME_FISIER = "produse.dat";

    public ProdusService() {
        this.listaProduse = new ArrayList<>();
        incarcaDate();
        System.out.println("ProdusService inițializat. " + listaProduse.size() + " produse incarcate.");
    }

    @SuppressWarnings("unchecked")
    private void incarcaDate() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(NUME_FISIER))) {
            this.listaProduse = (List<Produs>) ois.readObject();
            if (!this.listaProduse.isEmpty()) {
                int maxId = 0;
                for (Produs p : this.listaProduse) {
                    if (p.getId() > maxId) {
                        maxId = p.getId();
                    }
                }
                this.idContor = maxId;
            }
        } catch (IOException | ClassNotFoundException e ) {
            System.out.println("Nu s-a putut incarca fisierul, se incepe cu o lista goala");
            this.listaProduse = new ArrayList<>();
        }
    }

    public void salveazaDate() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(NUME_FISIER))) {
            oos.writeObject(this.listaProduse);
            System.out.println("Datele au fost salvate cu succes in " + NUME_FISIER);
        } catch (IOException e) {
            System.out.println("Eroare la salvarea datelor: " + e.getMessage());
        }
    }
    private int genereazaIdNou() {
        this.idContor++;
        return this.idContor;
    }

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
        System.out.println("Produs adaugat: " + produsNou);
    }

    public List<Produs> getProduse() {
        return this.listaProduse;
    }

    public Produs getProdusDupaId(int id) {
        for (Produs p : listaProduse) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }

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
        if(produsDeActualizat != null) {
        produsDeActualizat.setNume(numeNou);
        produsDeActualizat.setPret(pretNou);
        produsDeActualizat.setStoc(stocNou);
        System.out.println("Produs actualizat:" + produsDeActualizat);
        return true;
        }
        System.out.println("Eroare la actualizarea produsului cu id:"+id);
        return false;
    }
    public boolean stergeProdus(int id) {
        Produs produsDeSters = getProdusDupaId(id);
        if (produsDeSters !=null) {
            listaProduse.remove(produsDeSters);
            System.out.println("Produsul cu id-ul: " + id + "a fost sters cu succes!");
            return true;
        }
        System.out.println("Eroare la stergerea produsului cu id-ul: " + id);
        return false;
    }
}