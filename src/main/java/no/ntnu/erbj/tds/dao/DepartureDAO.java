package no.ntnu.erbj.tds.dao;

import jakarta.persistence.*;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import no.ntnu.erbj.tds.model.Departure;
import no.ntnu.erbj.tds.ui.cli.utilities.TdsLogger;
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
@Transactional
public class DepartureDAO implements DAO<Departure> {
  @PersistenceContext private EntityManager em;

  /** {@inheritDoc} */
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

  /** {@inheritDoc} */
  @Override
  public void remove(Departure departure) {
    em.getTransaction().begin();
    em.remove(em.contains(departure) ? departure : em.merge(departure));
    em.getTransaction().commit();
  }

  /** {@inheritDoc} */
  @Override
  public void update(Departure departure) {
    em.getTransaction().begin();
    em.merge(departure);
    em.flush();
    em.getTransaction().commit();
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

  /** {@inheritDoc} */
  @Override
  public void printAllDetails() {
    List<Departure> departureList = getAll();
    for (Departure departure : departureList) {
      TdsLogger.getInstance().info("Departure Details" + " :: " + departure.getId());
    }
  }

  /** {@inheritDoc} */
  @Override
  public void close() {
    if (em.isOpen()) {
      this.em.close();
    }
  }
}
