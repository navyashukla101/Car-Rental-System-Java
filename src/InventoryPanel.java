import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InventoryPanel extends JPanel {
    private CarRentalSystem rentalSystem;
    private JTable carTable;
    private DefaultTableModel tableModel;

    public InventoryPanel(CarRentalSystem rentalSystem) {
        this.rentalSystem = rentalSystem;

        setBackground(new Color(240, 245, 250));
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Title panel
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(240, 245, 250));
        titlePanel.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("📋 Vehicle Inventory");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setForeground(new Color(25, 118, 210));
        titlePanel.add(titleLabel, BorderLayout.WEST);

        add(titlePanel, BorderLayout.NORTH);

        // Create table model
        tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tableModel.addColumn("Car ID");
        tableModel.addColumn("Brand");
        tableModel.addColumn("Model");
        tableModel.addColumn("Daily Rate");
        tableModel.addColumn("Availability");

        // Create table
        carTable = new JTable(tableModel);
        carTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        carTable.setRowHeight(30);
        carTable.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        carTable.setShowGrid(true);
        carTable.setGridColor(new Color(220, 225, 235));

        // Style table header
        JTableHeader header = carTable.getTableHeader();
        header.setBackground(new Color(25, 118, 210));
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Segoe UI", Font.BOLD, 12));
        header.setPreferredSize(new Dimension(0, 35));

        // Alternate row colors
        carTable.setDefaultRenderer(Object.class, new DefaultTableRenderer());

        JScrollPane scrollPane = new JScrollPane(carTable);
        scrollPane.setBackground(Color.WHITE);
        scrollPane.getViewport().setBackground(Color.WHITE);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(180, 190, 210), 1));
        add(scrollPane, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(240, 245, 250));
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));

        JButton refreshButton = createStyledButton("🔄 Refresh Inventory", new Color(25, 118, 210));
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTable();
            }
        });
        buttonPanel.add(refreshButton);

        add(buttonPanel, BorderLayout.SOUTH);

        // Initial load
        updateTable();
    }

    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setPreferredSize(new Dimension(180, 40));
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

    private void updateTable() {
        tableModel.setRowCount(0);

        for (Car car : rentalSystem.cars) {
            Object[] row = {
                    car.getCarId(),
                    car.getBrand(),
                    car.getModel(),
                    String.format("$%.2f", car.getBasePricePerDay()),
                    car.isAvailable() ? "✓ Available" : "✗ Rented"
            };
            tableModel.addRow(row);
        }
    }

    // Custom table cell renderer for alternating row colors
    private static class DefaultTableRenderer extends javax.swing.table.DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                boolean hasFocus, int row, int column) {
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            if (!isSelected) {
                if (row % 2 == 0) {
                    c.setBackground(Color.WHITE);
                } else {
                    c.setBackground(new Color(245, 248, 252));
                }
            } else {
                c.setBackground(new Color(200, 220, 255));
            }

            c.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            ((javax.swing.JLabel) c).setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

            return c;
        }
    }
}
