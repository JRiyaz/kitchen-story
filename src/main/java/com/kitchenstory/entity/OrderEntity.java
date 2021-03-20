package com.kitchenstory.entity;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Data
@DynamicUpdate
@DynamicInsert
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Table(name = "orders")
public class OrderEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private String id;

    @Column(name = "bill_amount")
    private Double billAmount;

    private Integer quantity;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @ToString.Exclude
    @OneToMany(targetEntity = DishEntity.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "order_dishes",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "dish_id"))
    private List<DishEntity> dishes;

    @ToString.Exclude
    @ManyToOne(targetEntity = UserEntity.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "user_orders",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private UserEntity user;

    public OrderEntity(Double billAmount, Integer quantity, Date date, List<DishEntity> dishes, UserEntity user) {
        this.billAmount = billAmount;
        this.quantity = quantity;
        this.date = date;
        this.dishes = dishes;
        this.user = user;
    }
}