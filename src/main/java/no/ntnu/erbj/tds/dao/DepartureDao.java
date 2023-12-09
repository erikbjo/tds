package no.ntnu.erbj.tds.dao;

import jakarta.persistence.*;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import no.ntnu.erbj.tds.model.departures.Departure;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * This class is a Data Access Object (DAO) for the Departure class. It provides methods for
 * accessing the database.
 *
 * @version 1.1
 * @author Erik
 */
@Repository
public class DepartureDao implements Dao<Departure> {
  @PersistenceContext private EntityManager em;

  /** {@inheritDoc} */
  @Override
  @Transactional
  public void add(Departure departure) {
    if (getAll().contains(departure)) {
      throw new IllegalArgumentException("Instance of departure already exists in the database.");
    } else if (getAll().stream().anyMatch(dep -> dep.getTrain().equals(departure.getTrain()))) {
      throw new IllegalArgumentException("Train already has a departure.");
    }

    this.em.persist(departure);
  }

  /** {@inheritDoc} */
  @Override
  @Transactional
  public void remove(Departure departure) {
    em.remove(em.contains(departure) ? departure : em.merge(departure));
  }

  /** {@inheritDoc} */
  @Override
  @Transactional
  public void update(Departure departure) {
    em.merge(departure);
  }

  /** {@inheritDoc} */
  @Override
  public Iterator<Departure> iterator() {
    TypedQuery<Departure> query = this.em.createQuery("SELECT d FROM Departure d", Departure.class);
    return query.getResultList().iterator();
  }

  /** {@inheritDoc} */
  @Override
  public Optional<Departure> find(Long id) {
    return Optional.ofNullable(em.find(Departure.class, id));
  }

  /** {@inheritDoc} */
  @Override
  public List<Departure> getAll() {
    return em.createQuery("SELECT d FROM Departure d", Departure.class).getResultList();
  }

  /** Get a departure by train number. */
  public Departure getByTrainNumber(String trainNumber) {
    TypedQuery<Departure> query =
            this.em.createQuery(
                    "SELECT d FROM Departure d WHERE d.train.trainNumber = :trainNumber", Departure.class);
    query.setParameter("trainNumber", trainNumber);
    return query.getSingleResult();
  }
}
