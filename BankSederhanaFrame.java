package com.mycompany.uts_pbol;

import java.awt.*;
import javax.swing.*;

public class BankSederhanaFrame extends JFrame {

    public BankSederhanaFrame() {
        initializeFrame();
        
        JPanel backgroundPanel = createBackgroundPanel();
        JPanel bankPanel = createBankPanel();
        JPanel buttonPanel = createButtonPanel();

        backgroundPanel.add(bankPanel, BorderLayout.CENTER);
        backgroundPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(backgroundPanel);
    }

    private void initializeFrame() {
        setTitle("Bank Sederhana");
        setSize(500, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
    }

    private JPanel createBackgroundPanel() {
        JPanel backgroundPanel = new JPanel(new BorderLayout(0, 20));
        backgroundPanel.setBackground(new Color(240, 240, 255));
        backgroundPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        return backgroundPanel;
    }

    private JPanel createBankPanel() {
        JPanel bankPanel = new JPanel();
        bankPanel.setLayout(new BoxLayout(bankPanel, BoxLayout.Y_AXIS));
        bankPanel.setOpaque(false);

        JLabel bankLabel = createLabel("BSA", new Font("Serif", Font.BOLD, 100), Color.BLACK);
        JLabel subtitleLabel = createLabel("Bank Sederhana", new Font("SansSerif", Font.PLAIN, 18), new Color(80, 80, 80));

        bankPanel.add(bankLabel);
        bankPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        bankPanel.add(subtitleLabel);

        return bankPanel;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);

        JButton loginButton = new JButton("Login") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Background color
                g2.setColor(new Color(64, 64, 64));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);

                // Font color and text alignment
                g2.setColor(Color.WHITE);
                g2.setFont(new Font("Arial", Font.BOLD, 16));
                FontMetrics fm = g2.getFontMetrics();
                int textWidth = fm.stringWidth(getText());
                int textHeight = fm.getAscent();
                int x = (getWidth() - textWidth) / 2;
                int y = (getHeight() + textHeight) / 2 - 4;
                g2.drawString(getText(), x, y);

                g2.dispose();
            }

            @Override
            public boolean isContentAreaFilled() {
                return false;
            }
        };

        loginButton.setPreferredSize(new Dimension(120, 40));
        loginButton.setFocusPainted(false);
        loginButton.setBorderPainted(false);

        loginButton.addActionListener(e -> {
            LoginDialog loginDialog = new LoginDialog(this);
            loginDialog.setVisible(true);
        });

        buttonPanel.add(loginButton);
        return buttonPanel;
    }

    private JLabel createLabel(String text, Font font, Color color) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(font);
        label.setForeground(color);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BankSederhanaFrame frame = new BankSederhanaFrame();
            frame.setVisible(true);
        });
    }
}