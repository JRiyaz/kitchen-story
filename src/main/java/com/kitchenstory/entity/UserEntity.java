package com.kitchenstory.entity;

import com.kitchenstory.model.UserRole;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;

@Entity
@Data
@DynamicInsert
@DynamicUpdate
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Table(name = "users")
public class UserEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private String id;

    @Column(length = 50)
    @Size(min = 4, max = 30, message = "{user.name.invalid}")
    @NotEmpty(message = "Please enter name")
    private String name;

    @Column(nullable = false, length = 100, unique = true, name = "email")
    @Email(message = "{user.email.invalid}")
    @NotEmpty(message = "Please enter email")
    private String email;

    @Column(length = 10)
    @NotEmpty(message = "Please select your Gender")
    private String gender;

    @Column(length = 100)
    @Size(min = 4, message = "Please create a strong password")
    @NotEmpty(message = "Please enter password")
    private String password;

    @Column(length = 50)
    @Size(min = 4, max = 50, message = "Please enter valid address")
    @NotEmpty(message = "Please enter address")
    private String address;

    @Column(length = 20)
    @Size(min = 4, max = 20, message = "Please provide a valid city")
    @NotEmpty(message = "Please enter city")
    private String city;

    @Column(length = 20)
    @Size(min = 4, max = 20, message = "Please select a valid state")
    @NotEmpty(message = "Please enter state")
    private String state;

    @Column(length = 10)
    @Size(min = 6, max = 6, message = "Please provide a valid zipcode")
    @NotEmpty(message = "Please enter zipcode")
    private String zipcode;

    @NotNull(message = "please accept the terms and conditions")
    @AssertTrue
    private Boolean terms;

    @Column(name = "is_account_non_expired")
    private Boolean isAccountNonExpired;

    @Column(name = "is_account_non_locked")
    private Boolean isAccountNonLocked;

    @Column(name = "is_credentials_non_expired")
    private Boolean isCredentialsNonExpired;

    @Column(name = "is_enabled")
    private Boolean isEnabled;

    @Column(name = "user_role", length = 10)
    @Enumerated(EnumType.STRING)
    private UserRole userRole;
}
