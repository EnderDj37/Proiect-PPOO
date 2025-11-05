public class Produs {

    private int id;
    private String nume;
    private double pret;
    private int stoc;

    public Produs() {
    }

    public Produs(int id, String nume, double pret, int stoc) {
        this.id = id;
        this.nume = nume;
        this.pret = pret;
        this.stoc = stoc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public double getPret() {
        return pret;
    }

    public void setPret(double pret) {
        this.pret = pret;
    }

    public int getStoc() {
        return stoc;
    }

    public void setStoc(int stoc) {
        this.stoc = stoc;
    }


    @Override
    public String toString() {
        return "Produs [ID=" + id + ", Nume=" + nume + ", Pret=" + pret + ", Stoc=" + stoc + "]";
    }
}