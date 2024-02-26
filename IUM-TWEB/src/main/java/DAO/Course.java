package DAO;

import utils.JSONClasses.SubjectJSON;
import javax.sql.rowset.CachedRowSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class Course {
    protected static CachedRowSet checkCourse(String title) {
        String query = "SELECT * FROM course WHERE Title = '" + title + "' AND Active = 1";
        return DAO.myExecute(query);
    }
    public static String insertCourse(String title, String description) throws SQLException { // Web
        CachedRowSet rowSet = checkCourse(title);
        if (!rowSet.next()) {
            String query = "INSERT INTO course(ID_Course, Title, Description, Active) VALUES (NULL, '" + title + "', '" + description + "', 1)";
            if(DAO.myUpdate(query))
                return "Course added successfully";
            else
                return "An error occurred while trying to add a course";
        } else
            return "course already exists!";
    }
    public static String removeCourse(String title) throws SQLException { // Web - modifica per togliere stampe restituire gli errori o i successi come stringa
        CachedRowSet rowSet = checkCourse(title);
        if (rowSet.next()) {
            int idCourse = rowSet.getInt(1);
            String query = "UPDATE course SET Active = 0 WHERE ID_Course = " + idCourse + "";
            if (DAO.myUpdate(query)) {
                String query1 = "UPDATE teaching SET Active = 0 WHERE Course = " + idCourse + "";
                if(DAO.myUpdate(query1)) {
                    String query2 = "UPDATE lesson SET Status = 'Deleted' WHERE Course = " + idCourse + "";
                    if (DAO.myUpdate(query2))
                        return "Course removed successfully";
                    else
                        return "An error occurred while trying to remove a lesson booked with course to remove";
                } else
                    return "An error occurred while trying to remove a teaching assigned to course to remove";
            } else
                return "Unable to delete course";
        } else
            return "Course doesn't exist";
    }
    public static ArrayList<SubjectJSON> getAllActiveCourses() throws SQLException { // Web & App
        ArrayList<SubjectJSON> res = new ArrayList<>();
        String query = "SELECT Title, Description, Active FROM course WHERE Active = 1";
        CachedRowSet rowSet = DAO.myExecute(query);
        if (rowSet != null) {
            while (rowSet.next()) {
                String title = rowSet.getString("Title");
                String description = rowSet.getString("Description");
                boolean status = rowSet.getBoolean("Active");
                SubjectJSON s = new SubjectJSON(title, description, status);
                res.add(s);
            }
        }
        return res;
    }
    public static ArrayList<SubjectJSON> getAllCourses() throws SQLException { // Web & App
        ArrayList<SubjectJSON> res = new ArrayList<>();
        String query = "SELECT Title, Description, Active FROM course";
        CachedRowSet rowSet = DAO.myExecute(query);
        if (rowSet != null) {
            while (rowSet.next()) {
                String title = rowSet.getString("Title");
                String description = rowSet.getString("Description");
                boolean status = rowSet.getBoolean("Active");
                SubjectJSON s = new SubjectJSON(title, description, status);
                res.add(s);
            }
        }
        return res;
    }
    public static String getCourseTitle(int id) throws SQLException { // Web & App
        String query = "SELECT Title FROM course WHERE ID_Course = '" + id + "'";
        CachedRowSet rowSet = DAO.myExecute(query);
        if (rowSet != null && rowSet.next()) {
            return rowSet.getString(1);
        }
        return null;
    }
}
