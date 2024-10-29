package com.mycompany.uts_pbol;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

public class LoginDialog extends JDialog {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private final UserManager userManager;
    private final Frame parentFrame;

    public LoginDialog(Frame parent) {
        super(parent, "Login", true);
        this.parentFrame = parent;
        this.userManager = new UserManager();
        
        setSize(400, 500);
        initializeComponents();
        setLocationRelativeTo(parent);
    }

    private void initializeComponents() {
        // Panel utama dengan BoxLayout
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(new Color(240, 240, 255));
        mainPanel.setBorder(new EmptyBorder(30, 30, 30, 30));

        // Ikon profil
        JLabel iconLabel = new JLabel("\uD83D\uDC64"); // Unicode user icon
        iconLabel.setFont(new Font("Dialog", Font.PLAIN, 70));
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(iconLabel);

        // Spasi
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Panel untuk fields
        JPanel fieldsPanel = new JPanel();
        fieldsPanel.setLayout(new GridBagLayout());
        fieldsPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 0, 10, 0);

        // Username field dengan placeholder
        JPanel usernamePanel = createFieldPanel("\uD83D\uDC64", "Username");
        usernameField = (JTextField) ((JPanel)usernamePanel.getComponent(1)).getComponent(0);
        gbc.gridx = 0;
        gbc.gridy = 0;
        fieldsPanel.add(usernamePanel, gbc);

        // Password field dengan placeholder
        JPanel passwordPanel = createFieldPanel("\uD83D\uDD12", "Password");
        passwordField = new JPasswordField("Password");
        passwordField.setBorder(null);
        passwordField.setForeground(new Color(150, 150, 150));
        passwordField.setEchoChar((char) 0);
        ((JPanel)passwordPanel.getComponent(1)).remove(0);
        ((JPanel)passwordPanel.getComponent(1)).add(passwordField);
        setupPasswordFieldBehavior(passwordField);
        gbc.gridy = 1;
        fieldsPanel.add(passwordPanel, gbc);

        mainPanel.add(fieldsPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Login button
        JButton loginButton = createStyledButton("Login");
        loginButton.addActionListener(e -> handleLogin());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.add(loginButton);
        mainPanel.add(buttonPanel);

        // Register link panel
        JPanel registerPanel = new JPanel();
        registerPanel.setOpaque(false);
        
        JLabel signupLabel = new JLabel("Belum memiliki akun? ");
        signupLabel.setForeground(new Color(100, 100, 100));
        
        JLabel registerLabel = new JLabel("Daftar Sekarang");
        registerLabel.setForeground(new Color(66, 133, 244));
        registerLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        registerLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                openRegistrationDialog();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                registerLabel.setText("<html><u>Daftar Sekarang</u></html>");
            }

            @Override
            public void mouseExited(MouseEvent e) {
                registerLabel.setText("Daftar Sekarang");
            }
        });

        registerPanel.add(signupLabel);
        registerPanel.add(registerLabel);
        mainPanel.add(registerPanel);

        add(mainPanel);
        setResizable(false);
    }

    private JPanel createFieldPanel(String icon, String placeholder) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        panel.setBackground(Color.WHITE);
        panel.setPreferredSize(new Dimension(300, 40));
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                new EmptyBorder(10, 10, 10, 10)
        ));

        JLabel iconLabel = new JLabel(icon);
        iconLabel.setForeground(new Color(150, 150, 150));
        panel.add(iconLabel);

        JPanel fieldPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        fieldPanel.setBackground(Color.WHITE);
        
        JTextField field = new JTextField(placeholder, 20);
        field.setBorder(null);
        field.setForeground(new Color(150, 150, 150));
        setupFieldBehavior(field, placeholder);
        
        fieldPanel.add(field);
        panel.add(fieldPanel);

        return panel;
    }

    private void setupFieldBehavior(JTextField field, String placeholder) {
        field.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (field.getText().equals(placeholder)) {
                    field.setText("");
                    field.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (field.getText().isEmpty()) {
                    field.setForeground(new Color(150, 150, 150));
                    field.setText(placeholder);
                }
            }
        });
    }

    private void setupPasswordFieldBehavior(JPasswordField field) {
        field.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (String.valueOf(field.getPassword()).equals("Password")) {
                    field.setText("");
                    field.setForeground(Color.BLACK);
                    field.setEchoChar('•');
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (String.valueOf(field.getPassword()).isEmpty()) {
                    field.setForeground(new Color(150, 150, 150));
                    field.setText("Password");
                    field.setEchoChar((char) 0);
                }
            }
        });
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
                g2.setFont(new Font("Arial", Font.BOLD, 16));
                FontMetrics fm = g2 .getFontMetrics();
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

        button.setPreferredSize(new Dimension(120, 40));
        button.setFocusPainted(false);
        button.setBorderPainted(false);

        return button;
    }

    private void handleLogin() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        if (userManager.authenticateUser(username, password)) {
            User user = userManager.getUser(username);
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