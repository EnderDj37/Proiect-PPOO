import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static ProdusService service = new ProdusService();
    private static Scanner scanner = new Scanner(System.in);

    public static void main (String[] args) {
        System.out.println("App starting");
        boolean ruleaza = true;
        while (ruleaza) {
            afiseazaMeniu();
            int optiune = citesteIntValid("Alegeti o optiune: ");
            switch (optiune){
                case 1:
                    meniuAdaugaProduse();
                    break;
                case 2:
                    meniuAfiseazaToateProdusele();
                    break;
                case 3:
                    meniuAfiseazaProdusDupaId();
                    break;
                case 4:
                    meniuActualizeazaProdus();
                    break;
                case 5:
                    meniuStergeProdus();
                    break;
                case 0:
                    System.out.println("Se salveaza datele...");
                    service.salveazaDate();
                    ruleaza = false;
                    break;
                default:
                    System.out.println("Optiune invalida");
            }
        }
        System.out.println("Aplicatia se inchide");
        scanner.close();
    }
    private static int citesteIntValid(String prompt) {
        while (true) {
            try {
                System.out.println(prompt);
                int input = scanner.nextInt();
                scanner.nextLine();
                return input;
            } catch (InputMismatchException e) {
                System.out.println("Trebuie sa introduceti un numar intreg");
                scanner.nextLine();
            }
        }
    }

    private static double citesteDoubleValid(String prompt) {
        while (true) {
            try {
                System.out.println(prompt);
                double input = scanner.nextDouble();
                scanner.nextLine();
                return input;
            } catch (InputMismatchException e) {
                System.out.println("Trebuie sa introduceti un numar");
                scanner.nextLine();
            }
        }
    }

    private static void afiseazaMeniu() {
        System.out.println("\nMeniu Principal");
        System.out.println("1.Adauga produs");
        System.out.println("2.Afiseaza toate produsele");
        System.out.println("3.Cauta produs dupa ID");
        System.out.println("4.Actualizeaza produs");
        System.out.println("5.Sterge produs");
        System.out.println("0.Iesire");
        System.out.print("Alegeti o optiune: ");
    }

    private static void meniuAdaugaProduse() {
        System.out.println("\nAdauga produs nou");
        System.out.print("Nume: ");
        String nume = scanner.nextLine();
        double pret = citesteDoubleValid("Pret: ");
        int stoc = citesteIntValid("Stoc: ");
        try{
            service.adaugaProdus(nume, pret, stoc);
            System.out.println("Produs adaugat!");
        } catch (ValidareException e) {
            System.out.println("Eroare la adaugare: " + e.getMessage());
        }
    }

    private static void meniuAfiseazaToateProdusele() {
        System.out.println("\nAfiseaza toate produsele");
        List<Produs> produse = service.getProduse();
        if(produse.isEmpty()) {
            System.out.println("Nu exista produse!");
        } else {
            for (Produs p : produse) {
                System.out.println(p);
            }
        }
    }

    private static void meniuAfiseazaProdusDupaId() {
        System.out.println("\nCauta produs dupa ID");
        int id = citesteIntValid("Introduceti ID-ul");

        Produs p = service.getProdusDupaId(id);
        if (p!=null) {
            System.out.println("Produs gasit: " + p );
        } else  {
            System.out.println("Produsul cu ID-ul " + id + " nu exista");
        }
    }

    private static void meniuActualizeazaProdus() {
        System.out.println("\nActualizeaza produs");
        int id = citesteIntValid("Introduceti ID-ul produsului pentru actualizare");

        Produs produsExistent = service.getProdusDupaId(id);
        if(produsExistent == null){
            System.out.println("Produsul cu id-ul introdus nu exista");
            return;
        }
        System.out.println("Se actualizeaza: " + produsExistent);
        System.out.println("Nume nou (lasati gol daca nu doriti sa actualizati numele) " + produsExistent.getNume());
        String numeNou = scanner.nextLine();
        if(numeNou.isEmpty()){
            numeNou=produsExistent.getNume();
        }
        double pretNou = citesteDoubleValid("Pret nou (actual: " + produsExistent.getPret() + "): ");
        int stocNou = citesteIntValid("Stoc nou (actual: " + produsExistent.getStoc() + "): ");
        try {
            service.updateProdus(id, numeNou, pretNou, stocNou);
            System.out.println("Produs actualizat cu succes!");
        } catch (ValidareException e) {
            System.out.println("Eroare la actualizare." + e.getMessage());
        }
    }

    private static void meniuStergeProdus() {
        System.out.println("\nSterge produs");
        int id = citesteIntValid("Introduceti id-ul produsului pe care doriti sa-l stergeti: ");

        if(service.stergeProdus(id)) {
            System.out.println("Produsul a fost sters cu succes!");
        } else {
            System.out.println("Eroare la stergere, id-ul nu a fost gasit");
        }
    }
}