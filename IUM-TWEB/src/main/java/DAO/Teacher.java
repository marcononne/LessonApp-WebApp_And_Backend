package DAO;

import utils.JSONClasses.TeacherJSON;
import javax.sql.rowset.CachedRowSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Teacher {
    protected static CachedRowSet checkTeacher(String name, String surname) {
        String query = "SELECT * FROM teacher WHERE Name = '" + name + "' AND Surname = '" + surname + "' AND Active = 1";
        return DAO.myExecute(query);
    }
    public static String insertTeacher(String name, String surname) throws SQLException { // Web
        CachedRowSet rowSet = checkTeacher(name, surname);
        if (!rowSet.next()) {
            String query = "INSERT INTO teacher(ID_Teacher, Name, Surname, Active) VALUES (NULL, '" + name + "', '" + surname + "', 1)";
            if(DAO.myUpdate(query))
                return "Teacher added successfully";
            else
                return "An error occurred while trying to add a teacher";
        } else
            return "teacher already exists!";
    }
    public static String removeTeacher(String name, String surname) throws SQLException { // Web
        CachedRowSet rowSet = checkTeacher(name, surname);
        if (rowSet.next()) {
            int idTeacher = rowSet.getInt(1);
            String query = "UPDATE teacher SET Active = 0 WHERE ID_Teacher = " + idTeacher + "";
            if (DAO.myUpdate(query)) {
                String query1 = "UPDATE teaching SET Active = 0 WHERE Teacher = " + idTeacher + "";
                if(DAO.myUpdate(query1)) {
                    String query2 = "UPDATE lesson SET Status = 'Deleted' WHERE Teacher = " + idTeacher + "";
                    if (DAO.myUpdate(query2))
                        return "Teacher removed successfully";
                    else
                        return "An error occurred while trying to remove a lesson booked with teacher to remove";
                } else
                    return "An error occurred while trying to remove a teaching assigned to teacher to remove";
            } else
                return "Unable to delete teacher";
        } else
            return "Teacher doesn't exist";
    }
    public static String getTeacherName(int id) throws SQLException { // Web & App
        String query = "SELECT Name, Surname FROM teacher WHERE ID_Teacher = " + id + "";
        CachedRowSet rowSet = DAO.myExecute(query);
        if (rowSet != null && rowSet.next()) {
            String name = rowSet.getString(1);
            String surname = rowSet.getString(2);
            return name + " " + surname;
        }
        return null;
    }
    public static int getTeacherID(String name, String surname) throws SQLException { // Web & App
        String query = "SELECT ID_Teacher FROM teacher WHERE Name = '" + name + "' AND Surname = '" + surname + "'";
        CachedRowSet rowSet = DAO.myExecute(query);
        if (rowSet != null && rowSet.next()) {
            return rowSet.getInt(1);
        }
        return -1;
    }
    public static ArrayList<TeacherJSON> getAllTeachers() throws SQLException { // Web & App
        ArrayList<TeacherJSON> res = new ArrayList<>();
        String query = "SELECT Name, Surname, Active FROM teacher";
        CachedRowSet rowSet = DAO.myExecute(query);
        if (rowSet != null) {
            while (rowSet.next()) {
                String name = rowSet.getString("Name");
                String surname = rowSet.getString("Surname");
                boolean status = rowSet.getBoolean("Active");
                TeacherJSON s = new TeacherJSON(name, surname, status);
                res.add(s);
            }
        } else {
            return null;
        }
        return res;
    }

    public static ArrayList<TeacherJSON> getAllActiveTeachers() throws SQLException { // Web
        ArrayList<TeacherJSON> res = new ArrayList<>();
        String query = "SELECT Name, Surname, Active FROM teacher WHERE Active = 1";
        CachedRowSet rowSet = DAO.myExecute(query);
        if (rowSet != null) {
            while (rowSet.next()) {
                String name = rowSet.getString("Name");
                String surname = rowSet.getString("Surname");
                boolean status = rowSet.getBoolean("Active");
                TeacherJSON s = new TeacherJSON(name, surname, status);
                res.add(s);
            }
        } else {
            return null;
        }
        return res;
    }
}
