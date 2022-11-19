package recipecalc.restapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * REST API application using Spring.
 */
@SpringBootApplication
public class RecipecalcApplication {

  /**
   * Starts the REST API.
   *
   * @param args Default strings
   */
  public static void main(String[] args) {
    SpringApplication.run(RecipecalcApplication.class, args);
  }

}
