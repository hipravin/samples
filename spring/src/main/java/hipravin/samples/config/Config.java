package hipravin.samples.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestTemplate;

@Configuration
public class Config {
    @Bean
    @MyQualifier
    public RestTemplate myTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Bean
    @Primary
    public RestTemplate primaryTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    // > spring 6.2
//    @Bean
//    @Fallback
//    public RestTemplate primaryTemplate(RestTemplateBuilder builder) {
//        return builder.build();
//    }

    @Bean
    @Qualifier("qualifier1")
    public RestTemplate myOtherTemplate(@Autowired @MyQualifier RestTemplate rt) {
        return rt;
    }
}
