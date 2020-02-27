package com.valitov.jwtdemo.repositories;

import com.valitov.jwtdemo.entities.RolesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolesRepo extends JpaRepository<RolesEntity, Integer> {
    RolesEntity findOneByRole(String role);
}
