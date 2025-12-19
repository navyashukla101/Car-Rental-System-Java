import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RentPanel extends JPanel {
    private CarRentalSystem rentalSystem;
    private JTextField customerNameField;
    private JComboBox<String> carComboBox;
    private JSpinner rentalDaysSpinner;
    private JLabel totalPriceLabel;
    private JButton rentButton;

    public RentPanel(CarRentalSystem rentalSystem) {
        this.rentalSystem = rentalSystem;

        setBackground(new Color(240, 245, 250));
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Main content panel
        JPanel contentPanel = new JPanel();
        contentPanel.setBackground(new Color(240, 245, 250));
        contentPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 15, 12, 15);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Title
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        JLabel titleLabel = new JLabel("🔑 Rent a Vehicle");
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

        // Customer Name
        gbc.gridx = 0;
        JLabel nameLabel = new JLabel("Full Name:");
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        nameLabel.setForeground(new Color(50, 70, 100));
        contentPanel.add(nameLabel, gbc);

        gbc.gridx = 1;
        customerNameField = new JTextField(20);
        customerNameField.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        customerNameField.setBorder(BorderFactory.createLineBorder(new Color(180, 190, 210), 1));
        customerNameField.setPreferredSize(new Dimension(250, 35));
        contentPanel.add(customerNameField, gbc);

        // Car Selection
        gbc.gridx = 0;
        gbc.gridy++;
        JLabel carLabel = new JLabel("Select Vehicle:");
        carLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        carLabel.setForeground(new Color(50, 70, 100));
        contentPanel.add(carLabel, gbc);

        gbc.gridx = 1;
        carComboBox = new JComboBox<>();
        carComboBox.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        carComboBox.setPreferredSize(new Dimension(250, 35));
        updateCarComboBox();
        contentPanel.add(carComboBox, gbc);

        // Rental Days
        gbc.gridx = 0;
        gbc.gridy++;
        JLabel daysLabel = new JLabel("Rental Days:");
        daysLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        daysLabel.setForeground(new Color(50, 70, 100));
        contentPanel.add(daysLabel, gbc);

        gbc.gridx = 1;
        rentalDaysSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 365, 1));
        rentalDaysSpinner.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        rentalDaysSpinner.setPreferredSize(new Dimension(250, 35));
        contentPanel.add(rentalDaysSpinner, gbc);

        // Total Price
        gbc.gridx = 0;
        gbc.gridy++;
        JLabel priceLabel = new JLabel("Total Price:");
        priceLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        priceLabel.setForeground(new Color(50, 70, 100));
        contentPanel.add(priceLabel, gbc);

        gbc.gridx = 1;
        totalPriceLabel = new JLabel("$0.00");
        totalPriceLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        totalPriceLabel.setForeground(new Color(76, 175, 80));
        contentPanel.add(totalPriceLabel, gbc);

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

        rentButton = createStyledButton("✓ Rent Car", new Color(76, 175, 80));
        rentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rentCar();
            }
        });

        JButton calculateButton = createStyledButton("💰 Calculate Price", new Color(25, 118, 210));
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculatePrice();
            }
        });

        JButton refreshButton = createStyledButton("🔄 Reset", new Color(255, 152, 0));
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateCarComboBox();
                customerNameField.setText("");
                rentalDaysSpinner.setValue(1);
                totalPriceLabel.setText("$0.00");
            }
        });

        buttonPanel.add(calculateButton);
        buttonPanel.add(rentButton);
        buttonPanel.add(refreshButton);
        contentPanel.add(buttonPanel, gbc);

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

    private void updateCarComboBox() {
        carComboBox.removeAllItems();
        for (Car car : rentalSystem.cars) {
            if (car.isAvailable()) {
                carComboBox.addItem(car.getCarId() + " - " + car.getBrand() + " " + car.getModel() + " ($"
                        + String.format("%.0f", car.getBasePricePerDay()) + "/day)");
            }
        }
    }

    private void calculatePrice() {
        if (carComboBox.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(this, "Please select a car.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String selectedCarString = (String) carComboBox.getSelectedItem();
        String carId = selectedCarString.split(" - ")[0];

        Car selectedCar = null;
        for (Car car : rentalSystem.cars) {
            if (car.getCarId().equals(carId)) {
                selectedCar = car;
                break;
            }
        }

        if (selectedCar != null) {
            int days = (int) rentalDaysSpinner.getValue();
            double totalPrice = selectedCar.calculatePrice(days);
            totalPriceLabel.setText(String.format("$%.2f", totalPrice));
        }
    }

    private void rentCar() {
        String customerName = customerNameField.getText().trim();
        if (customerName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter your name.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (carComboBox.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(this, "Please select a car.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String selectedCarString = (String) carComboBox.getSelectedItem();
        String carId = selectedCarString.split(" - ")[0];

        Car selectedCar = null;
        for (Car car : rentalSystem.cars) {
            if (car.getCarId().equals(carId) && car.isAvailable()) {
                selectedCar = car;
                break;
            }
        }

        if (selectedCar == null) {
            JOptionPane.showMessageDialog(this, "Selected car is not available.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int rentalDays = (int) rentalDaysSpinner.getValue();
        double totalPrice = selectedCar.calculatePrice(rentalDays);

        // Create customer
        Customer newCustomer = new Customer("CUS" + (rentalSystem.customers.size() + 1), customerName);
        rentalSystem.addCustomer(newCustomer);

        // Confirm rental
        String message = String.format(
                "Customer: %s\nCar: %s %s\nDays: %d\nTotal Price: $%.2f\n\nConfirm rental?",
                customerName,
                selectedCar.getBrand(),
                selectedCar.getModel(),
                rentalDays,
                totalPrice);

        int confirm = JOptionPane.showConfirmDialog(this, message, "Confirm Rental", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            rentalSystem.rentCar(selectedCar, newCustomer, rentalDays);
            JOptionPane.showMessageDialog(this, "✓ Car rented successfully!", "Success",
                    JOptionPane.INFORMATION_MESSAGE);
            updateCarComboBox();
            customerNameField.setText("");
            rentalDaysSpinner.setValue(1);
            totalPriceLabel.setText("$0.00");
        }
    }
}
