package procesos;

public class Moneda {
    String monedaUsuario;
    String monedaCambio;

    public void setMonedaCambio(String monedaCambio) {
        this.monedaCambio = monedaCambio.toUpperCase();
    }

    public void setMonedaUsuario(String monedaUsuario) {
        this.monedaUsuario = monedaUsuario.toUpperCase();
    }

    public String getMonedaCambio() {
        return monedaCambio;
    }

    public String getMonedaUsuario() {
        return monedaUsuario;
    }

    public Moneda(String monedaUsuario){
        this.monedaUsuario = monedaUsuario;
    }

}
