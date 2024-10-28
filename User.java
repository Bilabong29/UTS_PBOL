package com.mycompany.uts_pbol;

import java.io.Serializable;

public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    private String username;
    private String password;
    private double balance;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.balance = 0.0;
    }

    public String getUsername() { 
        return username; 
    }

    public String getPassword() { 
        return password; 
    }

    public double getBalance() { 
        return balance; 
    }

    public void setBalance(double balance) { 
        this.balance = balance; 
    }
}