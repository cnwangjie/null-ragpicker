package com.nullteam.ragpicker.model;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

/**
 * <p>Title: Admin.java</p>
 * <p>Package: com.nullteam.ragpicker.model</p>
 * <p>Description: </p>
 * <p>Copyright: 2018 the original author or authors.</p>

 * @date 01/19/18
 * @author WangJie <i@i8e.net>
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Admin {

    @Id
    @GeneratedValue
    private Integer id;

    @OneToOne
    @JoinColumn(name = "wx_user_id", columnDefinition = "int(11) UNSIGNED", referencedColumnName = "id")
    private WxUser info;

//    private Authority authority;

    @Column(name = "created_at")
    @CreatedDate
    private Date createdAt;

    @Column(name = "updated_at")
    @LastModifiedDate
    private Date updatedAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public WxUser getInfo() {
        return info;
    }

    public void setInfo(WxUser info) {
        this.info = info;
    }

//    public Authority getAuthority() {
//        return authority;
//    }
//
//    public void setAuthority(Authority authority) {
//        this.authority = authority;
//    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
