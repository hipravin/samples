package hipravin.samples.finalautowired;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Lazy
public class ComponentAAutowired implements InitializingBean {
    private static final Logger log = LoggerFactory.getLogger(ComponentAAutowired.class);
    private final UUID id = UUID.randomUUID();

    @Autowired
    private ComponentBAutowired componentBAutowired;

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("Created: {} / {}", id, componentBAutowired);
    }

    public UUID getId() {
        return id;
    }
}
