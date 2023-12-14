//Daniel Sanchez
//CSC 240 Final Stage 4
import java.util.Objects;

/**
 * The Room class represents a room in a hotel.
 * It contains information about the room number, room type, room cost, daily cost, and total cost.
 */
public class Room {
    protected String roomNumber;
    protected String roomType;
    protected double roomCost; // cost per night
    protected double dailyCost; // this is actually duration of stay
    protected double totalCost; // total cost of stay

    /**
     * Constructs a Room object with the specified room number, room type, room cost, daily cost, and total cost.
     * @param roomNumber the room number
     * @param roomType the room type
     * @param roomCost the room cost
     * @param dailyCost the daily cost
     * @param totalCost the total cost
     */
    public Room(String roomNumber, String roomType, double roomCost, double dailyCost, double totalCost) {
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.roomCost = roomCost; // cost per night
        this.dailyCost = dailyCost; // this is actually duration of stay
        this.totalCost = roomCost * dailyCost; // total cost of stay

    }
    /**
     * Returns the room number.
     * @return the room number
     */
    public String getRoomNumber() {
        return roomNumber;
    }

    /**
     * Returns the cost of the room.
     * @return the cost of the room
     */
    public double getRoomCost() {
        return roomCost;
    }

    /**
     * Returns the room type.
     * @return the room type as a String.
     */
    public String getRoomType() {
        return this.roomType;
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     * The equality is determined based on the room number, room type, and room cost.
     * @param o the reference object with which to compare
     * @return true if this object is the same as the o argument; false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return Double.compare(room.roomCost, roomCost) == 0 &&
                Objects.equals(roomNumber, room.roomNumber) &&
                Objects.equals(roomType, room.roomType);
    }

    /**
     * Returns the hash code value for this Room object.
     * The hash code is calculated based on the roomNumber, roomType, and roomCost properties.
     * @return the hash code value for this Room object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(roomNumber, roomType, roomCost);
    }

    /**
        * Returns a string representation of the Room object.
        * @return a string representation of the Room object
        */
    @Override
    public String toString() {
        return "Room{" +
                "roomNumber='" + roomNumber + '\'' +
                ", roomType='" + roomType + '\'' +
                ", roomCost=" + roomCost +
                '}';
    }
}