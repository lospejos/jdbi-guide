package restjdbi;

import org.jooby.Jooby;

public class App extends Jooby {

  {
    get("/", () -> "Welcome to the rest-jdbi guide!");
  }

  public static void main(final String[] args) throws Exception {
    new App().start(args);
  }

}
