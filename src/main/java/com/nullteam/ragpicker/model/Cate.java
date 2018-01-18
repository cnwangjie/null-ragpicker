package com.nullteam.ragpicker.model;

import com.sun.istack.internal.NotNull;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Cate {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "int(11) UNSIGNED")
    private Integer id;

    @Column(columnDefinition = "varchar(100) COMMENT '分类名'")
    @NotNull
    private String name;

    @Column(name = "created_at")
    @CreatedDate
    private Date createdAt;

    @Column(name = "updated_at")
    @LastModifiedDate
    private Date updatedAt;
}
