package test;

import javax.swing.*;
import java.awt.event.*;
import java.sql.Connection;
import koneksi.Koneksi;

public class TestKoneksi extends JFrame {

    private JButton btnTest;
    private JLabel lblStatus;

    public TestKoneksi() {
        setTitle("Test Koneksi Database");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        btnTest = new JButton("Test Koneksi");
        btnTest.setBounds(80, 30, 120, 30);
        add(btnTest);

        lblStatus = new JLabel("Status: belum dicek");
        lblStatus.setBounds(80, 70, 200, 30);
        add(lblStatus);

        // Action tombol
        btnTest.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    Connection con = Koneksi.configDB();
                    if (con != null) {
                        lblStatus.setText("Status: Koneksi berhasil!");
                    } else {
                        lblStatus.setText("Status: Koneksi gagal!");
                    }
                } catch (Exception ex) {
                    lblStatus.setText("Error: " + ex.getMessage());
                }
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        new TestKoneksi();
    }
}
