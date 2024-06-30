package hipravin.samples.client.cbrf;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.function.Supplier;

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
                .header("Content-Type", "application/json")
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

    private HttpResponse.BodyHandler<String> currencyBodyHandler() {
        return (responseInfo) -> {
            System.out.println("Body handler 1: " + Thread.currentThread());
            return HttpResponse.BodySubscribers.ofString(StandardCharsets.UTF_8);
        };
    }

    public static <W> HttpResponse.BodySubscriber<Supplier<W>> asJSON(Class<W> targetType) {
        HttpResponse.BodySubscriber<InputStream> upstream = HttpResponse.BodySubscribers.ofInputStream();

        HttpResponse.BodySubscriber<Supplier<W>> downstream = HttpResponse.BodySubscribers.mapping(
                upstream,
                (InputStream is) -> () -> {
                    try (InputStream stream = is) {
                        ObjectMapper objectMapper = new ObjectMapper();
                        return objectMapper.readValue(stream, targetType);
                    } catch (IOException e) {
                        throw new UncheckedIOException(e);
                    }
                });
        return downstream;
    }
}
