package jdbi;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.jooby.test.JoobyRule;
import org.junit.ClassRule;
import org.junit.Test;

import static jdbi.Utils.*;

/**
 * @author jooby generator
 */
public class AppTest {

    @ClassRule
    public static JoobyRule app = new JoobyRule(new App());

    @Test
    public void pets() throws Exception {
        given()
                .contentType(ContentType.JSON /* "application/json"*/)
                .body("{\"name\": \"Oliver\"}")
                .post(APP_PATH)
                .then()
                .assertThat()
                .body(equalTo("{\"id\":1,\"name\":\"Oliver\"}"));

        get(APP_PATH)
                .then()
                .assertThat()
                .body(equalTo("[{\"id\":1,\"name\":\"Oliver\"}]"));

        get(APP_PATH + "/1")
                .then()
                .assertThat()
                .body(equalTo("{\"id\":1,\"name\":\"Oliver\"}"));

        given()
                .contentType(ContentType.JSON /*"application/json"*/)
                .body("{\"id\": 1, \"name\": \"Jemima\"}")
                .put(APP_PATH)
                .then()
                .assertThat()
                .body(equalTo("{\"id\":1,\"name\":\"Jemima\"}"));

        given()
                .delete(APP_PATH + "/1")
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_NO_CONTENT /*204*/);
    }
}
