package com.nullteam.ragpicker.model;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@EntityListeners(AuditingEntityListener.class)
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
}
