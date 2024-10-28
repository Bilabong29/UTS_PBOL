package com.mycompany.uts_pbol;

import javax.swing.*;
import java.awt.*;

public class LoginDialog extends JDialog {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private final UserManager userManager;
    private final Frame parentFrame;

    public LoginDialog(Frame parent) {
        super(parent, "Login", true);
        this.parentFrame = parent;
        this.userManager = new UserManager();
        
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

        // Buttons panel
        JPanel buttonPanel = new JPanel();
        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Register");
        JButton cancelButton = new JButton("Cancel");
        
        loginButton.addActionListener(e -> handleLogin());
        registerButton.addActionListener(e -> openRegistrationDialog());
        cancelButton.addActionListener(e -> dispose());
        
        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);
        buttonPanel.add(cancelButton);
        
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.gridwidth = 2;
        add(buttonPanel, gbc);

        pack();
    }

    private void handleLogin() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        if (userManager.authenticateUser(username, password)) {
            User user = userManager.getUser(username); // Add this method to UserManager
            JOptionPane.showMessageDialog(this,
                "Login successful!",
                "Success",
                JOptionPane.INFORMATION_MESSAGE);
            
            // Close the login dialog and parent frame
            dispose();
            parentFrame.dispose();
            
            // Open ATM window
            SimpleATM atm = new SimpleATM(user, userManager);
            atm.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this,
                "Invalid username or password.",
                "Login Failed",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void openRegistrationDialog() {
        RegistrationDialog registrationDialog = new RegistrationDialog(this, userManager);
        registrationDialog.setVisible(true);
    }
}