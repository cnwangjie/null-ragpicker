package com.nullteam.ragpicker.model;

import com.sun.istack.internal.NotNull;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(indexes = {
        @Index(name = "USER_ID", columnList = "user_id")
})
public class Address {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "int(11) UNSIGNED")
    private Integer id;

    @Column(columnDefinition = "int(11) UNSIGNED COMMENT '用户id'", name = "user_id")
    @NotNull
    private Integer userId;

    @Column(columnDefinition = "int(6) UNSIGNED COMMENT '地点'")
    @NotNull
    private Integer location;

    @Column(columnDefinition = "varchar(255) COMMENT '具体位置信息'")
    @NotNull
    private String detail;

    @Column(columnDefinition = "varchar(13) COMMENT '电话'")
    @NotNull
    private String tel;

    @Column(name = "created_at")
    @CreatedDate
    private Date createdAt;

    @Column(name = "updated_at")
    @LastModifiedDate
    private Date updatedAt;
}
