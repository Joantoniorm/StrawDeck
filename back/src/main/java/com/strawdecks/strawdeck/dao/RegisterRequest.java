package com.strawdecks.strawdeck.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
public class RegisterRequest {
    private String username;
    private String gmail;
    private String password;
}
