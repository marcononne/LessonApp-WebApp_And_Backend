package DAO;

import utils.CryptPassword;
import javax.sql.rowset.CachedRowSet;
import java.sql.SQLException;

public class User {
    protected static CachedRowSet checkUser(String username) {
        String query = "SELECT * FROM user WHERE Username = '" + username + "'";
        return DAO.myExecute(query);
    }

    public static boolean checkUserMask(String username) throws SQLException { // Web
        CachedRowSet rowSet = checkUser(username);
        return rowSet.next();
    }

    private static CachedRowSet checkCredentials(String username, String password) { // Web & App
        String pwd = CryptPassword.encryptSHA2(password);
        String query = "SELECT * FROM user WHERE Username = '" + username + "' AND Password = '" + pwd + "'";
        return DAO.myExecute(query);
    }

    public static boolean authenticate(String username, String password) throws SQLException { // Web & App
        CachedRowSet rowSet = checkUser(username);
        if (rowSet.next()) {
            rowSet = checkCredentials(username, password);
            return rowSet.next();
        } else {
            return false;
        }
    }

    public static String getRole(String username) throws SQLException { // Web & App
        String query = "SELECT Role FROM user WHERE Username = '" + username + "'";
        CachedRowSet rowSet = DAO.myExecute(query);
        if (rowSet != null) {
            if (rowSet.next())
                return rowSet.getString(1);
            else
                return "ospite";
        } else {
            return "ospite";
        }
    }

    public static boolean insertUser(String username, String password, String name, String gender, String role) throws SQLException { // Web
        String pwd = CryptPassword.encryptSHA2(password);
        String query = "INSERT INTO user(Username, Password, Name, Gender, Role) VALUES ('" + username + "', '" + pwd + "', '" + name + "', '" + gender + "', '" + role + "')";
        return DAO.myUpdate(query);
    }
}
