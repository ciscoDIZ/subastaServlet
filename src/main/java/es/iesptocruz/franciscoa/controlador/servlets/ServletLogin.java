package es.iesptocruz.franciscoa.controlador.servlets;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

public class ServletLogin implements Servlet {
    ServletConfig config;

    public void init(ServletConfig config) throws ServletException {
        if (config == null) {
            throw new ServletException("error config");
        }
        this.config = config;
    }

    public ServletConfig getServletConfig() {
        return config;
    }

    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        PrintWriter out = res.getWriter();
        res.setContentType("text/html");
        out.print("<html>");
        out.print("<body>");
        out.print("<form action='main'>");
        out.print("<input type='hidden' name='accion' value='validar'>");
        out.print("<input type='hidden' name='log' value='1'>");
        out.print("<label for='login'>Login</label>");
        out.print("<input id='login' name='login' value=''><br>");
        out.print("<label for='password'>Password</label>");
        out.print("<input type='password' id='password' name='passwd' value=''><br>");
        out.print("<input type='submit' value='enviar'></form>");
        out.print("<a href='registro'>Registro</a>");
        out.print("</body>");
        out.print("</html>");


    }

    public String getServletInfo() {
        return null;
    }

    public void destroy() {

    }
}
