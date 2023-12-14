//Daniel Sanchez
//CSC 240 Final Stage 4
import java.io.*;
import java.util.*;

/**
 * The DataPersistence class is responsible for saving and loading data from a file.
 * It provides methods to save guest and room information to a file, as well as load guest data from a file
 * and populate a Reports object with the loaded data.
 */
public class DataPersistence {
    protected Reports reports;

    /**
     * Saves the data from the Reports object to a file named "data.csv".
     * The data includes the formatted guest information and the room type for each guest.
     * @param reports The Reports object containing the guest and room information.
     */
    public void saveData(Reports reports) {
        try {
            PrintWriter writer = new PrintWriter("data.csv");
            for (List<Guest> guestList : reports.guestMap.values()) {
                for (Guest guest : guestList) {
                    Room room = reports.getRoom(guest.getRoomNumber());
                    writer.println(guest.getFormattedGuestInfo() + "          " + room.getRoomType());
                }
            }
            writer.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error: File not found");
        }
    }

    /**
     * Loads guest data from a file and populates the provided Reports object with the data.
     * @param reports The Reports object to populate with guest and room data.
     * @return A list of Guest objects loaded from the file.
     */
    public List<Guest> loadData(Reports reports) {
        List<Guest> guests = new ArrayList<>();
        File file = new File("data.csv");

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] data = line.split("\\s{2,}"); // Split the line by two or more spaces

                if (data.length < 6) {
                    System.out.println("Invalid line: " + line);
                    continue;
                }

                String guestName = data[0];
                String roomNumber = data[1];
                String status = data[2];
                double totalCost = Double.parseDouble(data[3]);
                int duration = Integer.parseInt(data[4]);
                String roomType = data[5];

                Guest guest = new Guest(guestName, roomNumber, status, totalCost, duration);
                Room room = new Room(roomNumber, roomType.toUpperCase(), totalCost, duration, 0);

                reports.addGuest(guest);
                reports.addRoom(room);
                guests.add(guest);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error: File not found");
        } catch (NumberFormatException e) {
            System.out.println("Error parsing numeric data: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return guests;
    }

}
