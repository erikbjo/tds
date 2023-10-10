package no.ntnu.erbj.tds.dao;

import jakarta.persistence.*;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import no.ntnu.erbj.tds.model.Station;
import no.ntnu.erbj.tds.ui.cli.utilities.TdsLogger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class StationDAO implements DAO<Station> {
  @PersistenceContext
  private EntityManager em;

  /**
   * Adds a new instance of an entity to the database.
   *
   * @param station The entity to be added.
   */
  @Override
  public void add(Station station) {
    if (getAll().contains(station)) {
      throw new IllegalArgumentException("Instance of station already exists in the database.");
    } else {
      this.em.getTransaction().begin();
      this.em.persist(station);
      this.em.getTransaction().commit();
    }
  }

  /**
   * Removes an instance of an entity from the database.
   *
   * @param station The entity to be removed.
   */
  @Override
  public void remove(Station station) {
    em.getTransaction().begin();
    em.remove(em.contains(station) ? station : em.merge(station));
    em.getTransaction().commit();
  }

  /**
   * Updates all fields of an entity in the database.
   *
   * @param station The entity to be updated.
   */
  @Override
  public void update(Station station) {
    em.getTransaction().begin();
    em.merge(station);
    em.flush();
    em.getTransaction().commit();
  }

  /**
   * Returns an iterator of all entity instances in the database.
   *
   * @return An iterator of all entities in the database.
   */
  @Override
  public Iterator<Station> iterator() {
    TypedQuery<Station> query = this.em.createQuery("SELECT s FROM Station s", Station.class);
    return query.getResultList().iterator();
  }

  /**
   * Finds a specific entity instance by using the entity's id.
   *
   * @param id The id as a String.
   * @return The entity instance found.
   */
  @Override
  public Optional<Station> find(Long id) {
    return Optional.ofNullable(em.find(Station.class, id));
  }

  /**
   * Returns a List of all entity instances in the database.
   *
   * @return All entity instances in the database as a List.
   */
  @Override
  public List<Station> getAll() {
    return em.createQuery("SELECT s FROM Station s", Station.class).getResultList();
  }

  /** Prints entity-specific details of all the entity instances in the database. */
  @Override
  public void printAllDetails() {
    List<Station> stationList = getAll();
    for (Station station : stationList) {
      TdsLogger.getInstance().info("Station Details" + " :: " + station.getId());
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
