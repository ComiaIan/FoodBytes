Food Delivery System
I. Project Overview
The Food Delivery System is a Java-based application that allows users to register, log in, and place orders for food items. It provides a simple and interactive console interface for users to explore a menu, add items to their cart, and check out. This system is designed with a focus on user authentication and session management, ensuring that only registered users can access the ordering features.

The project integrates a small database of users and food items, and follows a structured approach to handle user requests, making it an ideal project to demonstrate object-oriented programming (OOP) principles in action.

II. Application of OOP Principles
This project incorporates core Object-Oriented Programming (OOP) principles, as described below:

Encapsulation: The project uses classes such as User, MenuItem, and Cart to encapsulate related data and behavior. These classes provide public methods to interact with the data, while internal data fields are kept private to ensure control over how they are accessed or modified.

Abstraction: Complex operations like user authentication, cart management, and database interactions are abstracted into dedicated classes (UserService, CartService). These abstractions make it easier to understand and manage the program’s high-level logic.

Inheritance: If there are specific types of users or menu items (like premium users or discounted items), these can inherit from base classes like User or MenuItem. Currently, the project demonstrates a foundational structure that can be extended with inheritance.

Polymorphism: Polymorphism is utilized in the project through method overloading and interface implementations (if needed). The project structure allows for future scalability, such as introducing different types of users or payment methods that follow a common interface.

III. Integration of the Sustainable Development Goal (SDG)
This project aligns with SDG 12: Responsible Consumption and Production. By implementing a food delivery system, the application encourages mindful ordering and reduces food waste. Users are encouraged to order only what they need, with features like cart management allowing them to review and adjust their orders before finalizing them.

The project can also be extended to track ordering patterns and suggest portion sizes or sustainable meal options, further supporting SDG 12. Additionally, it may integrate data on carbon footprint per order in future updates, contributing to awareness about responsible consumption.
