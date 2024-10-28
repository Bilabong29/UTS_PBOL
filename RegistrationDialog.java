package com.mycompany.uts_pbol;

import javax.swing.*;
import java.awt.*;

public class RegistrationDialog extends JDialog {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private final UserManager userManager;

    public RegistrationDialog(Window parent, UserManager userManager) {
        super(parent, "Register", ModalityType.APPLICATION_MODAL);
        this.userManager = userManager;
        
        initializeComponents();
        setLocationRelativeTo(parent);
    }

    private void initializeComponents() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Username field
        gbc.gridx = 0; gbc.gridy = 0;
        add(new JLabel("Username:"), gbc);
        gbc.gridx = 1;
        usernameField = new JTextField(20);
        add(usernameField, gbc);

        // Password field
        gbc.gridx = 0; gbc.gridy = 1;
        add(new JLabel("Password:"), gbc);
        gbc.gridx = 1;
        passwordField = new JPasswordField(20);
        add(passwordField, gbc);

        // Confirm Password field
        gbc.gridx = 0; gbc.gridy = 2;
        add(new JLabel("Confirm Password:"), gbc);
        gbc.gridx = 1;
        confirmPasswordField = new JPasswordField(20);
        add(confirmPasswordField, gbc);

        // Buttons panel
        JPanel buttonPanel = new JPanel();
        JButton registerButton = new JButton("Register");
        JButton cancelButton = new JButton("Cancel");
        
        registerButton.addActionListener(e -> handleRegistration());
        cancelButton.addActionListener(e -> dispose());
        
        buttonPanel.add(registerButton);
        buttonPanel.add(cancelButton);
        
        gbc.gridx = 0; gbc.gridy = 3;
        gbc.gridwidth = 2;
        add(buttonPanel, gbc);

        pack();
    }

    private void handleRegistration() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Username and password cannot be empty.",
                "Registration Failed",
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this,
                "Passwords do not match.",
                "Registration Failed",
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (userManager.registerUser(username, password)) {
            JOptionPane.showMessageDialog(this,
                "Registration successful!",
                "Success",
                JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this,
                "Username already exists.",
                "Registration Failed",
                JOptionPane.ERROR_MESSAGE);
        }
    }
}