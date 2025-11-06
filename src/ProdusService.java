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

    public boolean updateProdus(int id, String numeNou, double pretNou, int stocNou) {
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