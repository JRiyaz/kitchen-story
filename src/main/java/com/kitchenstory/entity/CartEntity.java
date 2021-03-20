package com.kitchenstory.entity;

import lombok.*;
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
@EqualsAndHashCode
@Table(name = "cart")
public class CartEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ToString.Exclude
    @OneToMany(targetEntity = DishEntity.class, cascade = CascadeType.ALL)
    private List<DishEntity> dishes;

    @ToString.Exclude
    @OneToOne(targetEntity = UserEntity.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "cart")
    private UserEntity user;
}
