package com.nullteam.ragpicker.model;

import com.sun.istack.internal.NotNull;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Collector {

    @Id
    @GeneratedValue
    private Integer id;

    @OneToOne(mappedBy = "wx_user"
            , cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    private WxUser wxUser;

    @Column(columnDefinition = "varchar(30) COMMENT '姓名'")
    @NotNull
    private String name;

    @Column(columnDefinition = "int(6) UNSIGNED COMMENT '地点'")
    @NotNull
    private Integer location;

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
