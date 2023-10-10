package no.ntnu.erbj.tds.dao;

import jakarta.persistence.*;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import no.ntnu.erbj.tds.model.Departure;
import no.ntnu.erbj.tds.ui.cli.utilities.TdsLogger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class DepartureDAO implements DAO<Departure> {
  @PersistenceContext private EntityManager em;

  /**
   * Adds a new instance of an entity to the database.
   *
   * @param departure The entity to be added.
   */
  @Override
  public void add(Departure departure) {
    if (getAll().contains(departure)) {
      throw new IllegalArgumentException("Instance of departure already exists in the database.");
    } else {
      this.em.getTransaction().begin();
      this.em.persist(departure);
      this.em.getTransaction().commit();
    }
  }

  /**
   * Removes an instance of an entity from the database.
   *
   * @param departure The entity to be removed.
   */
  @Override
  public void remove(Departure departure) {
    em.getTransaction().begin();
    em.remove(em.contains(departure) ? departure : em.merge(departure));
    em.getTransaction().commit();
  }

  /**
   * Updates all fields of an entity in the database.
   *
   * @param departure The entity to be updated.
   */
  @Override
  public void update(Departure departure) {
    em.getTransaction().begin();
    em.merge(departure);
    em.flush();
    em.getTransaction().commit();
  }

  /**
   * Returns an iterator of all entity instances in the database.
   *
   * @return An iterator of all entities in the database.
   */
  @Override
  public Iterator<Departure> iterator() {
    TypedQuery<Departure> query = this.em.createQuery("SELECT d FROM Departure d", Departure.class);
    return query.getResultList().iterator();
  }

  /**
   * Finds a specific entity instance by using the entity's id.
   *
   * @param id The id as a String.
   * @return The entity instance found.
   */
  @Override
  public Optional<Departure> find(Long id) {
    return Optional.ofNullable(em.find(Departure.class, id));
  }

  /**
   * Returns a List of all entity instances in the database.
   *
   * @return All entity instances in the database as a List.
   */
  @Override
  public List<Departure> getAll() {
    return em.createQuery("SELECT d FROM Departure d", Departure.class).getResultList();
  }

  /** Prints entity-specific details of all the entity instances in the database. */
  @Override
  public void printAllDetails() {
    List<Departure> departureList = getAll();
    for (Departure departure : departureList) {
      TdsLogger.getInstance().info("Departure Details" + " :: " + departure.getId());
    }
  }

  /** Closes the EntityManagerFactory and the EntityManager. */
  @Override
  public void close() {
    if (em.isOpen()) {
      this.em.close();
    }
    // if (emf.isOpen()) {
    //  this.emf.close();
    // }
  }
}
