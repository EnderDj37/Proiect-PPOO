import java.util.ArrayList;
import java.util.List;

public class ProdusService {

    private List<Produs> listaProduse;

    private int id = 0;

    public ProdusService() {
        this.listaProduse = new ArrayList<>();
        System.out.println("ProdusService inițializat.");
    }
    private int genereazaId() {
        this.id++;
        return this.id;
    }

    public void adaugaProdus(String nume, double pret, int stoc) {
        int idNou = genereazaId();
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
}