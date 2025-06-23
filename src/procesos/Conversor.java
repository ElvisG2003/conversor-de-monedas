package procesos;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.Map;

public class Conversor {
    public String convertir(RespuestaApi datos, Moneda moneda, double monto){
        String monedaFinal = moneda.getMonedaCambio();
        String monedaInicial = moneda.getMonedaUsuario();
        Map<String, Double> cambio = datos.getConversion_rates();

        if (cambio == null || !cambio.containsKey(monedaFinal)){
            return "No se encontro la tasa de cambio para "+monedaFinal;
        }
        double cambioFinal = cambio.get(monedaFinal);

        double resultado = monto * cambioFinal;

        return "El cambio final de "+monedaInicial+" a "+monedaFinal+" es ="+resultado;
    }

    public String convertirCambio(RespuestaApi datos, Moneda moneda, double monto){
        String monedaFinal = moneda.getMonedaCambio();
        String monedaInicial = moneda.getMonedaUsuario();
        Map<String,Double> cambio = datos.getConversion_rates();

        if (cambio == null || !cambio.containsKey(monedaFinal)){
            return "No se encontro la tasa de cambio para "+monedaFinal;
        }
        double cambioFinal = cambio.get(monedaFinal);

        double resultado = monto / cambioFinal;

        return "El cambio final de "+monedaFinal+" a "+monedaInicial+" es ="+resultado;
    }



}
