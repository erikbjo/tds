package no.ntnu.erbj.tds.dao;

import jakarta.persistence.*;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import no.ntnu.erbj.tds.model.Wagon;
import no.ntnu.erbj.tds.ui.cli.utilities.TdsLogger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class WagonDAO implements DAO<Wagon> {
  @PersistenceContext
  private EntityManager em;

  /**
   * Adds a new instance of an entity to the database.
   *
   * @param wagon The entity to be added.
   */
  @Override
  public void add(Wagon wagon) {
    if (getAll().contains(wagon)) {
      throw new IllegalArgumentException("Instance of wagon already exists in the database.");
    } else {
//      this.em.getTransaction().begin();
      this.em.persist(wagon);
//      this.em.getTransaction().commit();
    }
  }

  /**
   * Removes an instance of an entity from the database.
   *
   * @param wagon The entity to be removed.
   */
  @Override
  public void remove(Wagon wagon) {
    em.getTransaction().begin();
    em.remove(em.contains(wagon) ? wagon : em.merge(wagon));
    em.getTransaction().commit();
  }

  /**
   * Updates all fields of an entity in the database.
   *
   * @param wagon The entity to be updated.
   */
  @Override
  public void update(Wagon wagon) {
    em.getTransaction().begin();
    em.merge(wagon);
    em.flush();
    em.getTransaction().commit();
  }

  /**
   * Returns an iterator of all entity instances in the database.
   *
   * @return An iterator of all entities in the database.
   */
  @Override
  public Iterator<Wagon> iterator() {
    TypedQuery<Wagon> query = this.em.createQuery("SELECT a FROM Wagon a", Wagon.class);
    return query.getResultList().iterator();
  }

  /**
   * Finds a specific entity instance by using the entity's id.
   *
   * @param id The id as a String.
   * @return The entity instance found.
   */
  @Override
  public Optional<Wagon> find(Long id) {
    return Optional.ofNullable(em.find(Wagon.class, id));
  }

  /**
   * Returns a List of all entity instances in the database.
   *
   * @return All entity instances in the database as a List.
   */
  @Override
  public List<Wagon> getAll() {
    return em.createQuery("SELECT a FROM Wagon a", Wagon.class).getResultList();
  }

  /** Prints entity-specific details of all the entity instances in the database. */
  @Override
  public void printAllDetails() {
    List<Wagon> wagonList = getAll();
    for (Wagon wagon : wagonList) {
      TdsLogger.getInstance()
          .info("Wagon Details" + " :: " + wagon.getId() + " :: " + wagon.getWagonType());
    }
  }

  /** Closes the EntityManagerFactory and the EntityManager. */
  @Override
  public void close() {
    if (em.isOpen()) {
      this.em.close();
    }
  }
}
