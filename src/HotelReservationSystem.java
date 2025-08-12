import java.sql.*;
import java.util.Scanner;

public class HotelReservationSystem {

    private static final String url="jdbc:mysql://localhost:3306/hotel_db";
    private static final String username="root";
    private static final String password="2233Akash@";

    public static void main(String[] args) {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch (ClassNotFoundException e){
            System.out.println(e.getMessage());
        }
        Scanner scanner = new Scanner(System.in);
        try{
            Connection connection = DriverManager.getConnection(url, username ,password);
            while(true){
                System.out.println("HOTEL MANAGEMENT SYSTEM");
                StringBuilder options = new StringBuilder("1. Reserve a room\n");
                options.append("2. View Reservations\n");
                options.append("3. Get Room Number\n");
                options.append("4. Update Reservations\n");
                options.append("5. Delete Reservations\n");
                options.append("0. Exit");
                System.out.println(options);
                System.out.print("Choose an option: ");
                int choice = scanner.nextInt();

                switch(choice){
                    case 1:
                        reserverRoom(connection, scanner);
                        break;
                    case 2:
                        viewReservations(connection, scanner);
                        break;
                    case 3:
                        getRoomNumber(connection, scanner);
                        break;
                    case 4:
                        updateReservation(connection, scanner);
                        break;
                    case 5:
                        deleteReservation(connection, scanner);
                        break;
                    case 0:
                        exit();
                        return;
                    default:
                        System.out.println("Choose from given options");
                }
            }

        }catch(SQLException | InterruptedException e){
            System.out.println(e.getMessage());
        }
    }

    private static void reserverRoom(Connection connection, Scanner scanner){
        System.out.print("Enter guest name: ");
        String name = scanner.next();
        scanner.nextLine();
        System.out.print("Enter room number: ");
        int roomNumber = scanner.nextInt();
        System.out.print("Enter contact number: ");
        String contactNumber = scanner.next();

        String reserveQuery = "INSERT INTO reservations (guest_name, room_number, contact_number) VALUES(?, ?, ?)";
        try(PreparedStatement reservePreparedStatement = connection.prepareStatement(reserveQuery)){
            reservePreparedStatement.setString(1, name);
            reservePreparedStatement.setInt(2, roomNumber);
            reservePreparedStatement.setString(3, contactNumber);
            int rowsAffected = reservePreparedStatement.executeUpdate();
            if(rowsAffected>0){
                System.out.println("Reservation Successful!");
            }else{
                System.out.println("Reservation Failed!");
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    private static void viewReservations(Connection connection, Scanner scanner){
        String viewQuery = "SELECT * FROM reservations";
        try(PreparedStatement viewPreparedStatement = connection.prepareStatement(viewQuery)){
            ResultSet resultSet = viewPreparedStatement.executeQuery();
            System.out.println("+----------------+-----------------+---------------+----------------------+-------------------------+");
            System.out.println("| Reservation ID | Guest           | Room Number   | Contact Number      | Reservation Date        |");
            System.out.println("+----------------+-----------------+---------------+----------------------+-------------------------+");

            while(resultSet.next()){
                int id = resultSet.getInt("reservation_id");
                String name = resultSet.getString("guest_name");
                String contactNumber = resultSet.getString("contact_number");
                int roomNumber = resultSet.getInt("room_number");
                String reservationDate = resultSet.getTimestamp("reservation_date").toString();
                System.out.printf("| %-14d | %-15s | %-13d | %-20s | %-19s   |\n",
                        id, name, roomNumber, contactNumber, reservationDate);
            }
            System.out.println("+----------------+-----------------+---------------+----------------------+-------------------------+");
            System.out.println("\nPress enter to continue...");
            scanner.nextLine();
            scanner.nextLine();
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
    private static void getRoomNumber(Connection connection, Scanner scanner){
        System.out.print("Enter reservation ID: ");
        int id = scanner.nextInt();
        System.out.print("Enter guest name: ");
        String name = scanner.next();

        String roomQuery = "Select room_number FROM reservations WHERE reservation_id=? AND guest_name=?";
        try(PreparedStatement roomPreparedStatement = connection.prepareStatement(roomQuery)){
            roomPreparedStatement.setInt(1, id);
            roomPreparedStatement.setString(2, name);
            ResultSet resultSet = roomPreparedStatement.executeQuery();

            if(resultSet.next()){
                int roomNumber = resultSet.getInt("room_number");
                System.out.println("Room number for Reservation ID " + id + " and Guest Name '" + name + "' is: " + roomNumber);
            }else{
                System.out.println("Reservation not found for given id and guest name");
            }
            System.out.println("\nPress enter to continue...");
            scanner.nextLine();
            scanner.nextLine();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    private static void updateReservation(Connection connection, Scanner scanner){
        System.out.print("Enter reservation id: ");
        int id = scanner.nextInt();
        System.out.print("Enter updated guest name: ");
        String name = scanner.next();
        scanner.nextLine();
        System.out.print("Enter updated room number:  ");
        int roomNumber = scanner.nextInt();
        System.out.print("Enter updates contact number: ");
        String contactNumber = scanner.next();

        String updateQuery = "UPDATE reservations SET guest_name=?, room_number=?, contact_number=? WHERE reservation_id=?";
        try(PreparedStatement updatePreparedStatement = connection.prepareStatement(updateQuery)){
            updatePreparedStatement.setString(1, name);
            updatePreparedStatement.setInt(2, roomNumber);
            updatePreparedStatement.setString(3, contactNumber);
            updatePreparedStatement.setInt(4, id);
            int affectedRows= updatePreparedStatement.executeUpdate();
            if(affectedRows>0){
                System.out.println("Successfully updated reservation!");
            }else{
                System.out.println("Failed to update reservation");
            }
            System.out.println("\nPress enter to continue...");
            scanner.nextLine();
            scanner.nextLine();
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
    private static void deleteReservation(Connection connection, Scanner scanner){
        System.out.print("Enter reservation id: ");
        int id = scanner.nextInt();

        String deleteQuery = "DELETE FROM reservations WHERE reservation_id=?";
        try(PreparedStatement delPreparedStatement = connection.prepareStatement(deleteQuery)){
            delPreparedStatement.setInt(1, id);
            int rowsAffected = delPreparedStatement.executeUpdate();
            if(rowsAffected>0){
                System.out.println("Reservation Deleted Successfully!");
            }else{
                System.out.println("Failed to delete reservation");
            }
            System.out.println("\nPress enter to continue...");
            scanner.nextLine();
            scanner.nextLine();
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
    private static void exit() throws InterruptedException{
        System.out.println("Exiting System");
        int i=5;
        while(i>0){
            System.out.print(" .");
            Thread.sleep(450);
            i--;
        }
        System.out.println();
        System.out.println("ThankYou for using Hotel Reservation System!!!");
    }
}