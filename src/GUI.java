import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class GUI extends JFrame {
    private ProdusService service;

    private JTable tabelProduse;
    private DefaultTableModel modelTabel;

    private JTextField txtNume;
    private JTextField txtPret;
    private JTextField txtStoc;
    private JButton btnAdauga;

    public GUI() {
        this.service = new ProdusService();
        setTitle("Magazin online");
        setSize(600,400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        initializeazaTabel();
        initializeazaFromularAdaugare();
        refreshTabel();
    }

    private void initializeazaTabel() {
        String[] coloane = {"ID", "Nume", "Pret", "Stoc"};
        modelTabel = new DefaultTableModel(coloane, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabelProduse = new JTable(modelTabel);
        add(new JScrollPane(tabelProduse), BorderLayout.CENTER);
    }

    private void initializeazaFromularAdaugare() {
        JPanel panouAdaugare = new JPanel(new FlowLayout());

        panouAdaugare.add(new JLabel("Nume:"));
        txtNume = new JTextField(20);
        panouAdaugare.add(txtNume);

        panouAdaugare.add(new JLabel("Pret:"));
        txtPret = new JTextField(20);
        panouAdaugare.add(txtPret);

        panouAdaugare.add(new JLabel("Stoc:"));
        txtStoc = new JTextField(20);
        panouAdaugare.add(txtStoc);

        btnAdauga = new JButton("Adauga produs");
        panouAdaugare.add(btnAdauga);

        btnAdauga.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adaugaProdusDinFormular();
            }
        });
        add(panouAdaugare, BorderLayout.SOUTH);
    }

    private void adaugaProdusDinFormular() {
        try {
            String nume = txtNume.getText();
            String pretText = txtPret.getText();
            String stocText = txtStoc.getText();

            double pret;
            int stoc;

            if (pretText.isEmpty() || stocText.isEmpty()) {
                throw new ValidareException("Pretul si stocul nu pot fi goale.");
            }

            try {
                pret = Double.parseDouble(pretText);
            } catch (NumberFormatException ex) {
                throw new ValidareException("Pretul trebuie sa fie un numar valid.");
            }
            try {
                stoc = Integer.parseInt(stocText);
            } catch (NumberFormatException ex) {
                throw new ValidareException("Stocul trebuie sa fie un numar intreg.");
            }

            service.adaugaProdus(nume, pret, stoc);

            refreshTabel();
            curataFormular();

            JOptionPane.showMessageDialog(this, "Produs adaugat cu succes!", "Succes", JOptionPane.INFORMATION_MESSAGE);
        } catch (ValidareException ex) {
            JOptionPane.showMessageDialog(this, "Eroare de validare:\n" + ex.getMessage(), "Eroare", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void curataFormular() {
        txtNume.setText("");
        txtPret.setText("");
        txtStoc.setText("");
    }

    private void refreshTabel() {
        modelTabel.setRowCount(0);
        List<Produs> produse = service.getProduse();

        for (Produs p : produse) {
            Object[] row = {p.getId(), p.getNume(), p.getPret(), p.getStoc()};
            modelTabel.addRow(row);
        }
    }
}
