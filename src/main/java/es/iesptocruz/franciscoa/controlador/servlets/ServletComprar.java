package es.iesptocruz.franciscoa.controlador.servlets;

import es.iesptocruz.franciscoa.controlador.Usuario;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;

public class ServletComprar implements Servlet {

    ServletConfig config;
    public void init(ServletConfig config) throws ServletException {
        if(config == null){
            throw new ServletException("Error de configuracion");
        }
        this.config = config;
    }

    public ServletConfig getServletConfig() {
        return config;
    }

    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        PrintWriter out = res.getWriter();
        res.setContentType("text/html");
        String produto = req.getParameter("producto");
        Usuario u = (Usuario)((HttpServletRequest)req).getSession().getAttribute("user");
        String postor = u.getLogin();
        out.print("<form action='main'>");
        out.print("<input type='hidden' name='accion' value='comprar'>");
        out.print("<input type='hidden' name='producto' value='"+produto+"'>");
        out.print("<input type='hidden' name='postor' value='"+postor+"'>");
        out.print("<label for='valor'>Valor</label>");
        out.print("<input id='valor' name='valor' value=''><br>");
        out.print("<input type='submit' value='enviar'></form>");
        out.print("<a href='main?accion=validar'>Volver</a>");
    }

    public String getServletInfo() {
        return "serrvlet de compra";
    }

    public void destroy() {

    }
}
