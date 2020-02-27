package com.valitov.jwtdemo.repositories;

import com.valitov.jwtdemo.entities.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MyUsersRepo extends JpaRepository<MyUser, Integer> {
    MyUser findOneByUuid(String uuid);
    MyUser findOneByName(String name);
    void deleteByUuid(String uuid);
}
