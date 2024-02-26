package Servlet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "ServletController", value = "/ServletController")
public class ServletController extends HttpServlet {

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
        PrintWriter out = response.getWriter();
        String action = request.getParameter("action");
        ServletContext ctx = request.getServletContext();
        RequestDispatcher rd;

        if (action != null) {
            switch (action) {
                case "courses":
                    rd = ctx.getNamedDispatcher("ServletCourse");
                    rd.include(request, response);
                    out.print(request.getAttribute("result"));
                    break;
                case "user":
                    rd = ctx.getNamedDispatcher("ServletUser");
                    rd.include(request,response);
                    out.print(request.getAttribute("role"));
                    break;
                case "teacher":
                    rd = ctx.getNamedDispatcher("ServletTeacher");
                    rd.include(request,response);
                    out.print(request.getAttribute("result"));
                    break;
                case "teaching":
                    rd = ctx.getNamedDispatcher("ServletTeaching");
                    rd.include(request,response);
                    out.print(request.getAttribute("result"));
                    break;
                case "lesson":
                    rd = ctx.getNamedDispatcher("ServletLesson");
                    rd.include(request, response);
                    out.print(request.getAttribute("result"));
                    break;
            }
        } else {
            out.print("Error");
        }
    }
    public void destroy() {
    }

}
