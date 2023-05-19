package com.mak.model.case2;

import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Table(name = "tags")
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@SQLDelete(sql = "UPDATE users SET del = true WHERE id=?")
@Where(clause = "del=false")

public class Tags extends BaseEntity {

    @NaturalId
    private String name;

}
