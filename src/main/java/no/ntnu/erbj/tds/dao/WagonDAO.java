package no.ntnu.erbj.tds.dao;

import jakarta.persistence.*;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import no.ntnu.erbj.tds.model.Wagon;
import no.ntnu.erbj.tds.ui.cli.utilities.TdsLogger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * This class is a Data Access Object (DAO) for the Wagon class. It provides methods for
 * accessing the database.
 *
 * @version 1.1
 * @author Erik
 */
@Repository
@Transactional
public class WagonDAO implements DAO<Wagon> {
  @PersistenceContext
  private EntityManager em;

  /** {@inheritDoc} */
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

  /** {@inheritDoc} */
  @Override
  public void remove(Wagon wagon) {
    em.getTransaction().begin();
    em.remove(em.contains(wagon) ? wagon : em.merge(wagon));
    em.getTransaction().commit();
  }

  /** {@inheritDoc} */
  @Override
  public void update(Wagon wagon) {
    em.getTransaction().begin();
    em.merge(wagon);
    em.flush();
    em.getTransaction().commit();
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

  /** {@inheritDoc} */
  @Override
  public void printAllDetails() {
    List<Wagon> wagonList = getAll();
    for (Wagon wagon : wagonList) {
      TdsLogger.getInstance()
          .info("Wagon Details" + " :: " + wagon.getId() + " :: " + wagon.getWagonType());
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
