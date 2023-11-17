package no.ntnu.erbj.tds.dao;

import jakarta.persistence.*;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import no.ntnu.erbj.tds.model.Wagon;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * This class is a Data Access Object (DAO) for the Wagon class. It provides methods for accessing
 * the database.
 *
 * @version 1.1
 * @author Erik
 */
@Repository
public class WagonDao implements Dao<Wagon> {
  @PersistenceContext private EntityManager em;

  /** {@inheritDoc} */
  @Transactional
  @Override
  public void add(Wagon wagon) {
    if (getAll().contains(wagon)) {
      throw new IllegalArgumentException("Instance of wagon already exists in the database.");
    } else {
      this.em.persist(wagon);
    }
  }

  /** {@inheritDoc} */
  @Transactional
  @Override
  public void remove(Wagon wagon) {
    Wagon managedWagon = em.contains(wagon) ? wagon : em.merge(wagon);
    em.remove(managedWagon);
  }

  /** {@inheritDoc} */
  @Transactional
  @Override
  public void update(Wagon wagon) {
    em.merge(wagon);
  }

  /** {@inheritDoc} */
  @Override
  public Iterator<Wagon> iterator() {
    TypedQuery<Wagon> query = this.em.createQuery("SELECT a FROM Wagon a", Wagon.class);
    return query.getResultList().iterator();
  }

  /** {@inheritDoc} */
  @Override
  public Optional<Wagon> find(Long id) {
    return Optional.ofNullable(em.find(Wagon.class, id));
  }

  /** {@inheritDoc} */
  @Override
  public List<Wagon> getAll() {
    return em.createQuery("SELECT a FROM Wagon a", Wagon.class).getResultList();
  }

  /**
   * Gets all unoccupied wagons.
   *
   * @return a list of all unoccupied wagons
   */
  public List<Wagon> getAllUnoccupiedWagons() {
    List<Wagon> wagonList = getAll();

    return wagonList.stream().filter(wagon -> wagon.getTrain() == null).toList();
  }

  /**
   * Merges a wagon.
   *
   * @param wagon the wagon to merge.
   * @return the wagon train.
   */
  @Transactional
  public Wagon merge(Wagon wagon) {
    return em.merge(wagon);
  }
}
