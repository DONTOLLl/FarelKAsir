
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package kasirapp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import koneksi.Koneksi;

/**
 *
 * @author Jo
 */
public class menu_masakan extends javax.swing.JFrame {

    private Connection conn;
    private DefaultTableModel tabmode;
    
    public menu_masakan() {
        initComponents();
        
        table_masakan.addMouseListener(new java.awt.event.MouseAdapter() {
    public void mouseClicked(java.awt.event.MouseEvent evt) {
        int baris = table_masakan.getSelectedRow();
        if (baris != -1) {
            // ambil data dari tabel
            String id = table_masakan.getValueAt(baris, 0).toString();
            String nama = table_masakan.getValueAt(baris, 1).toString();
            String harga = table_masakan.getValueAt(baris, 2).toString();
            String status = table_masakan.getValueAt(baris, 3).toString();

            // isi ke field input
            text_id_masakan.setText(id);
            text_nama_masakan.setText(nama);
            text_harga_masakan.setText(harga);
            combo_status_masakan.setSelectedItem(status);
        }
    }
});

    try {
        conn = Koneksi.configDB(); // langsung ambil koneksi dari Koneksi.java
        tampil_data();
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Koneksi gagal: " + e.getMessage());
    }
    }   

    private void tampil_data() {
     // Kolom header tabel
    Object[] baris = {"ID", "Nama Masakan", "Harga", "Status"};
    tabmode = new DefaultTableModel(null, baris);
    table_masakan.setModel(tabmode);

    // Query ambil data
    String sql = "SELECT id_masakan, nama_masakan, harga, status FROM public.tbl_masakan ORDER BY id_masakan ASC";

    try {
        java.sql.Statement stat = conn.createStatement();
        java.sql.ResultSet hasil = stat.executeQuery(sql);

        // Loop isi data ke tabel
        while (hasil.next()) {
            String id = hasil.getString("id_masakan");
            String nama = hasil.getString("nama_masakan");
            String harga = hasil.getString("harga");
            String status = hasil.getString("status");

            String[] data = {id, nama, harga, status};
            tabmode.addRow(data);
        }

    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Data gagal dimuat: " + e.getMessage());
    }
}

    
    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {                                          
         try {
            String sql = "INSERT INTO tbl_masakan (nama_masakan, harga, status) VALUES (?, ?, ?)";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, text_nama_masakan.getText());
            pst.setInt(2, Integer.parseInt(text_harga_masakan.getText()));
            pst.setString(3, combo_status_masakan.getSelectedItem().toString());
            pst.executeUpdate();

            JOptionPane.showMessageDialog(this, "Data berhasil disimpan");
            tampil_data();
            resetForm();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error simpan: " + e.getMessage());
        }
    }                                         

    // ubah data
    private void btnUbahActionPerformed(java.awt.event.ActionEvent evt) {                                        
        try {
            String sql = "UPDATE tbl_masakan SET nama=?, harga=? WHERE id_masakan=?";
            Connection conn = Koneksi.configDB();
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, text_nama_masakan.getText());
            pst.setString(2, text_harga_masakan.getText());
            pst.setString(3, text_id_masakan.getText());
            pst.executeUpdate();
            JOptionPane.showMessageDialog(this, "Data berhasil diubah");
            tampil_data();
            reset();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error ubah: " + e.getMessage());
        }
    }
    
     private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {                                         
        try {
            String sql = "DELETE FROM tbl_masakan WHERE id_masakan=?";
            Connection conn = Koneksi.configDB();
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, text_id_masakan.getText());
            pst.executeUpdate();
            JOptionPane.showMessageDialog(this, "Data berhasil dihapus");
            tampil_data();
            reset();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error hapus: " + e.getMessage());
        }
    }                                        

    // klik tabel → isi form
    private void tableMasakanMouseClicked(java.awt.event.MouseEvent evt) {                                          
        int baris = table_masakan.rowAtPoint(evt.getPoint());
        text_id_masakan.setText(table_masakan.getValueAt(baris, 0).toString());
        text_nama_masakan.setText(table_masakan.getValueAt(baris, 1).toString());
        text_harga_masakan.setText(table_masakan.getValueAt(baris, 2).toString());
    }                                         

    // reset input
    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {                                         
        reset();
    }                                        

    private void reset() {
        text_id_masakan.setText("");
        text_nama_masakan.setText("");
        text_harga_masakan.setText("");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        text_id_masakan = new javax.swing.JTextField();
        text_nama_masakan = new javax.swing.JTextField();
        text_harga_masakan = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        combo_status_masakan = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        table_masakan = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        btn_input = new javax.swing.JButton();
        btn_update = new javax.swing.JButton();
        btn_delete = new javax.swing.JButton();
        btn_transaksi = new javax.swing.JButton();
        btn_logout = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Consolas", 1, 36)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("MENU MASAKAN ");
        jLabel1.setToolTipText("");
        jLabel1.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);

        jLabel2.setFont(new java.awt.Font("Consolas", 1, 24)); // NOI18N
        jLabel2.setText("ID Masakan");

        jLabel3.setFont(new java.awt.Font("Consolas", 1, 24)); // NOI18N
        jLabel3.setText("Nama Masakan");

        jLabel4.setFont(new java.awt.Font("Consolas", 1, 24)); // NOI18N
        jLabel4.setText("Harga");

        text_id_masakan.setEnabled(false);
        text_id_masakan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text_id_masakanActionPerformed(evt);
            }
        });

        text_nama_masakan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text_nama_masakanActionPerformed(evt);
            }
        });

        text_harga_masakan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text_harga_masakanActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Consolas", 1, 24)); // NOI18N
        jLabel5.setText("Status Makanan");

        combo_status_masakan.setFont(new java.awt.Font("Consolas", 1, 24)); // NOI18N
        combo_status_masakan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tersedia", "Habis", " ", " " }));
        combo_status_masakan.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        table_masakan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(table_masakan);
        if (table_masakan.getColumnModel().getColumnCount() > 0) {
            table_masakan.getColumnModel().getColumn(0).setResizable(false);
            table_masakan.getColumnModel().getColumn(1).setResizable(false);
            table_masakan.getColumnModel().getColumn(2).setResizable(false);
            table_masakan.getColumnModel().getColumn(3).setResizable(false);
        }

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        btn_input.setFont(new java.awt.Font("Consolas", 1, 18)); // NOI18N
        btn_input.setText("INPUT");
        btn_input.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_inputActionPerformed(evt);
            }
        });

        btn_update.setFont(new java.awt.Font("Consolas", 1, 18)); // NOI18N
        btn_update.setText("UPDATE");
        btn_update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_updateActionPerformed(evt);
            }
        });

        btn_delete.setFont(new java.awt.Font("Consolas", 1, 18)); // NOI18N
        btn_delete.setText("DELETE");
        btn_delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_deleteActionPerformed(evt);
            }
        });

        btn_transaksi.setFont(new java.awt.Font("Consolas", 1, 18)); // NOI18N
        btn_transaksi.setText("MENU TRANSAKSI");
        btn_transaksi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_transaksiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_input)
                .addGap(18, 18, 18)
                .addComponent(btn_update)
                .addGap(18, 18, 18)
                .addComponent(btn_delete)
                .addGap(18, 18, 18)
                .addComponent(btn_transaksi)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btn_update, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_delete, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btn_transaksi)
                            .addComponent(btn_input))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        btn_logout.setFont(new java.awt.Font("Consolas", 1, 18)); // NOI18N
        btn_logout.setText("LOGOUT");
        btn_logout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_logoutActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
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
                                .addComponent(btn_logout)
                                .addGap(12, 12, 12))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(313, 313, 313)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(44, 44, 44)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(text_harga_masakan, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(text_nama_masakan, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(text_id_masakan, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(combo_status_masakan, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1124, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 46, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(328, 328, 328)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btn_logout, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(text_id_masakan, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(text_nama_masakan, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(text_harga_masakan, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(combo_status_masakan, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(26, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void text_id_masakanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text_id_masakanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_text_id_masakanActionPerformed

    private void text_nama_masakanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text_nama_masakanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_text_nama_masakanActionPerformed

    private void text_harga_masakanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text_harga_masakanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_text_harga_masakanActionPerformed

    private void btn_inputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_inputActionPerformed
           try {
        // Insert lengkap ke DB (nama, harga, status)
        String sql = "INSERT INTO tbl_masakan (nama_masakan, harga, status) VALUES (?, ?, ?)";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setString(1, text_nama_masakan.getText());
        pst.setInt(2, Integer.parseInt(text_harga_masakan.getText()));
        pst.setString(3, combo_status_masakan.getSelectedItem().toString());
        pst.executeUpdate();

        JOptionPane.showMessageDialog(this, "Data berhasil disimpan ke database!");

        // Refresh tabel dari database biar ID auto increment muncul bener
        tampil_data();

        // Reset field input
        resetForm();

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Gagal simpan data: " + e.getMessage());
    }
    }//GEN-LAST:event_btn_inputActionPerformed

    private void btn_logoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_logoutActionPerformed
       int confirm = JOptionPane.showConfirmDialog(this, 
        "Yakin mau logout?", "Konfirmasi", JOptionPane.YES_NO_OPTION);

    if (confirm == JOptionPane.YES_OPTION) {
        new Login().setVisible(true); // buka kembali form login
        this.dispose(); // tutup form menu_masakan
    }        
    }//GEN-LAST:event_btn_logoutActionPerformed

    private void btn_transaksiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_transaksiActionPerformed
        int confirm = JOptionPane.showConfirmDialog(this, 
        "Yakin", "Konfirmasi", JOptionPane.YES_NO_OPTION);

    if (confirm == JOptionPane.YES_OPTION) {
        new menu_transaksi().setVisible(true); 
        this.dispose(); 
    }        
    }//GEN-LAST:event_btn_transaksiActionPerformed

    private void btn_deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_deleteActionPerformed
         int baris = table_masakan.getSelectedRow(); // ambil baris yg diklik user
    
    if (baris == -1) {
        JOptionPane.showMessageDialog(this, "Pilih data yang mau dihapus dulu!");
        return;
    }

    // ambil id langsung dari tabel
    String id = table_masakan.getValueAt(baris, 0).toString();

    int confirm = JOptionPane.showConfirmDialog(this,
            "Yakin mau hapus data dengan ID " + id + "?",
            "Konfirmasi", JOptionPane.YES_NO_OPTION);

    if (confirm == JOptionPane.YES_OPTION) {
        hapusData(id);   // fungsi hapus
        loadData();      // refresh tabel
        resetForm();     // reset input
    }
    }//GEN-LAST:event_btn_deleteActionPerformed
    
    public void hapusData(String idMasakan) {
    Connection conn = null;
    PreparedStatement pst = null;

    try {
        conn = Koneksi.configDB(); 
        String sql = "DELETE FROM tbl_masakan WHERE id_masakan = ?"; // ✅ fix nama tabel
        pst = conn.prepareStatement(sql);
        pst.setInt(1, Integer.parseInt(idMasakan));

        int rows = pst.executeUpdate();
        if (rows > 0) {
            JOptionPane.showMessageDialog(null, "Data berhasil dihapus!");
        } else {
            JOptionPane.showMessageDialog(null, "Data tidak ditemukan!");
        }

    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Gagal hapus data: " + e.getMessage());
    } finally {
        try {
            if (pst != null) pst.close();
            if (conn != null) conn.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    }

    
    private void loadData() {
     DefaultTableModel model = new DefaultTableModel();
    model.addColumn("ID Masakan");
    model.addColumn("Nama Masakan");
    model.addColumn("Harga");
    model.addColumn("Status");

    try {
        String sql = "SELECT * FROM tbl_masakan";
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(sql);

        while (rs.next()) {
            model.addRow(new Object[]{
                rs.getString("id_masakan"),
                rs.getString("nama_masakan"),
                rs.getString("harga"),
                rs.getString("status") // ✅ fix kolom status
            });
        }

        table_masakan.setModel(model);
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error load data: " + e.getMessage());
    }
}

    
    private void btn_updateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_updateActionPerformed
        try {
            String sql = "UPDATE tbl_masakan SET nama_masakan=?, harga=?, status=? WHERE id_masakan=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, text_nama_masakan.getText());
            pst.setInt(2, Integer.parseInt(text_harga_masakan.getText()));
            pst.setString(3, combo_status_masakan.getSelectedItem().toString());
            pst.setInt(4, Integer.parseInt(text_id_masakan.getText()));
            pst.executeUpdate();

            JOptionPane.showMessageDialog(this, "Data berhasil diupdate");
            tampil_data();
            resetForm();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error update: " + e.getMessage());
        }
    }//GEN-LAST:event_btn_updateActionPerformed
   
    private void clearForm() {
    text_id_masakan.setText("");
    text_nama_masakan.setText("");
    text_harga_masakan.setText("");
    combo_status_masakan.setSelectedIndex(0);
    }
    
    private void tampilData() {
    DefaultTableModel model = new DefaultTableModel();
    model.addColumn("ID");
    model.addColumn("Nama Masakan");
    model.addColumn("Harga");
    model.addColumn("Status");

    try {
        String sql = "SELECT * FROM tbl_masakan";
        java.sql.Connection conn = (Connection)Koneksi.configDB();
        java.sql.Statement stm = conn.createStatement();
        java.sql.ResultSet res = stm.executeQuery(sql);

        while (res.next()) {
            model.addRow(new Object[]{
                res.getInt("id_masakan"),
                res.getString("nama_masakan"),
                res.getInt("harga"),
                res.getString("status")
            });
        }

        table_masakan.setModel(model);
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Error tampil data: " + e.getMessage());
    }
}

    private void table_masakanMouseClicked(java.awt.event.MouseEvent evt) {                                          
        int baris = table_masakan.rowAtPoint(evt.getPoint());

        text_id_masakan.setText(table_masakan.getValueAt(baris, 0).toString());
        text_nama_masakan.setText(table_masakan.getValueAt(baris, 1).toString());
        text_harga_masakan.setText(table_masakan.getValueAt(baris, 2).toString());
        combo_status_masakan.setSelectedItem(table_masakan.getValueAt(baris, 3).toString());
    } 
    
    private void resetForm() {
        text_id_masakan.setText("");
        text_nama_masakan.setText("");
        text_harga_masakan.setText("");
        combo_status_masakan.setSelectedIndex(0);
    }


    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(menu_masakan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(menu_masakan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(menu_masakan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(menu_masakan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new menu_masakan().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btn_delete;
    public javax.swing.JButton btn_input;
    public javax.swing.JButton btn_logout;
    public javax.swing.JButton btn_transaksi;
    public javax.swing.JButton btn_update;
    private javax.swing.JComboBox<String> combo_status_masakan;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable table_masakan;
    private javax.swing.JTextField text_harga_masakan;
    private javax.swing.JTextField text_id_masakan;
    private javax.swing.JTextField text_nama_masakan;
    // End of variables declaration//GEN-END:variables
}
