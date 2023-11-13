/**
 * Daniel Sanchez
 * Guest class
 * CS 234
 */
import java.util.ArrayList;
import java.util.List;

/**
 * The Guest class represents a guest in a hotel. It contains information about the guest such as their name, contact number, and email address.
 */
public class Guest {
    private String name;
    private String contactNumber;
    private String email;

    /** Constructs a new Guest object with the given name, contact number, and email.
     * @param name the name of the guest
     * @param contactNumber the contact number of the guest
     * @param email the email of the guest
     */
    public Guest(String name, String contactNumber, String email) {
        this.name = name;
        this.contactNumber = contactNumber;
        this.email = email;
    }

    /**
     * Returns the name of the guest as a String.
     * @return the name of the guest
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the guest.
     * @param name the name of the guest
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the contact number of the guest.
     * @param contactNumber the new contact number of the guest
     */
    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    /**
     * Sets the email of the guest.
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns a string representation of the object.
     * @return a string containing the name, contact number, and email of the guest
     */
    @Override
    public String toString() {
        return "Guest [Name: " + name + ", Contact Number: " + contactNumber + ", Email: " + email + "]";
    }

    /**
     * This method auto-populates a list of guests with default values.
     * @return List of Guest objects
     */
    public static List<Guest> autoPopulateGuests() {
        List<Guest> guests = new ArrayList<>();

        for (int i = 1; i <= 5; i++) {
            guests.add(new Guest("Guest" + i, "1800-505-010" + i, "guest" + i + "@gmail.com"));
        }
        return guests;
    }
}