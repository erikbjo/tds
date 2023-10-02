package no.ntnu.tds.model;

/**
 * This class represents a train. A train have the following attributes:
 *
 * <ul>
 *   <li>id: The id of the train
 *   <li>type: The type of the train
 *   <li>wagons: The wagons of the train
 * </ul>
 *
 * The class also has getters and setters for all the attributes. The class has a builder class,
 * which is used to create a train object.
 *
 * @version 1.0
 * @see Wagon
 * @see TrainBuilder
 * @author Erik Bjørnsen
 */
public class Train {
    private int id;
    private String type;
    private Wagon[] wagons;

}

