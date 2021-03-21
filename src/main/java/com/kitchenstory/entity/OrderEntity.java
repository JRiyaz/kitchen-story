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

    @NotNull(message = "Select your card")
    @Column(length = 20)
    private String type;

    @NotNull(message = "Name on card cannot be null")
    @Column(length = 50)
    private String nameOnCard;

    @Min(value = 12, message = "Card Number must be 12 digit number")
    @Max(value = 12, message = "Card Number must be 12 digit number")
    @Column(length = 12)
    private Integer number;

    @Column(length = 10)
    private String month;

    @Column(length = 4)
    private Integer year;

    @Min(value = 3, message = "CVV number must be 3 digit number")
    @Max(value = 3, message = "CVV number must be 3 digit number")
    @Column(length = 3)
    private Integer cvv;

    @Column(name = "bill_amount")
    private Double billAmount;

    @Column(length = 5)
    private Integer quantity;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @ToString.Exclude
    @OneToMany(targetEntity = DishEntity.class, fetch = FetchType.LAZY)
    @JoinTable(name = "order_dishes",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "dish_id"))
    private List<DishEntity> dishes;

    @ToString.Exclude
    @ManyToOne(targetEntity = UserEntity.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    public OrderEntity(Double billAmount, Integer quantity, Date date, List<DishEntity> dishes, UserEntity user) {
        this.billAmount = billAmount;
        this.quantity = quantity;
        this.date = date;
        this.dishes = dishes;
        this.user = user;
    }
}