package com.wndpo.ep.security;

import org.springframework.security.core.GrantedAuthority;

import java.util.Set;

public enum ApplicationPermission{

        SITE_READ("site:read"),
        SITE_WRITE("site:write"),
        ANTENNA_READ("antenna:read"),
        ANTENNA_WRITE("antenna:write");

        private final String permision;
        ApplicationPermission(String permision) {
        this.permision = permision;
        }
       public  String getPermission(){
        return permision;
        }

        }
