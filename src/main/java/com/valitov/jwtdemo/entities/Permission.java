package com.valitov.jwtdemo.entities;

import javax.persistence.*;

@Entity
@Table(name = "permissions")
public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private MyUser user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private RolesEntity role;

    public Permission() {
    }

    @Override
    public String toString() {
        return "Permission{" +
                "id=" + id +
                ", user=" + user +
                ", role=" + role +
                '}';
    }

    public RolesEntity getRole() {
        return role;
    }

    public void setRole(RolesEntity role) {
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public MyUser getUser() {
        return user;
    }

    public void setUser(MyUser user) {
        this.user = user;
    }


}
