package kasirapp;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import koneksi.Koneksi;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.WindowConstants;

/**
 * MenuPilihMasakan - Digunakan untuk menampilkan daftar menu dan memilih satu item.
 * Berfungsi sebagai JDialog (Pop-up modal) untuk form menu_transaksi.
 */
public class MenuPilihMasakan extends JDialog {

    // --- Variabel untuk menyimpan hasil pemilihan ---
    private String idMasakanDipilih = null;
    private String namaMasakanDipilih = null;
    private double hargaDipilih = 0.0;
    
    // --- Getters untuk form utama (menu_transaksi) ---
    public String getIdMasakanDipilih() { return idMasakanDipilih; }
    public String getNamaMasakanDipilih() { return namaMasakanDipilih; }
    public double getHargaDipilih() { return hargaDipilih; }

    // --- Deklarasi Komponen GUI ---
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableMenu;
    
    /**
     * Konstruktor JDialog
     */
    public MenuPilihMasakan(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setTitle("Pilih Menu Masakan");
        // Atur agar JDialog hanya tertutup, bukan keluar dari aplikasi
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE); 
        load_data();
    }
    
    // --- Method untuk Memuat Data Menu dari tbl_masakan ---
    private void load_data() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Nama Masakan");
        model.addColumn("Harga");
        
        try {
            // Hanya tampilkan masakan yang statusnya 'Tersedia'
            String sql = "SELECT id_masakan, nama_masakan, harga FROM tbl_masakan WHERE status = 'Tersedia' ORDER BY id_masakan ASC";
            Connection conn = Koneksi.configDB();
            Statement stm = conn.createStatement();
            ResultSet res = stm.executeQuery(sql);

            while (res.next()) {
                model.addRow(new Object[]{
                    res.getString("id_masakan"),
                    res.getString("nama_masakan"),
                    res.getDouble("harga")
                });
            }
            jTableMenu.setModel(model); 
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error memuat data menu: " + e.getMessage());
        }
    }
    
    // --- Method Handling Klik pada Tabel Menu ---
    private void jTableMenuMouseClicked(java.awt.event.MouseEvent evt) {                                     
        int baris = jTableMenu.rowAtPoint(evt.getPoint());
        
        if(baris > -1) {
            try {
                // Ambil data dari tabel
                idMasakanDipilih = jTableMenu.getValueAt(baris, 0).toString();
                namaMasakanDipilih = jTableMenu.getValueAt(baris, 1).toString();
                hargaDipilih = Double.parseDouble(jTableMenu.getValueAt(baris, 2).toString()); 
                
                // Tutup JDialog dan kirim data kembali
                this.dispose();
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Data harga tidak valid!");
            }
        }
    }                                    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: DO NOT MODIFY THIS CODE if you want to keep Netbeans compatibility. 
     * Since you are using VS Code, you can safely paste this over.
     */
    @SuppressWarnings("unchecked")
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableMenu = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI Black", 1, 36)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Pilih Menu Masakan");

        jTableMenu.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "ID", "Nama Masakan", "Harga"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        
        // Tambahkan MouseListener
        jTableMenu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableMenuMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTableMenu);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 756, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 756, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null); 
    }// </editor-fold>                        
    
    // Main method dihilangkan karena ini adalah JDialog yang dipanggil dari JFrame lain.
}