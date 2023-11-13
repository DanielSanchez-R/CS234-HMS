/**
 * Daniel Sanchez
 * Automation class
 * CS 234
 */
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * The Automation class provides methods to automate the creation of guests, rooms, bookings, and daily sales for testing purposes.
 */
public class Automation {

    /**
     * Automates the creation of guests, rooms, bookings, and daily sales for testing purposes.
     * @param system the SystemsIntegration object used to access the necessary lists
     */
    public static void automateBooking(SubMenus system) {
        try {
            Thread.sleep(1000);

            List<Guest> guests = SubMenus.guests;
            List<Room> rooms = SubMenus.rooms;
            List<Booking> bookings = SubMenus.bookings;
            List<Sale> dailySales = SubMenus.dailySales;

            for (int i = 0; i < 3; i++) {
                Guest guest = new Guest("AutoGuest" + (i + 1), "AutoContact" + (i + 1), "auto" + (i + 1) + "@gmail.com");
                guests.add(guest);

                Room room = new Room(i + 1, "AutoRoomType" + (i + 1), 100.0);
                rooms.add(room);

                bookRoomForGuest(guest, room, bookings, dailySales);
            }

            System.out.println("Automated guest and room creation completed.");
        } catch (InterruptedException e) {
            System.err.println("Error while waiting: " + e.getMessage());
        }
    }

    /**
     * Books a room for a guest, sets the room as occupied, calculates the total amount for the stay, creates a booking and a sale, and adds them to the respective lists.
     * @param guest the guest to book the room for
     * @param room the room to be booked
     * @param bookings the list of bookings to add the new booking to
     * @param dailySales the list of sales to add the new sale to
     */
    private static void bookRoomForGuest(Guest guest, Room room, List<Booking> bookings, List<Sale> dailySales) {
        room.setOccupied(true);

        Calendar calendar = Calendar.getInstance();
        calendar.set(11, 11);
        calendar.set(12, 0);
        Date checkInDate = calendar.getTime();

        calendar.add(Calendar.DAY_OF_MONTH, 1);
        Date checkOutDate = calendar.getTime();

        double totalAmount = calculateTotalAmount(checkInDate, checkOutDate, room.getPrice());

        Booking booking = new Booking(guest, room, checkInDate, checkOutDate);
        bookings.add(booking);

        Sale sale = new Sale(guest, room, checkInDate, checkOutDate);
        sale.setTotalAmount(totalAmount);
        dailySales.add(sale);
    }

    /**
     * Calculates the total amount for a hotel room based on the check-in date, check-out date, and room price.
     * @param checkInDate the date the guest checks in
     * @param checkOutDate the date the guest checks out
     * @param roomPrice the price per night of the hotel room
     * @return the total amount for the hotel room stay
     */
    private static double calculateTotalAmount(Date checkInDate, Date checkOutDate, double roomPrice) {
        long durationInMillis = checkOutDate.getTime() - checkInDate.getTime();
        int days = (int) (durationInMillis / (24 * 60 * 60 * 1000));
        return days * roomPrice;
    }
}