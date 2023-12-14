//Daniel Sanchez
//CSC 240 Final Stage 4
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JComponent;

/**
 * This class represents a CapacityPie object.
 * It contains methods and data structures for drawing a pie chart representing room capacity.
 */
public class CapacityPie extends JComponent {
    private final Reports reports;

    /**
     * Constructs a CapacityPie object with the specified Reports object.
     * @param reports the Reports object
     */
    public CapacityPie(Reports reports) {
        this.reports = reports;
    }

    /**
     * Overrides the paintComponent method to draw a pie chart representing room capacity.
     * @param g the Graphics object used for drawing
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        // Calculate the angles for the pie chart based on the room data
        int totalRooms = 90;
        int bookedRooms = reports.getBookedRoomsCount();
        int availableRooms = totalRooms - bookedRooms;
        int angleBooked = bookedRooms * 360 / totalRooms;
        int angleAvailable = availableRooms * 360 / totalRooms;
        // Draw the first pie chart
        g2.setColor(Color.GRAY);
        g2.fillArc(13, 13, 200, 200, 0, angleBooked);
        g2.setColor(Color.GREEN);
        g2.fillArc(13, 13, 200, 200, angleBooked, angleAvailable);
        // Draw labels for the first pie chart
        g2.setColor(Color.BLACK);
        g2.drawString("Room Capacity", 75, 12); // Adjusted y-coordinate
        g2.setColor(Color.GRAY);
        g2.drawString("Booked: " + bookedRooms, 20, 240);
        g2.setColor(Color.GREEN);
        g2.drawString("Available: " + availableRooms, 120, 240);

    }
}