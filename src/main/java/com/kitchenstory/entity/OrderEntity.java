package com.kitchenstory.entity;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
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

    @Column(length = 20)
    @NotNull(message = "Select your card")
    private String type;

    @Column(length = 50)
    @NotNull(message = "Name on card cannot be null")
    private String nameOnCard;

    @Column(length = 20)
    @Min(value = 12, message = "Card Number must be 12 digit number")
    @Max(value = 12, message = "Card Number must be 12 digit number")
    private String number;

    @Column(length = 10)
    private String month;

    @Column(length = 4)
    private Integer year;

    @Column(length = 3)
    @Min(value = 3, message = "CVV number must be 3 digit number")
    @Max(value = 3, message = "CVV number must be 3 digit number")
    private Integer cvv;

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