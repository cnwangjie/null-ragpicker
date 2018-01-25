package com.nullteam.ragpicker.model;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "`order`")
//@Table(indexes = {
//        @Index(name = "USER_ID", columnList = "user_id"),
//        @Index(name = "COLLECTOR_ID", columnList = "collector_id")
//})
public class Order {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "int(11) UNSIGNED")
    private Integer id;

    @Column(columnDefinition = "int(11) UNSIGNED COMMENT '用户id'", name = "user_id", nullable = false)
    private Integer userId;

    @Column(columnDefinition = "int(11) UNSIGNED COMMENT '回收员id'", name = "collector_id", nullable = false)
    private Integer collectorId;

    @Column(columnDefinition = "int(6) UNSIGNED COMMENT '地点'", nullable = false)
    private Integer location;

    @Column(columnDefinition = "varchar(255) COMMENT '具体位置信息'", name ="loc_detail", nullable = false)
    private String locDetail;

    @Column(columnDefinition = "varchar(255) COMMENT '备注'", nullable = false)
    private String remark;

    @Column(columnDefinition = "char(30) COMMENT '订单编号'", name = "order_no", nullable = false)
    private String orderNo;

    @Column(columnDefinition = "tinyint(3) UNSIGNED COMMENT '订单状态'", nullable = false)
    private Integer status;

    @Column(columnDefinition = "int(11) UNSIGNED COMMENT '金额'")
    private Integer amount;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private Set<OrderDetail> orderDetail;

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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getCollectorId() {
        return collectorId;
    }

    public void setCollectorId(Integer collectorId) {
        this.collectorId = collectorId;
    }

    public Integer getLocation() {
        return location;
    }

    public void setLocation(Integer location) {
        this.location = location;
    }

    public String getLocDetail() {
        return locDetail;
    }

    public void setLocDetail(String locDetail) {
        this.locDetail = locDetail;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Set<OrderDetail> getOrderDetail() {
        return orderDetail;
    }

    public void setOrderDetail(Set<OrderDetail> orderDetail) {
        this.orderDetail = orderDetail;
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
