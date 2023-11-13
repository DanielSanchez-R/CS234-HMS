/**
 * Daniel Sanchez
 * MainMenu class
 * CS 234
 */
import java.util.Scanner;
import java.util.*;

/**
 * This class represents the main class of the Hotel Management System.
 * It contains the main method which runs the program and displays the menu options to the user.
 * The program allows the user to perform ADD,DELETE,EDIT operations on guests, rooms, and ADD/DELETE bookings, 
 * as well as view reports of guests, rooms, and bookings.
 * The program also includes a front desk menu for guest intake and daily sales reports of the current days date.
 */
public class MainMenu {
    /**
     * The main method is the entry point of the program. It initializes the system, populates the rooms and guests with dummy data,
     * automates dummy data for reports and booking of guests to rooms, and displays the hotel management menu. 
     * The user can choose to modify check, or perform front desk operations, or exit the program.
     * @param args an array of command-line arguments for the program
     */
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            SubMenus system = new SubMenus();
            //Automate room creation for dummy data
            Room.autoPopulateRooms(SubMenus.rooms);
            //Automate guest creation for dummy data
            SubMenus.guests = (ArrayList<Guest>) Guest.autoPopulateGuests();
            // Automate dummy data for reports, booking of guest to room
            Automation.automateBooking(system);
            while (true) {
                try {
                    System.out.println("\nHotel Management SYSTEM:");
                    System.out.println("1. Manager Desk only (crud operations)");
                    System.out.println("2. Reports (guest, room, booking)");
                    System.out.println("3. Front Desk only (guest intake, daily sales report)");
                    System.out.println("4. Exit Program");
                    System.out.print("Enter choice: ");
                    int choice = scanner.nextInt();
                    scanner.nextLine();
                    switch (choice) {
                        case 1:
                            SubMenus.managerMenu(scanner);
                            break;
                        case 2:
                            SubMenus.listMenu(scanner);
                            break;
                        case 3:
                            SubMenus.frontDeskMenu(scanner);
                            break;
                        case 4:
                            scanner.close();
                            System.out.println("Exiting..");
                            System.exit(0);
                    }
                } catch (Exception e) {
                    System.out.println("An error occurred. Please try again.");
                    scanner.nextLine();
                }
            }
        }
    }
}