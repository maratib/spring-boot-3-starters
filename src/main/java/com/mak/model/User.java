package com.mak.model;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.hibernate.validator.constraints.Length;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity
@Table(name = "users")
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@SQLDelete(sql = "UPDATE users SET del = true WHERE id=?")
@Where(clause = "del=false")
public class User extends BaseModel {

    @Column(nullable = false, length = 50, unique = true)
    @Email
    @Length(min = 5, max = 50)
    private String email;

    private String password;

    public User() {
        super();
    }

}
// 03225154531 / Abdul Rashid / 24
// 03064523840 / abdul hamid / 21
