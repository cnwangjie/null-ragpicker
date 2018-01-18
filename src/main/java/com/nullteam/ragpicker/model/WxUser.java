package com.nullteam.ragpicker.model;

import com.sun.istack.internal.NotNull;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class WxUser {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "int(11) UNSIGNED")
    private Integer id;

    @Column(columnDefinition = "char(30) COMMENT '微信openid'")
    @NotNull
    private String wxid;

    @Column(columnDefinition = "varchar(255) COMMENT '微信昵称'")
    @NotNull
    private String nickname;

    @Column(columnDefinition = "varchar(255) COMMENT '微信头像'")
    @NotNull
    private String avatar;

    @Column(name = "created_at")
    @CreatedDate
    private Date createdAt;

    @Column(name = "updated_at")
    @LastModifiedDate
    private Date updatedAt;

    @OneToOne
    @JoinColumn(name = "wx_user_id")
    private User user;

    @OneToOne
    @JoinColumn(name = "wx_user_id")
    private Collector collector;

    @OneToOne
    @JoinColumn(name = "wx_user_id")
    private Admin admin;
}
