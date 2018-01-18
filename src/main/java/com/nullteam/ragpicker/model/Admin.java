package com.nullteam.ragpicker.model;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Admin {
    
    @Id
    @GeneratedValue
    private Integer id;

    @OneToOne(mappedBy = "wx_user"
            , cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    private WxUser wxUser;

    private Authority authority;

    @Column(name = "created_at")
    @CreatedDate
    private Date createdAt;

    @Column(name = "updated_at")
    @LastModifiedDate
    private Date updatedAt;
}
