package com.knowit.commentService.controller;

import com.knowit.commentService.entity.Comment;
import com.knowit.commentService.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/projects/{projectId}/tasks/{taskId}/comments")
    public ResponseEntity<Object> createCommentOnTask(
            @PathVariable int projectId,
            @PathVariable int taskId,
            @RequestBody Comment comment
    ){
        Comment savedComment = commentService.createCommentOnTask(
                projectId,
                taskId,
                comment
        );
        return new ResponseEntity<>(savedComment, HttpStatus.CREATED);
    }


    @PostMapping("/projects/{projectId}/comments")
    public ResponseEntity<Object> createCommentOnProject(
            @PathVariable int projectId,
            @RequestBody Comment comment
    ){
        Comment savedComment = commentService.createCommentOnProject(projectId, comment);
        return new ResponseEntity<>(savedComment, HttpStatus.CREATED);
    }


    @GetMapping("/projects/{projectId}/tasks/{taskId}/comments")
    public ResponseEntity<Object> getAllCommentsOnTask(
            @PathVariable int projectId,
            @PathVariable int taskId
    ){
        List<Comment> comments = commentService.getAllCommentsOnTask(projectId, taskId);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }
    @GetMapping("/projects/{projectId}")
    public ResponseEntity<Object> getCommnetOfProject(@PathVariable int projectId)
    {
    	List<Comment> commnets = commentService.getAllCommentOfProject(projectId);
    	return new ResponseEntity<>(commnets,HttpStatus.OK);
    }
    @GetMapping("/projects/{projectId}/comments")
    public ResponseEntity<Object> getAllCommentsOnProject(@PathVariable int projectId){
        List<Comment> comments = commentService.getAllCommentsOnProject(projectId);
         return new ResponseEntity<>(comments, HttpStatus.OK);
    }


    @PutMapping("/users/{userId}/comments/{commentId}")
    public ResponseEntity<Object> updateCommentByCommentId(
            @PathVariable int commentId,
            @PathVariable int userId,
            @RequestBody Comment comment
    ){
        commentService.updateCommentByCommentId(
                commentId,
                userId,
                comment
        );
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/users/{userId}/comments/{commentId}")
    public ResponseEntity<Object> deleteCommentByCommentId(
            @PathVariable int commentId,
            @PathVariable int userId
    ){
        commentService.deleteCommentByCommentId(
                commentId,
                userId
        );
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
