package com.valitov.jwtdemo.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "roles")
public class RolesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String role;

    @OneToMany(mappedBy = "role", fetch = FetchType.EAGER)
    private List<Permission> permissions;

    public RolesEntity() {
    }

    public RolesEntity(int id, String role, List<Permission> permissions) {
        this.id = id;
        this.role = role;
        this.permissions = permissions;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }
}
