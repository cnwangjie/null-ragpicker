package com.nullteam.ragpicker.model;

import com.sun.istack.internal.NotNull;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(indexes = {
        @Index(name = "USER_ID", columnList = "user_id"),
        @Index(name = "COLLECTOR_ID", columnList = "collector_id")
})
public class Order {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "int(11) UNSIGNED")
    private Integer id;

    @Column(columnDefinition = "int(11) UNSIGNED COMMENT '用户id'", name = "user_id")
    @NotNull
    private Integer userId;

    @Column(columnDefinition = "int(11) UNSIGNED COMMENT '回收员id'", name = "collector_id")
    @NotNull
    private Integer collectorId;

    @Column(columnDefinition = "int(6) UNSIGNED COMMENT '地点'")
    @NotNull
    private Integer location;

    @Column(columnDefinition = "varchar(255) COMMENT '具体位置信息'")
    @NotNull
    private String detail;

    @Column(columnDefinition = "varchar(255) COMMENT '备注'")
    @NotNull
    private String remark;

    @Column(columnDefinition = "char(30) COMMENT '订单编号'")
    @NotNull
    private String order_id;

    @Column(columnDefinition = "tinyint(3) COMMENT '订单状态'")
    @NotNull
    private Integer status;

    @Column(columnDefinition = "int(11) COMMENT '金额'")
    @NotNull
    private Integer amount;

    @Column(name = "created_at")
    @CreatedDate
    private Date createdAt;

    @Column(name = "updated_at")
    @LastModifiedDate
    private Date updatedAt;
}
