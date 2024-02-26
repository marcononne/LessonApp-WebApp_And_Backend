package Servlet;

import DAO.User;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import utils.SessionManager;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

@WebServlet(name = "ServletUser", value = "/ServletUser")
public class ServletUser extends HttpServlet {

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
        JsonObject jsonObject = new JsonObject();
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String choice = request.getParameter("choice");

        switch (choice) {
            case "login": // Web & App
                if (!Objects.equals(username, "null") && !Objects.equals(password, "null")) {
                    try {
                        if (User.authenticate(username, password)) {
                            String role = User.getRole(username);
                            if (role != null) {
                                HttpSession session = request.getSession();
                                session.setAttribute("username", username);
                                session.setAttribute("role", role);
                                String token = SessionManager.createToken(session);
                                jsonObject.addProperty("role", role);
                                jsonObject.addProperty("token", token);
                                jsonObject.addProperty("username", username);
                                request.setAttribute("role", jsonObject);
                            } else {
                                jsonObject.addProperty("role", "Failed authentication");
                                request.setAttribute("role", jsonObject);
                            }
                        } else {
                            jsonObject.addProperty("role", "Wrong credentials");
                            request.setAttribute("role", jsonObject);
                        }
                    } catch (SQLException e) {
                        jsonObject.addProperty("role", "Couldn't log in the user");
                        request.setAttribute("role", jsonObject);
                    }
                } else {
                    jsonObject.addProperty("role", "Incomplete fields");
                    request.setAttribute("role", jsonObject);
                }
                break;
            case "register": // Web
                String name = request.getParameter("name");
                String gender = request.getParameter("gender");
                if (username != null && password != null && name != null && gender != null) {
                    try {
                        if (!User.checkUserMask(username)) {
                            if (User.insertUser(username, password, name, gender, "Client")) {
                                HttpSession session = request.getSession();
                                session.setAttribute("username", username);
                                session.setAttribute("role", "Client");
                                String token = SessionManager.createToken(session);
                                jsonObject.addProperty("token", token);
                                jsonObject.addProperty("username", username);
                                request.setAttribute("role", jsonObject);
                            } else {
                                jsonObject.addProperty("role", "Failed registration");
                                request.setAttribute("role", jsonObject);
                            }
                        } else {
                            jsonObject.addProperty("role", "Username already exists, please choose another one");
                            request.setAttribute("role", jsonObject);
                        }
                    } catch (SQLException e) {
                        jsonObject.addProperty("role", "Couldn't sign up in the user");
                        request.setAttribute("role", jsonObject);
                    }
                } else {
                    jsonObject.addProperty("role", "Incomplete fields");
                    request.setAttribute("role", jsonObject);
                }
                break;
            case "logout": // Web & App
                String token = request.getHeader("Authorization");
                if (token != null && !SessionManager.checkToken(token).equals("")) {
                    SessionManager.invalideSession(token);
                    jsonObject.addProperty("role", "Logout successful");
                    request.setAttribute("role", jsonObject);
                } else {
                    jsonObject.addProperty("role", "You are not authorized to do this operation");
                    request.setAttribute("role", new Gson().toJson(jsonObject));
                }
                break;
            default:
                jsonObject.addProperty("role", "You are not authorized to do this operation");
                request.setAttribute("role", new Gson().toJson(jsonObject));
                break;
        }

    }

    public void destroy() {
    }
}
