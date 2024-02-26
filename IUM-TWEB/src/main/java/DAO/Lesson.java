package DAO;

import utils.JSONClasses.LessonJSON;
import utils.JSONClasses.TeacherJSON;
import utils.JSONClasses.TimeSlotsJSON;
import javax.sql.rowset.CachedRowSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;
import static java.sql.Date.valueOf;

public class Lesson {
    public static ArrayList<TeacherJSON> teachersAvailable(String title) throws SQLException { // Web & App
        String query = "SELECT ID_Course FROM course WHERE Title = '" + title + "' AND Active = 1";
        CachedRowSet rowSet = DAO.myExecute(query);
        ArrayList<TeacherJSON> teachers = new ArrayList<>();
        if (rowSet != null) {
            if (rowSet.next()) {
                int idCourse = rowSet.getInt(1);
                query = "SELECT Teacher FROM teaching WHERE Course = " + idCourse + " AND Active = 1";
                rowSet = DAO.myExecute(query);
                if (rowSet != null) {
                    if (rowSet.next()) {
                        do {
                            TeacherJSON teacher = getCompleteNameIfAvailable(rowSet.getInt(1));
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
    private static TeacherJSON getCompleteNameIfAvailable(int idTeacher) throws SQLException {
        String query = "SELECT Name, Surname, Active FROM teacher WHERE ID_Teacher = " + idTeacher + " AND Active = 1";
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
    private static CachedRowSet checkBusyTeacher(int idTeacher, LocalDate date, int hour) {
        String query = "SELECT * FROM lesson WHERE Teacher = " + idTeacher + " AND Date = '" + valueOf(date) + "' AND Hour = " + hour + " AND Status = 'Active'";
        return DAO.myExecute(query);
    }
    private static CachedRowSet checkBusyUser(String username, LocalDate date, int hour) {
        String query = "SELECT * FROM lesson WHERE User = '" + username + "' AND Date = '" + valueOf(date) + "' AND Hour = " + hour + " AND Status = 'Active'";
        return DAO.myExecute(query);
    }
    public static String insertLesson(String title, String name, String surname, String username, LocalDate date, int hour) throws SQLException { // Web & App
        CachedRowSet rowSet = User.checkUser(username);
        if (rowSet.next()) {
            rowSet = Course.checkCourse(title);
            if (rowSet.next()) {
                int idCourse = rowSet.getInt(1);
                rowSet = Teacher.checkTeacher(name, surname);
                if (rowSet.next()) {
                    int idTeacher = rowSet.getInt(1);
                    rowSet = Teaching.checkTeaching(idCourse, idTeacher);
                    if (rowSet.next()) {
                        rowSet = checkBusyTeacher(idTeacher, date, hour);
                        if (!rowSet.next()) { //Docente non ancora occupato, andiamo bene (Lato disponibilità docente)
                            rowSet = checkBusyUser(username, date, hour);
                            if (!rowSet.next()) { //Utente non ancora occupato, andiamo bene (Lato disponibilità utente)
                                String query = "INSERT INTO lesson(ID_Lesson, User, Course, Teacher, Date, Day, Hour, Status)" +
                                        "VALUES (NULL, '" + username + "', " + idCourse + ", " + idTeacher + ", '" + valueOf(date) + "', " +
                                        "'" + (date.getDayOfWeek().toString()).substring(0, 3) + "', " + hour + ", 'Active')";
                                if(DAO.myUpdate(query))
                                    return "Lesson added successfully";
                                else
                                    return "An error occurred while trying to add a lesson";
                            } else
                                return "You already have a booking scheduled for this day and this time slot";
                        } else
                            return "Teacher not available in such date and hour";
                    } else
                        return "Teaching doesn't exist";
                } else
                    return "Teacher doesn't exist";
            } else
                return "Course doesn't exist";
        } else
            return "User doesn't exist";
    }
    public static String setDeleted(String title, String name, String surname, String username, LocalDate date, int hour) throws SQLException { // Web & App - modifica per togliere stampe restituire gli errori o i successi come stringa
        CachedRowSet rowSet = User.checkUser(username);
        if (rowSet.next()) {
            rowSet = Course.checkCourse(title);
            if (rowSet.next()) {
                int idCourse = rowSet.getInt(1);
                rowSet = Teacher.checkTeacher(name, surname);
                if (rowSet.next()) {
                    int idTeacher = rowSet.getInt(1);
                    rowSet = Teaching.checkTeaching(idCourse, idTeacher);
                    if (rowSet.next()) {
                        rowSet = checkBusyTeacher(idTeacher, date, hour);
                        if (rowSet.next()) {
                            rowSet = checkBusyUser(username, date, hour);
                            if (rowSet.next()) { //Tupla trovata, possiamo fare cose
                                String query = "UPDATE lesson SET Status = 'Deleted' WHERE User = '" + username + "' AND Course = " + idCourse + " AND " +
                                        "Teacher = " + idTeacher + " AND Date = '" + valueOf(date) + "' AND Hour = " + hour + "";
                                if(DAO.myUpdate(query))
                                    return "Lesson removed successfully";
                                else
                                    return "An error occurred while trying to remove a lesson";
                            } else
                                return "No booking with this user in such date and hour";
                        } else
                            return "No booking with teacher in such date and hour";
                    } else
                        return "Teaching doesn't exist";
                } else
                    return "Teacher doesn't exist";
            } else
                return "Course doesn't exist";
        } else
            return "User doesn't exist";
    }
    public static String setAccomplished(String title, String name, String surname, String username, LocalDate date, int hour) throws SQLException { // Web & App - modifica per togliere stampe restituire gli errori o i successi come stringa
        CachedRowSet rowSet = User.checkUser(username);
        if (rowSet.next()) {
            rowSet = Course.checkCourse(title);
            if (rowSet.next()) {
                int idCourse = rowSet.getInt(1);
                rowSet = Teacher.checkTeacher(name, surname);
                if (rowSet.next()) {
                    int idTeacher = rowSet.getInt(1);
                    rowSet = Teaching.checkTeaching(idCourse, idTeacher);
                    if (rowSet.next()) {
                        rowSet = checkBusyTeacher(idTeacher, date, hour);
                        if (rowSet.next()) {
                            rowSet = checkBusyUser(username, date, hour);
                            if (rowSet.next()) { //Tupla trovata, possiamo fare cose
                                String query = "UPDATE lesson  SET Status = 'Done' WHERE User = '" + username + "' AND Course = " + idCourse + " AND " +
                                        "Teacher = " + idTeacher + " AND Date = '" + valueOf(date) + "' AND Hour = " + hour + "";
                                if(DAO.myUpdate(query))
                                    return "Lesson accomplished successfully";
                                else
                                    return "An error occurred while trying to accomplish a lesson";
                            } else
                                return "No booking with this user in such date and hour";
                        } else
                            return "No booking with teacher in such date and hour";
                    } else
                        return "Teaching doesn't exist";
                } else
                    return "Teacher doesn't exist";
            } else
                return "Course doesn't exist";
        } else
            return "User doesn't exist";
    }
    public static ArrayList<LessonJSON> getUserLessons(String username, String my_status) throws SQLException { // Web & App
        ArrayList<LessonJSON> lessons = new ArrayList<>();
        String query = "SELECT User, Course, Teacher, Date, Day, Hour, Status FROM lesson WHERE User = '" + username + "' AND Status = '" + my_status + "' ORDER BY Date";
        CachedRowSet rowSet = DAO.myExecute(query);
        if (rowSet != null) {
            if (rowSet.next()) {
                do {
                    String title = Course.getCourseTitle(rowSet.getInt(2));
                    String completeName = Teacher.getTeacherName(rowSet.getInt(3));
                    if(title == null || completeName == null)
                        return null;
                    else {
                        String user = rowSet.getString(1);
                        String date = rowSet.getDate(4).toString();
                        String day = rowSet.getString(5);
                        int hour = rowSet.getInt(6);
                        String status = rowSet.getString(7);
                        lessons.add(new LessonJSON(user, completeName, title, date, day, hour, status));
                    }
                } while(rowSet.next());
                return lessons;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }
    public static ArrayList<LessonJSON> getAllLessons() throws SQLException { // Web & App
        ArrayList<LessonJSON> lessons = new ArrayList<>();
        String query = "SELECT User, Course, Teacher, Date, Day, Hour, Status FROM lesson ORDER BY Date";
        CachedRowSet rowSet = DAO.myExecute(query);
        if (rowSet != null) {
            if (rowSet.next()) {
                do {
                    String title = Course.getCourseTitle(rowSet.getInt(2));
                    String completeName = Teacher.getTeacherName(rowSet.getInt(3));
                    if(title == null || completeName == null)
                        return null;
                    else {
                        String user = rowSet.getString(1);
                        String date = rowSet.getDate(4).toString();
                        String day = rowSet.getString(5);
                        int hour = rowSet.getInt(6);
                        String status = rowSet.getString(7);
                        lessons.add(new LessonJSON(user, completeName, title, date, day, hour, status));
                    }
                } while(rowSet.next());
                return lessons;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }
    public static ArrayList<TimeSlotsJSON> getSlots(String name, String surname, LocalDate date) throws SQLException { // Web & App
        ArrayList<TimeSlotsJSON> slotsJSON = new ArrayList<>();
        ArrayList<Integer> skip = new ArrayList<>();

        int id = Teacher.getTeacherID(name, surname);

        String query = "SELECT Hour, Status FROM lesson WHERE Teacher = " + id + " AND Date = '" + date + "'";
        CachedRowSet rowset = DAO.myExecute(query);
        if (rowset != null && rowset.next()) {
            do {
                if (!Objects.equals(rowset.getString(2), "Deleted")) {
                    int slot = rowset.getInt(1);
                    skip.add(slot);
                }
            } while (rowset.next());
        }
        for(int i=15; i<19; i++) {
              if(!skip.contains(i))
                  slotsJSON.add(new TimeSlotsJSON(i, i+1));
        }
        return slotsJSON;
    }
}
