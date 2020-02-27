package com.valitov.jwtdemo.repositories;

import com.valitov.jwtdemo.entities.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionsRepo extends JpaRepository<Permission, Integer> {
}
