package es.iesptocruz.franciscoa.controlador.servlets.utilidades;

import es.iesptocruz.franciscoa.controlador.ObjetoSubasta;
import es.iesptocruz.franciscoa.controlador.Usuario;
import es.iesptocruz.franciscoa.vista.Subasta;

import java.io.PrintWriter;
import java.util.Map;

public class GestorSubasta {
    final Subasta subasta = new Subasta();

    public Subasta getSubasta() {
        return subasta;
    }

    String login;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    String passwd;

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    String accion;

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public GestorSubasta() {
    }

    public void genTablasArticulos(String login, PrintWriter out) {
        out.print("<h2>Mis articulos</h2>");
        out.print("<table>");
        out.print("<tr><th>Producto</th><th>Valor</th><th>Adjudicar</th><th>Cancelar</th></tr>");
        for (Map.Entry<String, ObjetoSubasta> entry : subasta.getArticulos().entrySet()) {
            if (entry.getValue().getPropietario().equals(login)) {
                out.print("<tr>");
                out.print("<td>" + entry.getValue().getProducto() + "</td><td>" + entry.getValue().getValor()
                        + "<td><a href='main?accion=adjudicar&&producto=" + entry.getValue().getProducto() + "'>Adjudicar</a></td><td>" +
                        "<a href='main?accion=cancelar&&producto=" + entry.getValue().getProducto() + "'>Cancelar</a></td>");
                out.print("</tr>");
            }
        }
        out.print("</table>");
        out.print("<h2>Articulos</h2>");
        out.print("<table>");
        out.print("<tr><th>Producto</th><th>Valor</th><th>Adjudicar</th><th>Cancelar</th></tr>");
        for (Map.Entry<String, ObjetoSubasta> objetoSubasta : subasta.getArticulos().entrySet()) {
            if (!objetoSubasta.getValue().getPropietario().equals(login)) {
                out.print("<tr>");
                out.print("<td>" + objetoSubasta.getValue().getProducto() + "</td><td>" + objetoSubasta.getValue().getValor()
                        + "<td><a href='comprar?producto=" + objetoSubasta.getValue().getProducto() + "'>Comprar</a></td><td>");
                out.print("</tr>");
            }
        }
    }

    public boolean validarUsuario(String login, String passwd) {
        Usuario u = buscarUsuario(login);
        boolean resultado = false;
        if (u != null) {
            resultado = u.getPasswd().equals(passwd);
        }

        return resultado;
    }

    public boolean registrarUsuario(String login, String passwd, String email) {
        boolean resultado = !subasta.getUsuarios().containsKey(login);
        if (resultado) {
            Usuario u = new Usuario(login, passwd, email);
            subasta.getUsuarios().put(login, u);
        }
        return resultado;
    }

    public Usuario buscarUsuario(String login) {
        return subasta.getUsuarios().get(login);
    }
}