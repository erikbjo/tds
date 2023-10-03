package no.ntnu.tds.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Departure {
  private final LocalTime departureTime;
  private final String line;
  private final String destination;
  private final int track;
  private final LocalTime delay;
  private final Train train;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  public Departure(DepartureBuilder builder) {
    this.departureTime = builder.getDepartureTime();
    this.line = builder.getLine();
    this.destination = builder.getDestination();
    this.track = builder.getTrack();
    this.delay = builder.getDelay();
    this.train = builder.getTrain();
  }
}
