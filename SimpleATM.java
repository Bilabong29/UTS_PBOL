package com.mycompany.uts_pbol;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class SimpleATM extends JFrame {
    private final User currentUser ;
    private final UserManager userManager;

    private JLabel lblBalance;
    private JTextField txtAmount;
    private JButton btnWithdraw;
    private JButton btnDeposit;
    private JButton btnCheckBalance;
    private JButton btnLogout;

    public SimpleATM(User user, UserManager userManager) {
        this.currentUser  = user;
        this.userManager = userManager;
        
        initializeATM();
    }

    private void initializeATM() {
        setTitle("ATM Sederhana - Welcome " + currentUser.getUsername());
        setSize(500, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(new Color(240, 240, 240));
    
        // Membuat panel utama dengan BorderLayout
        JPanel panelMain = new JPanel(new BorderLayout(10, 10));
        panelMain.setBorder(new EmptyBorder(20, 20, 20, 20));
        panelMain.setBackground(Color.WHITE);
    
        // Panel atas untuk informasi user di kiri atas
        JPanel userInfoPanel = new JPanel();
        userInfoPanel.setLayout(new BoxLayout(userInfoPanel, BoxLayout.Y_AXIS));
        userInfoPanel.setBackground(Color.WHITE);
        userInfoPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
    
        // Label untuk informasi user
        JLabel lblUsername = new JLabel("Username: " + currentUser.getUsername());
        JLabel lblEmail = new JLabel("Email: " + currentUser.getEmail());
    
        // Styling label informasi user
        Font userInfoFont = new Font("Arial", Font.BOLD, 14);
        lblUsername.setFont(userInfoFont);
        lblEmail.setFont(userInfoFont);
    
        // Mengatur alignment label ke kiri
        lblUsername.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblEmail.setAlignmentX(Component.LEFT_ALIGNMENT);
    
        // Menambahkan label ke panel informasi user
        userInfoPanel.add(lblUsername);
        userInfoPanel.add(Box.createVerticalStrut(5)); // Spacing
        userInfoPanel.add(lblEmail);
    
        // Panel tengah untuk menampung saldo dan input
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(Color.WHITE);
    
        // Panel untuk saldo
        lblBalance = new JLabel("Saldo: Rp " + currentUser.getBalance(), SwingConstants.CENTER);
        lblBalance.setFont(new Font("Arial", Font.BOLD, 20));
        lblBalance.setForeground(new Color(51, 51, 51));
        lblBalance.setAlignmentX(Component.CENTER_ALIGNMENT); // Mengatur alignment ke tengah
    
        // Panel untuk input jumlah uang
        JPanel inputPanel = new JPanel();
        inputPanel.setBackground(Color.WHITE);
        inputPanel.setAlignmentX(Component.CENTER_ALIGNMENT); // Mengatur alignment ke tengah
    
        txtAmount = new JTextField(15);
        txtAmount.setFont(new Font("Arial", Font.PLAIN, 16));
        JLabel lblAmount = new JLabel("Jumlah: ");
        lblAmount.setFont(new Font("Arial", Font.PLAIN, 16));
        
        inputPanel.add(lblAmount);
        inputPanel.add(txtAmount);
    
        // Menambahkan komponen ke centerPanel dengan spacing
        centerPanel.add(Box.createVerticalGlue()); // Spacing atas
        centerPanel.add(lblBalance);
        centerPanel.add(Box.createVerticalStrut(20)); // Spacing antara saldo dan input
        centerPanel.add(inputPanel);
        centerPanel.add(Box.createVerticalGlue()); // Spacing bawah
    
        // Panel bawah untuk tombol
        btnWithdraw = createStyledButton("Tarik Tunai");
        btnDeposit = createStyledButton("Setor Tunai");
        btnCheckBalance = createStyledButton("Cek Saldo");
        btnLogout = createStyledButton("Logout");
    
        JPanel panelButtons = new JPanel(new GridLayout(2, 2, 10, 10));
        panelButtons.add(btnWithdraw);
        panelButtons.add(btnDeposit);
        panelButtons.add(btnCheckBalance);
        panelButtons.add(btnLogout);
        panelButtons.setBackground(Color.WHITE);
    
        // Menambahkan panel ke panel utama
        panelMain.add(userInfoPanel, BorderLayout.NORTH);
        panelMain.add(centerPanel, BorderLayout.CENTER);
        panelMain.add(panelButtons, BorderLayout.SOUTH);
    
        // Menambahkan panel utama ke frame
        add(panelMain);
    
        // Event handling
        btnWithdraw.addActionListener(new WithdrawHandler());
        btnDeposit.addActionListener(new DepositHandler());
        btnCheckBalance.addActionListener(new CheckBalanceHandler());
        btnLogout.addActionListener(e -> handleLogout());
    }
    
    private RoundedButton createStyledButton(String text) {
    RoundedButton button = new RoundedButton(text, 20); // radius 20
    button.setFont(new Font("Arial", Font.BOLD, 14));
    button.setBackground(new Color(64, 64, 64)); // Dark grey color
    button.setForeground(Color.WHITE);
    button.setFocusPainted(false);
    button.setBorderPainted(false);
    button.setPreferredSize(new Dimension(150, 40));
    button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    
    // Hover effect
    button.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseEntered(java.awt.event.MouseEvent evt) {
            button.setBackground(new Color(96, 96, 96)); // Lighter grey for hover
        }

        public void mouseExited(java.awt.event.MouseEvent evt) {
            button.setBackground(new Color(64, 64, 64)); // Back to original dark grey
        }
    });
    
    return button;
}

    private void handleLogout() {
        int confirm = JOptionPane.showConfirmDialog(
            this,
 "Apakah Anda yakin ingin logout?",
            "Konfirmasi Logout",
            JOptionPane.YES_NO_OPTION
        );
        
        if (confirm == JOptionPane.YES_OPTION) {
            dispose();
            BankSederhanaFrame loginFrame = new BankSederhanaFrame();
            loginFrame.setVisible(true);
        }
    }

    // Handler untuk penarikan
    private class WithdrawHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                double amount = Double.parseDouble(txtAmount.getText());
                if (amount > currentUser.getBalance()) {
                    JOptionPane.showMessageDialog(null, "Saldo tidak mencukupi!");
                } else if (amount <= 0) {
                    JOptionPane.showMessageDialog(null, "Jumlah penarikan harus lebih dari 0!");
                } else {
                    currentUser.setBalance(currentUser.getBalance() - amount);
                    updateBalanceDisplay();
                    JOptionPane.showMessageDialog(null, "Penarikan berhasil!");
                    txtAmount.setText("");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Masukkan jumlah yang valid!");
            }
        }
    }

    // Handler untuk penyetoran
    private class DepositHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                double amount = Double.parseDouble(txtAmount.getText());
                if (amount <= 0) {
                    JOptionPane.showMessageDialog(null, "Jumlah setoran harus lebih dari 0!");
                } else {
                    currentUser.setBalance(currentUser.getBalance() + amount);
                    updateBalanceDisplay();
                    JOptionPane.showMessageDialog(null, "Setoran berhasil!");
                    txtAmount.setText("");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Masukkan jumlah yang valid!");
            }
        }
    }

    // Handler untuk cek saldo
    private class CheckBalanceHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(null, 
                "Saldo " + currentUser.getUsername() + " saat ini: Rp " + currentUser.getBalance());
        }
    }

    private void updateBalanceDisplay() {
        lblBalance.setText("Saldo: Rp " + currentUser.getBalance());
    }
}