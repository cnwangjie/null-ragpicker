package com.nullteam.ragpicker.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

//@Entity
public class Authority {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(columnDefinition = "varchar(100) COMMENT '权限名'")
    @Enumerated(EnumType.STRING)
    private AuthorityName name;

    @JsonIgnore
    @ManyToMany(mappedBy = "authorities", fetch = FetchType.EAGER)
    @Column(nullable = false)
    private Set<User> users = new HashSet<User>();

    public enum AuthorityName {
        ROLE_ADMIN
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public AuthorityName getName() {
        return name;
    }

    public void setName(AuthorityName name) {
        this.name = name;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}
