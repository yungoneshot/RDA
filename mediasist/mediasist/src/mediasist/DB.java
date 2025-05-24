package mediasist;

import java.sql.Connection;
import java.sql.DriverManager;

public class DB {
    public static Connection getConnection() throws Exception {
        String url = "jdbc:mysql://ucka.veleri.hr:3306/dsepic?useSSL=false&serverTimezone=UTC";
        String user = "dsepic";
        String password = "11";
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(url, user, password);
    }
}
