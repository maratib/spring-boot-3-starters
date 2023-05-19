package com.mak.model.case2.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mak.model.case2.Post;

@Repository
public interface PostRepo extends JpaRepository<Post, Long> {
    // Optional<Post> findById(Long id);

}
