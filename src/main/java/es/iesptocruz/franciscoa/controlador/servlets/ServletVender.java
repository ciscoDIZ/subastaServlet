package es.iesptocruz.franciscoa.controlador.servlets;

import javax.servlet.*;
import java.io.IOException;
import java.io.PrintWriter;

public class ServletVender implements Servlet {
    ServletConfig config;
    public void init(ServletConfig config) throws ServletException {
        if (config == null){
            throw new ServletException("error configuraion");
        }
        this.config = config;
    }

    public ServletConfig getServletConfig() {
        return config;
    }

    public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        out.print("<form action='main'>");
        out.print("<input type='hidden' name='accion' value='vender'>");
        out.print("<label for='producto'>Producto</label>");
        out.print("<input id='producto' name='producto' value=''><br>");
        out.print("<label for='valor'>Valor</label>");
        out.print("<input id='valor' name='valor' value=''><br>");
        out.print("<input type='submit' value='enviar'></form>");
        out.print("<a href='/subasta/main'>Volver</a>");
    }

    public String getServletInfo() {
        return "";
    }

    public void destroy() {

    }
}
