package com.valitov.jwtdemo.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "my_users")
public class MyUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  int id;

    private String uuid;

    private String name;

    private String password;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Permission> permissions;

    public MyUser() {
    }

    public MyUser(int id, String uuid, String name, String password, List<Permission> permissions) {
        this.id = id;
        this.uuid = uuid;
        this.name = name;
        this.password = password;
        this.permissions = permissions;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
