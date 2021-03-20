package com.kitchenstory.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Data
@DynamicUpdate
@DynamicInsert
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cards")
public class CardEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 20)
    private String type;

    @Column(length = 50)
    @NotNull(message = "Name on card cannot be null")
    private String nameOnCard;

    @Column(length = 20)
    @Length(min = 12, max = 12, message = "Card Number must be 12 digit number")
    private String number;

    @Column(length = 10)
    private String month;

    @Column(length = 4)
    private Integer year;

    @Column(length = 3)
    @Length(min = 3, max = 3, message = "CVV number must be 3 digit number")
    private Integer cvv;

    public CardEntity(String type, String nameOnCard, String number, String month, Integer year, Integer cvv) {
        this.type = type;
        this.nameOnCard = nameOnCard;
        this.number = number;
        this.month = month;
        this.year = year;
        this.cvv = cvv;
    }
}