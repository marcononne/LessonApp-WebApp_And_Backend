package Servlet;

import DAO.Teacher;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import utils.JSONClasses.TeacherJSON;
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

@WebServlet(name = "ServletTeacher", value = "/ServletTeacher")
public class ServletTeacher extends HttpServlet {

    private JsonObject jsonResult;

    public void init(ServletConfig config) {
        jsonResult = new JsonObject();
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

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String choice = request.getParameter("choice");

        switch (choice) {
            case "getAllTeachers": // Web & App
                ArrayList<TeacherJSON> allTeachers = null;
                try {
                    allTeachers = Teacher.getAllTeachers();
                    request.setAttribute("result", new Gson().toJson(allTeachers));
                } catch (SQLException e) {
                    jsonResult.addProperty("result", "Couldn't fetch the teachers");
                    request.setAttribute("result", new Gson().toJson(jsonResult));
                }
                break;
            case "getAllActiveTeachers": // Web
                ArrayList<TeacherJSON> allActiveTeachers = null;
                try {
                    allActiveTeachers = Teacher.getAllActiveTeachers();
                    request.setAttribute("result", new Gson().toJson(allActiveTeachers));
                } catch (SQLException e) {
                    jsonResult.addProperty("result", "Couldn't fetch the teachers");
                    request.setAttribute("result", new Gson().toJson(jsonResult));
                }
                break;
            case "add": // Web
                try {
                    String insertResult = Teacher.insertTeacher(name, surname);
                    jsonResult.addProperty("result", insertResult);
                    request.setAttribute("result", new Gson().toJson(jsonResult));
                } catch (SQLException e) {
                    jsonResult.addProperty("result", "Couldn't add the teacher");
                    request.setAttribute("result", new Gson().toJson(jsonResult));
                }
                break;
            case "delete": // Web
                try {
                    String removeResult = Teacher.removeTeacher(name, surname);
                    jsonResult.addProperty("result", removeResult);
                    request.setAttribute("result", new Gson().toJson(jsonResult));
                } catch (SQLException e) {
                    jsonResult.addProperty("result", "Couldn't remove the teacher");
                    request.setAttribute("result", new Gson().toJson(jsonResult));
                }
                break;
        }
    }

    public void destroy() {
    }
}
