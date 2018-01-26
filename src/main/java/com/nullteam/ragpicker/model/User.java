package com.nullteam.ragpicker.model;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class User {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "int(11) UNSIGNED")
    private Integer id;

    @OneToOne
    @JoinColumn(name = "wx_user_id", columnDefinition = "int(11) UNSIGNED", referencedColumnName = "id")
    private WxUser info;

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
