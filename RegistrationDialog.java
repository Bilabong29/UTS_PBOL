package com.mycompany.uts_pbol;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

public class RegistrationDialog extends JDialog {
    private JTextField usernameField, fullNameField, emailField;
    private JPasswordField passwordField, confirmPasswordField;
    private final UserManager userManager;

    public RegistrationDialog(Window parent, UserManager userManager) {
        super(parent, "Pendaftaran", ModalityType.APPLICATION_MODAL);
        this.userManager = userManager;
        
        setSize(400, 650);
        initializeComponents();
        setLocationRelativeTo(parent);
    }

    private void initializeComponents() {
        // Main panel with BoxLayout
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        // Header Label
        JLabel headerLabel = new JLabel("Daftar");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 20));
        headerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(headerLabel);

        JLabel subheaderLabel = new JLabel("Silahkan membuat akun baru anda!");
        subheaderLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(subheaderLabel);

        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Username Field
        usernameField = new JTextField(20);
        JPanel usernamePanel = createFieldPanel("Ex : johndoe", usernameField);
        mainPanel.add(usernamePanel);

        // Nama Lengkap Field
        fullNameField = new JTextField(20);
        JPanel fullNamePanel = createFieldPanel("Ex : John Doe", fullNameField);
        mainPanel.add(fullNamePanel);

        // Email Field
        emailField = new JTextField(20);
        JPanel emailPanel = createFieldPanel("Ex : johndoe@youremail.com", emailField);
        mainPanel.add(emailPanel);

        // Password Field
        passwordField = new JPasswordField(20);
        JPanel passwordPanel = createPasswordPanel("Kata sandi minimal 8 digit karakter", passwordField);
        mainPanel.add(passwordPanel);

        // Confirm Password Field
        confirmPasswordField = new JPasswordField(20);
        JPanel confirmPasswordPanel = createPasswordPanel("Masukan ulang kata sandi anda", confirmPasswordField);
        mainPanel.add(confirmPasswordPanel);

        // Terms and Conditions checkbox
        JCheckBox termsCheckbox = new JCheckBox("Saya setuju dengan syarat dan ketentuan");
        termsCheckbox.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(termsCheckbox);

        // Register button
        JButton registerButton = createStyledButton("Daftar");
        registerButton.addActionListener(e -> {
            if (!termsCheckbox.isSelected()) {
                JOptionPane.showMessageDialog(this, 
                    "Anda harus menyetujui syarat dan ketentuan untuk melanjutkan.", 
                    "Warning", 
                    JOptionPane.WARNING_MESSAGE);
            } else {
                handleRegistration();
            }
        });
        registerButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(registerButton);

        // Login link
        JLabel loginLabel = new JLabel("Sudah punya akun? Masuk");
        loginLabel.setForeground(new Color(66, 133, 244));
        loginLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        loginLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
            }
        });

        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(loginLabel);

        add(mainPanel);
        setResizable(false);
    }

    private JPanel createFieldPanel(String placeholder, JTextField textField) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        panel.setBackground(Color.WHITE);
        panel.setPreferredSize(new Dimension(300, 50)); // Tinggi diubah dari 40 ke 50
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                new EmptyBorder(15, 15, 15, 15)  // Padding diperbesar
        ));
    
        textField.setText(placeholder);
        textField.setForeground(Color.GRAY);
        textField.setBorder(null);
        textField.setPreferredSize(new Dimension(260, 25)); // Set ukuran text field
        textField.setFont(new Font("Arial", Font.PLAIN, 14)); // Ukuran font diperbesar
    
        textField.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (textField.getText().equals(placeholder)) {
                    textField.setText("");
                    textField.setForeground(Color.BLACK);
                    panel.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(66, 133, 244), 2), // Warna border saat focus
                        new EmptyBorder(15, 15, 15, 15)
                    ));
                }
            }
    
            public void focusLost(FocusEvent e) {
                if (textField.getText().isEmpty()) {
                    textField.setForeground(Color.GRAY);
                    textField.setText(placeholder);
                    panel.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(200, 200, 200)),
                        new EmptyBorder(15, 15, 15, 15)
                    ));
                }
            }
        });
    
        panel.add(textField);
        return panel;
    }
    
    private JPanel createPasswordPanel(String placeholder, JPasswordField passwordField) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        panel.setBackground(Color.WHITE);
        panel.setPreferredSize(new Dimension(300, 50)); // Tinggi diubah dari 40 ke 50
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                new EmptyBorder(15, 15, 15, 15)  // Padding diperbesar
        ));
    
        passwordField.setText(placeholder);
        passwordField.setForeground(Color.GRAY);
        passwordField.setEchoChar((char) 0);
        passwordField.setBorder(null);
        passwordField.setPreferredSize(new Dimension(260, 25)); // Set ukuran password field
        passwordField.setFont(new Font("Arial", Font.PLAIN, 14)); // Ukuran font diperbesar
    
        passwordField.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (new String(passwordField.getPassword()).equals(placeholder)) {
                    passwordField.setText("");
                    passwordField.setForeground(Color.BLACK);
                    passwordField.setEchoChar('•');
                    panel.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(66, 133, 244), 2), // Warna border saat focus
                        new EmptyBorder(15, 15, 15, 15)
                    ));
                }
            }
    
            public void focusLost(FocusEvent e) {
                if (new String(passwordField.getPassword()).isEmpty()) {
                    passwordField.setForeground(Color.GRAY);
                    passwordField.setText(placeholder);
                    passwordField.setEchoChar((char) 0);
                    panel.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(200, 200, 200)),
                        new EmptyBorder(15, 15, 15, 15)
                    ));
                }
            }
        });
    
        panel.add(passwordField);
        return panel;
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(64, 64, 64));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                g2.setColor(Color.WHITE);
                g2.setFont(new Font("Arial", Font.BOLD, 14));
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

        button.setPreferredSize(new Dimension(170, 35));
        button.setFocusPainted(false);
        button.setBorderPainted(false);

        return button;
    }

    private void handleRegistration() {
        String username = usernameField.getText();
        String fullName = fullNameField.getText();
        String email = emailField.getText();
        String password = new String(passwordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());

        if (username.isEmpty() || fullName.isEmpty() || email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "All fields are required.",
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

        if (userManager.registerUser(username, fullName, email, password)) {
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