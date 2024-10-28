package com.mycompany.uts_pbol;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class SimpleATM extends JFrame {
    private final User currentUser;
    private final UserManager userManager;

    private JLabel lblBalance;
    private JTextField txtAmount;
    private JButton btnWithdraw;
    private JButton btnDeposit;
    private JButton btnCheckBalance;
    private JButton btnLogout;

    public SimpleATM(User user, UserManager userManager) {
        this.currentUser = user;
        this.userManager = userManager;
        
        initializeATM();
    }

    private void initializeATM() {
        setTitle("ATM Sederhana - Welcome " + currentUser.getUsername());
        setSize(500, 350);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Membuat panel utama dengan BorderLayout
        JPanel panelMain = new JPanel(new BorderLayout(10, 10));
        panelMain.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Panel atas untuk menampilkan saldo
        lblBalance = new JLabel("Saldo: Rp " + currentUser.getBalance(), SwingConstants.CENTER);
        lblBalance.setFont(new Font("Arial", Font.BOLD, 16));
        JPanel panelBalance = new JPanel();
        panelBalance.add(lblBalance);

        // Panel tengah untuk input jumlah uang
        txtAmount = new JTextField(15);
        JPanel panelAmount = new JPanel(new FlowLayout());
        panelAmount.add(new JLabel("Jumlah: "));
        panelAmount.add(txtAmount);

        // Panel bawah untuk tombol
        btnWithdraw = new JButton("Tarik Tunai");
        btnDeposit = new JButton("Setor Tunai");
        btnCheckBalance = new JButton("Cek Saldo");
        btnLogout = new JButton("Logout");

        JPanel panelButtons = new JPanel(new GridLayout(2, 2, 10, 10));
        panelButtons.add(btnWithdraw);
        panelButtons.add(btnDeposit);
        panelButtons.add(btnCheckBalance);
        panelButtons.add(btnLogout);

        // Menambahkan panel ke panel utama
        panelMain.add(panelBalance, BorderLayout.NORTH);
        panelMain.add(panelAmount, BorderLayout.CENTER);
        panelMain.add(panelButtons, BorderLayout.SOUTH);

        // Menambahkan panel utama ke frame
        add(panelMain);

        // Event handling
        btnWithdraw.addActionListener(new WithdrawHandler());
        btnDeposit.addActionListener(new DepositHandler());
        btnCheckBalance.addActionListener(new CheckBalanceHandler());
        btnLogout.addActionListener(e -> handleLogout());
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