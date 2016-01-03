package restjdbi;

import org.junit.Test;

/**
 * @author jooby generator
 */
public class AppTest extends BaseTest {

  @Test
  public void pets() throws Exception {
    server.post("/pets")
        .body("{\"name\": \"Oliver\"}", "application/json")
        .expect("{\"id\":1,\"name\":\"Oliver\"}");

    server.get("/pets")
        .expect("[{\"id\":1,\"name\":\"Oliver\"}]");
  }

}