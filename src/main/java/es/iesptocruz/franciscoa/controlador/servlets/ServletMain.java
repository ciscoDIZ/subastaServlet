package es.iesptocruz.franciscoa.controlador.servlets;


import es.iesptocruz.franciscoa.controlador.ObjetoSubasta;
import es.iesptocruz.franciscoa.controlador.Usuario;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class ServletMain implements Servlet {
  
    private ServletConfig config;
    private static Map<String, Usuario> usuarios;
    private Map<String, ObjetoSubasta> articulos;
    HttpSession session;
    String login;
    String passwd;
    String email;
    String accion;
    Usuario u;




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
        session = ((HttpServletRequest)req).getSession();


        accion = req.getParameter("accion");

        String producto;
        double valor;
        RequestDispatcher dispatcher;
        PrintWriter out = res.getWriter();
        u = (Usuario) ((HttpServletRequest)req).getSession().getAttribute("user");
        HttpServletRequest request;
        HttpServletResponse response;
        if(u != null){
            login = u.getLogin();
            passwd = u.getPasswd();
            email = u.getEmail();
            accion = (accion==null)?"validar":accion;
        }else{
             login = req.getParameter("login");
             passwd= req.getParameter("passwd");
             email = req.getParameter("email");
             accion = req.getParameter("accion");
             u = usuarios.get(login);
             if(u!=null){
                 session.setAttribute("user",u);
                 dispatcher = req.getRequestDispatcher("main?validar");
                 dispatcher.forward(req,res);
                 email = u.getEmail();
             }
        }
        if(login == null || passwd == null || email == null) {
            dispatcher = req.getRequestDispatcher("login");
            dispatcher.forward(req,res);
        }
        switch (accion){
            case "registrar":
                if(registrarUsuario(login,passwd,email)){
                    session.setAttribute("user", new Usuario(login,passwd,email));
                    dispatcher = req.getRequestDispatcher("login");
                }else {
                    dispatcher = req.getRequestDispatcher("registro");
                }
                dispatcher.forward(req,res);
                break;
            case "validar":
                if(req.getParameter("log") != null){
                    login = req.getParameter("login");
                    passwd = req.getParameter("passwd");
                    Usuario ulogin = usuarios.get(login);
                    if(ulogin != null && ulogin.getPasswd().equals(passwd)){
                        session.setAttribute(login,ulogin);
                    }else{
                        dispatcher = req.getRequestDispatcher("login");
                        dispatcher.forward(req,res);
                    }
                }
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

                genTablasArticulos(login, out);
                out.print("</table>");
                out.print("</body>");
                out.print("</html>");
                break;
            case "comprar":
                producto = req.getParameter("producto");
                valor = Double.parseDouble(req.getParameter("valor"));
                String postor = req.getParameter("postor");
                ObjetoSubasta objetoSubasta = articulos.get(producto);
                if(objetoSubasta.getValor() < (valor+objetoSubasta.getValor())){
                    if (u!=null) {
                        objetoSubasta.setPuja(postor, valor);
                        articulos.put(producto, objetoSubasta);
                    };
                }

                response = (HttpServletResponse)res;
                response.sendRedirect("main?accion=validar");
                break;
            case "vender":
                producto = req.getParameter("producto");
                valor = Double.parseDouble(req.getParameter("valor"));
                articulos.put(producto,new ObjetoSubasta(producto, valor,null,login));
                response = (HttpServletResponse)res;
                response.sendRedirect("main?accion=validar");
                break;
            case "adjudicar":
                producto = req.getParameter("producto");
                objetoSubasta = articulos.get(producto);
                objetoSubasta.setPropietario(objetoSubasta.getUsuario());
                usuarios.get(objetoSubasta.getPropietario()).getArticulos().put(producto,objetoSubasta);
                articulos.remove(producto);
                response = (HttpServletResponse)res;
                response.sendRedirect("main?accion=validar");
                break;
            case "cancelar":
                producto = req.getParameter("producto");
                articulos.remove(producto);
                response = (HttpServletResponse)res;
                response.sendRedirect("main?accion=validar");
                break;
            default:
                break;

        }
    }

    private void genTablasArticulos(String login, PrintWriter out) {
        out.print("<h2>Mis articulos</h2>");
        out.print("<table>");
        out.print("<tr><th>Producto</th><th>Valor</th><th>Adjudicar</th><th>Cancelar</th></tr>");
        for (Map.Entry<String,ObjetoSubasta> entry : articulos.entrySet()) {
            if(entry.getValue().getPropietario().equals(login)){
                out.print("<tr>");
                out.print("<td>"+entry.getValue().getProducto()+"</td><td>"+entry.getValue().getValor()
                +"<td><a href='main?accion=adjudicar&&producto="+entry.getValue().getProducto()+"'>Adjudicar</a></td><td>" +
                        "<a href='main?accion=cancelar&&producto="+entry.getValue().getProducto()+"'>Cancelar</a></td>");
                out.print("</tr>");
            }
        }
        out.print("</table>");
        out.print("<h2>Articulos</h2>");
        out.print("<table>");
        out.print("<tr><th>Producto</th><th>Valor</th><th>Adjudicar</th><th>Cancelar</th></tr>");
        for (Map.Entry<String,ObjetoSubasta> objetoSubasta : articulos.entrySet()) {
            if(!objetoSubasta.getValue().getPropietario().equals(login)){
                out.print("<tr>");
                out.print("<td>"+objetoSubasta.getValue().getProducto()+"</td><td>"+objetoSubasta.getValue().getValor()
                        +"<td><a href='comprar?producto="+objetoSubasta.getValue().getProducto()+"'>Comprar</a></td><td>");
                out.print("</tr>");
            }
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
            resultado = u.getPasswd().equals(passwd);
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
