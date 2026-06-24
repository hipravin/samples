package hipravin.samples.finalautowired;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Lazy
public class ComponentAFinal implements InitializingBean {
    private static final Logger log = LoggerFactory.getLogger(ComponentAFinal.class);

    private final UUID id = UUID.randomUUID();
    private final ComponentBFinal componentBFinal;

    public ComponentAFinal(ComponentBFinal componentBFinal) {
        this.componentBFinal = componentBFinal;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("Created: {} / {}", id, componentBFinal);
    }

    public UUID getId() {
        return id;
    }
}
