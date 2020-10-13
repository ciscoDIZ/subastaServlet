package es.iesptocruz.franciscoa.controlador.servlets;

import javax.servlet.*;
import java.io.IOException;

/**
 * Servlet usado para dar registro a los usuarios de la aplicacion de subastas
 * @author Francisco A Domñinguez Iceta 2DAW IES Puerto de la Cruz
 */
public class ServletRegistro implements Servlet {

    private ServletConfig servletConfig;

    public void init(ServletConfig servletConfig) throws ServletException {
        this.servletConfig = servletConfig;
        if(servletConfig != null){
            throw new ServletException("Error a la creacion de servlet");
        }
    }

    public ServletConfig getServletConfig() {
        return null;
    }

    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {

    }

    public String getServletInfo() {
        return null;
    }

    public void destroy() {

    }
}
