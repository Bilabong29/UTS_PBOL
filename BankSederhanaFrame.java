    import java.awt.*;
    import javax.swing.*;

    public class BankSederhanaFrame extends JFrame {

        public BankSederhanaFrame() {
            // Set frame properties
            setTitle("Bank Sederhana");
            setSize(500, 700);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLocationRelativeTo(null);
            setLayout(new BorderLayout());

            // Background panel to hold all components with padding
            JPanel backgroundPanel = new JPanel(new BorderLayout(0, 20));
            backgroundPanel.setBackground(new Color(240, 240, 255));
            backgroundPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

            // Panel for the logo and bank name
            JPanel bankPanel = new JPanel();
            bankPanel.setLayout(new BoxLayout(bankPanel, BoxLayout.Y_AXIS));
            bankPanel.setOpaque(false);

            // Bank name label
            JLabel bankLabel = new JLabel("BSA", SwingConstants.CENTER);
            bankLabel.setFont(new Font("Serif", Font.BOLD, 70));
            bankLabel.setForeground(new Color(0, 55, 0));
            bankLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            // Subtitle label
            JLabel subtitleLabel = new JLabel("Bank Sederhana");
            subtitleLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
            subtitleLabel.setForeground(new Color(80, 80, 80));
            subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            // Add labels to the logo panel with spacing
            bankPanel.add(bankLabel);
            bankPanel.add(Box.createRigidArea(new Dimension(0, 10)));
            bankPanel.add(subtitleLabel);

            // Panel for the login button
            JPanel buttonPanel = new JPanel();
            buttonPanel.setOpaque(false);

            // Custom "Login" button
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

            // Add Action Listener for button click
            loginButton.addActionListener(e -> 
                JOptionPane.showMessageDialog(null, "Login Button Clicked!")
            );

            buttonPanel.add(loginButton);

            // Add components to the background panel
            backgroundPanel.add(bankPanel, BorderLayout.CENTER);
            backgroundPanel.add(buttonPanel, BorderLayout.SOUTH);

            // Add the background panel to the frame
            add(backgroundPanel);
        }

        public static void main(String[] args) {
            SwingUtilities.invokeLater(() -> {
                BankSederhanaFrame frame = new BankSederhanaFrame();
                frame.setVisible(true);
            });
        }
    }
