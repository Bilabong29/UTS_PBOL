package com.mycompany.uts_pbol;

import java.io.*;
import java.security.*;
import java.util.*;

public class UserManager {
    private static final String USER_FILE = "users.dat";
    private Map<String, User> users;

    public UserManager() {
        users = new HashMap<>();
        loadUsers();
    }

    public User getUser(String username) {
        return users.get(username);
    }

    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes());
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean registerUser(String username, String fullName, String email, String password) {
        if (users.containsKey(username)) {
            return false;
        }

        String hashedPassword = hashPassword(password);
        User newUser = new User(username, fullName, email, hashedPassword);
        users.put(username, newUser);
        saveUsers();
        return true;
    }

    public boolean authenticateUser(String username, String password) {
        User user = users.get(username);
        if (user == null) {
            return false;
        }
        String hashedPassword = hashPassword(password);
        return user.getPassword().equals(hashedPassword);
    }

    private void loadUsers() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(USER_FILE))) {
            @SuppressWarnings("unchecked")
            Map<String, User> loadedUsers = (Map<String, User>) ois.readObject();
            users = loadedUsers;
        } catch (FileNotFoundException e) {
            // File doesn't exist yet, start with empty map
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void saveUsers() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(USER_FILE))) {
            oos.writeObject(users);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}