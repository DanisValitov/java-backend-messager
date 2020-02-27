package com.valitov.jwtdemo.services;

import com.valitov.jwtdemo.entities.*;
import com.valitov.jwtdemo.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class PostgresService {
    @Autowired
    private MyUsersRepo myUsersRepo;

    @Autowired
    private MessagesRepo messagesRepo;

    @Autowired
    private ContactsRepo contactsRepo;

    @Autowired
    private RolesRepo rolesRepo;

    @Autowired
    private PermissionsRepo permissionsRepo;

    public RolesEntity getRoleByRole(String role) {
        return rolesRepo.findOneByRole(role);
    }

    public Permission saveNewPermission(Permission permission) {
        return permissionsRepo.saveAndFlush(permission);
    }

    public boolean isUserExists(String name) {
        return myUsersRepo.findOneByName(name) != null;
    }

    public MyUser saveNewUser(MyUser newUser) {
        return myUsersRepo.saveAndFlush(newUser);
    }

    public MyUser getUserByName(String name) {return  myUsersRepo.findOneByName(name);}

    public MyMessage addNewMessage(MyMessage newMessage) {
       return messagesRepo.saveAndFlush(newMessage);
    }

    public List<MyMessage> getMessages(String sender, String receiver) {
        return messagesRepo.findAllBySenderAndReceiver(sender,receiver);
    }
@Transactional
    public void removeUser(String uuid) {
        myUsersRepo.deleteByUuid(uuid);
        contactsRepo.deleteByUuid(uuid);
    }

    public void removeMessageById(int id) {
        messagesRepo.deleteById(id);
    }

    public List<Contact> getContacts() {
        return contactsRepo.findAll();
    }

    public Contact addNewContact(Contact contact) {
        return  contactsRepo.saveAndFlush(contact);
    }

    @Transactional
    public void updateUser(String email, String uuid) {
        System.out.println("UPDATING");
        MyUser myUser = myUsersRepo.findOneByUuid(uuid);
        Contact contact = contactsRepo.findOneByUuid(uuid);
        myUser.setName(email);
        contact.setName(email);
        myUsersRepo.saveAndFlush(myUser);
        contactsRepo.saveAndFlush(contact);
    }
}
