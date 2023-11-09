package no.ntnu.erbj.tds.dao;

import jakarta.persistence.*;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import no.ntnu.erbj.tds.model.Station;
import no.ntnu.erbj.tds.ui.utilities.TdsLogger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * This class is a Data Access Object (DAO) for the Station class. It provides methods for
 * accessing the database.
 *
 * @version 1.1
 * @author Erik
 */
@Repository
public class StationDAO implements DAO<Station> {
  @PersistenceContext private EntityManager em;

  /** {@inheritDoc} */
  @Override
  @Transactional
  public void add(Station station) {
    if (getAll().contains(station)) {
      throw new IllegalArgumentException("Instance of station already exists in the database.");
    } else {
      this.em.persist(station);
    }
  }

  /** {@inheritDoc} */
  @Override
  @Transactional
  public void remove(Station station) {
    em.remove(em.contains(station) ? station : em.merge(station));
  }

  /** {@inheritDoc} */
  @Override
  @Transactional
  public void update(Station station) {
    em.merge(station);
  }

  /** {@inheritDoc} */
  @Override
  public Iterator<Station> iterator() {
    TypedQuery<Station> query = this.em.createQuery("SELECT s FROM Station s", Station.class);
    return query.getResultList().iterator();
  }

  /** {@inheritDoc} */
  @Override
  public Optional<Station> find(Long id) {
    return Optional.ofNullable(em.find(Station.class, id));
  }

  /** {@inheritDoc} */
  @Override
  public List<Station> getAll() {
    return em.createQuery("SELECT s FROM Station s", Station.class).getResultList();
  }

  /** {@inheritDoc} */
  @Override
  public void printAllDetails() {
    List<Station> stationList = getAll();
    for (Station station : stationList) {
      TdsLogger.getInstance().info("Station Details" + " :: " + station.getId());
    }
  }
}
