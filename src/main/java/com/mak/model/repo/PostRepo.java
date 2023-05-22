package com.mak.model.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mak.model.Post;

@Repository
public interface PostRepo extends JpaRepository<Post, Long> {

}
