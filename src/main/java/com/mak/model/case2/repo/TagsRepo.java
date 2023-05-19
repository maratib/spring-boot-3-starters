package com.mak.model.case2.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mak.model.case2.PostDetails;
import com.mak.model.case2.Tags;

@Repository
public interface TagsRepo extends JpaRepository<Tags, Long> {

}
