/**
 * Daniel Sanchez
 * Booking class
 * CS 234
 */
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * The Booking class represents a booking made by a guest for a room in a hotel.
 * Each booking has a unique ID, a guest, a room, a check-in date, and a check-out date.
 */
public class Booking {
    private static int nextId = 1;
    private int id;
    private Guest guest;
    private Room room;
    private Date checkInDate;
    private Date checkOutDate;


    /**
     * Represents a booking made by a guest for a room in the hotel.
     * Each booking has a unique ID, a guest, a room, a check-in date, and a check-out date.
     */
    public Booking(Guest guest, Room room, Date checkInDate, Date checkOutDate) {
        this.id = nextId++; // Generate a unique ID for each booking
        this.guest = guest;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }

    /**
     * Returns the ID of the booking.
     * @return the ID of the booking
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the guest associated with this booking.
     * @return the guest associated with this booking
     */
    public Guest getGuest() {
        return guest;
    }

    /**
     * Returns the room associated with this booking.
     * @return the room object
     */
    public Room getRoom() {
        return room;
    }

    /**
     * Returns the check-in date of the booking.
     * @return the check-in date as a Date object.
     */
    public Date getCheckInDate() {
        return checkInDate;
    }

    /**
     * Returns the check-out date of the booking.
     * @return the check-out date as a Date object
     */
    public Date getCheckOutDate() {
        return checkOutDate;
    }

    /**
     * Calculates the total amount to be charged for the booking based on the duration of stay and room price.
     * @return the total amount to be charged for the booking
     */
    public double getTotalAmount() {
        // Calculate the duration of stay in milliseconds
        long durationInMillis = checkOutDate.getTime() - checkInDate.getTime();

        // Calculate the number of days stayed
        int days = (int) Math.ceil(durationInMillis / (24 * 60 * 60 * 1000.0));

        // Ensure a minimum of one day is charged
        days = Math.max(1, days);

        double roomPrice = room.getPrice();
        return days * roomPrice;
    }

    /**
     * Returns a string representation of the booking object.
     * @return a string containing the booking ID, guest name, room, check-in date, check-out date, and total amount.
     */
    @Override
    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy");

        String formattedCheckInDate = dateFormat.format(checkInDate);
        String formattedCheckOutDate = dateFormat.format(checkOutDate);

        return "Booking ID: " + id +
                "\nGuest: " + guest.getName() +
                "\nRoom: " + room +
                "\nCheck-In Date: " + formattedCheckInDate +
                "\nCheck-Out Date: " + formattedCheckOutDate +
                "\nTotal Amount: $" + getTotalAmount() + "\n";
    }
}