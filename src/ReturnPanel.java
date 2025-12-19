import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ReturnPanel extends JPanel {
    private CarRentalSystem rentalSystem;
    private JTextField carIdField;
    private JButton returnButton;

    public ReturnPanel(CarRentalSystem rentalSystem) {
        this.rentalSystem = rentalSystem;

        setBackground(new Color(240, 245, 250));
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Main panel
        JPanel contentPanel = new JPanel();
        contentPanel.setBackground(new Color(240, 245, 250));
        contentPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 15, 12, 15);
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Title
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        JLabel titleLabel = new JLabel("↩️ Return a Vehicle");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setForeground(new Color(25, 118, 210));
        contentPanel.add(titleLabel, gbc);

        gbc.gridy++;
        JSeparator separator1 = new JSeparator();
        separator1.setForeground(new Color(180, 190, 210));
        gbc.gridwidth = 2;
        contentPanel.add(separator1, gbc);

        gbc.gridwidth = 1;
        gbc.gridy++;

        // Car ID Input
        gbc.gridx = 0;
        JLabel carIdLabel = new JLabel("Car ID to Return:");
        carIdLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        carIdLabel.setForeground(new Color(50, 70, 100));
        contentPanel.add(carIdLabel, gbc);

        gbc.gridx = 1;
        carIdField = new JTextField(20);
        carIdField.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        carIdField.setBorder(BorderFactory.createLineBorder(new Color(180, 190, 210), 1));
        carIdField.setPreferredSize(new Dimension(250, 35));
        contentPanel.add(carIdField, gbc);

        // Separator
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        JSeparator separator2 = new JSeparator();
        separator2.setForeground(new Color(180, 190, 210));
        contentPanel.add(separator2, gbc);

        // Buttons Panel
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(240, 245, 250));
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 0));

        returnButton = createStyledButton("✓ Return Car", new Color(76, 175, 80));
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                returnCar();
            }
        });

        JButton clearButton = createStyledButton("🔄 Clear", new Color(255, 152, 0));
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                carIdField.setText("");
            }
        });

        buttonPanel.add(returnButton);
        buttonPanel.add(clearButton);
        contentPanel.add(buttonPanel, gbc);

        // Info panel
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        JPanel infoPanel = new JPanel();
        infoPanel.setBackground(Color.WHITE);
        infoPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(180, 190, 210), 1),
                "📋 Instructions",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Segoe UI", Font.BOLD, 12),
                new Color(25, 118, 210)));
        infoPanel.setLayout(new BorderLayout());
        infoPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JTextArea infoArea = new JTextArea();
        infoArea.setText("To return a vehicle:\n\n" +
                "1. Enter the Car ID (e.g., C001, C002, C003)\n" +
                "2. Click 'Return Car' to process the return\n" +
                "3. The system will confirm the return and mark\n" +
                "   the vehicle as available\n\n" +
                "Note: You can only return cars that are\n" +
                "currently rented out.");
        infoArea.setEditable(false);
        infoArea.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        infoArea.setBackground(Color.WHITE);
        infoArea.setForeground(new Color(50, 70, 100));
        infoArea.setLineWrap(true);
        infoArea.setWrapStyleWord(true);

        infoPanel.add(new JScrollPane(infoArea), BorderLayout.CENTER);
        contentPanel.add(infoPanel, gbc);

        add(contentPanel, BorderLayout.NORTH);
    }

    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setPreferredSize(new Dimension(140, 40));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor.darker());
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor);
            }
        });

        return button;
    }

    private void returnCar() {
        String carId = carIdField.getText().trim();

        if (carId.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a car ID.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Car carToReturn = null;
        for (Car car : rentalSystem.cars) {
            if (car.getCarId().equals(carId) && !car.isAvailable()) {
                carToReturn = car;
                break;
            }
        }

        if (carToReturn == null) {
            JOptionPane.showMessageDialog(this, "Invalid car ID or car is not currently rented.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Find customer for this rental
        Customer customer = null;
        for (Rental rental : rentalSystem.rentals) {
            if (rental.getCar() == carToReturn) {
                customer = rental.getCustomer();
                break;
            }
        }

        if (customer != null) {
            rentalSystem.returnCar(carToReturn);
            JOptionPane.showMessageDialog(this,
                    "✓ Car returned successfully by " + customer.getName() + "!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
            carIdField.setText("");
        } else {
            JOptionPane.showMessageDialog(this,
                    "Car rental information not found.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}