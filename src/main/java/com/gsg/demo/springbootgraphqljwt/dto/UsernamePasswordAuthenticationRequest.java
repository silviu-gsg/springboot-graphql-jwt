package com.gsg.demo.springbootgraphqljwt.dto;

import lombok.Data;

@Data
public class UsernamePasswordAuthenticationRequest {
    private String username;
    private String password;
}
