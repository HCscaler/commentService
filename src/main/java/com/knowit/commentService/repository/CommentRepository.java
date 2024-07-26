package com.knowit.commentService.repository;

import com.knowit.commentService.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

    @Query("select comment from Comment comment where comment.projectId = :projectId and comment.taskId = :taskId")
    List<Comment> getAllCommentsOnTask(@Param("projectId") int projectId, @Param("taskId") int taskId);

    @Query("select comment from Comment comment where comment.projectId = :projectId and comment.taskId is null")
    List<Comment> getAllCommentsOnProject(@Param("projectId") int projectId);

   
    

}
