package Servlet;

import DAO.Teaching;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import utils.JSONClasses.TeacherJSON;
import utils.JSONClasses.TeachingJSON;
import utils.SessionManager;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet(name = "ServletTeaching", value = "/ServletTeaching")
public class ServletTeaching extends HttpServlet {
    private JsonObject jsonResult;

    public void init(ServletConfig config) {
        jsonResult = new JsonObject();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String token = request.getHeader("Authorization");
        if (token != null && SessionManager.checkToken(token).equals("Admin")) {
            processRequest(request, response);
        } else {
            jsonResult.addProperty("result", "You are not authorized to do this operation");
            request.setAttribute("result", new Gson().toJson(jsonResult));
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String token = request.getHeader("Authorization");
        if (token != null && SessionManager.checkToken(token).equals("Admin")) {
            processRequest(request, response);
        } else {
            jsonResult.addProperty("result", "You are not authorized to do this operation");
            request.setAttribute("result", new Gson().toJson(jsonResult));
        }
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");

        String choice = request.getParameter("choice");
        String title = request.getParameter("title");

        switch (choice) {
            case "allTeachings": // Admin - Web
                try {
                    ArrayList<TeachingJSON> teachings = Teaching.getAllTeachings();
                    request.setAttribute("result", new Gson().toJson(teachings));
                } catch (SQLException e) {
                    jsonResult.addProperty("result", "No teachings available");
                    request.setAttribute("result", new Gson().toJson(jsonResult));
                }
                break;
            case "insertTeaching": // Admin - Web
                try {
                    String teacherName = request.getParameter("name");
                    String teacherSurname = request.getParameter("surname");
                    String insertResult = Teaching.insertTeaching(teacherName, teacherSurname, title);
                    jsonResult.addProperty("result", insertResult);
                    request.setAttribute("result", new Gson().toJson(jsonResult));
                } catch (SQLException e) {
                    jsonResult.addProperty("result", "Couldn't insert this teaching");
                    request.setAttribute("result", new Gson().toJson(jsonResult));
                }
                break;
            case "deleteTeaching": // Admin - Web
                try {
                    String teacherName = request.getParameter("name");
                    String teacherSurname = request.getParameter("surname");
                    String removeResult = Teaching.removeTeaching(teacherName, teacherSurname, title);
                    jsonResult.addProperty("result", removeResult);
                    request.setAttribute("result", new Gson().toJson(jsonResult));
                } catch (SQLException e) {
                    jsonResult.addProperty("result", "Couldn't delete this teaching");
                    request.setAttribute("result", new Gson().toJson(jsonResult));
                }
                break;
            case "teachersPerCourse": // Admin - App
                try {
                    ArrayList<TeacherJSON> teachers = Teaching.teacherPerCourse(title);
                    request.setAttribute("result", new Gson().toJson(teachers));
                } catch (SQLException e) {
                    jsonResult.addProperty("result", "No teachers available");
                    request.setAttribute("result", new Gson().toJson(jsonResult));
                }
                break;
        }
    }

    public void destroy() {
    }
}
