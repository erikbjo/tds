package no.ntnu.erbj.tds.model;

import jakarta.persistence.*; // Importing 5+ packages from jakarta.persistence, so * is used
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * This class represents a train. A train have the following attributes:
 *
 * <ul>
 *   <li>id: The id of the train
 *   <li>type: The type of the train
 *   <li>wagons: The wagons of the train
 * </ul>
 *
 * <p>The class also has getters and setters for all the attributes. The class has a builder class,
 * which is used to create a train object.
 *
 * @version 2.0
 * @see Wagon
 * @author Erik Bjørnsen
 */
@Entity
public class Train {
  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JoinColumn(name = "train_id")
  private List<Wagon> wagons;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  /*
    Det må være mulig for brukeren av applikasjonen din å oppgi dette unike tognummeret, så ikke
  la applikasjonen din automatisk generere det unike tognummeret
     */
  private String trainNumber;

  /**
   * Constructor for the Train class.
   *
   * @param wagons the wagons of the train
   * @throws IllegalArgumentException if the wagons are null
   */
  public Train(List<Wagon> wagons) {
    if (wagons == null) {
      throw new IllegalArgumentException("Wagons cannot be null");
    }
    this.wagons = wagons;
  }

  /** Overloaded constructor for the Train class. */
  public Train() {
    if (wagons == null) {
      wagons = new ArrayList<>(); // Got DB error when not checking for null
    } // Even though the field should always be null (?)
  }

  /**
   * Overloaded constructor for the Train class. Train number is added as a parameter.
   *
   * @param wagons the wagons of the train
   * @param trainNumber the train number
   * @throws IllegalArgumentException if the wagons are null, or if the train number is null, blank
   *     or empty
   */
  public Train(List<Wagon> wagons, String trainNumber) {
    if (wagons == null) {
      throw new IllegalArgumentException("Wagons cannot be null");
    }
    if (trainNumber == null) {
      throw new IllegalArgumentException("Train number cannot be null");
    }
    trainNumber = trainNumber.trim();
    if (trainNumber.isBlank() || trainNumber.isEmpty()) {
      throw new IllegalArgumentException("Train number cannot be blank or empty");
    }
    this.wagons = wagons;
    this.trainNumber = trainNumber;
  }

  /**
   * Overloaded constructor for the Train class. Train number is added as a parameter.
   *
   * @param trainNumber the train number
   * @throws IllegalArgumentException if the train number is null, blank or empty
   */
  public Train(String trainNumber) {
    if (trainNumber == null) {
      throw new IllegalArgumentException("Train number cannot be null");
    }
    trainNumber = trainNumber.trim();
    if (trainNumber.isBlank() || trainNumber.isEmpty()) {
      throw new IllegalArgumentException("Train number cannot be blank or empty");
    }
    this.trainNumber = trainNumber;
    this.wagons = new ArrayList<>();
  }

  /**
   * Gets the id of the train.
   *
   * @return the id of the trainxw
   */
  public Long getId() {
    return id;
  }

  /**
   * NOTE: This method is maybe useless, depending on the task. It is not used in the current
   * implementation. Makes a reservation in a wagon of the requested type.
   *
   * @param wagonType type of wagon to reserve seats in
   * @param numberOfSeats number of seats to reserve
   * @throws IllegalArgumentException if wagon-type is null, or if the number of seats is not
   *     positive
   * @throws NoSuchElementException if no wagon of the requested type is available, or if there are
   *     not enough available seats in the wagons of the requested type
   */
  public void makeReservation(WagonType wagonType, int numberOfSeats) {
    if (wagonType == null) {
      throw new IllegalArgumentException("Wagon type cannot be null");
    }
    if (numberOfSeats <= 0) {
      throw new IllegalArgumentException("Number of seats must be positive");
    }

    List<Wagon> filteredWagons =
        wagons.stream().filter(wagon -> wagon.getWagonType().equals(wagonType)).toList();
    if (filteredWagons.isEmpty()) {
      throw new NoSuchElementException("No wagon of the requested type is available.");
    }

    int availableSeats =
        filteredWagons.stream().mapToInt(Wagon::getOpenSeats).reduce(0, Integer::sum);

    if (availableSeats < numberOfSeats) {
      throw new NoSuchElementException(
          "Not enough available seats in the wagons of the requested type.");
    }

    for (Wagon wagon : filteredWagons) {
      if (wagon.getOpenSeats() >= numberOfSeats) {
        wagon.reserveSeats(numberOfSeats);
        return;
      } else {
        numberOfSeats -= wagon.getOpenSeats();
        wagon.reserveSeats(wagon.getOpenSeats());
      }
    }
  }

  /**
   * Adds a wagon to the train.
   *
   * @param wagon the wagon to add
   * @throws IllegalArgumentException if the wagon is null
   */
  public void addWagon(Wagon wagon) {
    if (wagon == null) {
      throw new IllegalArgumentException("Wagon cannot be null");
    }
    wagons.add(wagon);
  }

  /**
   * Removes a wagon from the train.
   *
   * @param wagon the wagon to remove
   * @throws IllegalArgumentException if the wagon is null
   */
  public void removeWagon(Wagon wagon) {
    if (wagon == null) {
      throw new IllegalArgumentException("Wagon cannot be null");
    }
    if (!wagons.contains(wagon)) {
      throw new IllegalArgumentException("Wagon is not in the train");
    }
    wagons.remove(wagon);
  }

  /**
   * Gets the wagons of the train.
   *
   * @return the wagons of the train
   */
  public List<Wagon> getWagons() {
    return wagons;
  }

  /**
   * Sets the wagons of the train.
   *
   * @param wagons the wagons of the train
   * @throws IllegalArgumentException if the wagons are null or empty
   */
  public void setWagons(List<Wagon> wagons) {
    if (wagons == null || wagons.isEmpty()) {
      throw new IllegalArgumentException("Wagons cannot be null or empty");
    }
    this.wagons = wagons;
  }

  /**
   * Gets the number of wagons of the train.
   *
   * @return the number of wagons of the train
   */
  public int getNumberOfWagons() {
    return wagons.size();
  }

  /**
   * Gets the train number of the train.
   *
   * @return the train number of the train
   */
  public String getTrainNumber() {
    return trainNumber;
  }

  /**
   * Sets the train number of the train.
   *
   * @param trainNumber the train number of the train
   * @throws IllegalArgumentException if the train number is null, blank or empty
   */
  public void setTrainNumber(String trainNumber) {
    this.trainNumber = trainNumber;
  }

  /**
   * Gets the number of seats in the train.
   *
   * @return the number of seats in the train
   */
  public int getNumberOfSeats() {
    return wagons.stream().mapToInt(Wagon::getOpenSeats).reduce(0, Integer::sum);
  }
}
