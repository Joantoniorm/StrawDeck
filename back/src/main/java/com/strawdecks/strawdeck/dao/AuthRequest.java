package com.strawdecks.strawdeck.dao;

import lombok.Data;

@Data
public class AuthRequest {
    private String username;
    private String password;
}
