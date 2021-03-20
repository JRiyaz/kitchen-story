package com.kitchenstory.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Base64;

@Entity
@Data
@DynamicUpdate
@DynamicInsert
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Table(name = "dishes")
public class DishEntity implements Serializable {

    @Id
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "uuid2")
    private String id;

    @Column(length = 50, nullable = false, unique = true)
    @NotNull(message = "Dish name cannot be null")
//    @UniqueElements(message = "Dish name already exists")
    private String name;

    @Column(length = 50, nullable = false)
    @NotNull(message = "Dish type cannot be null")
    private String type;

    @Column(length = 50, nullable = false)
    @NotNull(message = "Spicy field cannot be null")
    private String spicy;

    @Column(length = 10, nullable = false)
    @Min(value = 100, message = "Price must be minimum of 100")
    private Double price;

    @Column(length = 3, nullable = false)
    @Min(value = 2, message = "Rating cannot be null")
    private Double rating;

    @Column(length = 50, nullable = false)
    @NotNull(message = "Normal field cannot be null")
    private String special;

    @Transient
    @NotNull(message = "URL field cannot be null")
    private String url;

    @Lob
    @Column(length = Integer.MAX_VALUE)
    private byte[] image;

    @Column(length = 1000)
    private String description;

    public DishEntity(String name, String type, String spicy, Double price, Double rating, String special,
                      byte[] image, String description) {
        this.name = name;
        this.type = type;
        this.spicy = spicy;
        this.price = price;
        this.rating = rating;
        this.special = special;
        this.image = image;
        this.description = description;
    }

    public static String bytesToImageConverter(byte[] imageInBytes) {

        return imageInBytes != null && imageInBytes.length > 0 ? Base64.getEncoder().encodeToString(imageInBytes) : "";
    }
}
