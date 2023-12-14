//Daniel Sanchez
//CSC 240 Final Stage 4
import javax.swing.*;

/**
 * The Main class is the entry point of the application.
 * It prompts the user for a username and password, and launches the GUI with the provided credentials.
 * If the user cancels the input, it prints a message to the console.
 */
public class Main {
    public static void main(String[] args) {
        // Prompt the user for username and password
        String username = JOptionPane.showInputDialog(null, "Enter username:");
        String password = JOptionPane.showInputDialog(null, "Enter password:");

        // Check if the user canceled the input dialogs
        if (username != null && password != null) {
            // Launch the GUI with the provided username and password
            SwingUtilities.invokeLater(() -> {
                GUI gui = new GUI(username, password);
                gui.setVisible(true);
            });
        } else {
            // User canceled, you can handle this case accordingly
            System.out.println("User canceled input.");
        }
    }
}