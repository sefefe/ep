package com.wndpo.ep.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
    private String username;
    private String password;
    private boolean existing;
    private boolean hasWriteAccess;
    private String[] regions;
    private String role;
}
