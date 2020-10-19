package es.iesptocruz.franciscoa.controlador;

public class ObjetoSubasta {
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

    public void setPuja(String propietario, double puja) {
        this.usuario = propietario;
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
