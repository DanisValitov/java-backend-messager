package com.valitov.jwtdemo.repositories;

import com.valitov.jwtdemo.entities.Contact;
import com.valitov.jwtdemo.entities.MyMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessagesRepo extends JpaRepository<MyMessage, Integer> {
    List<MyMessage> findAllBySenderAndReceiver(String sender, String receiver);
}
