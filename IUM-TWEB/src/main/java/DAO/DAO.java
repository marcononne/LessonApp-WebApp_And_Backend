package DAO;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;
import java.sql.*;

public class DAO {

    static {
        registerDriver();
    }
    private static final String url = "jdbc:mysql://localhost:3306/db_progetto";
    public static void registerDriver() {
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            System.out.println("Driver registered");
        } catch (SQLException e) {
            System.out.println("Error_DAO.registerDriver: " + e.getMessage());
        }
    }
    public static Connection start() {
        Connection conn;
        try {
            conn = DriverManager.getConnection(url, "root", "");
            System.out.println("DataBase DB_Progetto connected");
            return conn;
        } catch (SQLException e) {
            System.out.println("Error_DAO.start: " + e.getMessage());
            return null;
        }
    }
    public static void end(Connection conn) {
        try {
            if (conn != null) {
                conn.close();
                System.out.println("DataBase DB_Progetto disconnected");
            }
        } catch (SQLException e) {
            System.out.println("Error_DAO.end: " + e.getMessage());
        }
    }
    protected static CachedRowSet myExecute(String query) {
        Connection conn = null;
        try {
            RowSetFactory factory = RowSetProvider.newFactory();
            conn = start(); // inizio connessione
            Statement st;
            if (conn != null) {
                st = conn.createStatement();
                ResultSet rs = st.executeQuery(query);
                CachedRowSet rowSet = factory.createCachedRowSet();
                rowSet.populate(rs);
                return rowSet;
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.out.println("Error_DAO.myExecute: " + e.getMessage());
            return null;
        } finally {
            end(conn);
        }
    }

    protected static boolean myUpdate(String query) {
        Connection conn = null;
        try {
            conn = start();
            Statement st;
            if (conn != null) {
                st = conn.createStatement();
                int res = st.executeUpdate(query);
                return (res == 1);
            } else {
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Error_DAO.myUpdate: " + e.getMessage());
            return false;
        } finally {
            end(conn);
        }
    }
}
