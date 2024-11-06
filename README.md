# 🍔 FoodBytes
## **I. Project Overview**
FoodBytes is a Java console application allowing users to register, log in, and place food orders. Users can explore restaurant menus, add items to a cart, and complete the checkout process. The application includes user authentication and a small database of users, food items, and orders.

This project showcases essential Object-Oriented Programming (OOP) principles in Java and demonstrates a simple yet scalable structure for future expansions.

## **II. Application of OOP Principles**
This project showcases core OOP principles as follows:

### <ins>**Encapsulation:** </ins> 

- **User Class:** This class encapsulates the user-specific data, such as username and password, and provides methods like the login() and register() methods. The attributes used are private, which means they can't be accessed directly from outside the class.
- **Database Class:** This class encapsulates the connection logic for the database (MySQL). It provided a getConnection() method, which included the details of establishing a database connection. This method makes the connection of the code to the database modular and reusable across different parts of the code.
- **Cart Class:** This class hold items and methods to manipulate the user's cart, these methods are addItem(), getQuantity(), removeItem() and getTotalPrice(). This encapsulation ensures the cart's logic is contained, which simplifies the management and maintenance of the cart-related functionalities.

### <ins>**Abstraction:**</ins> 
- **Database Class Abstraction:** By creating a Database class with a getConnection() method, the details of how a connection is established (JDBC URL, driver) are hidden from the rest of the application. Other classes can simply call Database.getConnection() without needing to know the specifics of how the connection is configured.
- **Main Class with User Interaction:** The Main class uses abstraction by interacting with the user through high-level methods like authenticateUser(), addToCart(), and checkout(). These methods encapsulate multiple steps, such as scanning inputs, handling SQL exceptions, and interacting with other objects.

### <ins>**Inheritance:**</ins> 
- **User Subclasses:** If the system were to support different types of users (e.g., Customer, Admin, or RestaurantOwner), each could inherit from a base User class. The base User class contains shared attributes like username and password, while subclasses define specific behaviors. An Admin class might have additional permissions, while a Customer class could have a viewCart() method.
- **FoodItem Subclasses:** Similarly, if there were different types of food items (e.g., Beverage, MainCourse, Dessert), each could inherit from a base FoodItem class and add unique fields or methods specific to that type (e.g., Beverage might have an attribute for size).

**NOTE:** Although inheritance is not explicitly used in the code, designing the classes with a possible hierarchy would improve flexibility.

### <ins>**Polymorphism:**</ins> 
- **Overloading Methods:** There could be multiple versions of the addToCart() method, one that takes an itemId and quantity, and another that takes an Item object.
- **Dynamic Method Dispatch:** If a user type hierarchy were to be implemented, polymorphism allows the autenthicateUser() method to return different user types based on credentials. A method like viewCat(), restaurantOwnerPanel() or accessAdminPanel() could behave differently depending whether the user's type is a Customer, restaurantOwner or Admin.

**NOTE:** While direct polymorphism isn't heavily used in the code, it could be incorporated as the system grows to support different user types and different item types.

### How These OOP Principles Benefit the Project
- **Readability:** Encapsulation and abstraction keeps the code modular and easier to udnerstand.
- **Reusability:** Inheritance and polymorphism promote code reuse, making the project easier to extend.
- **Maintainability:** Encapsulation allows for modification of one class without affecting others, which simplifies the maintenance of the code.
- **Scalability:** Abstraction and inheritance supports future enhancements, this allows for new features to be added easily. 

## III. Integration of the Sustainable Development Goal (SDG)
The project aligns with <ins>**SDG 12: Responsible Consumption and Production**</ins> by encouraging mindful food ordering to reduce waste. Users are able to review their cart and order only what they need. Future updates can include tracking ordering patterns or suggesting sustainable meal options, further contributing to responsible consumption practices.
