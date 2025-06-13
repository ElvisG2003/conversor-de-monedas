package procesos;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Conversor {
    public void convertir(String json, Moneda moneda, double monto){
        String monedaFinal = moneda.getMonedaCambio();
        String monedaInicial = moneda.getMonedaUsuario();
        JsonObject objeto = JsonParser.parseString(json).getAsJsonObject();
        JsonObject cambio = objeto.getAsJsonObject("conversion_rates");
        double cambioFinal = cambio.get(monedaFinal).getAsDouble();

        double resultado = monto * cambioFinal;

        System.out.println("El cambio final de "+monedaInicial+" a "+monedaFinal+" es ="+resultado);
    }

    public void convetirCambio(String json, Moneda moneda, double monto){
        String monedaFinal = moneda.getMonedaCambio();
        String monedaInicial = moneda.getMonedaUsuario();
        JsonObject objeto = JsonParser.parseString(json).getAsJsonObject();
        JsonObject cambio = objeto.getAsJsonObject("conversion_rates");
        double cambioFinal = cambio.get(monedaFinal).getAsDouble();

        double resultado = monto / cambioFinal;

        System.out.println("El cambio final de "+monedaFinal+" a "+monedaInicial+" es ="+resultado);
    }



}
