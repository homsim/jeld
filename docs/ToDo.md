# Tasks & Questions

## Technical questions

- How to store data? In-memory/embedded DB/small external DB?
  - Answer: Use [H2](https://mvnrepository.com/artifact/com.h2database/h2)
- How to represent the stacked `FinanceTimeSeries`s such that they can be easily loaded and unloaded in some graph 
  view? Do I even need a specific data-class for that? I guess it highly depends on the graph view that I use.

## Operative questions
- I will probably need some way to also include custom data: I am thinking about money that is not actually 
  accessible to the user, e.g. via some age-plan that is externally managed or whatnot...

# ToDo

- Fix below warning. [stackoverflow](https://stackoverflow.com/questions/67854139/javafx-warning-unsupported-javafx-configuration-classes-were-loaded-from-unna)
    ```java
    com.sun.javafx.application.PlatformImpl startup
    WARNUNG: Unsupported JavaFX configuration: classes were loaded from 'unnamed module @36271219'
    ```
