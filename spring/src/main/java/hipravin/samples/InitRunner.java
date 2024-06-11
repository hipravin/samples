package hipravin.samples;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class InitRunner implements CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(InitRunner.class);

    @Autowired
    List<RestTemplate> restTemplates;

    @Autowired
    RestTemplate myTemplate;

    @Override
    public void run(String... args) throws Exception {
        log.info("RestTemplates autowired: {}", restTemplates);
    }
}
