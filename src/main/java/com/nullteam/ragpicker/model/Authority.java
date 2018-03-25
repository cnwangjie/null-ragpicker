package com.nullteam.ragpicker.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * <p>Title: Authority.java</p>
 * <p>Package: com.nullteam.ragpicker.model</p>
 * <p>Description: </p>
 * <p>Copyright: 2018 the original author or authors.</p>

 * @date 01/19/18
 * @author WangJie <i@i8e.net>
 */
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
