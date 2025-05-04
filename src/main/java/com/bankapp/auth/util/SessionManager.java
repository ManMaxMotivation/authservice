package com.bankapp.auth.util;

import com.bankapp.auth.model.Client;
import org.springframework.stereotype.Component;

@Component
public class SessionManager {
    private Client loggedInClient;

    public void login(Client client) {
        this.loggedInClient = client;
    }

    public Client getLoggedInClient() {
        return loggedInClient;
    }

    public void logout() {
        this.loggedInClient = null;
    }

    public boolean isLoggedIn() {
        return loggedInClient != null;
    }
    // В классе SessionManager
    public String getLoginStatus() {
        if (isLoggedIn()) {
            return "аутентифицирован";
        } else {
            return "не аутентифицирован";
        }
    }
}