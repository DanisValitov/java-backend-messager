package com.valitov.jwtdemo.objects;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public interface CustomUserDetails extends UserDetails {
     String getUserUuid();
}
