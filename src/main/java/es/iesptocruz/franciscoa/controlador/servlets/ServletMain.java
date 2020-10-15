package es.iesptocruz.franciscoa.controlador.servlets;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.http.HttpResponse;
import java.util.*;

public class ServletMain implements Servlet {
    static class Usuario{
        String login;
        String passwd;
        String email;

        public Usuario(String login, String passwd, String email) {
            this.login = login;
            this.passwd = passwd;
            this.email = email;
        }
    }
    static class ObjetoSubasta{
        String producto;
        Double valor;
        String usuario;
        String propietario;

        public ObjetoSubasta(String producto, Double valor, String usuario, String propietario) {
            this.producto = producto;
            this.valor = valor;
            this.usuario = usuario;
            this.propietario = propietario;
        }

        public void setPuja(double puja){
            valor += puja;
        }

        public String getProducto() {
            return producto;
        }

        public void setProducto(String producto) {
            this.producto = producto;
        }

        public Double getValor() {
            return valor;
        }

        public void setValor(Double valor) {
            this.valor = valor;
        }

        public String getUsuario() {
            return usuario;
        }

        public void setUsuario(String usuario) {
            this.usuario = usuario;
        }

        public String getPropietario() {
            return propietario;
        }

        public void setPropietario(String propietario) {
            this.propietario = propietario;
        }
    }
    private ServletConfig config;
    private Map<String,Usuario> usuarios;
    private Map<String,ObjetoSubasta> articulos;
    public void init(ServletConfig config) throws ServletException {
        if(config == null){
            throw new ServletException("error al crear el servlet");
        }
        usuarios = new HashMap<>();
        articulos = new HashMap<>();
        this.config = config;
    }


    public ServletConfig getServletConfig() {
        return config;
    }

    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        String login = req.getParameter("login");
        String passwd= req.getParameter("passwd");
        String email = req.getParameter("email");
        String action = req.getParameter("accion");
        String producto;
        double valor;
        RequestDispatcher dispatcher;
        PrintWriter out = res.getWriter();
        Usuario u = (Usuario) ((HttpServletRequest)req).getSession().getAttribute("user");
        if(u != null){
            login = u.login;
            passwd = u.passwd;
            action = "validar";
        }
        if(login == null || passwd == null || action == null) {
            dispatcher = req.getRequestDispatcher("login");
            dispatcher.forward(req,res);
        }
        switch (action){
            case "registrar":
                if(registrarUsuario(login,passwd,email)){
                    HttpSession session = ((HttpServletRequest)req).getSession();
                    session.setAttribute("user", new Usuario(login,passwd,email));
                    req.setAttribute("login",login);
                    dispatcher = req.getRequestDispatcher("login");
                    dispatcher.forward(req,res);
                }else {
                    dispatcher = req.getRequestDispatcher("registro");
                    dispatcher.forward(req,res);
                }
                break;
            case "validar":

                if(u == null){
                    dispatcher = req.getRequestDispatcher("login");
                    dispatcher.forward(req,res);
                }

                res.setContentType("text/html");
                ((HttpServletResponse)res).setHeader("refresh","5");
                out.print("<!DOCTYPE html>");
                out.print("<html>");
                out.print("<body>");
                out.print("<a href='vender'>Vender articulo</a>");
                out.print("<a href='login?session=out'>Cerrar sesion</a>");
                out.print("<h2></h2");
                for (Map.Entry<String,ObjetoSubasta> objetoSubasta : articulos.entrySet()) {
                    if(objetoSubasta.getValue().getPropietario().equals(login)){
                        out.print("");
                    }
                }

                out.print("</body>");
                out.print("</html>");
                break;
            case "comprar":
                producto = req.getParameter("producto");
                valor = Double.parseDouble(req.getParameter("valor"));
                ObjetoSubasta objetoSubasta = articulos.get(producto);
                if(objetoSubasta.valor < valor){
                    objetoSubasta.setPuja(valor);
                    articulos.put(producto,objetoSubasta);
                }
                dispatcher = req.getRequestDispatcher("main?accion=validar");
                dispatcher.forward(req,res);
                break;
            case "vender":
                producto = req.getParameter("producto");
                valor = Double.parseDouble(req.getParameter("valor"));
                articulos.put(producto,new ObjetoSubasta(producto, valor,null,login));
                dispatcher = req.getRequestDispatcher("main?accion=validar");
                dispatcher.forward(req,res);
                break;
            case "adjudicar":
            case "cancelar":
                producto = req.getParameter("producto");
                articulos.remove(producto);
                dispatcher = req.getRequestDispatcher("main?accion=validar");
                dispatcher.forward(req,res);
                break;
            default:
                break;

        }
    }

    public String getServletInfo() {
        return "";
    }

    public void destroy() {

    }
    boolean validarUsuario(String login, String passwd){
        Usuario u  = buscarUsuario(login);
        boolean resultado = false;
        if (u != null) {
            resultado = u.passwd.equals(passwd);
        }

        return resultado;
    }

    boolean registrarUsuario(String login, String passwd, String email){
        boolean resultado = !usuarios.containsKey(login);
        if(resultado){
            Usuario u = new Usuario(login,passwd,email);
            usuarios.put(login, u);
        }
        return resultado;
    }

    Usuario buscarUsuario(String login){
        return usuarios.get(login);
    }


}
