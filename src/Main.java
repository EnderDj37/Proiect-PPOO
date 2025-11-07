import javax.swing.SwingUtilities;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static ProdusService service;
    private static Scanner scanner;

    static void main () {
        Scanner scannerInitial = new Scanner(System.in);
        System.out.println("Magazin online");
        System.out.println("1.Mod consola");
        System.out.println("2.Mod GUI");
        System.out.print("Alegeti modul de operare: ");

        int mod;
        try {
            mod = scannerInitial.nextInt();
        } catch (InputMismatchException e) {
            mod = 1;
        }

        if (mod == 2) {
            System.out.println("GUI Starting");
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    GUI fereastraGUI = new GUI();
                    fereastraGUI.setVisible(true);
                }
            });
        } else {
            System.out.println("Console starting");
            service = new ProdusService();
            scanner = new Scanner(System.in);

            ruleazaConsola();
            scanner.close();
        }
    }

    private static void ruleazaConsola() {
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
                case 6:
                    meniuStatistici();
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
        System.out.println("6.Statistici");
        System.out.println("0.Iesire");
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

    private static void meniuStatistici() {
        System.out.println("\nStatistici");
        List<Produs> produse = service.getProduse();

        if(produse.isEmpty()) {
            System.out.println("Nu exista produse");
            return;
        }

        int[] contorCategoriiPret = new int[3];
        int[] contorStoc = new int[2];

        double valoareTotalaStoc = 0.0;

        for(Produs p : produse) {
            if (p.getPret() < 50) {
                contorCategoriiPret[0]++;
            } else if (p.getPret() <= 200) {
                contorCategoriiPret[1]++;
            } else {
                contorCategoriiPret[2]++;
            }

            if (p.getStoc() < 10 && p.getStoc() >0) {
                contorStoc[0]++;
            } else {
                contorStoc[1]++;
            }
            valoareTotalaStoc += p.getStoc() * p.getPret();
        }

        System.out.println("Numar total de produse: " + produse.size());
        System.out.println("Valoarea totala a stocului: " + String.format("%.2f", valoareTotalaStoc));
        System.out.println("\nCategorii de pret");
        System.out.println("Produse sub 50 de lei: " + contorCategoriiPret[0]);
        System.out.println("Produse intre 50 si 200 de lei: " + contorCategoriiPret[1]);
        System.out.println("Produse peste 200 de lei: " + contorCategoriiPret[2]);
        System.out.println("\nNivel stoc");
        System.out.println("Sub 10 bucati in stoc: " + contorStoc[0]);
        System.out.println("Peste 10 bucati in stoc: " + contorStoc[1]);
    }
}