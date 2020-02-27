package com.valitov.jwtdemo.services;

import com.valitov.jwtdemo.entities.MyUser;
import com.valitov.jwtdemo.repositories.MyUsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private MyUsersRepo myUsersRepo;
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        MyUser myUser = myUsersRepo.findOneByName(s);
        List<GrantedAuthority> grantedAuthority = new ArrayList<>();
        myUser.getPermissions().forEach(permission -> {
            if (permission.getRole().getRole().equals("user")) {
                grantedAuthority.add(new SimpleGrantedAuthority("USER"));
            }
            if (permission.getRole().getRole().equals("admin")) {
                grantedAuthority.add(new SimpleGrantedAuthority("ADMIN"));
            }

        });
        if (myUser!=null) return new User(myUser.getName(), myUser.getPassword(), grantedAuthority);
        else throw new UsernameNotFoundException("User not found!");

    }
}
