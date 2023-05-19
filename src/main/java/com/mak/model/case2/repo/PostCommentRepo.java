package com.mak.model.case2.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mak.model.case2.PostComment;

@Repository
public interface PostCommentRepo extends JpaRepository<PostComment, Long> {

}
