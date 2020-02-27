package com.valitov.jwtdemo.repositories;

import com.valitov.jwtdemo.entities.Contact;
import com.valitov.jwtdemo.entities.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactsRepo extends JpaRepository<Contact, Integer> {
    Contact findOneByUuid(String uuid);
    Contact findOneByName(String name);
    void deleteByUuid(String uuid);
}
