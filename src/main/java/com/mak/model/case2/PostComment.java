package com.mak.model.case2;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import jakarta.persistence.*;

import lombok.*;

@Data
@Entity
@Table(name = "post_comments")
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@SQLDelete(sql = "UPDATE users SET del = true WHERE id=?")
@Where(clause = "del=false")

public class PostComment extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;

    private String review;

}
