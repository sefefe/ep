package com.wndpo.ep.DTO;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {

    private Boolean authenticated;
    private String username;
    private String token;
    private List<String> access;
    private String role;
    private Boolean firstlogin;
    private Boolean isActive;
    private Boolean isAcountNotBlocked;
    private Boolean isAcountNotExpired;

}
