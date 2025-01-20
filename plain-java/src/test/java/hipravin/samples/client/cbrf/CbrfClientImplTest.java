package hipravin.samples.client.cbrf;

import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

class CbrfClientImplTest {
    @Test
    void sample() {
        CbrfClient cbrfClient = new CbrfClientImpl("https://www.cbr-xml-daily.ru", HttpClient.newBuilder());

        DailyCurrency dailyCurrency = cbrfClient.getDailyCurrency();
        System.out.println(dailyCurrency);
    }

    @Test
    void mockServer() throws IOException, InterruptedException {
        MockWebServer server = new MockWebServer();

        String dailyCurrenciesJson = loadFromClasspath("data/cbrf/daily-sample.json");
        final Dispatcher dispatcher = new Dispatcher() {
            @Override
            public MockResponse dispatch (RecordedRequest request) throws InterruptedException {
                switch (request.getPath()) {
                    case "/daily_json.js":
                        return new MockResponse().setResponseCode(200).setBody(dailyCurrenciesJson);
                }
                return new MockResponse().setResponseCode(404);
            }
        };
        server.setDispatcher(dispatcher);

        // Start the server.
        server.start();

        System.out.println(server.getPort());

        HttpClient httpClient = HttpClient.newBuilder().build();
        HttpRequest getRequest = HttpRequest.newBuilder(URI.create("http://localhost:%d/daily_json.js".formatted(server.getPort()))).build();
        HttpResponse<String> response = httpClient.send(getRequest, HttpResponse.BodyHandlers.ofString());

        System.out.println("RESPONSE: ");
        System.out.println(response.statusCode());
//        System.out.println(response.body());

        CbrfClient cbrfClient = new CbrfClientImpl("http://localhost:%d".formatted(server.getPort()), HttpClient.newBuilder());
        DailyCurrency dailyCurrency = cbrfClient.getDailyCurrency();

        System.out.println(dailyCurrency);
    }

    private static String loadFromClasspath(String fileName) {
        try (InputStream is = ClassLoader.getSystemResourceAsStream(fileName)) {
            if (is != null) {
                return new String(is.readAllBytes(), StandardCharsets.UTF_8);
            } else {
                throw new RuntimeException("Not found in classpath: " + fileName);
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}