package kasirapp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import koneksi.Koneksi; // Pastikan package koneksi sudah benar

/**
 *
 * @author Jo
 */
public class menu_transaksi extends javax.swing.JFrame {

    private DefaultTableModel keranjangModel;
    private int idTransaksiBaru = 0;
    
    // --- VARIABEL UNTUK MENYIMPAN MENU YANG DIPILIH DARI POP-UP ---
    private String idMasakanSaatIni = null;
    private double hargaMasakanSaatIni = 0.0;
    private String namaMasakanSaatIni = null; 

    /**
     * Creates new form menu_transaksi
     */
    public menu_transaksi() {
        initComponents();
        setupKeranjangTable();
        // isi_combo_masakan(); <-- DIHILANGKAN
        kosong();
    }
    
    // --- SETUP DAN PEMBANTU ---
    
    // Setup model tabel untuk Keranjang Belanja
    private void setupKeranjangTable() {
        keranjangModel = new DefaultTableModel();
        keranjangModel.addColumn("ID Masakan");
        keranjangModel.addColumn("Nama Masakan");
        keranjangModel.addColumn("Harga");
        keranjangModel.addColumn("Jumlah Beli");
        keranjangModel.addColumn("Subtotal");
        table_transaksi.setModel(keranjangModel);
    }
    
    // Mengosongkan field input dan keranjang
    private void kosong() {
        // Reset field header
        text_id_transaksi.setText("Otomatis");
        text_nama_pelanggan.setText(null);
        
        // Reset field item
        text_jml_beli.setText("0");
        
        // Reset Total Bayar dan Tanggal
 // Di method private void kosong()
SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
jLabel6.setText(sdf.format(new Date()));
        
        // Reset Variabel Pemilihan Menu
        idMasakanSaatIni = null;
        namaMasakanSaatIni = null;
        hargaMasakanSaatIni = 0.0;
        
        // Kosongkan keranjang
        keranjangModel.setRowCount(0);
        
        // Aktifkan input utama
        text_nama_pelanggan.setEnabled(true);
        btn_input.setText("TAMBAH KE KERANJANG");
        btn_update.setText("SELESAI TRANSAKSI");
        btn_delete.setText("BATALKAN ITEM");
        
        // Cari ID Transaksi berikutnya
        cariIdTransaksiBaru();
    }
    
    private void cariIdTransaksiBaru() {
        try {
            String sql = "SELECT MAX(id_transaksi) FROM tbl_transaksi";
            Connection conn = Koneksi.configDB();
            Statement stm = conn.createStatement();
            ResultSet res = stm.executeQuery(sql);
            if (res.next()) {
                // Set ID Transaksi baru = MAX(ID) + 1. Jika null, mulai dari 1.
                idTransaksiBaru = res.getInt(1) + 1;
            } else {
                idTransaksiBaru = 1;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal mencari ID Transaksi baru: " + e.getMessage());
            idTransaksiBaru = 1; 
        }
        text_id_transaksi.setText(String.valueOf(idTransaksiBaru));
    }
    
    // Menghitung subtotal saat jumlah beli diubah (diperlukan untuk tombol INPUT/TAMBAH)
    private void hitungSubtotalSementara() {
        try {
            String jmlBeliStr = text_jml_beli.getText();
            
            // Menggunakan harga yang telah dipilih dari pop-up
            if (idMasakanSaatIni != null && !jmlBeliStr.isEmpty() && !jmlBeliStr.equals("0")) {
                double harga = hargaMasakanSaatIni;
                int jumlahBeli = Integer.parseInt(jmlBeliStr);
                
                double subtotal = harga * jumlahBeli;
                // Tampilkan harga di Total Bayar (sementara)
                text_total_bayar.setText(String.valueOf(subtotal));
            } else if (idMasakanSaatIni != null && (jmlBeliStr.isEmpty() || jmlBeliStr.equals("0"))) {
                 text_total_bayar.setText(String.valueOf(hargaMasakanSaatIni));
            }
            else {
                text_total_bayar.setText("0.0");
            }
        } catch (NumberFormatException e) {
            text_total_bayar.setText("Jml Beli Salah!");
        }
    }
    
    // Menghitung Total Bayar Akumulatif dari Keranjang
    private double hitungTotalBayarFinal() {
        double total = 0.0;
        for (int i = 0; i < keranjangModel.getRowCount(); i++) {
            total += Double.parseDouble(keranjangModel.getValueAt(i, 4).toString()); // Kolom Subtotal
        }
        DecimalFormat df = new DecimalFormat("#.##");
        text_total_bayar.setText(df.format(total));
        return total;
    }
    
    // --- FUNGSI UTAMA (CRUD Item/Keranjang dan Transaksi Final) ---
    
    // FUNGSI INPUT (TAMBAH KE KERANJANG)
    private void tambahKeKeranjang() {
        String jmlBeliStr = text_jml_beli.getText();
        
        // *** Cek apakah menu sudah dipilih dari pop-up ***
        if (idMasakanSaatIni == null) { 
            JOptionPane.showMessageDialog(this, "Pilih Masakan terlebih dahulu dengan tombol 'LIHAT MENU'!");
            return;
        }
        
        if (text_nama_pelanggan.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nama Pelanggan tidak boleh kosong!");
            text_nama_pelanggan.requestFocus();
            return;
        }
        
        if (jmlBeliStr.isEmpty() || jmlBeliStr.equals("0")) {
            JOptionPane.showMessageDialog(this, "Masukkan Jumlah Beli yang valid!");
            return;
        }

        try {
            int jumlahBeli = Integer.parseInt(jmlBeliStr);
            
            // --- Ambil data dari variabel member yang sudah dipilih ---
            String idMasakan = idMasakanSaatIni; 
            String namaMasakan = namaMasakanSaatIni;
            double harga = hargaMasakanSaatIni;
            double subtotal = harga * jumlahBeli;
            
            // Tambahkan ke Keranjang
            keranjangModel.addRow(new Object[]{
                idMasakan, namaMasakan, harga, jumlahBeli, subtotal
            });
            
            // Hitung ulang total
            hitungTotalBayarFinal();
            
            // Reset variabel pemilihan menu dan field Jumlah Beli untuk input item berikutnya
            idMasakanSaatIni = null;
            namaMasakanSaatIni = null;
            hargaMasakanSaatIni = 0.0;
            text_jml_beli.setText("0"); 
            
            // Setelah item ditambahkan, disable Nama Pelanggan
            text_nama_pelanggan.setEnabled(false);
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Jumlah Beli harus berupa angka!");
        }
    }
    
    // FUNGSI DELETE (BATALKAN ITEM DARI KERANJANG)
    private void batalkanItem() {
        int barisTerpilih = table_transaksi.getSelectedRow();
        if (barisTerpilih == -1) {
            JOptionPane.showMessageDialog(this, "Pilih item di keranjang yang ingin dibatalkan!");
            return;
        }
        
        keranjangModel.removeRow(barisTerpilih);
        hitungTotalBayarFinal();
        
        // Jika keranjang kosong, aktifkan kembali input nama pelanggan
        if (keranjangModel.getRowCount() == 0) {
            text_nama_pelanggan.setEnabled(true);
        }
    }
    
// Di dalam menu_transaksi.java

// Di dalam menu_transaksi.java

// Di dalam menu_transaksi.java

// Di dalam menu_transaksi.java

// Di dalam menu_transaksi.java

private void selesaiTransaksi() {
    if (keranjangModel.getRowCount() == 0) {
        JOptionPane.showMessageDialog(this, "Keranjang belanja masih kosong!");
        return;
    }
    
    // 1. Ambil data Header
    double totalBayarFinal = hitungTotalBayarFinal(); 
    String namaPelanggan = text_nama_pelanggan.getText(); 
    
    // AMBIL TANGGAL LANGSUNG DARI LABEL (karena Anda sudah menghapus "Tanggal: " di method kosong())
    String tanggalStr = jLabel6.getText(); 
    int idTrans = idTransaksiBaru; 
    
    if (namaPelanggan.trim().isEmpty()) {
        JOptionPane.showMessageDialog(this, "Nama Pelanggan tidak boleh kosong!");
        return;
    }
    
    int confirm = JOptionPane.showConfirmDialog(this, 
        "Total Bayar: " + totalBayarFinal + "\nSelesaikan Transaksi?", "Konfirmasi Pembayaran", JOptionPane.YES_NO_OPTION);

    if (confirm == JOptionPane.YES_OPTION) {
        Connection conn = null;
        try {
            conn = Koneksi.configDB();
            conn.setAutoCommit(false); 
            
            // Kolom: id_transaksi, nama_pelanggan, id_masakan, tanggal, jumlah_beli, total_bayar
            // Setiap item akan di-insert sebagai baris detail
            String sqlDetail = "INSERT INTO tbl_transaksi (id_transaksi, nama_pelanggan, id_masakan, tanggal, jumlah_beli, total_bayar) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement pstDetail = conn.prepareStatement(sqlDetail);

            for (int i = 0; i < keranjangModel.getRowCount(); i++) {
                // Ambil data dari tabel keranjang
                String idM = keranjangModel.getValueAt(i, 0).toString();
                String jmlB = keranjangModel.getValueAt(i, 3).toString();
                String subT = keranjangModel.getValueAt(i, 4).toString();

                // Bind parameter ke Prepared Statement
                pstDetail.setInt(1, idTrans);
                pstDetail.setString(2, namaPelanggan); // MENGISI nama_pelanggan (Mengatasi NOT NULL)
                pstDetail.setInt(3, Integer.parseInt(idM)); // MENGISI id_masakan (Mengatasi Foreign Key jika ID valid)
                
                // MENGISI TANGGAL (Mengatasi NOT NULL)
                pstDetail.setDate(4, java.sql.Date.valueOf(tanggalStr)); 
                
                pstDetail.setInt(5, Integer.parseInt(jmlB)); // MENGISI jumlah_beli (Mengatasi NOT NULL)
                pstDetail.setDouble(6, Double.parseDouble(subT)); 

                pstDetail.executeUpdate();
            }
            
            conn.commit(); 
            JOptionPane.showMessageDialog(this, "Transaksi (ID: " + idTrans + ") BERHASIL disimpan! Total: " + totalBayarFinal);
            kosong();
            
        } catch (SQLException e) {
            try {
                if (conn != null) conn.rollback(); 
            } catch (SQLException ex) {
                // Ignore rollback failure
            }
            
            // Tampilkan error yang jelas
            String errorMessage = e.getMessage();
            if (errorMessage.contains("duplicate key value")) {
                JOptionPane.showMessageDialog(this, "Gagal menyimpan: ID Transaksi sudah ada. Coba ulangi transaksi.");
            } else if (errorMessage.contains("violates foreign key constraint")) {
                JOptionPane.showMessageDialog(this, "Gagal menyimpan: ID Masakan tidak valid atau tidak ada di database.");
            } else {
                 JOptionPane.showMessageDialog(this, "Gagal menyimpan transaksi: " + errorMessage);
            }

        } catch (NumberFormatException e) {
             JOptionPane.showMessageDialog(this, "Gagal menyimpan transaksi, pastikan format data (tanggal, jumlah) sudah benar.");
        } finally {
            try {
                if (conn != null) conn.setAutoCommit(true);
            } catch (SQLException ex) {
                 // Ignore set autocommit failure
            }
        }
    }
}

    /**
     * This method is called from within the constructor to initialize the form.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
   // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
private void initComponents() {

    jLabel1 = new javax.swing.JLabel();
    jLabel2 = new javax.swing.JLabel();
    jLabel3 = new javax.swing.JLabel();
    text_id_transaksi = new javax.swing.JTextField();
    text_nama_pelanggan = new javax.swing.JTextField();
    jLabel5 = new javax.swing.JLabel(); // Ini label ID Masakan
    
    // combo_id_masakan DIHAPUS

    jScrollPane1 = new javax.swing.JScrollPane();
    table_transaksi = new javax.swing.JTable();
    jPanel1 = new javax.swing.JPanel();
    btn_cetak_laporan = new javax.swing.JButton();
    btn_input = new javax.swing.JButton();
    btn_update = new javax.swing.JButton();
    btn_delete = new javax.swing.JButton();
    btn_logout = new javax.swing.JButton();
    
    // btn_menu_masakan (Tombol Lihat Menu) sudah ada di kode Anda
    btn_menu_masakan = new javax.swing.JButton();
    
    // --- START: INISIALISASI TOMBOL BARU ---
    // Inisialisasi Tombol Kembali ke Menu Masakan
    btn_menu_masakan_kembali = new javax.swing.JButton(); 
    // --- END: INISIALISASI TOMBOL BARU ---

    jLabel6 = new javax.swing.JLabel();
    jLabel7 = new javax.swing.JLabel();
    text_jml_beli = new javax.swing.JTextField();
    jLabel8 = new javax.swing.JLabel();
    text_total_bayar = new javax.swing.JTextField();
    // text_nama_menu_terpilih dibuat untuk menggantikan combo box
    text_nama_menu_terpilih = new javax.swing.JTextField(); 

    setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

    // ... (Kode pengaturan jLabel1, jLabel2, jLabel3, text_id_transaksi, text_nama_pelanggan) ...

    jLabel1.setFont(new java.awt.Font("Consolas", 1, 36)); // NOI18N
    jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    jLabel1.setText("MENU TRANSAKSI");
    jLabel1.setToolTipText("");
    jLabel1.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);

    jLabel2.setFont(new java.awt.Font("Consolas", 1, 24)); // NOI18N
    jLabel2.setText("ID Transaksi");

    jLabel3.setFont(new java.awt.Font("Consolas", 1, 24)); // NOI18N
    jLabel3.setText("Nama Pelanggan");

    text_id_transaksi.setEnabled(false);
    text_id_transaksi.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            text_id_transaksiActionPerformed(evt);
        }
    });

    text_nama_pelanggan.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            text_nama_pelangganActionPerformed(evt);
        }
    });

    // Label ID Masakan dipertahankan
    jLabel5.setFont(new java.awt.Font("Consolas", 1, 24)); // NOI18N
    jLabel5.setText("Menu Dipilih"); 
    
    // Komponen text_nama_menu_terpilih sebagai penanda menu yang sudah dipilih
    text_nama_menu_terpilih.setEnabled(false); 
    text_nama_menu_terpilih.setFont(new java.awt.Font("Consolas", 0, 18));
    text_nama_menu_terpilih.setText("[Pilih Menu]");

    table_transaksi.setModel(new javax.swing.table.DefaultTableModel(
        new Object [][] {
            {null, null, null, null},
            {null, null, null, null},
            {null, null, null, null},
            {null, null, null, null}
        },
        new String [] {
            "Title 1", "Title 2", "Title 3", "Title 4"
        }
    ));
    table_transaksi.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            table_transaksiMouseClicked(evt);
        }
    });
    jScrollPane1.setViewportView(table_transaksi);

    jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

    btn_cetak_laporan.setFont(new java.awt.Font("Consolas", 1, 18)); // NOI18N
    btn_cetak_laporan.setText("CETAK LAPORAN");

    btn_input.setFont(new java.awt.Font("Consolas", 1, 18)); // NOI18N
    btn_input.setText("TAMBAH KE KERANJANG"); // Teks diubah
    btn_input.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            btn_inputActionPerformed(evt);
        }
    });

    btn_update.setFont(new java.awt.Font("Consolas", 1, 18)); // NOI18N
    btn_update.setText("SELESAI TRANSAKSI"); // Teks diubah
    btn_update.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            btn_updateActionPerformed(evt);
        }
    });

    btn_delete.setFont(new java.awt.Font("Consolas", 1, 18)); // NOI18N
    btn_delete.setText("BATALKAN ITEM"); // Teks diubah
    btn_delete.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            btn_deleteActionPerformed(evt);
        }
    });

    javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
    jPanel1.setLayout(jPanel1Layout);
    jPanel1Layout.setHorizontalGroup(
        jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel1Layout.createSequentialGroup()
            .addContainerGap()
            .addComponent(btn_cetak_laporan, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(btn_input, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(btn_update, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(btn_delete, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addContainerGap())
    );
    jPanel1Layout.setVerticalGroup(
        jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel1Layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(btn_cetak_laporan, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                .addComponent(btn_input, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                .addComponent(btn_delete, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                .addComponent(btn_update, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE))
            .addContainerGap())
    );

    // --- START: PENGATURAN PROPERTI DAN AKSI TOMBOL KEMBALI ---
    btn_menu_masakan_kembali.setFont(new java.awt.Font("Consolas", 1, 18)); // NOI18N
    btn_menu_masakan_kembali.setText("MENU MASAKAN");
    btn_menu_masakan_kembali.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            btn_kembali_masakanActionPerformed(evt);
        }
    });
    // --- END: PENGATURAN PROPERTI DAN AKSI TOMBOL KEMBALI ---
    
    btn_logout.setFont(new java.awt.Font("Consolas", 1, 18)); // NOI18N
    btn_logout.setText("LOGOUT");
    btn_logout.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            btn_logoutActionPerformed(evt);
        }
    });

    btn_menu_masakan.setFont(new java.awt.Font("Consolas", 1, 24)); // NOI18N
    btn_menu_masakan.setText("LIHAT MENU");
    btn_menu_masakan.setAlignmentY(0.0F);
    btn_menu_masakan.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            btn_menu_masakanActionPerformed(evt);
        }
    });

    jLabel6.setFont(new java.awt.Font("Consolas", 1, 24)); // NOI18N
    jLabel6.setText("Tanggal");

    jLabel7.setFont(new java.awt.Font("Consolas", 1, 24)); // NOI18N
    jLabel7.setText("Jumlah Beli");

    text_jml_beli.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            text_jml_beliActionPerformed(evt);
        }
    });
    text_jml_beli.addKeyListener(new java.awt.event.KeyAdapter() {
        public void keyReleased(java.awt.event.KeyEvent evt) {
            text_jml_beliKeyReleased(evt);
        }
    });

    jLabel8.setFont(new java.awt.Font("Consolas", 1, 24)); // NOI18N
    jLabel8.setText("Total Bayar");

    text_total_bayar.setEnabled(false);
    text_total_bayar.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            text_total_bayarActionPerformed(evt);
        }
    });

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    
    // --- START: MODIFIKASI LAYOUT HORIZONTAL ---
    layout.setHorizontalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(layout.createSequentialGroup()
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addGap(0, 0, Short.MAX_VALUE)
                            .addComponent(btn_menu_masakan_kembali) // <-- TOMBOL BARU DITAMBAH DI KIRI LOGOUT
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED) // Jarak
                            .addComponent(btn_logout) // Tombol LOGOUT yang sudah ada
                            .addGap(12, 12, 12))))
                .addGroup(layout.createSequentialGroup()
                    .addGap(294, 294, 294)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE))
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE) // Label Menu Dipilih
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(18, 18, 18)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(text_jml_beli, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(text_nama_pelanggan, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(text_id_transaksi, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(text_total_bayar, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addComponent(text_nama_menu_terpilih) // Text field untuk Nama Menu
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btn_menu_masakan, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGap(301, 301, 301))
                .addGroup(layout.createSequentialGroup()
                    .addGap(38, 38, 38)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1115, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(30, Short.MAX_VALUE)))
            .addContainerGap())
    );
    // --- END: MODIFIKASI LAYOUT HORIZONTAL ---
    
    // --- START: MODIFIKASI LAYOUT VERTIKAL ---
    layout.setVerticalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE) // <-- Group Tombol Logout
                .addComponent(btn_logout, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(btn_menu_masakan_kembali, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)) // <-- TOMBOL BARU DITAMBAH DI SINI
            .addGap(18, 18, 18)
            .addComponent(jLabel1)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel2)
                .addComponent(text_id_transaksi, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(18, 18, 18)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(text_nama_pelanggan, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel3))
            .addGap(18, 18, 18)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_menu_masakan, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                .addComponent(text_nama_menu_terpilih))
            .addGap(18, 18, 18)
            .addComponent(jLabel6)
            .addGap(18, 18, 18)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel7)
                .addComponent(text_jml_beli, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(18, 18, 18)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel8)
                .addComponent(text_total_bayar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(18, 18, 18)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(18, 18, 18)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 149, Short.MAX_VALUE)
            .addGap(16, 16, 16))
    );
    // --- END: MODIFIKASI LAYOUT VERTIKAL ---

    pack();
}// </editor-fold>                     

    private void text_id_transaksiActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void text_nama_pelangganActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    // INPUT (TAMBAH KE KERANJANG)
    private void btn_inputActionPerformed(java.awt.event.ActionEvent evt) {
        tambahKeKeranjang();
    }       

   // Di dalam file menu_transaksi.java

private void btn_logoutActionPerformed(java.awt.event.ActionEvent evt) {
    int confirm = JOptionPane.showConfirmDialog(this, 
    "Yakin mau logout?", "Konfirmasi", JOptionPane.YES_NO_OPTION);

    if (confirm == JOptionPane.YES_OPTION) {
        // Hapus komentar ini agar form Login dibuka!
        new kasirapp.Login().setVisible(true); // Pastikan package 'kasirapp' sesuai
        
        this.dispose(); // Tutup form menu_transaksi saat ini
    }
}
private void btn_kembali_masakanActionPerformed(java.awt.event.ActionEvent evt) {
    // Membuka form Menu Masakan
    new kasirapp.menu_masakan().setVisible(true); 
    
    // Menutup form transaksi yang sedang aktif
    this.dispose(); 
}
    // Aksi tombol LIHAT MENU
    private void btn_menu_masakanActionPerformed(java.awt.event.ActionEvent evt) {                                             
        // 1. Buat instance form pemilihan menu (JDialog)
        // Pastikan kelas MenuPilihMasakan sudah dibuat dengan benar
        MenuPilihMasakan pilihMenu = new MenuPilihMasakan(this, true); 
        
        // 2. Tampilkan form
        pilihMenu.setVisible(true);

        // 3. Setelah form ditutup (data dipilih atau dibatalkan), cek hasilnya
        if (pilihMenu.getIdMasakanDipilih() != null) {
            // Data berhasil diambil dan disimpan ke variabel member
            idMasakanSaatIni = pilihMenu.getIdMasakanDipilih();
            namaMasakanSaatIni = pilihMenu.getNamaMasakanDipilih();
            hargaMasakanSaatIni = pilihMenu.getHargaDipilih();
            
            // Tampilkan nama masakan yang dipilih di text field 
            text_nama_menu_terpilih.setText(namaMasakanSaatIni);
            
            // Tampilkan harga di Total Bayar (sementara)
            text_total_bayar.setText(String.valueOf(hargaMasakanSaatIni));
            
            // Fokuskan ke Jumlah Beli
            text_jml_beli.requestFocus();
        }
    }                                           


    private void text_jml_beliActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void text_total_bayarActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    // DELETE (BATALKAN ITEM DARI KERANJANG)
    private void btn_deleteActionPerformed(java.awt.event.ActionEvent evt) {
        batalkanItem();
    }

    private void table_transaksiMouseClicked(java.awt.event.MouseEvent evt) {
        // Tidak perlu diisi karena ini keranjang belanja sementara
    }

    // UPDATE (SELESAI TRANSAKSI)
    private void btn_updateActionPerformed(java.awt.event.ActionEvent evt) {
        selesaiTransaksi();
    }

    // Hitung subtotal sementara saat mengetik Jumlah Beli
    private void text_jml_beliKeyReleased(java.awt.event.KeyEvent evt) {
        hitungSubtotalSementara();
    }


    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(menu_transaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new menu_transaksi().setVisible(true);
        });
    }

    // Variables declaration - do not modify
    public javax.swing.JButton btn_cetak_laporan;
    public javax.swing.JButton btn_delete;
    public javax.swing.JButton btn_input;
    public javax.swing.JButton btn_logout;
    public javax.swing.JButton btn_update;
    // Hapus: private javax.swing.JComboBox<String> combo_id_masakan;
    private javax.swing.JButton btn_menu_masakan; // <-- PASTIKAN INI ADA
    private javax.swing.JButton btn_menu_masakan_kembali;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable table_transaksi;
    private javax.swing.JTextField text_id_transaksi;
    private javax.swing.JTextField text_jml_beli;
    private javax.swing.JTextField text_nama_pelanggan;
    private javax.swing.JTextField text_total_bayar;
    private javax.swing.JTextField text_nama_menu_terpilih; // Tambahan
    // End of variables declaration
}