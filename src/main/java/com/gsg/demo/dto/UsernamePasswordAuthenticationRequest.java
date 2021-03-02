package com.gsg.demo.dto;

import lombok.Data;

@Data
public class UsernamePasswordAuthenticationRequest {
    private String username;
    private String password;
}
