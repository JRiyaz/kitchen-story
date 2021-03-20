package com.kitchenstory.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Data
@DynamicUpdate
@DynamicInsert
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cart")
public class CartEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "cart_id")
    private Integer cartId;

    @ToString.Exclude
    @OneToMany(targetEntity = DishEntity.class, cascade = CascadeType.ALL)
    private List<DishEntity> dishEntities;

    public CartEntity(Integer cartId, List<DishEntity> dishEntities) {
        this.cartId = cartId;
        this.dishEntities = dishEntities;
    }
}