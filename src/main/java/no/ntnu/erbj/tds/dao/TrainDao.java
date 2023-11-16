package no.ntnu.erbj.tds.dao;

import jakarta.persistence.*;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import no.ntnu.erbj.tds.model.Train;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * This class is a Data Access Object (DAO) for the Train class. It provides methods for accessing
 * the database.
 *
 * @version 1.1
 * @author Erik
 */
@Repository
public class TrainDao implements Dao<Train> {
  @PersistenceContext private EntityManager em;

  /** {@inheritDoc} */
  @Override
  @Transactional
  public void add(Train train) {
    if (getAll().contains(train)) {
      throw new IllegalArgumentException("Instance of train already exists in the database.");
    } else if (!trainNumberIsUnique(train.getTrainNumber())) {
      throw new IllegalArgumentException("Train number is not unique.");
    } else {
      this.em.persist(train);
    }
  }

  /** {@inheritDoc} */
  @Override
  @Transactional
  public void remove(Train train) {
    em.remove(em.contains(train) ? train : em.merge(train));
  }

  /** {@inheritDoc} */
  @Override
  @Transactional
  public void update(Train train) {
    em.merge(train);
  }

  /** {@inheritDoc} */
  @Override
  public Iterator<Train> iterator() {
    TypedQuery<Train> query = this.em.createQuery("SELECT a FROM Train a", Train.class);
    return query.getResultList().iterator();
  }

  /** {@inheritDoc} */
  @Override
  public Optional<Train> find(Long id) {
    return Optional.ofNullable(em.find(Train.class, id));
  }

  /** {@inheritDoc} */
  @Override
  public List<Train> getAll() {
    return em.createQuery("SELECT a FROM Train a", Train.class).getResultList();
  }

  /** Gets all unoccupied trains. */
  public List<Train> getAllUnoccupiedTrains() {
    List<Train> trainList = getAll();
    List<Long> trainIds =
        em.createQuery("SELECT a.train.id FROM Departure a", Long.class).getResultList();

    return trainList.stream().filter(train -> !trainIds.contains(train.getId())).toList();
  }

  /**
   * Checks if a train is occupied. A train is occupied if a departure exists with the train as a
   * train.
   *
   * @param trainNumberToCheck the train number of the train to check.
   * @return true if the train is valid, false otherwise.
   */
  public boolean trainIsNotOccupied(String trainNumberToCheck) {
    TypedQuery<Long> query = em.createQuery("SELECT COUNT(d) FROM Departure d WHERE d.train.trainNumber = :trainNumber", Long.class);
    query.setParameter("trainNumber", trainNumberToCheck);
    long count = query.getSingleResult();
    return count == 0;
  }

  /**
   * Merges a train.
   *
   * @param train the train to merge.
   * @return the merged train.
   */
  @Transactional
  public Train merge(Train train) {
    return em.merge(train);
  }

  /**
   * Finds a train by its train number.
   *
   * @param trainIdString the train number of the train to find.
   * @return the train if it exists, empty otherwise.
   */
  public Optional<Train> findByTrainNumber(String trainIdString) {
    return getAll().stream()
        .filter(train -> train.getTrainNumber().equals(trainIdString))
        .findFirst();
  }

  /**
   * Checks if a given train number is unique. A train number is unique if no other train has the
   * same train number.
   *
   * @param trainNumber the train number to check.
   * @return true if the train number is unique, false otherwise.
   */
  public boolean trainNumberIsUnique(String trainNumber) {
    TypedQuery<Long> query =
        em.createQuery(
            "SELECT COUNT(t) FROM Train t WHERE t.trainNumber = :trainNumber", Long.class);
    query.setParameter("trainNumber", trainNumber);
    long count = query.getSingleResult();
    return count == 0;
  }
}
