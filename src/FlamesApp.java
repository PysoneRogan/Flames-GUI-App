import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class FlamesApp {
    public static void main(String[] args) {
        JFrame frame = new JFrame("FLAMES CALCULATOR");
        frame.setSize(400, 420);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        // Set Icon
        try {
            Image icon = Toolkit.getDefaultToolkit().getImage(FlamesApp.class.getResource("flames.png"));
            frame.setIconImage(icon);
        } catch (Exception e) {
            System.out.println("Icon not found");
        }

        // Fonts and Colors
        Font labelFont = new Font("Arial", Font.BOLD, 14);
        Font resultFont = new Font("Arial", Font.BOLD, 16);
        Color bgColor = new Color(255, 245, 230);
        Color btnColor = new Color(255, 128, 128);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        panel.setBackground(bgColor);

        JLabel label1 = new JLabel("Enter Name 1:");
        JTextField field1 = new JTextField("e.g. Rog");
        field1.setForeground(Color.GRAY);
        field1.setMaximumSize(new Dimension(200, 30));
        field1.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel label2 = new JLabel("Enter Name 2:");
        JTextField field2 = new JTextField("e.g. Ria");
        field2.setForeground(Color.GRAY);
        field2.setMaximumSize(new Dimension(200, 30));
        field2.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Placeholder logic
        setupPlaceholder(field1, "e.g. Rog");
        setupPlaceholder(field2, "e.g. Ria");

        field1.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY, 1, true),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));

        field2.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY, 1, true),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));

        JButton checkButton = new JButton("GO FLAMES");
        JLabel result = new JLabel("Result will appear here", SwingConstants.CENTER);
        JLabel creditLabel = new JLabel("Created by Pysone Rogan J", SwingConstants.CENTER);

        // Styling
        label1.setFont(labelFont);
        label2.setFont(labelFont);
        result.setFont(resultFont);
        result.setForeground(Color.BLUE);
        creditLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        creditLabel.setForeground(Color.DARK_GRAY);

        label1.setAlignmentX(Component.CENTER_ALIGNMENT);
        label2.setAlignmentX(Component.CENTER_ALIGNMENT);
        checkButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        result.setAlignmentX(Component.CENTER_ALIGNMENT);
        creditLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        checkButton.setBackground(btnColor);
        checkButton.setForeground(Color.WHITE);
        checkButton.setFocusPainted(false);

        // Action
        checkButton.addActionListener(e -> {
            String name1 = field1.getText().toLowerCase().replaceAll(" ", "");
            String name2 = field2.getText().toLowerCase().replaceAll(" ", "");

            if (name1.isEmpty() || name1.equals("e.g.Rog") || name2.isEmpty() || name2.equals("e.g.Ria")) {
                result.setText("Please enter both names!");
                return;
            }

            String flamesResult = FlamesCalculator.getResult(name1, name2);
            result.setText("Relationship: " + flamesResult);
        });

        // Layout
        panel.add(label1);
        panel.add(field1);
        panel.add(Box.createVerticalStrut(10));
        panel.add(label2);
        panel.add(field2);
        panel.add(Box.createVerticalStrut(10));
        panel.add(checkButton);
        panel.add(Box.createVerticalStrut(10));
        panel.add(result);
        panel.add(Box.createVerticalStrut(20));
        panel.add(creditLabel);

        frame.add(panel);
        frame.setVisible(true);
    }

    private static void setupPlaceholder(JTextField field, String placeholder) {
        field.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (field.getText().equals(placeholder)) {
                    field.setText("");
                    field.setForeground(Color.BLACK);
                }
            }
            public void focusLost(FocusEvent e) {
                if (field.getText().isEmpty()) {
                    field.setText(placeholder);
                    field.setForeground(Color.GRAY);
                }
            }
        });
    }
}

class FlamesCalculator {
    public static String getResult(String name1, String name2) {
        StringBuilder sb1 = new StringBuilder(name1);
        StringBuilder sb2 = new StringBuilder(name2);

        for (int i = 0; i < sb1.length(); i++) {
            char ch = sb1.charAt(i);
            for (int j = 0; j < sb2.length(); j++) {
                if (ch == sb2.charAt(j)) {
                    sb1.deleteCharAt(i);
                    sb2.deleteCharAt(j);
                    i--;
                    break;
                }
            }
        }

        int rem = sb1.length() + sb2.length();
        if (rem == 0) return "Same name, testing, huh?";

        StringBuilder flames = new StringBuilder("FLAMES");
        int index = 0;
        while (flames.length() > 1) {
            index = (index + rem - 1) % flames.length();
            flames.deleteCharAt(index);
        }

        return switch (flames.toString()) {
            case "F" -> "Friends";
            case "L" -> "Love";
            case "A" -> "Affection";
            case "M" -> "Marriage";
            case "E" -> "Enemies";
            case "S" -> "Siblings";
            default -> "Unknown";
        };
    }
}
