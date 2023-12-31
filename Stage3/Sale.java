/**
 * Daniel Sanchez
 * Sale class
 * CS 234
 */
import java.util.Calendar;
import java.util.Date;

/**
 * Sale class represents a sale generated by a guest for a room in a hotel.
 * It contains information about the guest, the room, the check-in date, the check-out date, and the total amount of the sale.
 */
public class Sale {
    private final Guest guest;
    private final Room room;
    private Date checkInDate;
    private Date checkOutDate;
    private double totalAmount;

    public Sale(Guest guest, Room room, Date checkInDate, Date checkOutDate) {
        this.guest = guest;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.totalAmount = calculateTotalAmount();
    }

    /**
     * Sets the total amount of the sale.
     * @param totalAmount the total amount of the sale
     */
    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    /**
     * Calculates the total amount to be charged for the stay in the room.
     * The calculation is based on the check-in and check-out dates, the default checkout times are currently made-up.
     * the duration of stay in milliseconds, the number of days stayed, and the price of the room.
     * @return the total amount to be charged for the stay in the room
     */
    private double calculateTotalAmount() {
        // Uses a default checkout time
        Calendar defaultCheckoutTime = Calendar.getInstance();
        defaultCheckoutTime.set(Calendar.HOUR_OF_DAY, 10);
        defaultCheckoutTime.set(Calendar.MINUTE, 0);

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
     * Calculates the total amount to be paid for the stay in the room.
     * The amount is calculated by multiplying the number of days stayed by the price of the room.
     * If the duration of stay is less than one day, the minimum charge for one day is applied.
     * @return the total amount to be paid for the stay in the room
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
     * Returns a string representation of the Sale object.
     * The string contains information about the guest, room, check-in and check-out dates, and total amount.
     * @return a string representation of the Sale object
     */
    @Override
    public String toString() {
        return "Sale [Guest: " + guest.getName() + ", Room: " + room.getRoomNumber() +
                ", Check-in Date: " + checkInDate + ", Check-out Date: " + checkOutDate +
                ", Total Amount: $" + totalAmount + "]";
    }
}