package rashjz.info.app.pow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties
@SpringBootApplication
public class AppPowApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppPowApplication.class, args);
	}


}
