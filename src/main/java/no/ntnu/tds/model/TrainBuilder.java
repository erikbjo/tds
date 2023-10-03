package no.ntnu.tds.model;

import java.util.ArrayList;
import java.util.List;

public class TrainBuilder {
  String type;
  ArrayList<Wagon> wagons;

  public TrainBuilder setType(String type) {
    if (type == null) throw new IllegalArgumentException("Type cannot be null");

    this.type = type;
    return this;
  }

  public TrainBuilder setWagons(List<Wagon> wagons) {
    if (wagons == null) throw new IllegalArgumentException("Wagons cannot be null");

    this.wagons = (ArrayList<Wagon>) wagons;
    return this;
  }

  public Train build() {
    return new Train(this);
  }
}
