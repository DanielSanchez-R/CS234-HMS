//Daniel Sanchez
//CSC 240 Final Stage 4
import java.util.Objects;

/**
 * The Guest class represents a guest in a hotel.
 * It stores information such as the guest's name, room number, status, total cost, and duration of stay.
 */
public class Guest {
    protected String guestName;
    protected String roomNumber;
    protected String status;
    protected double totalCost;
    protected int duration;

    /**
     * Constructs a Guest object with the specified guest name, room number, status, total cost, and duration.
     * @param guestName the guest name
     * @param roomNumber the room number
     * @param status the status
     * @param totalCost the total cost
     * @param duration the duration
     */
    public Guest(String guestName, String roomNumber, String status, double totalCost, int duration) {
        this.guestName = guestName;
        this.roomNumber = roomNumber;
        this.status = status;
        this.totalCost = totalCost;
        this.duration = duration;
    }
    /**
     * Returns a formatted string containing the guest information.
     * @return the formatted string containing the guest information.
     */
    public String getFormattedGuestInfo() {
        return String.format("%-15s %-15s %-15s %-15.2f %-15d",
                guestName, roomNumber, status, totalCost, duration);
    }

    /**
     * Returns the name of the guest.
     * @return the name of the guest
     */
    public String getGuestName() {
        return guestName;
    }

    /**
     * Returns the room number associated with the guest.
     * @return the room number
     */
    public String getRoomNumber() {
        return roomNumber;
    }

    /**
     * Returns the status of the guest.
     * @return the status of the guest as a String
     */
    public String getStatus() {
        return status;
    }

    /**
     * Returns the total cost of the guest.
     * @return the total cost of the guest
     */
    public double getTotalCost() {
        return totalCost;
    }

    /**
     * Returns the duration of the guest's stay.
     * @return the duration of the guest's stay
     */
    public int getDuration() {
        return duration;
    }

    /**
     * Compares this Guest object with the specified object for equality.
     * Two Guest objects are considered equal if they have the same total cost, duration,
     * guest name, room number, and status.
     * @param o the object to compare with this Guest object
     * @return true if the specified object is equal to this Guest object, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Guest guest = (Guest) o;
        return Double.compare(guest.totalCost, totalCost) == 0 &&
                duration == guest.duration &&
                Objects.equals(guestName, guest.guestName) &&
                Objects.equals(roomNumber, guest.roomNumber) &&
                Objects.equals(status, guest.status);
    }

    /**
     * Returns the hash code value for this Guest object.
     * The hash code is calculated based on the guestName, roomNumber, status, totalCost, and duration properties.
     * @return the hash code value for this Guest object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(guestName, roomNumber, status, totalCost, duration);
    }

    /**
        * Returns a string representation of the Guest object.
        * @return a string containing the guest's name, room number, status, total cost, and duration
        */
    @Override
    public String toString() {
        return "Guest Name: " + guestName + ", Room Number: " + roomNumber + ", Status: " + status + ", Total Cost: " + totalCost + ", Duration: " + duration;
    }
}

