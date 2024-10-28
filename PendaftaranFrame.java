
import java.awt.*;
import java.awt.event.*; // Add this import
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class PendaftaranFrame extends JFrame {

    public PendaftaranFrame() {
        setTitle("Pendaftaran");
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

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
        JPanel usernamePanel = createFieldPanel("Ex : johndoe");
        mainPanel.add(usernamePanel);

        // Nama Lengkap Field
        JPanel fullNamePanel = createFieldPanel("Ex : John Doe");
        mainPanel.add(fullNamePanel);

        // Email Field
        JPanel emailPanel = createFieldPanel("Ex : johndoe@youremail.com");
        mainPanel.add(emailPanel);

        // Kata Sandi Field
        JPanel passwordPanel = createPasswordPanel("Kata sandi minimal 8 digit karakter");
        mainPanel.add(passwordPanel);

        // Konfirmasi Kata Sandi Field
        JPanel confirmPasswordPanel = createPasswordPanel("Masukan ulang kata sandi anda");
        mainPanel.add(confirmPasswordPanel);

        // Terms and Conditions checkbox
        JCheckBox termsCheckbox = new JCheckBox("Saya setuju dengan syarat dan ketentuan");
        termsCheckbox.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(termsCheckbox);

        // Register button
        JButton registerButton = new JButton("Daftar") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(64, 64, 64)); // Button color
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                g2.setColor(Color.WHITE); // Text color
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

        registerButton.setPreferredSize(new Dimension(170, 35));
        registerButton.setFocusPainted(false);
        registerButton.setBorderPainted(false);
        registerButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        registerButton.addActionListener(e -> {
            if (!termsCheckbox.isSelected()) {
                JOptionPane.showMessageDialog(this, "Anda harus menyetujui syarat dan ketentuan untuk melanjutkan.", "Warning", JOptionPane.WARNING_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Registrasi Berhasil!", "Success", JOptionPane.INFORMATION_MESSAGE);
                dispose(); // Close the frame after registration
            }
        });

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
                new LoginFrame().setVisible(true); // Open registration frame
                PendaftaranFrame.this.dispose();
            }
        });

        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(loginLabel);

        // Add main panel to frame
        add(mainPanel);
    }

    private JPanel createFieldPanel(String placeholder) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        panel.setBackground(Color.WHITE);
        panel.setPreferredSize(new Dimension(300, 40));
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                new EmptyBorder(10, 10, 10, 10)
        ));

        JTextField textField = new JTextField(placeholder, 20);
        textField.setForeground(Color.GRAY);
        textField.setBorder(null);
        textField.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (textField.getText().equals(placeholder)) {
                    textField.setText("");
                    textField.setForeground(Color.BLACK);
                }
            }

            public void focusLost(FocusEvent e) {
                if (textField.getText().isEmpty()) {
                    textField.setForeground(Color.GRAY);
                    textField.setText(placeholder);
                }
            }
        });

        panel.add(textField);
        return panel;
    }

    private JPanel createPasswordPanel(String placeholder) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        panel.setBackground(Color.WHITE);
        panel.setPreferredSize(new Dimension(300, 40));
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                new EmptyBorder(10, 10, 10, 10)
        ));

        JPasswordField passwordField = new JPasswordField(placeholder, 20);
        passwordField.setForeground(Color.GRAY);
        passwordField.setEchoChar((char) 0);
        passwordField.setBorder(null);

        passwordField.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (new String(passwordField.getPassword()).equals(placeholder)) {
                    passwordField.setText("");
                    passwordField.setForeground(Color.BLACK);
                    passwordField.setEchoChar('•');
                }
            }

            public void focusLost(FocusEvent e) {
                if (new String(passwordField.getPassword()).isEmpty()) {
                    passwordField.setForeground(Color.GRAY);
                    passwordField.setText(placeholder);
                    passwordField.setEchoChar((char) 0);
                }
            }
        });

        panel.add(passwordField);
        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PendaftaranFrame pendaftaranFrame = new PendaftaranFrame();
            pendaftaranFrame.setVisible(true);
        });
    }
}
