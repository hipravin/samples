package hipravin.samples.finalautowired;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Lazy
public class ComponentBFinal implements InitializingBean {
    private static final Logger log = LoggerFactory.getLogger(ComponentBFinal.class);

    private final UUID id = UUID.randomUUID();
    private final ComponentAFinal componentAFinal;

    public ComponentBFinal(ComponentAFinal componentAFinal) {
        this.componentAFinal = componentAFinal;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("Created: {} / {}", id, componentAFinal);
    }

    public UUID getId() {
        return id;
    }
}
