package es.iesptocruz.franciscoa.vista;

import es.iesptocruz.franciscoa.controlador.ObjetoSubasta;
import es.iesptocruz.franciscoa.controlador.Usuario;

import java.util.Map;

public class Subasta {
    public Map<String, Usuario> usuarios;

    public Map<String, Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(Map<String, Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public Map<String, ObjetoSubasta> articulos;

    public Map<String, ObjetoSubasta> getArticulos() {
        return articulos;
    }

    public void setArticulos(Map<String, ObjetoSubasta> articulos) {
        this.articulos = articulos;
    }

    public Usuario usuario;

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public ObjetoSubasta objetoSubasta;

    public Subasta() {
    }

    public void cancelar(String producto) {
        articulos.remove(producto);
    }

    public void adjudicar(String producto) {
        objetoSubasta = articulos.get(producto);
        objetoSubasta.setPropietario(objetoSubasta.getUsuario());
        usuarios.get(objetoSubasta.getPropietario()).getArticulos().put(producto, objetoSubasta);
        articulos.remove(producto);
    }

    public void vender(String producto, double valor, String login) {
        articulos.put(producto, new ObjetoSubasta(producto, valor, null, login));
    }

    public void comprar(String producto, double valor, String postor) {
        objetoSubasta = articulos.get(producto);
        if (objetoSubasta.getValor() < (valor + objetoSubasta.getValor())) {
            if (usuario != null) {
                objetoSubasta.setPuja(postor, valor);
                articulos.put(producto, objetoSubasta);
            }
            ;
        }
    }
}