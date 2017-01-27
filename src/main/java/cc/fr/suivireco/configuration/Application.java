package cc.fr.suivireco.configuration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.EndpointAutoConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.ErrorMvcAutoConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@SpringBootApplication(exclude = { EndpointAutoConfiguration.class, ErrorMvcAutoConfiguration.class })
@EnableAutoConfiguration
@EnableGlobalMethodSecurity(jsr250Enabled = true)
public class Application {

  public static void main(String args[]) {

    SpringApplication.run(BatchConfiguration.class, args);

  }
}