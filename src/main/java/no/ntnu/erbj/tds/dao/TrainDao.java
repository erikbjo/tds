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
public class TrainDao implements Dao<Train> {
  @PersistenceContext private EntityManager em;

  /** {@inheritDoc} */
  @Override
  @Transactional
  public void add(Train train) {
    if (getAll().contains(train)) {
      throw new IllegalArgumentException("Instance of train already exists in the database.");
    } else if (trainNumberIsUnique(train.getTrainNumber())) {
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
    List<Train> unoccupiedTrains = getAllUnoccupiedTrains();
    for (Train train : unoccupiedTrains) {
      TdsLogger.getInstance().info("Train Details" + " :: " + train.getId());
    }
  }

  /** Gets all unoccupied trains. */
  public List<Train> getAllUnoccupiedTrains() {
    List<Train> trainList = getAll();
    List<Long> trainIds =
        em.createQuery("SELECT a.train.id FROM Departure a", Long.class).getResultList();

    return trainList.stream().filter(train -> !trainIds.contains(train.getId())).toList();
  }

  /**
   * Checks if a train is occupied. A train is occupied if a departure exists with the train as
   * a train.
   *
   * @param trainNumberToCheck the train number of the train to check.
   * @return true if the train is valid, false otherwise.
   */
  public boolean trainIsNotOccupied(String trainNumberToCheck) {
    List<String> validIds = getAll().stream().map(Train::getTrainNumber).toList();
    List<String> occupiedTrainIds =
        em.createQuery("SELECT a FROM Departure a", Departure.class).getResultList().stream()
            .map(Departure::getTrain)
            .map(Train::getTrainNumber)
            .toList();

    return validIds.contains(trainNumberToCheck) && !occupiedTrainIds.contains(trainNumberToCheck);
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
   * Checks if a given train number is unique.
   *
   * @param trainNumber the train number to check.
   * @return true if the train number is unique, false otherwise.
   */
  public boolean trainNumberIsUnique(String trainNumber) {
    return getAll().stream().noneMatch(train -> train.getTrainNumber().equals(trainNumber));
  }
}
