package es.iesptocruz.franciscoa.controlador;

import java.util.HashMap;

public class Usuario {
    String login;
    String passwd;
    String email;
    HashMap<String,ObjetoSubasta> articulos;

    public Usuario(String login, String passwd, String email) {
        this.login = login;
        this.passwd = passwd;
        this.email = email;
        articulos = new HashMap<>();
    }

    public HashMap<String, ObjetoSubasta> getArticulos() {
        return articulos;
    }

    public void adquirirArtidulo(String key, ObjetoSubasta articulo){
        articulos.put(key,articulo);
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
