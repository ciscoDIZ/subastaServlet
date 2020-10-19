package es.iesptocruz.franciscoa.controlador.servlets;


import es.iesptocruz.franciscoa.controlador.Usuario;
import es.iesptocruz.franciscoa.controlador.servlets.utilidades.GestorSubasta;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class ServletMain implements Servlet {

    final GestorSubasta gestorSubasta = new GestorSubasta();
    private ServletConfig config;
    HttpSession session;


    public void init(ServletConfig config) throws ServletException {
        if(config == null){
            throw new ServletException("error al crear el servlet");
        }
        gestorSubasta.getSubasta().setUsuarios(new HashMap<>());
        gestorSubasta.getSubasta().setArticulos(new HashMap<>());
        this.config = config;
    }


    public ServletConfig getServletConfig() {
        return config;
    }

    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        session = ((HttpServletRequest)req).getSession();


        gestorSubasta.setAccion(req.getParameter("accion"));

        String producto;
        double valor;
        RequestDispatcher dispatcher;
        PrintWriter out = res.getWriter();
        gestorSubasta.getSubasta().setUsuario((Usuario) ((HttpServletRequest) req).getSession().getAttribute("user"));
        HttpServletRequest request;
        HttpServletResponse response;
        if(gestorSubasta.getSubasta().getUsuario() != null){
            gestorSubasta.setLogin(gestorSubasta.getSubasta().getUsuario().getLogin());
            gestorSubasta.setPasswd(gestorSubasta.getSubasta().getUsuario().getPasswd());
            gestorSubasta.setEmail(gestorSubasta.getSubasta().getUsuario().getEmail());
            gestorSubasta.setAccion((gestorSubasta.getAccion() == null) ? "validar" : gestorSubasta.getAccion());
        }else{
            gestorSubasta.setLogin(req.getParameter("login"));
            gestorSubasta.setPasswd(req.getParameter("passwd"));
            gestorSubasta.setEmail(req.getParameter("email"));
            gestorSubasta.setAccion(req.getParameter("accion"));
            gestorSubasta.getSubasta().setUsuario(gestorSubasta.getSubasta().getUsuarios().get(gestorSubasta.getLogin()));
             if(gestorSubasta.getSubasta().getUsuario() !=null){
                 session.setAttribute("user", gestorSubasta.getSubasta().getUsuario());
                 dispatcher = req.getRequestDispatcher("main?validar");
                 dispatcher.forward(req,res);
                 gestorSubasta.setEmail(gestorSubasta.getSubasta().getUsuario().getEmail());
             }
        }
        if(gestorSubasta.getLogin() == null || gestorSubasta.getPasswd() == null || gestorSubasta.getEmail() == null) {
            dispatcher = req.getRequestDispatcher("login");
            dispatcher.forward(req,res);
        }
        switch (gestorSubasta.getAccion()){
            case "registrar":
                if(gestorSubasta.registrarUsuario(gestorSubasta.getLogin(), gestorSubasta.getPasswd(), gestorSubasta.getEmail())){
                    session.setAttribute("user", new Usuario(gestorSubasta.getLogin(), gestorSubasta.getPasswd(), gestorSubasta.getEmail()));
                    dispatcher = req.getRequestDispatcher("login");
                }else {
                    dispatcher = req.getRequestDispatcher("registro");
                }
                dispatcher.forward(req,res);
                break;
            case "validar":
                if(req.getParameter("log") != null){
                    gestorSubasta.setLogin(req.getParameter("login"));
                    gestorSubasta.setPasswd(req.getParameter("passwd"));
                    Usuario ulogin = gestorSubasta.getSubasta().getUsuarios().get(gestorSubasta.getLogin());
                    if(ulogin != null && ulogin.getPasswd().equals(gestorSubasta.getPasswd())){
                        session.setAttribute(gestorSubasta.getLogin(),ulogin);
                    }else{
                        dispatcher = req.getRequestDispatcher("login");
                        dispatcher.forward(req,res);
                    }
                }
                if(gestorSubasta.getSubasta().getUsuario() == null){
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

                gestorSubasta.genTablasArticulos(gestorSubasta.getLogin(), out);
                out.print("</table>");
                out.print("</body>");
                out.print("</html>");
                break;
            case "comprar":
                producto = req.getParameter("producto");
                valor = Double.parseDouble(req.getParameter("valor"));
                String postor = req.getParameter("postor");
                gestorSubasta.getSubasta().comprar(producto, valor, postor);

                response = (HttpServletResponse)res;
                response.sendRedirect("main?accion=validar");
                break;
            case "vender":
                producto = req.getParameter("producto");
                valor = Double.parseDouble(req.getParameter("valor"));
                gestorSubasta.getSubasta().vender(producto, valor, gestorSubasta.getLogin());
                response = (HttpServletResponse)res;
                response.sendRedirect("main?accion=validar");
                break;
            case "adjudicar":
                producto = req.getParameter("producto");
                gestorSubasta.getSubasta().adjudicar(producto);
                response = (HttpServletResponse)res;
                response.sendRedirect("main?accion=validar");
                break;
            case "cancelar":
                producto = req.getParameter("producto");
                gestorSubasta.getSubasta().cancelar(producto);
                response = (HttpServletResponse)res;
                response.sendRedirect("main?accion=validar");
                break;
            default:
                break;

        }
    }

    private void cancelar(String producto) {
        gestorSubasta.getSubasta().cancelar(producto);
    }

    private void adjudicar(String producto) {
        gestorSubasta.getSubasta().adjudicar(producto);
    }

    private void vender(String producto, double valor,String login) {
        gestorSubasta.getSubasta().vender(producto, valor, login);
    }

    private void comprar(String producto, double valor, String postor) {
        gestorSubasta.getSubasta().comprar(producto, valor, postor);
    }

    private void genTablasArticulos(String login, PrintWriter out) {
        gestorSubasta.genTablasArticulos(login, out);
    }

    public String getServletInfo() {
        return "";
    }

    public void destroy() {

    }
    boolean validarUsuario(String login, String passwd){

        return gestorSubasta.validarUsuario(login, passwd);
    }

    boolean registrarUsuario(String login, String passwd, String email){
        return gestorSubasta.registrarUsuario(login, passwd, email);
    }

    Usuario buscarUsuario(String login){
        return gestorSubasta.buscarUsuario(login);
    }


}
