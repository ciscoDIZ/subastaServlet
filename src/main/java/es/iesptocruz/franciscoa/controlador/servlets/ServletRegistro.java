package es.iesptocruz.franciscoa.controlador.servlets;

import javax.servlet.*;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Servlet usado para dar registro a los usuarios de la aplicacion de subastas
 *
 * @author Francisco A Domñinguez Iceta 2DAW IES Puerto de la Cruz
 */
public class ServletRegistro implements Servlet {

    private ServletConfig servletConfig;

    public void init(ServletConfig servletConfig) throws ServletException {
        if (servletConfig == null) {
            throw new ServletException("Error a la creacion de servlet");
        }
        this.servletConfig = servletConfig;
    }

    public ServletConfig getServletConfig() {
        return servletConfig;
    }

    public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        out.print("<form action='main'>");
        out.print("<input type='hidden' name='accion' value='registrar'/>");
        out.print("<label for='login'>Login</label>");
        out.print("<input id='login' name='login' value=''><br>");
        out.print("<label for='password'>Password</label>");
        out.print("<input type='password' id='password' name='passwd' value=''><br>");
        out.print("<label for='email'>E-mail</label>");
        out.print("<input id='email' name='email' value=''><br>");
        out.print("<input type='submit' value='enviar'></form>");

    }

    public String getServletInfo() {
        return null;
    }

    public void destroy() {

    }
}
