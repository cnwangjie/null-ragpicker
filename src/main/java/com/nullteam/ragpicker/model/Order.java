package com.nullteam.ragpicker.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "`order`")
//@Table(indexes = {
//        @Index(name = "USER_ID", columnList = "user_id"),
//        @Index(name = "COLLECTOR_ID", columnList = "collector_id")
//})
public class Order {

    @JsonIgnore
    @Id
    @GeneratedValue
    @Column(columnDefinition = "int(11) UNSIGNED")
    private Integer id;

    @ManyToOne
    @JoinColumn(columnDefinition = "int(11) UNSIGNED COMMENT '用户id'", name = "user_id", nullable = false, referencedColumnName = "id")
    private User user;

    @ManyToOne
    @JoinColumn(columnDefinition = "int(11) UNSIGNED COMMENT '回收员id'", name = "collector_id", referencedColumnName = "id")
    private Collector collector;

    @Column(columnDefinition = "int(6) UNSIGNED COMMENT '地点'", nullable = false)
    private Integer location;

    @Column(columnDefinition = "varchar(255) COMMENT '具体位置信息'", name ="loc_detail", nullable = false)
    private String locDetail;

    @Column(columnDefinition = "varchar(255) COMMENT '备注'", nullable = false)
    private String remark = "";

    @Column(columnDefinition = "char(30) COMMENT '订单编号'", name = "order_no", nullable = false)
    private String orderNo;

    @Column(columnDefinition = "tinyint(3) UNSIGNED COMMENT '订单状态'", nullable = false)
    private Integer status = Status.INIT;

    public static class Status {
        public static final int INIT = 0; // 刚被创建的初始状态
        public static final int ALLOTTED = 10; // 已经指派回收员
        public static final int GOTTED = 11; // 回收员已经回收
        public static final int PAID = 12; // 已经支付给用户
        public static final int COMPLETED = 2; // 完成
        public static final int CANCELED_BY_USER = 40; // 用户取消订单
        public static final int CANCELED_BY_SYSTEMED = 41; // 系统取消订单
    }

    @Column(columnDefinition = "int(11) UNSIGNED COMMENT '金额'")
    private Integer amount;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderDetail> orderDetail;

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

    public Collector getCollector() {
        return collector;
    }

    public void setCollector(Collector collector) {
        this.collector = collector;
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

    public List<OrderDetail> getOrderDetail() {
        return orderDetail;
    }

    public void setOrderDetail(List<OrderDetail> orderDetail) {
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

    public String formatOrderDetail() {
        StringBuilder formatter = new StringBuilder();
        for (OrderDetail item : orderDetail) {
            formatter.append(String.format("%s: %d %s\n", item.getCate().getName(), item.getSum(), item.getCate().getUnit()));
        }
        return formatter.toString();
    }
}
