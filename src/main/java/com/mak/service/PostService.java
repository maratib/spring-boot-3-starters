package com.mak.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.mak.model.case2.Post;
import com.mak.model.case2.PostComment;
import com.mak.model.case2.Tags;
import com.mak.model.case2.repo.PostRepo;
import com.mak.model.case2.repo.TagsRepo;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PostService {

    private final PostRepo postRepo;
    private final TagsRepo tagsRepo;

    private void addTags() {
        var jdbc = new Tags("JDBC");
        var java = new Tags("JAVA");
        var hibernate = new Tags("Hibernate");
        tagsRepo.save(jdbc);
        tagsRepo.save(java);
        tagsRepo.save(hibernate);

    }

    public void createPost() {

        addTags();
        var post = new Post();
        post.setTitle("Hello Post with comments 1 ");
        List<PostComment> comments = new ArrayList<>();

        comments.add(new PostComment(post, "Comment one 1"));
        comments.add(new PostComment(post, "Comment one 2"));
        comments.add(new PostComment(post, "Comment one 3"));

        post.setComments(comments);

        postRepo.save(post);

        var post1 = new Post();
        post1.setTitle("Hello Post with comments 2 ");
        List<PostComment> comments1 = new ArrayList<>();

        comments1.add(new PostComment(post1, "Comment two 1"));
        comments1.add(new PostComment(post1, "Comment two 2"));
        comments1.add(new PostComment(post1, "Comment two 3"));

        post1.setComments(comments1);

        postRepo.save(post1);

    }

}
