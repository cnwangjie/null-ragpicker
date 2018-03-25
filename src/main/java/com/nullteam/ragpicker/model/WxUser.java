package com.nullteam.ragpicker.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

/**
 * <p>Title: WxUser.java</p>
 * <p>Package: com.nullteam.ragpicker.model</p>
 * <p>Description: </p>
 * <p>Copyright: 2018 the original author or authors.</p>

 * @date 01/19/18
 * @author WangJie <i@i8e.net>
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
public class WxUser {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "int(11) UNSIGNED")
    private Integer id;

    @JsonIgnore
    @Column(columnDefinition = "char(30) COMMENT '微信openid'", nullable = false)
    private String wxid;

    @Column(columnDefinition = "varchar(255) COMMENT '微信昵称'", nullable = false)
    private String nickname;

    @Column(columnDefinition = "varchar(255) COMMENT '微信头像'", nullable = false)
    private String avatar;

    @JsonIgnore
    @Column(name = "created_at")
    @CreatedDate
    private Date createdAt;

    @JsonIgnore
    @Column(name = "updated_at")
    @LastModifiedDate
    private Date updatedAt;

    @JsonIgnore
    @OneToOne(mappedBy = "info", fetch = FetchType.LAZY)
    private User user;

    @JsonIgnore
    @OneToOne(mappedBy = "info", fetch = FetchType.LAZY)
    private Collector collector;

    @JsonIgnore
    @Column(columnDefinition = "varchar(255) COMMENT 'REFRESH_TOKEN'")
    private String refreshToken;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getWxid() {
        return wxid;
    }

    public void setWxid(String wxid) {
        this.wxid = wxid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Collector getCollector() {
        return collector;
    }

    public void setCollector(Collector collector) {
        this.collector = collector;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }


}
