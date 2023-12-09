# Train Dispatch System 🚂

![Java shield](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![Maven shield](https://img.shields.io/badge/Apache_Maven-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white)
![Spring boot shield](https://img.shields.io/badge/Spring_Boot-F2F4F9?style=for-the-badge&logo=spring-boot)
![IDE](https://img.shields.io/badge/IntelliJ_IDEA-000000.svg?style=for-the-badge&logo=intellij-idea&logoColor=white)

STUDENT NAME = Erik Bjørnsen  
STUDENT ID = erbj

## Project description 📝

<p>
Train Dispatch System is a Java application made to manage train departures.
The application is made with Spring Boot and Maven, and uses Derby as a database.
The user-interface is made with Spring Shell.
Exam project for the course IDATG1003 at NTNU Gjøvik.
</p>

## How to run the project 🚀

1. Clone the repository with:
```bash
git clone git@github.com:NTNU-BIDATA-IDATG1003-2023/mappe-idatg1003-traindispatchsystem-erikbjo.git
```

2. Run the program with:
```bash
./mvnw clean spring-boot:run
```

or

1. Download the jar file from git releases
2. Run the program with:

```bash
java -jar tds-3.jar
```

## How to run the tests 🧪

1. Clone the repository
2. Run the tests with:

```bash
./mvnw clean test
```

## How to use the program 🖥

When the program is running, you can use the following command to get help:
```tds
help
```

## Contact 📧

If you have any questions, you can contact me [here](mailto:bjornsen.erik@gmail.com)

## Project structure 📁

```
root
├── src
│   ├── main
│   │   ├── java
│   │   │   └── no
│   │   │       └── ntnu
│   │   │           └── erbj
│   │   │               └── tds
│   │   │                   ├── dao
│   │   │                   │   ├──Dao.java
│   │   │                   │   ├──DepartureDao.java
│   │   │                   │   ├──StationDao.java
│   │   │                   │   ├──TrainDao.java
│   │   │                   │   └──WagonDao.java
│   │   │                   │
│   │   │                   ├── model
│   │   │                   │   ├── departures
│   │   │                   │   │   ├── Departure.java
│   │   │                   │   │   └── DepartureBuilder.java
│   │   │                   │   │
│   │   │                   │   ├── Station.java
│   │   │                   │   ├── Train.java
│   │   │                   │   ├── Wagon.java
│   │   │                   │   └── WagonType.java
│   │   │                   │
│   │   │                   ├── shared
│   │   │                   │   └── utilites
│   │   │                   │       ├── StringValidator.java
│   │   │                   │       └── TimeParser.java
│   │   │                   │
│   │   │                   ├── ui
│   │   │                   │   ├── commands
│   │   │                   │   │   ├── CreateCommands.java
│   │   │                   │   │   ├── DepartureCommands.java
│   │   │                   │   │   ├── HelperCommands.java
│   │   │                   │   │   ├── TimeCommands.java
│   │   │                   │   │   ├── TrainCommands.java
│   │   │                   │   │   └── WagonCommands.java
│   │   │                   │   │
│   │   │                   │   ├── controllers
│   │   │                   │   │   └── TimeController.java
│   │   │                   │   │
│   │   │                   │   └── utilites
│   │   │                   │       ├── AnsiColors.java
│   │   │                   │       ├── Colorize.java
│   │   │                   │       ├── Printer.java
│   │   │                   │       ├── SortUtility.java
│   │   │                   │       ├── TablePrinter.java
│   │   │                   │       └── TdsLogger.java
│   │   │                   │
│   │   │                   └── TdsApplication.java (main class)
│   │   └── resources
│   │       ├── logback.xml
│   │       └── application.properties
│   │
│   └── test
│       └── java
│           └── no
│               └── ntnu
│                   └── erbj   
│                       └── tds
│                           └── model
│                               ├── DepartureBuilderTest.java
│                               ├── DepartureTest.java
│                               ├── StationTest.java
│                               ├── TrainTest.java
│                               └── WagonTest.java
├── .gitignore
├── LICENSE
├── mvnw
├── mvnw.cmd
├── pom.xml
└── README.md
```

## Troubleshooting 🛠

If you get any persistence errors, try to delete the tdsDB directory and run the program again.

## Link to repository 🌐

[GitHub Classroom](https://github.com/NTNU-BIDATA-IDATG1003-2023/mappe-idatg1003-traindispatchsystem-erikbjo)
