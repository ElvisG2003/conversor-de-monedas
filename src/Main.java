import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import procesos.Api;
import procesos.Conversor;
import procesos.Moneda;
import procesos.RespuestaApi;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean repetidor = true;
        ArrayList<String> historial = new ArrayList<>();
        Set<String> monedasPermitidas = new HashSet<>(Arrays.asList("ARS","BOB","BRL","CLP","COP","USD"));
        String resultado;
        Moneda moneda = null;
        String monedaLocal;
        String monedaDeEnvio;

        System.out.println("""
            //////////////
            Hola usuario; esta es una app para hacer y consultar cambios entre moneda.
            Entre las cosas que podras hacer es:
            *Cambios de tu moneda local a otra (viceversa).
            *Almacenar los cambios en una lista y revisarla.
            *Modificar la lista como te apetezca.
            """);

        System.out.println("Estas son las monedas permitas actualmente:");
        for (String monedaC : monedasPermitidas){
            System.out.println(monedaC);
        }

        while (true){
            System.out.println("Ingresa tu moneda local(Ejemplo: USD,CLP,ARS)");
            monedaLocal = scanner.nextLine().toUpperCase();
            if (monedasPermitidas.contains(monedaLocal)){
                moneda = new Moneda(monedaLocal);
                moneda.setMonedaUsuario(monedaLocal);
                break;
            }else{
                System.out.println("Moneda no permitida. Reintenta");
            }
        }

        while (true){
            System.out.println("Ingresa tu moneda de envio (Ejemplo: USD,CLP,ARS)");
            monedaDeEnvio = scanner.nextLine().toUpperCase();
            if (monedasPermitidas.contains(monedaDeEnvio)){
                moneda.setMonedaCambio(monedaDeEnvio);
                break;
            }else{
                System.out.println("Moneda no permitida. Reintenta");
            }
        }


        Api api = new Api();

        while (repetidor) {
            RespuestaApi datos = api.consultarMoneda(moneda);

            Conversor conversor = new Conversor();

            System.out.println("""
                Elige el numero de la opcion que te interesa:
                1.Cambiar de tu moneda local a la del destino.
                2.Cambiar de la moneda del destino a la local.
                3.Cambiar la moneda local.
                4.Cambiar la moneda de destino.
                5.Ver la lista de peticiones.
                6.Eliminar alguna peticion.
                7.Agregar alguna moneda a la lista permitida.
                0.Acabar el programa.
                """);

            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    System.out.println("¿Cuanta es la cantidad la que quieres cambiar?");
                    double monto = scanner.nextDouble();
                    scanner.nextLine();
                    resultado = conversor.convertir(datos, moneda, monto);
                    System.out.println(resultado);
                    historial.add(resultado);
                    break;
                case 2:
                    System.out.println("¿Cuantos " + moneda.getMonedaCambio() + " quieres cambiar a tu moneda local");
                    double montoInverso = scanner.nextDouble();
                    scanner.nextLine();
                    resultado = conversor.convertirCambio(datos, moneda, montoInverso);
                    System.out.println(resultado);
                    historial.add(resultado);
                    break;
                case 3:
                    System.out.println("La moneda actual es " + moneda.getMonedaUsuario() + " ¿a cual quieres cambiarlo?");
                    monedaLocal = scanner.nextLine().toUpperCase();
                    if (monedasPermitidas.contains(monedaLocal)){
                        moneda.setMonedaUsuario(monedaLocal);
                        api.consultarMoneda(moneda);
                    }else{
                        System.out.println("Moneda no permitida, asegurate de agregarla con la opcion 7");
                    }
                    break;
                case 4:
                    System.out.println("La moneda actual es " + moneda.getMonedaCambio() + " ¿a cual quieres cambiarlo?");
                    monedaDeEnvio = scanner.nextLine().toUpperCase();
                    if (monedasPermitidas.contains(monedaDeEnvio)){
                        moneda.setMonedaCambio(monedaDeEnvio);
                    }else {
                        System.out.println("Moneda no permitida, asegurate de agregarla con la opcion 7");
                    }
                    break;
                case 5:
                    if (historial.isEmpty()) {
                        System.out.println("No has hecho ningun cambio todavia");
                    } else {
                        System.out.println("///      Historial      ///");
                        for (String item : historial) {
                            System.out.println("-- " + item);
                        }
                    }
                    break;
                case 6:
                    if (historial.isEmpty()){
                        System.out.println("Aun no has agregado nada a tu lista");
                    }else {
                        System.out.println("""
                                A continuacion se te imprimira tu lista de peticiones
                                Para eliminar, escriba el numero a la izquierda de su opcion
                                """);
                        System.out.println("///      Historial      ///");
                        for (int i = 0;i<historial.size();i++) {
                            System.out.println(i+". "+historial.get(i));
                        }
                        System.out.println("¿Cual desea eliminar? escriba el valor correspondiente");
                        int valoreliminado = scanner.nextInt();
                        scanner.nextLine();
                        if (valoreliminado >=0 && valoreliminado < historial.size()){
                            String eliminado = historial.remove(valoreliminado);
                            System.out.println("Se elimino el siguiente cambio: "+eliminado);
                        }else{
                            System.out.println("Numero eliminado. No se elimino nada, por ende, se volvera al menu");
                        }
                    }
                    break;
                case 7:
                    System.out.println("Escribe la abreviacion de la moneda que quieras agregar(Ej:clp,ars,br,etc.)");
                    String nuevo = scanner.nextLine().toUpperCase();
                    Moneda prueba = new Moneda(nuevo);
                    Api apiPrueba = new Api();
                    try {
                        RespuestaApi resp = apiPrueba.consultarMoneda(prueba);
                        if (resp.getConversion_rates() == null || resp.getConversion_rates().isEmpty() ){
                            System.out.println("La moneda "+nuevo+" no se encuentra en la API; prueba otra moneda");
                        }else {
                            monedasPermitidas.add(nuevo);
                            System.out.println("Moneda "+nuevo+" fue agregada.");
                        }
                    }catch (Exception e){
                        System.out.println("Error en la consulta de la moneda");
                    }
                    break;
                case 0:
                    repetidor = false;
                    System.out.println("Saliste con exito");
                    break;
                default:
                    System.out.println("Opción no válida, intenta de nuevo.");
            }
        }

        scanner.close();
    }
}
