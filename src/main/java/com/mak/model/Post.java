package com.mak.model;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Table(name = "posts")
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@SQLDelete(sql = "UPDATE posts SET del = true WHERE id=?")
@Where(clause = "del=false")
public class Post extends BaseEntity {

    private String title;

}
