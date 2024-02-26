package DAO;

import utils.JSONClasses.TeacherJSON;
import utils.JSONClasses.TeachingJSON;
import javax.sql.rowset.CachedRowSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Teaching {
    protected static CachedRowSet checkTeaching(int idCourse, int idTeacher) {
        String query = "SELECT * FROM teaching WHERE Course = " + idCourse + " AND Teacher = " + idTeacher + " AND Active = 1";
        return DAO.myExecute(query);
    }
    public static ArrayList<TeachingJSON> getAllTeachings() throws SQLException { // Web
        ArrayList<TeachingJSON> teachings = new ArrayList<>();
        String query = "SELECT Course, Teacher, Active FROM teaching";
        CachedRowSet rowSet = DAO.myExecute(query);
        if (rowSet != null) {
            if (rowSet.next()) {
                do {
                    String title = Course.getCourseTitle(rowSet.getInt(1));
                    String completeName = Teacher.getTeacherName(rowSet.getInt(2));
                    if (title == null || completeName == null) {
                        return null;
                    } else {
                        boolean status = rowSet.getBoolean(3);
                        teachings.add(new TeachingJSON(title, completeName, status));
                    }
                } while(rowSet.next());
                return teachings;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }
    public static String insertTeaching(String name, String surname, String title) throws SQLException { // Web
        CachedRowSet rowSet = Teacher.checkTeacher(name, surname);
        if (rowSet.next()) {
            int idTeacher = rowSet.getInt(1);
            rowSet = Course.checkCourse(title);
            if (rowSet.next()) {
                int idCourse = rowSet.getInt(1);
                rowSet = checkTeaching(idCourse, idTeacher);
                if (!rowSet.next()) {
                    String query = "INSERT INTO teaching(Course, Teacher, Active) VALUES (" + idCourse + ", " + idTeacher + ", 1)";
                    if(DAO.myUpdate(query))
                        return "Teaching added successfully";
                    else
                        return "An error occurred while trying to add a teaching";
                } else
                    return "Teaching already exists";
            } else
                return "Course doesn't exist";
        } else
            return "Teacher doesn't exist";
    }
    public static String removeTeaching(String name, String surname, String title) throws SQLException { // Web
        CachedRowSet rowSet = Teacher.checkTeacher(name, surname);
        if (rowSet.next()) {
            int idTeacher = rowSet.getInt(1);
            rowSet = Course.checkCourse(title);
            if (rowSet.next()) {
                int idCourse = rowSet.getInt(1);
                rowSet = checkTeaching(idCourse, idTeacher);
                if (rowSet.next()) {
                    String query = "UPDATE teaching SET Active = 0 WHERE Course = " + idCourse + " AND Teacher = " + idTeacher + "";
                    if(DAO.myUpdate(query)) {
                        String query1 = "UPDATE lesson SET Status = 'Deleted' WHERE Course = " + idCourse + " AND Teacher = " + idTeacher + "";
                        if (DAO.myUpdate(query1))
                            return "Teaching removed successfully";
                        else
                            return "An error occurred while trying to remove a lesson booked with teaching to remove";
                    } else
                        return "Unable to remove a teaching";
                } else
                    return "Teaching doesn't exist";
            } else
                return "Course doesn't exist";
        } else
            return "Teacher doesn't exist";
    }

    public static ArrayList<TeacherJSON> teacherPerCourse(String title) throws SQLException { // App
        String query = "SELECT ID_Course FROM course WHERE Title = '" + title + "'";
        CachedRowSet rowSet = DAO.myExecute(query);
        ArrayList<TeacherJSON> teachers = new ArrayList<>();
        if (rowSet != null) {
            if (rowSet.next()) {
                int idCourse = rowSet.getInt(1);
                query = "SELECT Teacher FROM teaching WHERE Course = " + idCourse;
                rowSet = DAO.myExecute(query);
                if (rowSet != null) {
                    if (rowSet.next()) {
                        do {
                            TeacherJSON teacher = getCompleteName(rowSet.getInt(1));
                            if(teacher != null)
                                teachers.add(teacher);
                            else
                                return null;
                        } while (rowSet.next());
                        return teachers;
                    } else {
                        return null;
                    }
                } else {
                    return null;
                }
            } else {
                return null;
            }
        } else {
            return null;
        }
    }
    private static TeacherJSON getCompleteName(int idTeacher) throws SQLException { // App
        String query = "SELECT Name, Surname, Active FROM teacher WHERE ID_Teacher = " + idTeacher;
        CachedRowSet rowSet = DAO.myExecute(query);
        if (rowSet != null) {
            if (rowSet.next()) {
                String name = rowSet.getString(1);
                String surname = rowSet.getString(2);
                boolean status = rowSet.getBoolean(3);
                return new TeacherJSON(name, surname, status);
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

}
