package com.equp.back.backend.model;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;

@Slf4j
public class JWT {
    private String token = "tokentokentokentokentokentokentoken";

    public JWT() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
