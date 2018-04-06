package com.nullteam.ragpicker.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class OrderDetail {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "int(11) UNSIGNED")
    private Integer id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "cate_id")
    private Cate cate;

    @NotNull
    @Column(columnDefinition = "int(11) UNSIGNED COMMENT '数量'", nullable = false)
    private Integer sum;

    @Column(columnDefinition = "int(11) UNSIGNED COMMENT '单价'")
    private Integer price;

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Cate getCate() {
        return cate;
    }

    public void setCate(Cate cate) {
        this.cate = cate;
    }

    public Integer getSum() {
        return sum;
    }

    public void setSum(Integer sum) {
        this.sum = sum;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String toString() {
        return new StringBuilder().append("cate name: ").append(this.getCate().getName())
                .append("\ncate id: ").append(this.getCate().getId())
                .append("\ncate unit: ").append(this.getCate().getUnit())
                .append("\nsum: ").append(this.getSum())
                .append("\nprice: ").append(this.getPrice())
                .toString();
    }
}
