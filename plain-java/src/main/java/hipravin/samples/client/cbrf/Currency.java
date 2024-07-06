package hipravin.samples.client.cbrf;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Currency (
        @JsonProperty("ID") String id,
        @JsonProperty("NumCode") String numCode,
        @JsonProperty("CharCode") String charCode,
        @JsonProperty("Nominal") long nominal,
        @JsonProperty("Name") String name,
        @JsonProperty("Value") BigDecimal value,
        @JsonProperty("Previous") BigDecimal previous
) {}
/*
        {
            "ID": "R01010",
            "NumCode": "036",
            "CharCode": "AUD",
            "Nominal": 1,
            "Name": "Австралийский доллар",
            "Value": 59.015,
            "Previous": 58.5878
        }

 */