import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class GUI extends JFrame {
    private ProdusService service;
    private JTable tabelProduse;
    private DefaultTableModel modelTabel;

    public GUI() {
        this.service = new ProdusService();
        setTitle("Magazin online");
        setSize(600,400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initializeazaComponente();
        incarcaDateInTabel();
    }

    private void initializeazaComponente() {
        String[] coloane = {"ID", "Nume", "Pret", "Stoc"};
        modelTabel = new DefaultTableModel(coloane, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabelProduse = new JTable(modelTabel);
        add(new JScrollPane(tabelProduse));
    }

    private void incarcaDateInTabel() {
        modelTabel.setRowCount(0);
        List<Produs> produse = service.getProduse();
        for(Produs p : produse) {
            Object[] row = new Object[4];
            row[0] = p.getId();
            row[1] = p.getNume();
            row[2] = p.getPret();
            row[3] = p.getStoc();
            modelTabel.addRow(row);
        }
    }
}
