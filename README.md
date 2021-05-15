# Poker cards template-based recognition
## Following SOLID principles with pure Java

This project is quite experimental and kind of little OOP exercise for fun. It shows my vision of good OOP design.

Project have this features:
* decoupling using DI principle without any IoC container
* following SOLID principles
* loading configuration from properties
* immutable objects
* no one setter
* no one static field
* no one static method
* nested smart/utils classes in interfaces/enums for better cohesion, design, and at the same time for separation domain object contract from utils contract (instead of using static methods in separate procedure-like utility classes)

Recognition accuracy: 100%
