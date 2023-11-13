/**
 * Daniel Sanchez
 * Room class
 * CS 234
 */
import java.util.List;

/**
 * The Room class represents a hotel room with a room number, type, price, and occupancy status.
 */
public class Room {
    private int roomNumber;
    private String type;
    private double price;
    private boolean isOccupied;

    public Room(int roomNumber, String type, double price) {
        this.roomNumber = roomNumber;
        this.type = type;
        this.price = price;
        this.isOccupied = false;
    }

    /**
     * Returns the room number of this Room object.
     * @return the room number of this Room object
     */
    public int getRoomNumber() {
        return roomNumber;
    }

    /**
     * Returns the price of the room.
     * @return the price of the room
     */
    public double getPrice() {
        return price;
    }

    /**
     * Sets the price of the room.
     * @param price the new price of the room
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Sets the room number of the room.
     * @param roomNumber the new room number to set
     */
    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    /**
     * Sets the type of the room.
     * @param type the type of the room
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Sets the occupancy status of the room.
     * @param occupied true if the room is occupied, false otherwise.
     */
    public void setOccupied(boolean occupied) {
        isOccupied = occupied;
    }

    /**
     * Returns a string representation of the Room object
     * @return a string containing the room number, type, price, and occupancy status of the room.
     */
    @Override
    public String toString() {
        return "Room [Room Number: " + roomNumber + ", Type: " + type + ", Price: " + price + ", Occupied: " + isOccupied + "]";
    }

    /**
     * Populates a given list of rooms with predefined room types and prices.
     * @param rooms the list of rooms to be populated
     */
    public static void autoPopulateRooms(List<Room> rooms) {
        String[] roomTypes = {"Single", "Double", "Suite"};
        int roomsPerType = 5;

        for (String roomType : roomTypes) {
            for (int i = 1; i <= roomsPerType; i++) {
                int roomNumber = i;
                double roomPrice = (roomType.equals("Single")) ? 100.0 :
                        (roomType.equals("Double")) ? 150.0 :
                                (roomType.equals("Suite")) ? 200.0 : 0.0;

                rooms.add(new Room(roomNumber, roomType, roomPrice));
            }
        }
    }
}