package com.mycompany.uts_pbol;

import java.awt.*;
import javax.swing.*;

public class BankSederhanaFrame extends JFrame {

    public BankSederhanaFrame() {
        initializeFrame();
        
        JPanel backgroundPanel = createBackgroundPanel();
        JPanel bankPanel = createBankPanel();
        backgroundPanel.add(bankPanel, BorderLayout.NORTH);

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(e -> login());
        backgroundPanel.add(loginButton, BorderLayout.SOUTH);

        add(backgroundPanel);
    }

    private void initializeFrame() {
        setTitle("Bank Sederhana");
        setSize(500, 700);
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

    private JLabel createLabel(String text, Font font, Color color) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(font);
        label.setForeground(color);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
    }

    private void login() {
        LoginDialog loginDialog = new LoginDialog(this);
        loginDialog.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BankSederhanaFrame frame = new BankSederhanaFrame();
            frame.setVisible(true);
        });
    }
}