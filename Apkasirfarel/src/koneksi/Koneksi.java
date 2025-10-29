package koneksi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Koneksi {
    private static Connection postgreConfig;

    public static Connection configDB() throws SQLException {
        try {
            String url = "jdbc:postgresql://localhost:5432/kasir"; // ganti sesuai DB lu
            String user = "postgres"; // username PostgreSQL
            String pass = "12345678"; // password PostgreSQL

            Class.forName("org.postgresql.Driver"); // load driver PostgreSQL
            postgreConfig = DriverManager.getConnection(url, user, pass);
        } catch (Exception e) {
            System.out.println("Koneksi gagal: " + e.getMessage());
        }
        return postgreConfig;
    }
}
