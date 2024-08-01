package se.vgregion.k8s.quotes;

import io.dekorate.kubernetes.annotation.Env;
import io.dekorate.kubernetes.annotation.KubernetesApplication;
import io.dekorate.kubernetes.annotation.Port;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@KubernetesApplication(ports = @Port(name = "web", containerPort = 8080), envVars = @Env(name = "QUOTE_SERVICE", value = "HITCHHIKER"))
public class QuotesServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(QuotesServiceApplication.class, args);
  }
}
 