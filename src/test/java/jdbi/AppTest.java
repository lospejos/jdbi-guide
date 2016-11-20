package jdbi;

import org.junit.Test;

/**
 * @author jooby generator
 */
public class AppTest extends BaseTest {

  @Test
  public void pets() throws Exception {
    server.post("/api/pets")
        .body("{\"name\": \"Oliver\"}", "application/json")
        .expect("{\"id\":1,\"name\":\"Oliver\"}");

    server.get("/api/pets")
        .expect("[{\"id\":1,\"name\":\"Oliver\"}]");

    server.get("/api/pets/1")
        .expect("{\"id\":1,\"name\":\"Oliver\"}");

    server.put("/api/pets")
        .body("{\"id\": 1, \"name\": \"Jemima\"}", "application/json")
        .expect("{\"id\":1,\"name\":\"Jemima\"}");

    server.delete("/api/pets/1")
        .expect(204);
  }

}
