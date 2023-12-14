//Daniel Sanchez
//CSC 240 Final Stage 4
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

/**
 * This class represents a Reports object.
 * It contains methods and data structures for managing guest and room reports.
 */
public class Reports {
    protected final Map<String, List<Guest>> guestMap; // guest name is unique
    protected final Map<String, Room> roomMap; // number is unique

    /**
     * This class represents a Reports object.
     * It contains methods and data structures for managing guest and room reports.
     */
    public Reports() {
        this.guestMap = new HashMap<>();
        this.roomMap = new HashMap<>();
    }

    /**
     * Adds a guest to the guestMap.
     * If the guest's name already exists in the guestMap, the guest will be added to the existing list of guests.
     * If the guest's name does not exist in the guestMap, a new list will be created and the guest will be added to it.
     * @param guest the guest to be added
     */
    public void addGuest(Guest guest) {
        List<Guest> guestList = guestMap.getOrDefault(guest.getGuestName(), new ArrayList<>());
        guestList.add(guest);
        guestMap.put(guest.getGuestName(), guestList);
    }

    /**
     * Removes a guest from the guestMap.
     * @param guestName the name of the guest to be removed
     */
    public void removeGuest(String guestName) {
        guestMap.remove(guestName);
    }

    /**
     * Adds a room to the room map.
     * @param room the room to be added
     */
    public void addRoom(Room room) {
        roomMap.put(room.getRoomNumber(), room);
    }

    /**
     * Retrieves the Room object associated with the given room number.
     * @param roomNumber the room number of the desired Room object
     * @return the Room object associated with the given room number, or null if not found
     */
    public Room getRoom(String roomNumber) {
        return this.roomMap.get(roomNumber);
    }

    /**
     * Removes a room from the roomMap based on the given room number.
     * @param roomNumber the number of the room to be removed
     */
    public void removeRoom(String roomNumber) {
        roomMap.remove(roomNumber);
    }

    /**
     * Generates a guest report as a string.
     * The report includes the guest's name, room number, status, total cost, and duration.
     * @return the generated guest report as a string
     */
    public String generateGuestReport() {
        StringBuilder report = new StringBuilder();
        String header = "Name" + "\t\t" + "Room Number" + "\t\t" + "Status" + "\t\t" + "Total Cost" + "\t\t" + "Duration";
        report.append(header).append("\n");
        for (List<Guest> guestList : guestMap.values()) {
            for (Guest guest : guestList) {
                String guestName = guest.getGuestName();
                // Check if the guest name is not empty
                if (!guestName.isEmpty()) {
                    String roomNumber = guest.getRoomNumber();
                    String status = guest.getStatus();
                    double totalCost = guest.getTotalCost();
                    int duration = guest.getDuration();
                    String row = guestName + "\t\t" + roomNumber + "\t\t" + status + "\t\t" + totalCost + "\t\t" + duration;
                    report.append(row).append("\n");
                }
            }
        }
        return report.toString();
    }

    /**
     * Generates a sales report by calculating the total sales from all guest bookings.
     * @return A string representation of the total sales.
     */
    public String generateSalesReport() {
        double totalSales = 0.0;
        // Iterate over all guests
        for (List<Guest> guestList : guestMap.values()) {
            for (Guest guest : guestList) {
                // Add the total cost of each booking to the total sales
                totalSales += guest.getTotalCost();
            }
        }
        // Return the total sales as a string
        return "Total sales: " + totalSales;
    }

    /**
     * Searches and generates a report of rooms by the specified room type.
     * @param roomType the type of room to search for
     * @return a string containing the report of rooms with the specified room type
     */
    public String searchRoomsByType(String roomType) {
        StringBuilder report = new StringBuilder();
        for (Room room : roomMap.values()) {
            if (room.getRoomType().toString().equalsIgnoreCase(roomType)) {
                for (List<Guest> guestList : guestMap.values()) {
                    for (Guest guest : guestList) {
                        if (guest.getRoomNumber().equals(room.getRoomNumber()) && !Double.isNaN(room.getRoomCost()) && guest.getDuration() != 0 && guest.getTotalCost() != 0.0) {
                            report.append("Guest Name: ").append(guest.getGuestName()).append("\n");
                            report.append("Room Number: ").append(room.getRoomNumber()).append("\n");
                            report.append("Room Type: ").append(room.getRoomType()).append("\n");
                            report.append("Duration of Stay: ").append(guest.getDuration()).append("\n");
                            report.append("Cost of Room per Night: ").append(room.getRoomCost()).append("\n");
                            report.append("Total Cost: ").append(guest.getTotalCost()).append("\n\n");
                        }
                    }
                }
            }
        }
        if (report.length() == 0) {
            report.append("No rooms found with the type: ").append(roomType);
        }
        return report.toString();
    }
    /**
     * Returns the count of booked rooms.
     * @return The count of booked rooms.
     */
    public int getBookedRoomsCount() {
        int count = 0;
        for (List<Guest> guests : guestMap.values()) {
            for (Guest guest : guests) {
                if ("Booked".equals(guest.getStatus())) {
                    count++;
                }
            }
        }
        return count;
    }
}
