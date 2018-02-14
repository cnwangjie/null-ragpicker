package com.nullteam.ragpicker.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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

    // TODO: change all Integer type to Long where column type is int(11) because max digits of Integer is less than 11

    @JsonIgnore
    @ManyToOne
    @JoinColumn(columnDefinition = "int(11) UNSIGNED COMMENT '用户id'", name = "user_id", nullable = false, referencedColumnName = "id")
    private User user;

    @NotNull
    @Max(999999)
    @Min(100000)
    @Column(columnDefinition = "int(6) UNSIGNED COMMENT '地点'", nullable = false)
    private Integer location;

    @NotNull
    @Size(max = 255)
    @Column(columnDefinition = "varchar(255) COMMENT '具体位置信息'", nullable = false)
    private String detail;

    @NotNull
    @Size(max = 13)
    @Column(columnDefinition = "varchar(13) COMMENT '电话'", nullable = false)
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getLocation() {
        return location;
    }

    public void setLocation(Integer location) {
        this.location = location;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
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

    @Override
    public String toString() {
        return new StringBuilder().append("id: ").append(this.getId() )
                .append("\nuser_id: ").append(this.getUser() == null ? "null" : this.getUser().getId())
                .append("\nlocation: ").append(this.getLocation())
                .append("\ndetail: ").append(this.getDetail())
                .append("\ntel: ").append(this.getTel()).toString();
    }

}
