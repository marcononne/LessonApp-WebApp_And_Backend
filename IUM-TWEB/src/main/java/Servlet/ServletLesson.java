package Servlet;

import DAO.Lesson;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import utils.JSONClasses.LessonJSON;
import utils.JSONClasses.TeacherJSON;
import utils.JSONClasses.TimeSlotsJSON;
import utils.SessionManager;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

@WebServlet(name = "ServletLesson", value = "/ServletLesson")
public class ServletLesson extends HttpServlet {

    public void init(ServletConfig config) {}
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }
    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        JsonObject jsonResult = new JsonObject();

        String choice = request.getParameter("choice");
        String title = request.getParameter("title");
        String name, surname, username, token;
        LocalDate date;
        int hour;

        switch(choice) {
            case "teachersAvailable": // Client - Web & App
                token = request.getHeader("Authorization");
                if(token != null && SessionManager.checkToken(token).equals("Client")) {
                    try {
                        ArrayList<TeacherJSON> teachers = Lesson.teachersAvailable(title);
                        request.setAttribute("result", new Gson().toJson(teachers));
                    } catch (SQLException e) {
                        jsonResult.addProperty("result", "No teachers available");
                        request.setAttribute("result", new Gson().toJson(jsonResult));
                    }
                } else {
                    jsonResult.addProperty("result", "You are not authorized to do this operation");
                    request.setAttribute("result", new Gson().toJson(jsonResult));
                }
                break;
            case "insertLesson": // Client - Web & App
                token = request.getHeader("Authorization");
                if(token != null && SessionManager.checkToken(token).equals("Client")) {
                    try {
                        name = request.getParameter("name");
                        surname = request.getParameter("surname");
                        username = request.getParameter("username");
                        date = LocalDate.parse(request.getParameter("date").replace(" 00:00:00.000", ""));
                        hour = Integer.parseInt(request.getParameter("hour"));
                        String insertResult = Lesson.insertLesson(title, name, surname, username, date, hour);
                        jsonResult.addProperty("result", insertResult);
                        request.setAttribute("result", new Gson().toJson(jsonResult));
                    } catch (SQLException e) {
                        jsonResult.addProperty("result", "Couldn't insert this teacher");
                        request.setAttribute("result", new Gson().toJson(jsonResult));
                    }
                } else {
                    jsonResult.addProperty("result", "You are not authorized to do this operation");
                    request.setAttribute("result", new Gson().toJson(jsonResult));
                }
                break;
            case "deleteLesson": // Client e Admin - Web & App
                token = request.getHeader("Authorization");
                if(token != null && !SessionManager.checkToken(token).equals("")) {
                    try {
                        name = request.getParameter("name");
                        surname = request.getParameter("surname");
                        username = request.getParameter("username");
                        date = LocalDate.parse(request.getParameter("date"));
                        hour = Integer.parseInt(request.getParameter("hour"));
                        String deleteResult = Lesson.setDeleted(title, name, surname, username, date, hour);
                        jsonResult.addProperty("result", deleteResult);
                        request.setAttribute("result", new Gson().toJson(jsonResult));
                    } catch (SQLException e) {
                        jsonResult.addProperty("result", "Couldn't delete this lesson");
                        request.setAttribute("result", new Gson().toJson(jsonResult));
                    }
                } else {
                    jsonResult.addProperty("result", "You are not authorized to do this operation");
                    request.setAttribute("result", new Gson().toJson(jsonResult));
                }
                break;
            case "setAccomplished": // Client - Web & App
                token = request.getHeader("Authorization");
                if(token != null && SessionManager.checkToken(token).equals("Client")) {
                    try  {
                        name = request.getParameter("name");
                        surname = request.getParameter("surname");
                        username = request.getParameter("username");
                        date = LocalDate.parse(request.getParameter("date"));
                        hour = Integer.parseInt(request.getParameter("hour"));
                        String accomplishedResult = Lesson.setAccomplished(title, name, surname, username, date, hour);
                        jsonResult.addProperty("result", accomplishedResult);
                        request.setAttribute("result", new Gson().toJson(jsonResult));
                    } catch (SQLException e) {
                        jsonResult.addProperty("result", "Couldn't set this lesson as accomplished");
                        request.setAttribute("result", new Gson().toJson(jsonResult));
                    }
                } else {
                    jsonResult.addProperty("result", "You are not authorized to do this operation");
                    request.setAttribute("result", new Gson().toJson(jsonResult));
                }
                break;
            case "userLessons": // Client - Web & App
                token = request.getHeader("Authorization");
                if(token != null && SessionManager.checkToken(token).equals("Client")) {
                    try {
                        username = request.getParameter("username");
                        String status = request.getParameter("status");
                        ArrayList<LessonJSON> lessons = Lesson.getUserLessons(username, status);
                        request.setAttribute("result", new Gson().toJson(lessons));
                    } catch (SQLException e) {
                        jsonResult.addProperty("result", "Couldn't find users lesson");
                        request.setAttribute("result", new Gson().toJson(jsonResult));
                    }
                } else {
                    jsonResult.addProperty("result", "You are not authorized to do this operation");
                    request.setAttribute("result", new Gson().toJson(jsonResult));
                }
                break;
            case "allLessons": // Admin - Web & App
                token = request.getHeader("Authorization");
                if(token != null && SessionManager.checkToken(token).equals("Admin")) {
                    try {
                        ArrayList<LessonJSON> lessons = Lesson.getAllLessons();
                        request.setAttribute("result", new Gson().toJson(lessons));
                    } catch (SQLException e) {
                        jsonResult.addProperty("result", "Couldn't find users lesson");
                        request.setAttribute("result", new Gson().toJson(jsonResult));
                    }
                } else {
                    jsonResult.addProperty("result", "You are not authorized to do this operation");
                    request.setAttribute("result", new Gson().toJson(jsonResult));
                }
                break;
            case "timeSlots": // Client - Web & App
                token = request.getHeader("Authorization");
                if(token != null && SessionManager.checkToken(token).equals("Client")) {
                    try {
                        name = request.getParameter("name");
                        surname = request.getParameter("surname");
                        date = LocalDate.parse(request.getParameter("date"));
                        ArrayList<TimeSlotsJSON> slots = Lesson.getSlots(name, surname, date);
                        request.setAttribute("result", new Gson().toJson(slots));
                    } catch (Exception e) {
                        jsonResult.addProperty("result", "No slots available for the given date");
                        request.setAttribute("result", new Gson().toJson(jsonResult));
                    }
                } else {
                    jsonResult.addProperty("result", "You are not authorized to do this operation");
                    request.setAttribute("result", new Gson().toJson(jsonResult));
                }
                break;
        }
    }

    public void destroy() {}
}
