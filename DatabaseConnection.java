/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author David
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
        
    public static final int RETURN_GENERATED_KEYS = 1;

    private static ThreadLocal<Connection> con = new ThreadLocalConnection();

    public static Connection getConnection() {
        Connection c = con.get();
        try {
            c.getMetaData();
        } catch (SQLException e) { // connection is dead, therefore discard old object 5ever
            con.remove();
            c = con.get();
        }
        return c;
    }

    private static class ThreadLocalConnection extends ThreadLocal<Connection> {

        @Override
        protected Connection initialValue() {
            try {
                Class.forName("com.teradata.jdbc.TeraDriver"); // touch the mysql driver
            } catch (ClassNotFoundException e) {
                System.out.println("[SEVERE] SQL Driver Not Found. Consider death by clams.");
                return null;
            }
            try {
                return DriverManager.getConnection("jdbc:teradata://153.64.73.17/TMODE=ANSI,CHARSET=UTF8", "hack_user21", "tdhackathon");
            } catch (SQLException e) {
                System.out.println("[SEVERE] Unable to make database connection.");
                return null;
            }
        }
    }
}
