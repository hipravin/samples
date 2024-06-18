package hipravin.samples.client.cbrf;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class CbrfClientImpl implements CbrfClient {

    private final String baseUrl;

//    private final URI currenciesDailyUri = URI.create("https://www.cbr-xml-daily.ru/daily_json.js");
    private final HttpClient.Builder httpClientBuilder;
    private final ObjectMapper objectMapper;
    private final HttpClient httpClient;

    public CbrfClientImpl(String baseUrl,HttpClient.Builder httpClientBuilder) {
        this.baseUrl = baseUrl;
        this.httpClientBuilder = httpClientBuilder;
        this.objectMapper = createMapper();
        this.httpClient = createHttpClient();
    }

    @Override
    public DailyCurrency getDailyCurrency() {
        try {
            HttpResponse<String> response = httpClient.send(dailyCurrenciesGetRequest(), HttpResponse.BodyHandlers.ofString());

            if(response.statusCode() != 200) {
                throw new RuntimeException("Request failed: %s".formatted(response.statusCode()));
            }

            return objectMapper.readValue(response.body(), DailyCurrency.class);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private HttpRequest dailyCurrenciesGetRequest() {
        return HttpRequest.newBuilder().uri(URI.create(baseUrl + "/daily_json.js"))
                .build();
    }

    private HttpClient createHttpClient() {
        return httpClientBuilder.build();
    }

    private ObjectMapper createMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);

        return mapper;
    }
}
