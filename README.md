# Train Dispatch System 🚂

[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-24ddc0f5d75046c5622901739e7c5dd533143b0c8e959d652212380cedb1ea36.svg)](https://classroom.github.com/a/HVrmLnmo)

STUDENT NAME = Erik Bjørnsen  
STUDENT ID = erbj

## Project description

Train Dispatch System is a system for dispatching trains. It only shows the trains connected with Oslo S.

## Project structure
```
root
├── src
│   ├── main
│   │   ├── java
│   │   │   └── no
│   │   │       └── ntnu
│   │   │           └── tds
│   │   │               ├── dao
│   │   │               │   ├──Dao.java
│   │   │               │   ├──DepartureDao.java
│   │   │               │   ├──TrainDao.java
│   │   │               │   └──WagonDao.java
│   │   │               ├──ui
│   │   │               │   ├── cli
│   │   │               │   │   ├── utilities
│   │   │               │   │   │   └── TdsLogger.java
│   │   │               │   │   ├── placeholder
│   │   │               │   │   └── placeholder
│   │   │               │   └── gui
│   │   │               │       ├── controllers
│   │   │               │       │   ├── Controller.java
│   │   │               │       │   └──  MainMenuController.java
│   │   │               │       ├── view
│   │   │               │       │   ├── View.java
│   │   │               │       │   └──  MainMenuView.java
│   │   │               │       └── utilities
│   │   │               │           └── empty
│   │   │               ├── model
│   │   │               │   ├── Departure.java
│   │   │               │   ├── Train.java
│   │   │               │   ├── TrainBuilder.java
│   │   │               │   ├── WagonType.java
│   │   │               │   └── Wagon.java
│   │   │               └── TdsApplication.java
│   │   └── resources
│   │       ├── logback.xml
│   │       └── application.properties
│   └── test
│       └── java
│           └── no
│               └── ntnu
│                   └── tds
│                       └── model
│                           ├── DepartureTest.java
│                           ├── TrainTest.java
│                           └── WagonTest.java
├── pom.xml
├── README.md
├── LICENSE
├── mvnw
├── mvnw.cmd
├── HELP.md (to be removed)
└── .gitignore
```

## Link to repository

TODO
>>>>>>> 9747026 (init commit, added cli and gui solution)

## How to run the project

[//]: # (TODO: Describe how to run your project here. What is the main class? What is the main method?
What is the input and output of the program? What is the expected behaviour of the program?)
<<<<<<< HEAD

## How to run the tests

[//]: # (TODO: Describe how to run the tests here.)

## References

[//]: # (TODO: Include references here, if any. For example, if you have used code from the course book, include a reference to the chapter.
Or if you have used code from a website or other source, include a link to the source.)
=======
1. Clone the repository
2. Compile the program with:
```bash

```
3. Run the program with:
```bash
java -jar TrainDispatchSystem.jar
```

or

1. Download the executable file
2. Run the program

## How to run the tests

1. Clone the repository
2. Run the tests with:
```bash
mvn test
```

## References
