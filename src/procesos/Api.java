package procesos;
import com.google.gson.Gson;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Api {
    public RespuestaApi consultarMoneda(Moneda monedaUsuario){
        String tipoUsuario = monedaUsuario.getMonedaUsuario();
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://v6.exchangerate-api.com/v6/1cd968b76d387f7af7ea7c0d/latest/"+tipoUsuario))
                .build();

        try {
            HttpResponse<String> response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());
            String json = response.body();
            //System.out.println(json); // <-- Esta linea sirve para ver que json se este imprimiendo de manera correcta
            Gson gson = new Gson();
            RespuestaApi datos = gson.fromJson(json, RespuestaApi.class);
            return datos;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
