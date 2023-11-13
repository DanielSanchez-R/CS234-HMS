/**
 * Daniel Sanchez
 * SubMenus class
 * CS 234
 */
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * The SubMenus class contains static methods for Displaying and Handling Sub-Menus in the HMS program; a lot of submenus for the terminal system. (managerMenu, listMenu, frontDeskMenu)
 * SubMenus can add, delete, and edit guests, rooms, and add or delete bookings within the sub-menus. Bookings create a unique ID for each booking than remain as a sales reciept in booking report. 
 * I want in a future version to make the ID unqiue to the guest, so that the guest can have a list of bookings for history reports and such by guest only.
 * It also contains static ArrayLists for storing rooms, guests, bookings, and daily sales.
 * It also has a submenu for a daily sales report for the current date ie* todays date today: frontDeskMenu > daily sales report > enter date > sums total sales for a report.
 * It also has mulitple other submenus for reports for guests, rooms, and bookings.
 */
public class SubMenus {
    //protected static ArrayList<Employee> employees = new ArrayList<>();
    protected static ArrayList<Room> rooms = new ArrayList<>();
    protected static ArrayList<Guest> guests = new ArrayList<>();
    protected static ArrayList<Booking> bookings = new ArrayList<>();
    protected static ArrayList<Sale> dailySales = new ArrayList<>();

    protected SubMenus() {
        guests = new ArrayList<>();
    }
    /**
     * Displays the CRUD (Manager Desk) menu and handles the corresponding actions based on user input.
     * Allows the manager to add, delete, and edit guests, rooms, and add or delete bookings.
     * @param scanner the Scanner object used to get user input
     */
    protected static void managerMenu(Scanner scanner) {
        while (true) {
            try {
                System.out.println("\nCrud (Manager Desk) Options:");
                System.out.println("1. Add Guest");
                System.out.println("2. Add Room");
                System.out.println("3. Add Booking");
                System.out.println("4. Delete Room");
                System.out.println("5. Delete Guest");
                System.out.println("6. Delete Booking");
                System.out.println("7. Edit Guest");
                System.out.println("8. Edit Room");
                System.out.println("9. Back to Main Menu");
                System.out.print("Enter choice: ");
                int managerChoice = scanner.nextInt();
                scanner.nextLine();
                switch (managerChoice) {
                    case 1: //add guest
                        System.out.print("Enter Guest Name: ");
                        String name = scanner.nextLine();
                        System.out.print("Enter Contact Number: ");
                        String contact = scanner.nextLine();
                        System.out.print("Enter Email: ");
                        String email = scanner.nextLine();
                        guests.add(new Guest(name, contact, email));
                        break;
                    case 2: //add room
                        System.out.print("Enter Room Number: ");
                        int roomNumber = scanner.nextInt();
                        scanner.nextLine();
                        System.out.print("Enter Room Type (e.g., Single, Double, Suite): ");
                        String type = scanner.nextLine();
                        System.out.print("Enter Room Price: ");
                        double price = scanner.nextDouble();
                        rooms.add(new Room(roomNumber, type, price));
                        break;
                    case 3: // Add Booking
                        System.out.println("Select Guest (Enter index):");
                        int guestIndex;
                        for (guestIndex = 0; guestIndex < guests.size(); ++guestIndex) {
                            System.out.println("" + guestIndex + ": " + guests.get(guestIndex));
                        }
                        guestIndex = scanner.nextInt();
                        System.out.println("Select Room (Enter index):");
                        int roomIndex;
                        for (roomIndex = 0; roomIndex < rooms.size(); ++roomIndex) {
                            System.out.println("" + roomIndex + ": " + rooms.get(roomIndex));
                        }
                        roomIndex = scanner.nextInt();
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(11, 11);
                        calendar.set(12, 0);
                        Date checkInDate = calendar.getTime();

                        System.out.print("Enter Check-out date (yyyy-MM-dd): ");
                        String checkOutStr = scanner.next();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

                        try {
                            Date checkOutDate = sdf.parse(checkOutStr);

                            // Calculate the total amount for the booking
                            double totalAmount = calculateTotalAmount(checkInDate, checkOutDate, rooms.get(roomIndex).getPrice());
                            Sale sale = new Sale(guests.get(guestIndex), rooms.get(roomIndex), checkInDate, checkOutDate);
                            sale.setTotalAmount(totalAmount);

                            // Add the sale to the daily sales
                            dailySales.add(sale);

                            // Add the booking
                            bookings.add(new Booking(guests.get(guestIndex), rooms.get(roomIndex), checkInDate, checkOutDate));
                            rooms.get(roomIndex).setOccupied(true);

                            System.out.println("Booking created successfully.");
                            System.out.println("Total Amount: $" + totalAmount);
                        } catch (Exception var27) {
                            System.out.println("Invalid date format.");
                        }
                        break;
                    case 4: // Delete Room
                        System.out.println("List of Rooms to Delete:");
                        for (int i = 0; i < rooms.size(); i++) {
                            System.out.println(i + ": " + rooms.get(i));
                        }
                        System.out.println("Enter the index of the Room to delete: ");
                        int roomIndexToDelete = scanner.nextInt();
                        scanner.nextLine();
                        if (roomIndexToDelete >= 0 && roomIndexToDelete < rooms.size()) {
                            Room deletedRoom = rooms.remove(roomIndexToDelete);
                            System.out.println("Room at index " + roomIndexToDelete + " with number " + deletedRoom.getRoomNumber() + " deleted.");
                        } else {
                            System.out.println("Invalid index. No room deleted.");
                        }
                        break;
                    case 5: // Delete Guest
                        System.out.println("List of Guests to Delete:");
                        for (int i = 0; i < guests.size(); i++) {
                            System.out.println(i + ": " + guests.get(i));
                        }
                        System.out.println("Enter the index of the Guest to delete: ");
                        int guestIndexToDelete = scanner.nextInt();
                        scanner.nextLine();
                        if (guestIndexToDelete >= 0 && guestIndexToDelete < guests.size()) {
                            Guest deletedGuest = guests.remove(guestIndexToDelete);
                            System.out.println("Guest at index " + guestIndexToDelete + " with name " + deletedGuest.getName() + " deleted.");
                        } else {
                            System.out.println("Invalid index. No guest deleted.");
                        }
                        break;
                    case 6: // Delete Booking
                        System.out.println("List of Bookings to Delete:");
                        for (int i = 0; i < bookings.size(); i++) {
                            System.out.println(i + ": " + bookings.get(i));
                        }
                        System.out.println("Enter the index of the Booking to delete: ");
                        int bookingIndexToDelete = scanner.nextInt();
                        scanner.nextLine();
                        if (bookingIndexToDelete >= 0 && bookingIndexToDelete < bookings.size()) {
                            Booking deletedBooking = bookings.remove(bookingIndexToDelete);
                            System.out.println("Booking at index " + bookingIndexToDelete + " with ID " + deletedBooking.getId() + " deleted.");
                            Room room = deletedBooking.getRoom();
                            boolean isRoomOccupied = false;
                            for (Booking booking : bookings) {
                                if (booking.getRoom().equals(room)) {
                                    isRoomOccupied = true;
                                    break;
                                }
                            }
                            room.setOccupied(isRoomOccupied);
                        } else {
                            System.out.println("Invalid index. No booking deleted.");
                        }
                        break;
                    case 7:
                        // Edit Guest
                        System.out.println("List of Guests to Edit:");
                        for (int i = 0; i < guests.size(); i++) {
                            System.out.println(i + ": " + guests.get(i));
                        }
                        System.out.print("Select Guest to Edit (Enter index): ");
                        int guestIndexToEdit = -1;
                        while (guestIndexToEdit == -1) {
                            try {
                                guestIndexToEdit = scanner.nextInt();
                                scanner.nextLine(); // consume the newline character
                                if (guestIndexToEdit < 0 || guestIndexToEdit >= guests.size()) {
                                    System.out.println("Invalid index. Please enter a valid index.");
                                    guestIndexToEdit = -1; // reset to -1 to repeat the loop
                                }
                            } catch (InputMismatchException e) {
                                System.out.println("Invalid input. Please enter a valid integer index.");
                                scanner.nextLine(); // consume the invalid input
                            }
                        }
                        // Check if the selected index is valid
                        if (guestIndexToEdit >= 0 && guestIndexToEdit < guests.size()) {
                            // Get the guest object to edit
                            Guest guestToEdit = guests.get(guestIndexToEdit);
                            // Display current guest information
                            System.out.println("Editing Guest at index " + guestIndexToEdit + ": " + guestToEdit);
                            // Ask for new information
                            System.out.println("Enter new information for the Guest:");
                            System.out.print("Enter new name: ");
                            String newName = scanner.nextLine();
                            System.out.print("Enter new contact number: ");
                            String newContactNumber = scanner.nextLine();
                            System.out.print("Enter new email: ");
                            String newEmail = scanner.nextLine();
                            // Update the guest object with new information
                            guestToEdit.setName(newName);
                            guestToEdit.setContactNumber(newContactNumber);
                            guestToEdit.setEmail(newEmail);

                            System.out.println("Guest information updated successfully.");
                        } else {
                            System.out.println("Invalid index. No changes made.");
                        }
                        break;
                    case 8:
                        // Edit Room
                        System.out.println("List of Rooms to Edit:");
                        for (int i = 0; i < rooms.size(); i++) {
                            System.out.println(i + ": " + rooms.get(i));
                        }
                        System.out.print("Select Room to Edit (Enter index): ");
                        int roomIndexToEdit = -1;
                        while (roomIndexToEdit == -1) {
                            try {
                                roomIndexToEdit = scanner.nextInt();
                                scanner.nextLine(); // consume the newline character
                                if (roomIndexToEdit < 0 || roomIndexToEdit >= rooms.size()) {
                                    System.out.println("Invalid index. Please enter a valid index.");
                                    roomIndexToEdit = -1; // reset to -1 to repeat the loop
                                }
                            } catch (InputMismatchException e) {
                                System.out.println("Invalid input. Please enter a valid integer index.");
                                scanner.nextLine(); // consume the invalid input
                            }
                        }
                        // Check if the selected index is valid
                        if (roomIndexToEdit >= 0 && roomIndexToEdit < rooms.size()) {
                            // Get the room object to edit
                            Room roomToEdit = rooms.get(roomIndexToEdit);
                            // Display current room information
                            System.out.println("Editing Room at index " + roomIndexToEdit + ": " + roomToEdit);
                            // Ask for new information
                            System.out.println("Enter new information for the Room:");
                            System.out.print("Enter new room number: ");
                            int newRoomNumber = scanner.nextInt();
                            scanner.nextLine(); // consume the newline character
                            System.out.print("Enter new room type: ");
                            String newRoomType = scanner.nextLine();
                            System.out.print("Enter new room rate: ");
                            double newRoomRate = scanner.nextDouble();
                            // Update the room object with new information
                            roomToEdit.setRoomNumber(newRoomNumber);
                            roomToEdit.setType(newRoomType);
                            roomToEdit.setPrice(newRoomRate);
                            // Update the list of rooms with the modified room
                            rooms.set(roomIndexToEdit, roomToEdit);

                            System.out.println("Room information updated successfully.");
                        } else {
                            System.out.println("Invalid index. No changes made.");
                        }
                        break;
                    case 9: // back to menu
                        return;
                }
            } catch (Exception var28) {
                System.out.println("An error occurred. Please try again.");
                scanner.nextLine();
            }
        }
    }
    /**
     * Displays a menu with options to report guests, rooms, bookings or return to the main menu.
     * @param scanner a Scanner object to read user input
     */
    protected static void listMenu(Scanner scanner) {
        while (true) {
            try {
                System.out.println("\nReport Options:");
                System.out.println("1. Report Guests");
                System.out.println("2. Report Rooms");
                System.out.println("3. Report Bookings");
                System.out.println("4. Back to Main Menu");
                System.out.print("Enter choice: ");
                int listChoice = scanner.nextInt();
                scanner.nextLine();
                switch (listChoice) {
                    case 1: // Report Guests
                        System.out.println("Report of Guests:");

                        for (Guest guest : guests) {
                            System.out.println(guest);
                        }
                        break;
                    case 2: // Report Rooms
                        System.out.println("Report of Rooms:");

                        for (Room room : rooms) {
                            System.out.println(room);
                        }
                        break;
                    case 3: // Report Bookings
                        System.out.println("Report of Bookings:");

                        for (Booking booking : bookings) {
                            System.out.println("Booking ID: " + booking.getId());
                            System.out.println("Guest: " + booking.getGuest());
                            System.out.println("Room: " + booking.getRoom());
                            System.out.println("Check-In Date: " + booking.getCheckInDate());
                            System.out.println("Check-Out Date: " + booking.getCheckOutDate());
                            System.out.println();
                        }
                        break;
                    case 4: // back to menu
                        return;
                }
            } catch (Exception var4) {
                System.out.println("An error occurred. Please try again.");
                scanner.nextLine();
            }
        }
    }
    /**
     * Displays the front desk menu and handles user input for creating a booking or generating a daily sales report.
     * @param scanner the Scanner object used to read user input
     */
    protected static void frontDeskMenu(Scanner scanner) {
        while (true) {
            try {
                System.out.println("\nFront Desk Options:");
                System.out.println("1. Create Booking at front desk");
                System.out.println("2. Daily Sales Report");
                System.out.println("3. Back to Main Menu");
                System.out.print("Enter choice: ");
                int frontDeskChoice = scanner.nextInt();
                scanner.nextLine();
                switch (frontDeskChoice) {
                    case 1: // Create Booking
                        System.out.print("Enter Guest Name: ");
                        String guestName = scanner.nextLine();
                        System.out.print("Enter Contact Number: ");
                        String guestContact = scanner.nextLine();
                        System.out.print("Enter Email: ");
                        String guestEmail = scanner.nextLine();
                        guests.add(new Guest(guestName, guestContact, guestEmail));
                        System.out.println("Select Room (Enter index):");
                        int roomIndex;
                        for (roomIndex = 0; roomIndex < rooms.size(); ++roomIndex) {
                            System.out.println("" + roomIndex + ": " + rooms.get(roomIndex));
                        }
                        roomIndex = scanner.nextInt();
                        scanner.nextLine();
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(11, 11);
                        calendar.set(12, 0);
                        Date checkInDate = calendar.getTime();
                        System.out.print("Enter Check-out date (yyyy-MM-dd): ");
                        String checkOutStr = scanner.next();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        try {
                            Date checkOutDate = sdf.parse(checkOutStr);
                            // Create a booking with the newly created guest and selected room
                            Booking booking = new Booking(new Guest(guestName, guestContact, guestEmail), rooms.get(roomIndex), checkInDate, checkOutDate);
                            bookings.add(booking);
                            rooms.get(roomIndex).setOccupied(true);
                            // Calculate and display the total amount using the Sale class
                            Sale sale = new Sale(booking.getGuest(), booking.getRoom(), checkInDate, checkOutDate);
                            System.out.println("Booking created successfully.");
                            System.out.println("Total Amount: $" + sale.getTotalAmount());

                        } catch (Exception var12) {
                            System.out.println("Invalid date format. Booking not created.");
                        }
                        break;

                    case 2: // Daily Sales Report
                        dailySalesReport(bookings);
                    case 3: // back to menu
                        return;
                }
            } catch (Exception var13) {
                System.out.println("An error occurred. Please try again.");
                scanner.nextLine();
            }
        }
    }
    /**
     * Generates a daily sales report for a given date based on a list of bookings.
     * @param bookings the list of bookings to generate the report from
     */
    private static void dailySalesReport(List<Booking> bookings) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter todays current date for the daily sales report in the format (yyyy-MM-dd): ");
        String dateStr = scanner.nextLine();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date targetDate = sdf.parse(dateStr);

            double totalSales = 0.0;
            for (Booking booking : bookings) {
                if (isSameDay(booking.getCheckInDate(), targetDate)) {
                    double roomPrice = booking.getRoom().getPrice();
                    totalSales += calculateTotalAmount(booking.getCheckInDate(), booking.getCheckOutDate(), roomPrice);
                }
            }
            System.out.println("Daily Sales Report for " + dateStr + ":");
            System.out.println("Total Sales: $" + totalSales);
        } catch (Exception e) {
            System.out.println("Invalid date format.");
        }
    }
    /**
     * Determines if two dates are the same day.
     * @param date1 the first date to compare
     * @param date2 the second date to compare
     * @return true if the two dates are the same day, false otherwise
     */
    private static boolean isSameDay(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH) &&
                cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH);
    }
    /**
     * Calculates the total amount to be charged for a stay based on the check-in and check-out dates and the room price.
     * @param checkInDate the date of check-in
     * @param checkOutDate the date of check-out
     * @param roomPrice the price of the room per day
     * @return the total amount to be charged for the stay
     */
    private static double calculateTotalAmount(Date checkInDate, Date checkOutDate, double roomPrice) {
        // Calculate the duration of stay in milliseconds
        long durationInMillis = checkOutDate.getTime() - checkInDate.getTime();
        // Calculate the number of days stayed
        int days = (int) Math.ceil(durationInMillis / (24 * 60 * 60 * 1000.0));
        // Ensure a minimum of one day is charged
        days = Math.max(1, days);
        return days * roomPrice;
    }
}