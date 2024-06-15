package hipravin.samples.client.cbrf;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.OffsetDateTime;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DailyCurrency (
//        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
        @JsonProperty("Date") OffsetDateTime date,
//        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
        @JsonProperty("PreviousDate") OffsetDateTime previousDate,
        @JsonProperty("PreviousURL") String previousURL,
        @JsonProperty("Timestamp") String timestamp,
        @JsonProperty("Valute") Map<String, Currency> currencies
) {}
//TODO: adjust types properly
/*

{
"Date": "2024-06-15T11:30:00+03:00",
"PreviousDate": "2024-06-14T11:30:00+03:00",
"PreviousURL": "//www.cbr-xml-daily.ru/archive/2024/06/14/daily_json.js",
"Timestamp": "2024-06-14T20:00:00+03:00",
"Valute": {
"AUD": {
"ID": "R01010",
...
 */