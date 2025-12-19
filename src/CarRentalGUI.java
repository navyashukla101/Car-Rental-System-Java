import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;

public class CarRentalGUI extends JFrame {
    private CarRentalSystem rentalSystem;
    private JTabbedPane tabbedPane;

    public CarRentalGUI(CarRentalSystem rentalSystem) {
        this.rentalSystem = rentalSystem;

        // Frame setup
        setTitle("🚗 Car Rental System - Professional Edition");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 700);
        setLocationRelativeTo(null);
        setResizable(true);

        // Set modern look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Create main panel with gradient-like background
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(240, 245, 250));
        mainPanel.setLayout(new BorderLayout());

        // Create header panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(25, 118, 210));
        headerPanel.setPreferredSize(new Dimension(900, 80));
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JLabel titleLabel = new JLabel("🚗 Car Rental Management System");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel, BorderLayout.WEST);

        JLabel subtitleLabel = new JLabel("Manage your vehicle rentals efficiently");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitleLabel.setForeground(new Color(200, 220, 255));
        headerPanel.add(subtitleLabel, BorderLayout.SOUTH);

        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Create tabbed pane with custom styling
        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tabbedPane.setBackground(new Color(240, 245, 250));
        tabbedPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Add tabs
        tabbedPane.addTab("🔑 Rent a Car", new RentPanel(rentalSystem));
        tabbedPane.addTab("↩️ Return a Car", new ReturnPanel(rentalSystem));
        tabbedPane.addTab("📋 Inventory", new InventoryPanel(rentalSystem));

        mainPanel.add(tabbedPane, BorderLayout.CENTER);

        // Add footer panel
        JPanel footerPanel = new JPanel();
        footerPanel.setBackground(new Color(230, 235, 245));
        footerPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(180, 190, 210)));
        footerPanel.setPreferredSize(new Dimension(900, 40));
        footerPanel.setLayout(new BorderLayout());
        footerPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JLabel footerLabel = new JLabel("© 2025 Car Rental System | All Rights Reserved");
        footerLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        footerLabel.setForeground(new Color(100, 120, 150));
        footerPanel.add(footerLabel, BorderLayout.WEST);

        mainPanel.add(footerPanel, BorderLayout.SOUTH);

        // Add main panel to frame
        add(mainPanel);

        // Display frame
        setVisible(true);
    }
}
