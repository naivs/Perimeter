package org.naivs.perimeter.data.service;

import org.naivs.perimeter.data.entity.Role;
import org.naivs.perimeter.data.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = new User();
        user.setUsername("Naumov_IS");
        user.setPassword("DS@65-kj");
        user.setAccountIsNotLocked(true);
        user.setAccountNotExpired(true);
        user.setCredentialsIsNotExpired(true);
        List<Role> authorities = new ArrayList<>();
        authorities.add(Role.USER);
        user.setAuthorities(authorities);
        return user;
    }
}
