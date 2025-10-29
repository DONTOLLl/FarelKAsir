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
public class menu_registrasi extends javax.swing.JFrame {

    private Connection conn;
    private DefaultTableModel tabmode;
    
    public menu_registrasi() {
         initComponents();

        // klik baris tabel -> isi form (password sengaja dikosongkan agar tidak tampil)
        table_registrasi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int baris = table_registrasi.getSelectedRow();
                if (baris != -1) {
                    text_id_user.setText(table_registrasi.getValueAt(baris, 0).toString());
                    text_username.setText(table_registrasi.getValueAt(baris, 1).toString());
                    text_nama_user.setText(table_registrasi.getValueAt(baris, 2).toString());
                    combo_id_level.setSelectedItem(table_registrasi.getValueAt(baris, 3).toString());
                    // jangan tampilkan password di form untuk keamanan, kosongkan aja
                    text_password.setText("");
                }
            }
        });

        try {
            conn = Koneksi.configDB();
            tampil_data();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Koneksi gagal: " + e.getMessage());
        }
    }

     private void tampil_data() {
        Object[] header = {"ID User", "Username", "Nama User", "ID Level"};
        tabmode = new DefaultTableModel(null, header);
        table_registrasi.setModel(tabmode);

        String sql = "SELECT id_user, username, nama_user, id_level FROM public.tbl_user ORDER BY id_user ASC";

        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                String id = rs.getString("id_user");
                String username = rs.getString("username");
                String nama = rs.getString("nama_user");
                String level = rs.getString("id_level");

                String[] data = {id, username, nama, level};
                tabmode.addRow(data);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Gagal menampilkan data: " + e.getMessage());
        }
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        text_id_user = new javax.swing.JTextField();
        text_username = new javax.swing.JTextField();
        text_password = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        combo_id_level = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        table_registrasi = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        btn_input = new javax.swing.JButton();
        btn_update = new javax.swing.JButton();
        btn_delete = new javax.swing.JButton();
        btn_menu_masakan = new javax.swing.JButton();
        btn_logout = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        text_nama_user = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel2.setFont(new java.awt.Font("Consolas", 1, 24)); // NOI18N
        jLabel2.setText("ID User");

        jLabel3.setFont(new java.awt.Font("Consolas", 1, 24)); // NOI18N
        jLabel3.setText("Username");

        jLabel4.setFont(new java.awt.Font("Consolas", 1, 24)); // NOI18N
        jLabel4.setText("Password");

        text_id_user.setEnabled(false);
        text_id_user.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text_id_userActionPerformed(evt);
            }
        });

        text_username.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text_usernameActionPerformed(evt);
            }
        });

        text_password.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text_passwordActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Consolas", 1, 24)); // NOI18N
        jLabel5.setText("ID Level");

        combo_id_level.setFont(new java.awt.Font("Consolas", 1, 24)); // NOI18N
        combo_id_level.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", " " }));
        combo_id_level.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        combo_id_level.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combo_id_levelActionPerformed(evt);
            }
        });

        table_registrasi.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "ID User", "Username", "Nama User", "ID Level"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(table_registrasi);
        if (table_registrasi.getColumnModel().getColumnCount() > 0) {
            table_registrasi.getColumnModel().getColumn(0).setResizable(false);
            table_registrasi.getColumnModel().getColumn(1).setResizable(false);
            table_registrasi.getColumnModel().getColumn(3).setResizable(false);
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

        btn_menu_masakan.setFont(new java.awt.Font("Consolas", 1, 18)); // NOI18N
        btn_menu_masakan.setText("MENU MASAKAN");
        btn_menu_masakan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_menu_masakanActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btn_input)
                .addGap(18, 18, 18)
                .addComponent(btn_update)
                .addGap(18, 18, 18)
                .addComponent(btn_delete)
                .addGap(18, 18, 18)
                .addComponent(btn_menu_masakan, javax.swing.GroupLayout.DEFAULT_SIZE, 293, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_input, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                    .addComponent(btn_update, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_delete, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_menu_masakan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        btn_logout.setFont(new java.awt.Font("Consolas", 1, 18)); // NOI18N
        btn_logout.setText("LOGOUT");
        btn_logout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_logoutActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Consolas", 1, 24)); // NOI18N
        jLabel6.setText("Nama User");

        text_nama_user.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text_nama_userActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Consolas", 1, 36)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("MENU REGISTRASI ");
        jLabel1.setToolTipText("");
        jLabel1.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(180, 180, 180)
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(193, 193, 193)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(text_nama_user, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(text_password, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(text_username, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(text_id_user, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(combo_id_level, javax.swing.GroupLayout.PREFERRED_SIZE, 349, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btn_logout)))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(96, 96, 96)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 801, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(60, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 349, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(276, 276, 276))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btn_logout, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(text_id_user, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(text_username, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(text_password, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(text_nama_user, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(combo_id_level, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(60, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void text_id_userActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text_id_userActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_text_id_userActionPerformed

    private void text_usernameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text_usernameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_text_usernameActionPerformed

    private void text_passwordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text_passwordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_text_passwordActionPerformed

    private void btn_inputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_inputActionPerformed
        try {
            if (text_username.getText().trim().isEmpty() || text_password.getText().trim().isEmpty() || text_nama_user.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Isi Username, Password, dan Nama User terlebih dahulu!");
                return;
            }

            String sql = "INSERT INTO public.tbl_user (username, password, nama_user, id_level) VALUES (?, ?, ?, ?)";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, text_username.getText().trim());
            pst.setString(2, text_password.getText()); // simpan langsung (kalo mau hash nanti bisa ditambah)
            pst.setString(3, text_nama_user.getText().trim());
            pst.setInt(4, Integer.parseInt(combo_id_level.getSelectedItem().toString()));
            pst.executeUpdate();

            JOptionPane.showMessageDialog(this, "Data user berhasil disimpan!");
            tampil_data();
            resetForm();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal simpan: " + e.getMessage());
        }        // TODO add your handling code here:
    }//GEN-LAST:event_btn_inputActionPerformed

    private void btn_logoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_logoutActionPerformed
        int confirm = JOptionPane.showConfirmDialog(this, 
        "Yakin mau logout?", "Konfirmasi", JOptionPane.YES_NO_OPTION);

    if (confirm == JOptionPane.YES_OPTION) {
        new Login().setVisible(true); // buka kembali form login
        this.dispose(); // tutup form menu_masakan
    }
    }//GEN-LAST:event_btn_logoutActionPerformed

    private void combo_id_levelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combo_id_levelActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_combo_id_levelActionPerformed

    private void text_nama_userActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text_nama_userActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_text_nama_userActionPerformed

    private void btn_menu_masakanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_menu_masakanActionPerformed
        int confirm = JOptionPane.showConfirmDialog(this, 
        "Yakin", "Konfirmasi", JOptionPane.YES_NO_OPTION);

    if (confirm == JOptionPane.YES_OPTION) {
        new menu_masakan().setVisible(true); 
        this.dispose(); 
    }       
    }//GEN-LAST:event_btn_menu_masakanActionPerformed

    private void btn_updateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_updateActionPerformed
        try {
            if (text_id_user.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Pilih data yang ingin diubah terlebih dahulu!");
                return;
            }

            String id = text_id_user.getText().trim();
            String username = text_username.getText().trim();
            String nama = text_nama_user.getText().trim();
            String level = combo_id_level.getSelectedItem().toString();
            String password = text_password.getText();

            String sql;
            PreparedStatement pst;

            if (password == null || password.trim().isEmpty()) {
                // update tanpa password
                sql = "UPDATE public.tbl_user SET username = ?, nama_user = ?, id_level = ? WHERE id_user = ?";
                pst = conn.prepareStatement(sql);
                pst.setString(1, username);
                pst.setString(2, nama);
                pst.setInt(3, Integer.parseInt(level));
                pst.setInt(4, Integer.parseInt(id));
            } else {
                // update termasuk password
                sql = "UPDATE public.tbl_user SET username = ?, password = ?, nama_user = ?, id_level = ? WHERE id_user = ?";
                pst = conn.prepareStatement(sql);
                pst.setString(1, username);
                pst.setString(2, password);
                pst.setString(3, nama);
                pst.setInt(4, Integer.parseInt(level));
                pst.setInt(5, Integer.parseInt(id));
            }

            int updated = pst.executeUpdate();
            if (updated > 0) {
                JOptionPane.showMessageDialog(this, "Data berhasil diupdate!");
                tampil_data();
                resetForm();
            } else {
                JOptionPane.showMessageDialog(this, "Update gagal: data tidak ditemukan.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error update: " + e.getMessage());
        }        // TODO add your handling code here:
    }//GEN-LAST:event_btn_updateActionPerformed

    private void btn_deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_deleteActionPerformed
        try {
            int baris = table_registrasi.getSelectedRow();
            if (baris == -1) {
                JOptionPane.showMessageDialog(this, "Pilih data yang ingin dihapus dulu!");
                return;
            }

            String id = table_registrasi.getValueAt(baris, 0).toString();
            int confirm = JOptionPane.showConfirmDialog(this, "Yakin mau hapus user ID " + id + "?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                String sql = "DELETE FROM public.tbl_user WHERE id_user = ?";
                PreparedStatement pst = conn.prepareStatement(sql);
                pst.setInt(1, Integer.parseInt(id));
                int rows = pst.executeUpdate();
                if (rows > 0) {
                    JOptionPane.showMessageDialog(this, "User berhasil dihapus!");
                } else {
                    JOptionPane.showMessageDialog(this, "User tidak ditemukan.");
                }
                tampil_data();
                resetForm();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal hapus: " + e.getMessage());
        }        // TODO add your handling code here:
    }//GEN-LAST:event_btn_deleteActionPerformed
    
    private void resetForm() {
        text_id_user.setText("");
        text_username.setText("");
        text_password.setText("");
        text_nama_user.setText("");
        combo_id_level.setSelectedIndex(0);
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
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(menu_registrasi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new menu_registrasi().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btn_delete;
    public javax.swing.JButton btn_input;
    public javax.swing.JButton btn_logout;
    public javax.swing.JButton btn_menu_masakan;
    public javax.swing.JButton btn_update;
    private javax.swing.JComboBox<String> combo_id_level;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable table_registrasi;
    private javax.swing.JTextField text_id_user;
    private javax.swing.JTextField text_nama_user;
    private javax.swing.JTextField text_password;
    private javax.swing.JTextField text_username;
    // End of variables declaration//GEN-END:variables
}
