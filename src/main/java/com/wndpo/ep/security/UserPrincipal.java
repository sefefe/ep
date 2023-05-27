package com.wndpo.ep.security;

import com.wndpo.ep.entity.User;
import com.wndpo.ep.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

public class UserPrincipal implements UserDetails {
    private User user;


    public UserPrincipal(User user) {
        super();
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
//        List<SimpleGrantedAuthority> authorities= new ArrayList<>();
//
//              this.user.getRole().getPermissions().stream().map(
//                        permission->authorities.add(new SimpleGrantedAuthority(permission.getPermission()))
//                );

        return Collections.singleton(new SimpleGrantedAuthority(user.getRole()));//authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return user.isNotExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return user.isNotLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return user.isNotExpired();
    }

    @Override
    public boolean isEnabled() {
        return user.isActive();
    }

}
