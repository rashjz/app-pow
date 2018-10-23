package rashjz.info.app.pow.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "app.api")
public class ApiProperties {

    private String uri;
    private String username;
    private String password;
}
