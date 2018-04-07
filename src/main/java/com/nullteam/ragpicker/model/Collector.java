package com.nullteam.ragpicker.model;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

/**
 * <p>Title: Collector.java</p>
 * <p>Package: com.nullteam.ragpicker.model</p>
 * <p>Description: 回收员模型类</p>
 * <p>Copyright: 2018 the original author or authors.</p>

 * @date 01/19/18
 * @author WangJie <i@i8e.net>
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Collector {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "int(11) UNSIGNED")
    private Integer id;

    @OneToOne
    @JoinColumn(name = "wx_user_id", columnDefinition = "int(11) UNSIGNED", referencedColumnName = "id")
    private WxUser info;

    @Column(columnDefinition = "varchar(30) COMMENT '姓名'")
    private String name;

    @Column(columnDefinition = "int(6) UNSIGNED COMMENT '地点'")
    private Integer location;

    @Column(columnDefinition = "varchar(13) COMMENT '电话'")
    private String tel;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getLocation() {
        return location;
    }

    public void setLocation(Integer location) {
        this.location = location;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
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
