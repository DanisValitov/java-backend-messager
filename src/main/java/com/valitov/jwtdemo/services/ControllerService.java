package com.valitov.jwtdemo.services;

import com.valitov.jwtdemo.entities.Contact;
import com.valitov.jwtdemo.entities.MyUser;
import com.valitov.jwtdemo.entities.Permission;
import com.valitov.jwtdemo.entities.RolesEntity;
import com.valitov.jwtdemo.models.AuthenticationResponse;
import com.valitov.jwtdemo.models.CustomResponse;
import com.valitov.jwtdemo.utils.JwtUtil;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Service
public class ControllerService {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PostgresService postgresService;

    @Autowired
    private JwtUtil jwtUtil;

    public ResponseEntity<?> isAdmin(String userName) {
        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(userName);
        CustomResponse customResponse = new CustomResponse();

        userDetails.getAuthorities().forEach(role -> {
            if (role.getAuthority().equals("ADMIN")) {

                customResponse.setMessage("success");
                System.out.println("this is admin!");
            }
        });
        if (customResponse.getMessage()==null) {
            System.out.println("Its not the admin..");
            customResponse.setMessage("denied");
            return ResponseEntity.status(401).body(customResponse);
        } else {
            return ResponseEntity.status(200).body(customResponse);
        }
    }

    public ResponseEntity<?> createNewUser(String userName, String password) {
        MyUser newUser = new MyUser();
        Permission permission = new Permission();
        RolesEntity role = postgresService.getRoleByRole("user");

        newUser.setName(userName);
        newUser.setPassword(password);
        newUser.setUuid(UUID.randomUUID().toString());

        permission.setRole(role);
        permission.setUser(newUser);

        role.getPermissions().add(permission);

        List<Permission> permissions = new ArrayList<>();
        permissions.add(permission);
        newUser.setPermissions(permissions);

        System.out.println(role.toString());

        if(postgresService.saveNewUser(newUser) !=null) {
            Contact contact = new Contact();
            contact.setName(newUser.getName());
            contact.setUuid(newUser.getUuid());

            saveContactAndPermissions(contact, permission);

            JSONObject resp = new JSONObject();
            resp.put("answer", "success");
            return ResponseEntity.ok().body(resp);
        }
        JSONObject resp = new JSONObject();
        resp.put("error", "error");
        return  ResponseEntity.status(401).body(resp);
    }

    @Transactional
    private void saveContactAndPermissions(Contact contact, Permission permission) {
        postgresService.addNewContact(contact);
        postgresService.saveNewPermission(permission);
    }

    public ResponseEntity<?> answerWithAuthData(MyUser myUser, UserDetails userDetails) {
        System.out.println("user roles: "  + myUser.getPermissions().get(0).getRole().getRole());

        final String jwt = jwtUtil.generateToken(userDetails);

        final Long expiresIn = jwtUtil.getTokenDuration(jwt);

        final String userId = myUser.getUuid();

        final String name = myUser.getName();

        List<String> roles = new ArrayList<>();

        final Collection<? extends GrantedAuthority> grantedAuthorities = userDetails.getAuthorities();
        grantedAuthorities.forEach(item -> {
            roles.add(item.getAuthority());
        });

        return ResponseEntity.ok(new AuthenticationResponse(jwt, expiresIn, userId, name, roles));
    }
}
