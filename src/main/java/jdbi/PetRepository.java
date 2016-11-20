package jdbi;

import java.io.Closeable;
import java.util.List;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.GetGeneratedKeys;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.helpers.MapResultAsBean;

/**
 * Basic CRUD operations around {@link Pet}.
 */
public interface PetRepository extends Closeable {

  /**
   * @return List all pets.
   */
  @SqlQuery("select * from pets")
  @MapResultAsBean
  List<Pet> list();

  /**
   * Find a pet by ID.
   *
   * @param id Pet ID.
   * @return A pet.
   */
  @SqlQuery("select * from pets where id = :id")
  @MapResultAsBean
  Pet findById(@Bind("id") int id);

  /**
   * Insert a new pet.
   *
   * @param pet A pet.
   * @return The generated key.
   */
  @SqlUpdate("insert into pets (name) values (:pet.name)")
  @GetGeneratedKeys
  int insert(@BindBean("pet") Pet pet);

  /**
   * Update a pet by ID.
   *
   * @param pet Pet to update.
   */
  @SqlUpdate("update pets set name=:pet.name where id=:pet.id")
  void update(@BindBean("pet") Pet pet);

  /**
   * Delete a pet by ID.
   *
   * @param id Pet ID.
   */
  @SqlUpdate("delete pets where id = :id")
  void deleteById(@Bind("id") int id);
}
