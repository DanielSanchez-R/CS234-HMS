//Daniel Sanchez
//CSC 240 Final Stage 4
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * The GUI class represents the graphical user interface of the Hotel Management System.
 * It provides various functionalities for managing guests, bookings, and generating reports.
 * The GUI class extends the JFrame class and implements the ActionListener interface.
 * //@param username The username for authentication.
 * //@param password The password for authentication.
 */
public class GUI extends JFrame {
    protected static Reports reports;
    private final JPanel buttonPanel = new JPanel();
    private final JPanel contentPane;
    private final JButton buttonOK;
    private final JButton bookButton;
    protected JTextField guestNameField;
    protected JTextField roomNumberField;
    private final JTable roomTable;
    protected JComboBox<String> roomTypeComboBox;
    protected JTextField durationField;
    private JTextField totalCostField;
    protected JTextField roomTypeField;
    protected JTextField roomCostField;
    protected JTextField statusField;
    protected JTextArea reportsTextArea;
    protected JRadioButton guestsRadioButton;
    protected JRadioButton salesRadioButton;
    private JButton deleteButton;
    protected JTextField frontDeskGuestNameField;
    protected JTextField frontDeskRoomNumberField;
    protected JComboBox<String> frontDeskRoomTypeComboBox;
    protected JTextField frontDeskDurationField;
    private boolean flipColors = false;
    protected DefaultTableModel sharedTableModel = new DefaultTableModel(
            new Object[]{"Name", "Room Number", "Status", "Total cost", "Duration"}, 0);
    private final JMenuItem saveMenuItem;
    protected double dailyCost;


    /**
     * Constructs a GUI object with the specified username and password.
     * @param username the username for authentication
     * @param password the password for authentication
     */
    public GUI(String username, String password) {
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        reports = new Reports();
        JMenu fileMenu = new JMenu("Menu");
        DataPersistence dataPersistence = new DataPersistence();
        List<Guest> guests = dataPersistence.loadData(reports);
        for (Guest guest : guests) {
            String guestName = guest.getGuestName();
            String roomNumber = guest.getRoomNumber();
            String status = guest.getStatus();
            double totalCost = guest.getTotalCost();
            int duration = guest.getDuration();
            sharedTableModel.addRow(new Object[]{guestName, roomNumber, status, totalCost, duration});
            frontDeskTableModel.addRow(new Object[]{guestName, roomNumber, status, totalCost, duration});
        }
        // Add save menu item
        saveMenuItem = new JMenuItem("Save"); // Initialize saveMenuItem
        fileMenu.add(saveMenuItem);
        saveMenuItem.addActionListener(e -> dataPersistence.saveData(reports)); // Add listener to saveMenuItem
        // Add menu bar
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        // Add File menu
        menuBar.add(fileMenu);
        JMenuItem exitMenuItem = new JMenuItem("Exit");
        JMenuItem optionsMenuItem = new JMenuItem("ENMU COLORS"); // New menu item
        fileMenu.add(optionsMenuItem); // Add OPTIONS menu item
        fileMenu.addSeparator(); // Add a separator for better organization
        fileMenu.add(exitMenuItem);
        // Help menu
        JMenu helpMenu = new JMenu("Help");
        menuBar.add(helpMenu);
        // Help and About Me menu items to Help menu
        JMenuItem helpMenuItem = new JMenuItem("Help Me");
        JMenuItem aboutMenuItem = new JMenuItem("About Me");
        helpMenu.add(helpMenuItem);
        helpMenu.add(aboutMenuItem);
        // listeners to menu items
        exitMenuItem.addActionListener(e -> onExit());
        optionsMenuItem.addActionListener(e -> onOptions());
        helpMenuItem.addActionListener(e -> onHelp());
        aboutMenuItem.addActionListener(e -> onAbout());

        // Check if the username and password are correct
        if (!"manager".equals(username) && !"enter".equals(password)) { // can change hard coded values
            // Authentication failed, show an error message
            JOptionPane.showMessageDialog(null, "Invalid username or password. Access denied.", "Authentication Failed", JOptionPane.ERROR_MESSAGE);
            // Exit the program
            System.exit(0);
        }
        setTitle("Hotel Management System");
        contentPane = new JPanel();
        setContentPane(contentPane);
        guestNameField = new JTextField(20);
        roomNumberField = new JTextField(20);
        roomTable = new JTable(sharedTableModel);
        roomTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        bookButton = new JButton("Book");
        buttonOK = new JButton("Clear Inputs");
        roomTypeComboBox = new JComboBox<>(new String[]{"Single", "Double", "Suite"});
        durationField = new JTextField(10);
        totalCostField = new JTextField(15);
        totalCostField.setEditable(false);
        contentPane.setLayout(new BorderLayout());
        JTabbedPane tabbedPane = new JTabbedPane();
        JPanel frontDeskPanel = createFrontDeskPanel();
        tabbedPane.addTab("Staff: Booking", frontDeskPanel);
        JPanel bookingPanel = new JPanel(new BorderLayout());
        bookingPanel.add(createCrudPanel(), BorderLayout.CENTER);
        tabbedPane.addTab("Management: Edits", bookingPanel);
        JPanel reportsPanel = createReportsPanel();
        tabbedPane.addTab("Reports: Guests, Sales, PIE", reportsPanel);
        deleteButton = new JButton("Delete");
        buttonPanel.add(deleteButton);
        JButton updateButton = new JButton("Update");
        // Create the "Search" tab
        JPanel searchPanel = createSearchPanel();
        tabbedPane.addTab("Search", searchPanel);
        bookingPanel.add(createCrudPanel(), BorderLayout.CENTER);
        buttonPanel.add(updateButton);
        updateButton.addActionListener(e -> onUpdate());
        contentPane.add(tabbedPane, BorderLayout.CENTER);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        bookButton.addActionListener(e -> onBook());
        buttonOK.addActionListener(e -> onOK());
        deleteButton.addActionListener(e -> onDelete());
        // Add an authentication listener to the "Crud" tab
        tabbedPane.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                // Refresh the data when the CRUD tab is selected
                if (tabbedPane.getSelectedIndex() == 1) {
                    refreshData();
                }
            }
        });

    }
    // Add a column for the room type in the frontDeskTableModel
    private final DefaultTableModel frontDeskTableModel = new DefaultTableModel(
            new Object[]{"Name", "Room Number", "Status", "Total cost", "Duration"}, 0);
    private final DefaultTableModel crudTableModel = new DefaultTableModel(
            new Object[]{"Name", "Room Number", "Status", "Total cost", "Duration"}, 0);

    /**
     * Refreshes the data in the GUI.
     * If the "Crud" tab is selected, prompts the user for username and password.
     * If the entered username and password are correct, authentication is successful and CRUD tab actions can be performed.
     * If authentication fails, an error message is displayed and the tab is switched back to the Front Desk tab.
     */
    private void refreshData() {
        // Check if the "Crud" tab is selected
        if (getContentPane().getComponent(0) instanceof JTabbedPane) {
            JTabbedPane tabbedPane = (JTabbedPane) getContentPane().getComponent(0);
            if (tabbedPane.getSelectedIndex() == 1) {
                // The "Crud" tab is selected, prompt the user for username and password
                String username;
                String password;
                boolean authenticated = false;
                while (!authenticated) {
                    username = JOptionPane.showInputDialog(contentPane, "Enter username:");
                    password = JOptionPane.showInputDialog(contentPane, "Enter password:");
                    // Check if the entered username and password are correct
                    if ("manager".equals(username) && "enter".equals(password)) { // can change hard coded values
                        // Authentication successful, proceed with CRUD tab actions
                        System.out.println("Authentication successful. You can perform menu operations now.");
                        authenticated = true;
                        // Refresh the CRUD table
                        refreshCrudTable();
                    } else {
                        // Authentication failed, show an error message
                        JOptionPane.showMessageDialog(contentPane, "Invalid username or password. Access denied.", "Authentication Failed. You are being redirected", JOptionPane.ERROR_MESSAGE);
                        // Switch back to the Front Desk tab
                        tabbedPane.setSelectedIndex(0);
                        break;
                    }
                }
            }
        }
    }
    /**
     * Refreshes the CRUD table by removing all rows from the sharedTableModel
     * and adding all bookings from the frontDeskTableModel to the sharedTableModel.
     */
    private void refreshCrudTable() { //latest update LOOK HERE
        // Remove all rows from the sharedTableModel
        while (sharedTableModel.getRowCount() > 0) {
            sharedTableModel.removeRow(0);
        }
        // Add all bookings from the frontDeskTableModel to the sharedTableModel
        for (int i = 0; i < frontDeskTableModel.getRowCount(); i++) {
            String guestName = (String) frontDeskTableModel.getValueAt(i, 0);
            String roomNumber = (String) frontDeskTableModel.getValueAt(i, 1);
            String status = (String) frontDeskTableModel.getValueAt(i, 2);
            double totalCost = (double) frontDeskTableModel.getValueAt(i, 3);
            int duration = (int) frontDeskTableModel.getValueAt(i, 4);
            sharedTableModel.addRow(new Object[]{guestName, roomNumber, status, totalCost, duration});
        }
    }

    /**
     * Updates the selected row in the GUI table with the new guest information.
     * Retrieves the input from the text fields and updates the corresponding values in the table.
     * If the guest name is empty and the duration is 0, sets the status to "Available".
     * Otherwise, sets the status to "Booked".
     * Removes the old guest and room instances from the Reports class and adds the new instances.
     * Updates both the shared table and the front desk table with the new guest information.
     * Displays an error message if no row is selected.
     */
    public void onUpdate() {
        // Get the selected row
        int selectedRow = roomTable.getSelectedRow();
        if (selectedRow != -1) {
            // Get the input from the text fields
            String newGuestName = guestNameField.getText();
            String roomNumber = roomNumberField.getText();
            String roomType = (String) roomTypeComboBox.getSelectedItem();
            String durationString = durationField.getText();
            int duration = durationString.isEmpty() ? 0 : Integer.parseInt(durationString);
            double totalCost = calculateTotalCost(roomType, duration);
            // Get the old guest name and room number from the table
            String oldGuestName = (String) sharedTableModel.getValueAt(selectedRow, 0);
            String oldRoomNumber = (String) sharedTableModel.getValueAt(selectedRow, 1);
            // Remove the old Guest and Room instances from the Reports class
            reports.removeGuest(oldGuestName);
            reports.removeRoom(oldRoomNumber);
            // Create new Guest and Room instances and add them to the Reports class
            Guest guest = new Guest(newGuestName, roomNumber, "Booked", totalCost, duration);
            Room room = new Room(roomNumber, roomType, totalCost, dailyCost, duration);
            reports.addGuest(guest);
            reports.addRoom(room);
            // Update the shared table
            sharedTableModel.setValueAt(newGuestName, selectedRow, 0);
            sharedTableModel.setValueAt(roomNumber, selectedRow, 1);
            // Check if the guest name is empty and the duration is 0
            if (newGuestName.isEmpty() && duration == 0) {
                // If they are, set the status to "Available"
                sharedTableModel.setValueAt("Available", selectedRow, 2);
            } else {
                sharedTableModel.setValueAt("Booked", selectedRow, 2);
            }
            sharedTableModel.setValueAt(totalCost, selectedRow, 3);
            sharedTableModel.setValueAt(duration, selectedRow, 4);
            // Update the front desk table
            for (int i = 0; i < frontDeskTableModel.getRowCount(); i++) {
                if (oldGuestName.equals(frontDeskTableModel.getValueAt(i, 0)) && oldRoomNumber.equals(frontDeskTableModel.getValueAt(i, 1))) {
                    frontDeskTableModel.setValueAt(newGuestName, i, 0);
                    frontDeskTableModel.setValueAt(roomNumber, i, 1);
                    // Check if the guest name is empty and the duration is 0
                    if (newGuestName.isEmpty() && duration == 0) {
                        // If they are, set the status to "Available"
                        frontDeskTableModel.setValueAt("Available", i, 2);
                    } else {
                        frontDeskTableModel.setValueAt("Booked", i, 2);
                    }
                    frontDeskTableModel.setValueAt(totalCost, i, 3);
                    frontDeskTableModel.setValueAt(duration, i, 4);
                    break;
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a row to update.", "Update Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    /**
     * Clears or resets the fields in the GUI.
     */
    private void onOK() {
        // This method can be used to clear or reset fields, for example
        guestNameField.setText("");
        roomNumberField.setText("");
        roomTypeComboBox.setSelectedIndex(0);
        durationField.setText("");
        totalCostField.setText("");
    }
    /**
     * Deletes the selected row from the table, removes the corresponding Guest and Room instances from the Reports class,
     * and updates the table models accordingly.
     * If no row is selected, displays an error message.
     */
    private void onDelete() {
        int selectedRow = roomTable.getSelectedRow();
        if (selectedRow != -1) {
            // Get the guest name and room number from the table
            String guestName = (String) sharedTableModel.getValueAt(selectedRow, 0);
            String roomNumber = (String) sharedTableModel.getValueAt(selectedRow, 1);
            // Remove the Guest and Room instances from the Reports class
            reports.removeGuest(guestName);
            reports.removeRoom(roomNumber);
            // Remove the row from the sharedTableModel
            sharedTableModel.removeRow(selectedRow);
            // Iterate over each row in the frontDeskTableModel
            for (int i = 0; i < frontDeskTableModel.getRowCount(); i++) {
                // Check if the guest name and room number match the deleted row
                if (guestName.equals(frontDeskTableModel.getValueAt(i, 0)) && roomNumber.equals(frontDeskTableModel.getValueAt(i, 1))) {
                    // If they do, remove the row from the frontDeskTableModel
                    frontDeskTableModel.removeRow(i);
                    // Break the loop as we've found and removed the matching row
                    break;
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a row to delete.", "Delete Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    /**
     * Performs the booking process when the user clicks on the "Book" button.
     * Retrieves input from text fields, validates the guest name length, calculates the total cost,
     * confirms the booking with the user, checks if the room is already booked,
     * adds the booking to the shared table model and updates the reports and front desk table model accordingly.
     */
    private void onBook() {
        // Get the input from the text fields
        String guestName = guestNameField.getText();
        if (guestName.length() > 20) { // Change this value to the maximum length you want to allow
            JOptionPane.showMessageDialog(null, "The guest name is too long. Please enter a shorter name.", "Input Error. Must be less than 15", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String roomNumber = roomNumberField.getText();
        String roomType = (String) roomTypeComboBox.getSelectedItem();
        int duration = Integer.parseInt(durationField.getText());
        double totalCost = calculateTotalCost(roomType, duration);
        // Confirm the booking
        int confirm = JOptionPane.showConfirmDialog(null, "Guest Name: " + guestName + "\nTotal Cost: " + totalCost + "\nConfirm booking?", "Confirm Booking", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            // Check if the room is already booked in the frontDeskTableModel
            for (int i = 0; i < frontDeskTableModel.getRowCount(); i++) {
                if (roomNumber.equals(frontDeskTableModel.getValueAt(i, 1)) && "Booked".equals(frontDeskTableModel.getValueAt(i, 2))) {
                    // The room is already booked, show an error message
                    JOptionPane.showMessageDialog(null, "This room is already booked. Please select a different room.", "Room Already Booked", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            // The room is not booked, add the booking to the sharedTableModel
            sharedTableModel.addRow(new Object[]{guestName, roomNumber, "Booked", totalCost, duration});
            // Create Guest and Room instances and add them to the Reports class
            Guest guest = new Guest(guestName, roomNumber, "Booked", totalCost, duration);
            Room room = new Room(roomNumber, roomType, totalCost, dailyCost, duration);
            reports.addGuest(guest);
            reports.addRoom(room);
            // Add the booking to the frontDeskTableModel
            // The room is not listed, add a new row with the booking information
            frontDeskTableModel.addRow(new Object[]{guestName, roomNumber, roomType, "Booked", totalCost, duration});
        }
    }

    /**
     * Calculates the total cost based on the room type and duration.
     * @param roomType the type of room (Single, Double, Suite)
     * @param duration the duration of the stay in days
     * @return the total cost of the stay
     */
    private double calculateTotalCost(String roomType, int duration) {
        // Calculate the total cost based on the room type and duration
        double roomCost = 0;
        switch (roomType) {
            case "Single":
                roomCost = 55;
                break;
            case "Double":
                roomCost = 70;
                break;
            case "Suite":
                roomCost = 125;
                break;
        }
        return roomCost * duration;
    }

    /**
     * Exits the program.
     */
    private void onExit() {
        System.exit(0);
    }

    /**
     * Displays a help message with password assistance information.
     */
    private void onHelp() {
        JOptionPane.showMessageDialog(contentPane, "<html>If you need password assistance Call 1-800-505-5555 <br>" +
                "Someone can assist you <br>" +
                "We are open 24/7!</html>");
    }

    /**
     * Displays information about the author and version of the program.
     */
    private void onAbout() {
        JOptionPane.showMessageDialog(contentPane, "<html>Author:   Daniel Ray Sanchez<br>" +
                "Computer Science Project Stage 4,<br>" +
                "Completed November 2023 Version 3.1 </html>");
    }

    /**
     * This method is called when the "Options" action is triggered.
     * It creates a custom color using green and grey RGB values, and flips the colors based on a flag.
     * The background color of the main content pane and other components is changed accordingly.
     * The flag is flipped for the next invocation.
     */
    private void onOptions() {
        // Create custom color with green and grey (modify RGB values as needed)
        Color greenColor = new Color(66, 141, 66);
        Color greyColor = new Color(169, 169, 169);
        // Flip colors if the flag is true
        Color selectedColor = flipColors ? greyColor : greenColor;
        // Change the background color of the main content pane and other components
        contentPane.setBackground(selectedColor);
        changeComponentColors(selectedColor);
        // Flip the flag for the next invocation
        flipColors = !flipColors;
    }

    /**
     * Changes the background color of various components in the GUI.
     * @param color the color to set as the background color
     */
    private void changeComponentColors(Color color) {
        // Change the background color of the menu bar
        JMenuBar menuBar = getJMenuBar();
        if (menuBar != null) {
            menuBar.setBackground(color);
        }
        // Change the background color of the tabbed pane
        JTabbedPane tabbedPane = (JTabbedPane) contentPane.getComponent(0);
        tabbedPane.setBackground(color);
        // Change the background color of the tabbed pane's components
        for (int i = 0; i < tabbedPane.getTabCount(); i++) {
            JPanel panel = (JPanel) tabbedPane.getComponent(i);
            panel.setBackground(color);
            for (int j = 0; j < panel.getComponentCount(); j++) {
                Component component = panel.getComponent(j);
                component.setBackground(color);
            }
        }
    }

    /**
     * Creates a JPanel for the front desk section of the GUI.
     * The panel contains input fields for guest name, room number, room type, and duration of stay.
     * It also includes a table for displaying front desk room information and a "Book" button.
     * @return The created JPanel for the front desk section.
     */
    private JPanel createFrontDeskPanel() {
        JPanel frontDeskPanel = new JPanel(new BorderLayout());
        frontDeskGuestNameField = new JTextField(20);
        frontDeskRoomNumberField = new JTextField(20);
        frontDeskRoomTypeComboBox = new JComboBox<>(new String[]{"Single", "Double", "Suite"});
        frontDeskDurationField = new JTextField(10);
        JPanel inputPanel = new JPanel(new GridLayout(4, 2));
        inputPanel.add(new JLabel("Guest Name Only:"));
        inputPanel.add(frontDeskGuestNameField);
        inputPanel.add(new JLabel("Room Number:"));
        inputPanel.add(frontDeskRoomNumberField);
        inputPanel.add(new JLabel("Room Type:"));
        inputPanel.add(frontDeskRoomTypeComboBox);
        inputPanel.add(new JLabel("Duration of Stay (days):"));
        inputPanel.add(frontDeskDurationField);
        frontDeskPanel.add(inputPanel, BorderLayout.NORTH);
        // Use frontDeskTableModel for the Front Desk table
        JTable frontDeskRoomTable = new JTable(frontDeskTableModel);
        frontDeskRoomTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        frontDeskPanel.add(new JScrollPane(frontDeskRoomTable), BorderLayout.CENTER);
        JButton frontDeskBookButton = new JButton("Book");
        frontDeskPanel.add(frontDeskBookButton, BorderLayout.SOUTH);
        frontDeskBookButton.addActionListener(e -> onFrontDeskBook()); // can be updated with new class
        // Refresh the frame
        frontDeskPanel.revalidate();
        frontDeskPanel.repaint();
        return frontDeskPanel;
    }

    /**
     * Performs the booking process for the front desk.
     * Retrieves input from text fields, validates the guest name length,
     * calculates the total cost, creates guest and room instances,
     * adds them to the Reports class, updates the table models,
     * and removes duplicates with "Available" status.
     */
    private void onFrontDeskBook() {
        // Get the input from the text fields
        String guestName = frontDeskGuestNameField.getText();
        if (guestName.length() > 15) {
            JOptionPane.showMessageDialog(null, "The guest name is too long. Please enter a shorter name.", "Input Error. Must be less than 15 ", JOptionPane.ERROR_MESSAGE);
            return;
        }
        // Get the input from the text fields
        String roomNumber = frontDeskRoomNumberField.getText();
        String roomType = (String) frontDeskRoomTypeComboBox.getSelectedItem();
        int duration = Integer.parseInt(frontDeskDurationField.getText());
        double totalCost = calculateTotalCost(roomType, duration);
        // Create Guest and Room instances and add them to the Reports class
        Guest guest = new Guest(guestName, roomNumber, "Booked", totalCost, duration);
        Room room = new Room(roomNumber, roomType, totalCost, dailyCost, duration);
        reports.addGuest(guest);
        reports.addRoom(room);
        // Add the booking to the sharedTableModel, frontDeskTableModel, and crudTableModel
        sharedTableModel.addRow(new Object[]{guestName, roomNumber, "Booked", totalCost, duration});
        frontDeskTableModel.addRow(new Object[]{guestName, roomNumber, "Booked", totalCost, duration});
        crudTableModel.addRow(new Object[]{guestName, roomNumber, "Booked", totalCost, duration});
        // check tables for any with duplicate room numbers and remove duplicates with Available status
        for (int i = 0; i < frontDeskTableModel.getRowCount(); i++) {
            if (roomNumber.equals(frontDeskTableModel.getValueAt(i, 1)) && "Available".equals(frontDeskTableModel.getValueAt(i, 2))) {
                frontDeskTableModel.removeRow(i);
                break;
            }
        }
    }

    /**
     * Creates a JPanel for the CRUD operations.
     * @return The created JPanel.
     */
    private JPanel createCrudPanel() {
        // Initialize buttonPanel
        JPanel bookingPanel = new JPanel(new BorderLayout());
        JPanel inputPanel = new JPanel(new GridLayout(4, 2));
        inputPanel.add(new JLabel("Guest Name Only:"));
        inputPanel.add(guestNameField);
        inputPanel.add(new JLabel("Room Number:"));
        inputPanel.add(roomNumberField);
        inputPanel.add(new JLabel("Room Type:"));
        inputPanel.add(roomTypeComboBox);
        inputPanel.add(new JLabel("Duration of Stay (days):"));
        inputPanel.add(durationField);
        bookingPanel.add(inputPanel, BorderLayout.NORTH);
        bookingPanel.add(new JScrollPane(roomTable), BorderLayout.CENTER);
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(bookButton);
        buttonPanel.add(buttonOK);
        bookingPanel.add(buttonPanel, BorderLayout.SOUTH);
        JPanel leftPanel = new JPanel(new GridLayout(5, 2));//
        leftPanel.add(roomTypeField = new JTextField());
        leftPanel.add(new JLabel(""));
        leftPanel.add(roomCostField = new JTextField());
        leftPanel.add(new JLabel(""));
        leftPanel.add(totalCostField = new JTextField());
        leftPanel.add(new JLabel(""));
        leftPanel.add(statusField = new JTextField());
        Dimension textFieldSize = new Dimension(150, 20);
        roomTypeField.setPreferredSize(textFieldSize);
        roomCostField.setPreferredSize(textFieldSize);
        totalCostField.setPreferredSize(textFieldSize);
        statusField.setPreferredSize(textFieldSize);
        // Adding "Update" button to the bottom of the screen
        JButton updateButton = new JButton("Update");
        buttonPanel.add(updateButton);
        updateButton.addActionListener(e -> onUpdate());
        // Adding a "Delete" button to the bottom of the screen
        deleteButton = new JButton("Delete");
        buttonPanel.add(deleteButton);
        bookingPanel.revalidate();
        bookingPanel.repaint();

        return bookingPanel;
    }
    //Reports tab panel
    /**
     * Creates a JPanel for displaying reports.
     * @return The created JPanel.
     */
    private JPanel createReportsPanel() {
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        JPanel reportsPanel = new JPanel(new BorderLayout());
        JPanel radioPanel = new JPanel();
        guestsRadioButton = new JRadioButton("Guests");
        salesRadioButton = new JRadioButton("Sales");
        ButtonGroup radioButtonGroup = new ButtonGroup();
        radioButtonGroup.add(guestsRadioButton);
        radioButtonGroup.add(salesRadioButton);
        radioPanel.add(guestsRadioButton);
        radioPanel.add(salesRadioButton);
        reportsTextArea = new JTextArea(10, 30);
        reportsTextArea.setEditable(false);
        JPanel buttonPanel = new JPanel();
        JButton generateReportButton = new JButton("Generate Report");
        generateReportButton.addActionListener(e -> onGenerateReport());
        buttonPanel.add(generateReportButton);
        reportsPanel.add(radioPanel, BorderLayout.NORTH);
        reportsPanel.add(new JScrollPane(reportsTextArea), BorderLayout.CENTER);
        reportsPanel.add(buttonPanel, BorderLayout.SOUTH);
        // Add the "Get Capacity" button
        JButton getCapacityButton = new JButton("Get Capacity");
        buttonPanel.add(getCapacityButton);
        getCapacityButton.addActionListener(e -> {
            JFrame frame = new JFrame("Capacity");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.getContentPane().add(new CapacityPie(reports));
            frame.setSize(245, 300);
            frame.setVisible(true);
        });

        return reportsPanel;
    }
    /**
     * Generates and displays a report based on the selected report type.
     * If the "Guests" radio button is selected, it generates a guest report.
     * If the "Sales" radio button is selected, it generates a sales report.
     * If no report type is selected, it displays an error message.
     */
    protected void onGenerateReport(){
        if(guestsRadioButton.isSelected()){
            reportsTextArea.setText(reports.generateGuestReport());
        }
        else if(salesRadioButton.isSelected()){
            reportsTextArea.setText(reports.generateSalesReport());
        }
        else{
            JOptionPane.showMessageDialog(null, "Please select a report type.", "Report Type Not Selected", JOptionPane.ERROR_MESSAGE);
        }
    }
    //Search tab panel
    /**
     * Creates a JPanel for the search functionality.
     * The panel contains input fields for searching guests by name and rooms by type,
     * as well as a button for executing the search.
     * The search results are displayed in a scrollable JTextArea.
     * @return the created search panel
     */
    private JPanel createSearchPanel() {
        JPanel searchPanel = new JPanel(new BorderLayout());
        JPanel inputPanel = new JPanel(new GridLayout(2, 1)); // Change to GridLayout to accommodate two input areas
        // Existing guest search area
        JPanel guestSearchPanel = new JPanel(new FlowLayout());
        JTextField guestSearchField = new JTextField(20);
        JButton guestSearchButton = new JButton("SEARCH");
        guestSearchPanel.add(new JLabel("Enter Guest Full Name:"));
        guestSearchPanel.add(guestSearchField);
        guestSearchPanel.add(guestSearchButton);
        inputPanel.add(guestSearchPanel);
        // New room type search area
        JPanel roomSearchPanel = new JPanel(new FlowLayout());
        JTextField roomSearchField = new JTextField(20);
        JButton roomSearchButton = new JButton("SEARCH");
        roomSearchPanel.add(new JLabel("Search Room by Type:"));
        roomSearchPanel.add(roomSearchField);
        roomSearchPanel.add(roomSearchButton);
        inputPanel.add(roomSearchPanel);
        JTextArea outputArea = new JTextArea(10, 30);
        outputArea.setEditable(false);
        searchPanel.add(inputPanel, BorderLayout.NORTH);
        searchPanel.add(new JScrollPane(outputArea), BorderLayout.CENTER);
        guestSearchButton.addActionListener(e -> {
            String guestName = guestSearchField.getText();
            List<Guest> guests = reports.guestMap.get(guestName);
            if (guests != null) {
                StringBuilder guestInfo = new StringBuilder();
                for (Guest guest : guests) {
                    Room room = reports.getRoom(guest.getRoomNumber());
                    guestInfo.append("Guest Name: ").append(guest.getGuestName()).append("\n");
                    guestInfo.append("Room Number: ").append(guest.getRoomNumber()).append("\n");
                    guestInfo.append("Room Type: ").append(room.getRoomType()).append("\n");
                    guestInfo.append("Duration of Stay: ").append(guest.getDuration()).append("\n");
                    guestInfo.append("Cost of Room per Night: ").append(room.getRoomCost()).append("\n");
                    guestInfo.append("Total Cost: ").append(guest.getTotalCost()).append("\n\n");
                }
                outputArea.setText(guestInfo.toString());
            } else {
                outputArea.setText("No guest found with the name: " + guestName);
            }
        });

        roomSearchButton.addActionListener(e -> {
            String roomType = roomSearchField.getText();
            String roomReport = reports.searchRoomsByType(roomType);
            outputArea.setText(roomReport);
        });
        return searchPanel;
    }
}