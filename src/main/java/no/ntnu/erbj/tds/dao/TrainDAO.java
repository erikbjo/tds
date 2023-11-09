package no.ntnu.erbj.tds.dao;

import jakarta.persistence.*;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import no.ntnu.erbj.tds.model.Departure;
import no.ntnu.erbj.tds.model.Train;
import no.ntnu.erbj.tds.ui.utilities.TdsLogger;
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
public class TrainDAO implements DAO<Train> {
  @PersistenceContext private EntityManager em;

  /** {@inheritDoc} */
  @Override
  @Transactional
  public void add(Train train) {
    if (getAll().contains(train)) {
      throw new IllegalArgumentException("Instance of train already exists in the database.");
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

  /** {@inheritDoc} */
  @Override
  public void printAllDetails() {
    List<Train> trainList = getAll();
    for (Train train : trainList) {
      TdsLogger.getInstance().info("Train Details" + " :: " + train.getId());
    }
  }

  /** Print all unoccupied trains. */
  public void printAllUnoccupiedTrains() {
    List<Train> trainList = getAll();
    List<Train> occupiedTrains =
        em.createQuery("SELECT a FROM Departure a", Departure.class).getResultList().stream()
            .map(Departure::getTrain)
            .toList();

    for (Train train : trainList) {
      if (!occupiedTrains.contains(train)) {
        TdsLogger.getInstance().info("Train Details" + " :: " + train.getId());
      }
    }
  }

  /**
   * Checks if a train is valid.
   *
   * @param trainIdToCheck the id of the train to check.
   * @return true if the train is valid, false otherwise.
   */
  public boolean trainIsValid(Long trainIdToCheck) {
    List<Long> validIds = getAll().stream().map(Train::getId).toList();
    List<Long> occupiedTrainIds =
        em.createQuery("SELECT a FROM Departure a", Departure.class).getResultList().stream()
            .map(Departure::getTrain)
            .map(Train::getId)
            .toList();

    return validIds.contains(trainIdToCheck) && !occupiedTrainIds.contains(trainIdToCheck);
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
}
