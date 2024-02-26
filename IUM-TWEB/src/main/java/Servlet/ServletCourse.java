package Servlet;

import DAO.Course;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import utils.JSONClasses.SubjectJSON;
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
import java.util.Objects;

@WebServlet(name = "ServletCourse", value = "/ServletCourse")
public class ServletCourse extends HttpServlet {
    public void init(ServletConfig config) {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        JsonObject jsonResult = new JsonObject();
        String choice, title, desc, token;

        choice = request.getParameter("choice");

        switch (choice) {
            case "getAllCourses": // Web & App
                token = request.getHeader("Authorization");
                if (token != null && Objects.equals(SessionManager.checkToken(token), "Admin")) {
                    ArrayList<SubjectJSON> courses = null;
                    try {
                        courses = Course.getAllCourses();
                    } catch (SQLException e) {
                        jsonResult.addProperty("result", "Couldn't fetch the courses");
                        request.setAttribute("result", new Gson().toJson(jsonResult));
                    }
                    request.setAttribute("result", new Gson().toJson(courses));
                } else {
                    jsonResult.addProperty("result", "You are not authorized to do this operation");
                    request.setAttribute("result", new Gson().toJson(jsonResult));
                }
                break;
            case "getAllActiveCourses": // Web & App
                ArrayList<SubjectJSON> activeCourses = null;
                try {
                    activeCourses = Course.getAllActiveCourses();
                } catch (SQLException e) {
                    jsonResult.addProperty("result", "Couldn't fetch the courses");
                    request.setAttribute("result", new Gson().toJson(jsonResult));
                }
                request.setAttribute("result", new Gson().toJson(activeCourses));
                break;
            case "add": // Web
                token = request.getHeader("Authorization");
                if (token != null && Objects.equals(SessionManager.checkToken(token), "Admin")) {
                    try {
                        title = request.getParameter("title");
                        desc = request.getParameter("desc");
                        String insertResult = Course.insertCourse(title, desc);
                        jsonResult.addProperty("result", insertResult);
                        request.setAttribute("result", new Gson().toJson(jsonResult));
                    } catch (SQLException e) {
                        jsonResult.addProperty("result", "Couldn't add the course");
                        request.setAttribute("result", new Gson().toJson(jsonResult));
                    }
                } else {
                    jsonResult.addProperty("result", "You are not authorized to do this operation");
                    request.setAttribute("result", new Gson().toJson(jsonResult));
                }
                break;
            case "remove": // Web
                token = request.getHeader("Authorization");
                if (token != null && Objects.equals(SessionManager.checkToken(token), "Admin")) {
                    try {
                        title = request.getParameter("title");
                        String removeResult = Course.removeCourse(title);
                        jsonResult.addProperty("result", removeResult);
                        request.setAttribute("result", new Gson().toJson(jsonResult));
                    } catch (SQLException e) {
                        jsonResult.addProperty("result", "Couldn't remove the course");
                        request.setAttribute("result", new Gson().toJson(jsonResult));
                    }
                } else {
                    jsonResult.addProperty("result", "You are not authorized to do this operation");
                    request.setAttribute("result", new Gson().toJson(jsonResult));
                }
                break;
        }
    }

    public void destroy() {
    }
}
