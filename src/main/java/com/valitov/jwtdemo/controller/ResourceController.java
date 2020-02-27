package com.valitov.jwtdemo.controller;

import com.valitov.jwtdemo.entities.*;
import com.valitov.jwtdemo.models.*;
import com.valitov.jwtdemo.services.ControllerService;
import com.valitov.jwtdemo.services.MyUserDetailsService;
import com.valitov.jwtdemo.services.PostgresService;
import com.valitov.jwtdemo.utils.JwtUtil;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController

public class ResourceController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtTokenUtil;

    @Autowired
    private PostgresService postgresService;

    @Autowired
    private MyUserDetailsService userDetailsService;

    @Autowired
    private ControllerService controllerService;

    @RequestMapping({ "/hello" })
    public String firstPage() {
        return "Hello World";
    }

    @RequestMapping("/contacts")
    public ResponseEntity<?> getAllContacts() {
        System.out.println("HERE");
        List answer = postgresService.getContacts();
        System.out.println(answer.size());
        answer.forEach(System.out::println);
        return ResponseEntity.ok(answer);
    }
//    check-user

    @RequestMapping(value = "/delete-message", method = RequestMethod.POST)
    public ResponseEntity<?> isFree(@RequestBody DeleteMessageRequest deleteMessageRequest) {
        System.out.println("deleting msg req");
        String userName = jwtTokenUtil.extractUsername(deleteMessageRequest.getToken());
        MyUser user = postgresService.getUserByName(userName);
        if (user != null) {
            if (user.getUuid().equals(deleteMessageRequest.getMessage().getSender())) {
                postgresService.removeMessageById(deleteMessageRequest.getMessage().getId());
                System.out.println("email is free");
                return ResponseEntity.ok().body(new CustomResponse("true"));
            }
        }
        return ResponseEntity.ok().body(new CustomResponse("false"));
    }

    @RequestMapping(value = "/check-user", method = RequestMethod.POST)
    public ResponseEntity<?> isFree(@RequestBody UserCheckRequest userCheckRequest) {

        System.out.println("is user free");
        String adminName = jwtTokenUtil.extractUsername(userCheckRequest.getToken());
        if (controllerService.isAdmin(adminName).getStatusCodeValue() == 200) {
            if(!postgresService.isUserExists(userCheckRequest.getEmail())) {
                System.out.println("email is free");
                return ResponseEntity.ok().body(new CustomResponse("true"));
            }
        }
        return ResponseEntity.ok().body(new CustomResponse("false"));
    }

    @RequestMapping(value = "/update-user", method = RequestMethod.POST)
    public ResponseEntity<?> updateUserEmail(@RequestBody UserUpdateRequest userUpdateRequest) {

        System.out.println("try to update");
        String adminName = jwtTokenUtil.extractUsername(userUpdateRequest.getToken());
        if (controllerService.isAdmin(adminName).getStatusCodeValue() == 200) {
            if(!postgresService.isUserExists(userUpdateRequest.getUuid())) {
                System.out.println("email is  free");
                postgresService.updateUser( userUpdateRequest.getEmail(), userUpdateRequest.getUuid());
                return ResponseEntity.ok().body(new CustomResponse("false"));
            }

        }
        return ResponseEntity.ok().body(new CustomResponse("false"));
    }

    @RequestMapping(value = "/delete-user", method = RequestMethod.POST)
    public ResponseEntity<?> removeUser(@RequestBody UserDeleteRequest userDeleteRequest) {
        System.out.println("get all users");
        System.out.println("is admin check");
        String adminName = jwtTokenUtil.extractUsername(userDeleteRequest.getToken());
        if (controllerService.isAdmin(adminName).getStatusCodeValue() == 200) {
            postgresService.removeUser(userDeleteRequest.getUuid());
        }
        return ResponseEntity.ok().body(new CustomResponse("OK"));
    }

    @RequestMapping("/messages/{userId}/{contactId}")
    public ResponseEntity<?> fetchMessages(@PathVariable("userId") String userId,
                                           @PathVariable("contactId") String contactId) {
        System.out.println("MSGS");

        List<MyMessage> from = postgresService.getMessages(userId, contactId);
        List<MyMessage> to = postgresService.getMessages(contactId, userId);
        List<MyMessage> answer = new ArrayList<>();
        answer.addAll(from);
        answer.addAll(to);
        if (answer.size() > 0) {
            Collections.sort(answer);
            answer.forEach(System.out::println);
            return ResponseEntity.ok(answer);
        } else {
            JSONObject resp = new JSONObject();
            resp.put("answer", "No messages");

            return ResponseEntity.status(200).body(resp);
        }

    }

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest)
            throws Exception {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
                            authenticationRequest.getPassword())
            );
        }
        catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());

        MyUser myUser = postgresService.getUserByName(authenticationRequest.getUsername());

        return controllerService.answerWithAuthData(myUser, userDetails);
    }

    @RequestMapping(value = "/new-message", method = RequestMethod.POST)
    public ResponseEntity<?> addNewMessage(@RequestBody MyMessage newMessage) throws Exception {
        System.out.println(newMessage.toString());
        if (postgresService.addNewMessage(newMessage) != null) {
            JSONObject resp = new JSONObject();
            resp.put("answer", "Success");
            System.out.println("added newmessage: " + newMessage.toString());
            return ResponseEntity.ok().body(resp);
        } else {
            JSONObject resp = new JSONObject();
            resp.put("answer", "Error add new message");
            System.err.println("ERROR : " + newMessage.toString());
            return ResponseEntity.status(401).body(resp);
        }
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public ResponseEntity<?> createNewUser(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        System.out.println(authenticationRequest.toString());
//        System.out.println();
        if (postgresService.isUserExists(authenticationRequest.getUsername())) {
            JSONObject resp = new JSONObject();
            resp.put("error", "Already exists");
            return ResponseEntity.badRequest().body(resp);
        }
        return controllerService
                .createNewUser(authenticationRequest.getUsername(), authenticationRequest.getPassword());
    }

    @RequestMapping(value = "/is-admin", method = RequestMethod.POST)
    public ResponseEntity<?> isAdmin(@RequestBody AdminCheck adminCheck) {
        System.out.println("is admin check");
        String userName = jwtTokenUtil.extractUsername(adminCheck.getToken());
        return controllerService.isAdmin(userName);
    }
}
