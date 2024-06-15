package hipravin.samples.client;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import hipravin.samples.client.cbrf.DailyCurrency;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class Playground {
    public static void main(String[] args) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2) // HTTP_2 is default and can be used anywhere because falls back to 1.1 if not supported
                .followRedirects(HttpClient.Redirect.NORMAL)
                .connectTimeout(Duration.ofSeconds(20)).build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://www.cbr-xml-daily.ru/daily_json.js"))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.statusCode());

        String body = response.body();

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);

        DailyCurrency dailyCurrency = mapper.readValue(body, DailyCurrency.class);
        System.out.println(dailyCurrency.date());
        System.out.println(dailyCurrency.previousDate());
//        System.out.println(dailyCurrency);

        String reserialised = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(dailyCurrency);

        System.out.println(reserialised);
    }
}
