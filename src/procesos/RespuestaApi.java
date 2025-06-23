package procesos;

import java.util.Map;

public class RespuestaApi {
    private String base;
    private Map<String, Double> conversion_rates;

    public Map<String, Double> getConversion_rates() {
        return conversion_rates;
    }

    public String getBase() {
        return base;
    }
}
