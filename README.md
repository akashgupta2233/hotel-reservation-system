# Hotel Reservation System

This is a simple console-based hotel reservation system built with Java, utilizing JDBC for database connectivity. It allows users to book, view, and cancel hotel reservations.

## Features

-   **Book a Room:** Reserve a room for a specified date and duration.
-   **View Reservations:** See a list of all current reservations.
-   **Cancel Reservation:** Remove an existing reservation from the system.
-   **Search Availability:** Check for available rooms.

## Technologies Used

-   **Java:** The core programming language for the application logic.
-   **JDBC (Java Database Connectivity):** Used to connect to and interact with the SQL database.
-   **SQL:** The database language for managing reservation data.

## Setup

1.  **Clone the Repository:**
    ```bash
    git clone [https://github.com/your-username/hotel-reservation-system.git](https://github.com/your-username/hotel-reservation-system.git)
    ```
2.  **Database Setup:**
    -   Create a new database (e.g., `hoteldb`) in your SQL server (e.g., MySQL, PostgreSQL).
    -   Run the following SQL script to create the necessary table:
        ```sql
        CREATE TABLE reservations (
            id INT AUTO_INCREMENT PRIMARY KEY,
            guest_name VARCHAR(255) NOT NULL,
            room_number INT NOT NULL,
            check_in_date DATE NOT NULL,
            check_out_date DATE NOT NULL
        );
        ```
3.  **Configure Database Connection:**
    -   Update the database connection details (URL, username, password) in the `DatabaseConnection.java` file (or equivalent) to match your local setup.
4.  **Run the Application:**
    -   Compile and run the main Java class to start the application.
