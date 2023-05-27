package com.wndpo.ep.service;

import com.wndpo.ep.entity.User;
import com.wndpo.ep.repository.UserRepository;
import com.wndpo.ep.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {

    @Autowired
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

              User user = userRepository.findByUserName(username);

       if(user ==null){
       throw  new UsernameNotFoundException("user not found");
       }
      else {
//           Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
//           user.getRole().getPermissions().stream().map(p->new  SimpleGrantedAuthority(p.getPermission()));
           return new UserPrincipal(user);
       }
    }




}
