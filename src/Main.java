import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import procesos.Api;
import procesos.Conversor;
import procesos.Moneda;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean repetidor = true;
        System.out.println("""
                //////////////
                Hola usuario; esta es una app para hacer y consultar cambios entre moneda.
                Entre las cosas que podras hacer es: 
                *Cambios de tu moneda local a otra (viceversa).
                *Almacenar los cambios en una lista y revisarla.
                *Modificar la lista como te apetezca.
                """);
        while (repetidor){
            System.out.println("Ingresa tu moneda (Ejemplo: USD,CLP,ARS)");
            String monedaLocal = scanner.nextLine();

            System.out.println("Ahora ingresa la moneda a la que quieres convertir");
            String monedaDeEnvio = scanner.nextLine();

            Moneda moneda = new Moneda(monedaLocal);
            moneda.setMonedaCambio(monedaDeEnvio);
            moneda.setMonedaUsuario(monedaLocal);

            Api api = new Api();
            String json = api.consultarMoneda(moneda);

            Conversor conversor = new Conversor();

            System.out.println("¿Que es lo que desear hacer?");
            while (repetidor){
                System.out.println("""
                        Elige el numero de la opcion que te interesa
                        1.Cambiar de tu moneda local a la del destino
                        2.Cambiar de la moneda del destino a la local
                        3.Cambiar la moneda local
                        4.Cambiar la moneda de destino
                        5.Ver la lista de peticiones 
                        6.Elimnar alguna peticion
                        0.Acabar el programa
                        """);
                int opcion = scanner.nextInt();
                scanner.nextLine();
                switch (opcion){
                    case 1:
                        System.out.println("¿Cuanta es la cantidad la que quieres cambiar?");
                        double monto = scanner.nextDouble();
                        scanner.nextLine();
                        conversor.convertir(json,moneda,monto);
                        break;
                    case 2:
                        System.out.println("¿Cuantos "+moneda.getMonedaCambio()+" quieres cambiar a tu moneda local");
                        double montoInverso = scanner.nextDouble();
                        scanner.nextLine();
                        conversor.convetirCambio(json, moneda, montoInverso);
                        break;
                    case 3:
                        System.out.println("La moneda actual es "+moneda.getMonedaUsuario()+" ¿a cual quieres cambiarlo?");
                        monedaLocal = scanner.nextLine();
                        moneda.setMonedaUsuario(monedaLocal);
                        break;
                    case 4:
                        System.out.println("La moneda actual es "+moneda.getMonedaCambio()+" ¿a cual quieres cambiarlo?");
                        monedaDeEnvio = scanner.nextLine();
                        moneda.setMonedaCambio(monedaDeEnvio);
                        break;
                    case 5:

                        break;
                    case 6:

                        break;
                    case 0:
                        repetidor=false;
                        System.out.println("Saliste con exito");
                        break;
                }
            }
        }
        scanner.close();
    }
}
