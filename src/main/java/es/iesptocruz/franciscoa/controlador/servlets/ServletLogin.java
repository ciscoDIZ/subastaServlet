package es.iesptocruz.franciscoa.controlador.servlets;

import javax.servlet.*;
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
        out.print("<form action='main'");
        out.print("<input type='hidden' name='accion' value='validar'>");
        out.print("<label for='login'>Login</label>");
        out.print("<input id='login' name='login' value=''><br>");
        out.print("<label for='password'>Password</label>");
        out.print("<input type='password' id='password' name='password' value=''><br>");
        out.print("<input type='submit' value='enviar'></form>");

    }

    public String getServletInfo() {
        return null;
    }

    public void destroy() {

    }
}
