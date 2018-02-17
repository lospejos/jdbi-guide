package jdbi;

import java.util.List;

import org.jooby.Jooby;
import org.jooby.Results;
import org.jooby.jdbc.Jdbc;
import org.jooby.jdbi.Jdbi;
import org.jooby.json.Jackson;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;

import com.typesafe.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static jdbi.Utils.*;

public class App extends Jooby {
    private static final Logger LOG = LoggerFactory.getLogger(App.class);

    {
        use(new Jackson());

        use(new Jdbc());

        use(new Jdbi()
                // 1 dbi ready
                .doWith((final DBI dbi, final Config conf) -> {
                    // 2 open a new handle
                    try (Handle handle = dbi.open()) {
                        // 3. execute script
                        handle.execute(conf.getString("schema"));
                    }
                }));

        /** Pet API. */
        use(APP_PATH)
                /** List pets. */
                .get(req -> {
                    return require(DBI.class).inTransaction((handle, status) -> {
                        PetRepository repo = handle.attach(PetRepository.class);
                        List<Pet> pets = repo.list();
                        LOG.debug("Got pets: {}", pets);
                        return pets;
                    });
                })
                /** Get a pet by ID. */
                .get("/:id", req -> require(DBI.class).inTransaction((handle, status) -> {
                    int id = req.param("id").intValue();
                    LOG.debug("Got pet id: {}", id);
                    PetRepository repo = handle.attach(PetRepository.class);
                    Pet pet = repo.findById(id);
                    LOG.debug("Got pet: {}", pet);
                    return pet;
                }))
                /** Create a pet. */
                .post(req -> {
                    return require(DBI.class).inTransaction((handle, status) -> {
                        // read from HTTP body
                        Pet pet = req.body(Pet.class);

                        LOG.debug("Got pet: {}", pet);
                        PetRepository repo = handle.attach(PetRepository.class);
                        int petId = repo.insert(pet);
                        pet.setId(petId);
                        LOG.debug("Set pet id: {}", petId);
                        return pet;
                    });
                })
                /** Update a pet. */
                .put(req -> {
                    return require(DBI.class).inTransaction((handle, status) -> {
                        // read from HTTP body
                        Pet pet = req.body(Pet.class);

                        LOG.debug("Got pet: {}", pet);
                        PetRepository repo = handle.attach(PetRepository.class);
                        repo.update(pet);
                        return pet;
                    });
                })
                /** Delete a pet by ID. */
                .delete("/:id", req -> {
                    return require(DBI.class).inTransaction((handle, status) -> {
                        int id = req.param("id").intValue();
                        LOG.debug("Will delete pet by id: {}", id);
                        PetRepository repo = handle.attach(PetRepository.class);
                        repo.deleteById(id);
                        LOG.debug("Deleted pet by id: {}", id);
                        return Results.noContent();
                    });
                });
    }

    public static void main(final String[] args) {
        run(App::new, args);
    }
}
