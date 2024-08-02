package com.knowit.commentService.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.knowit.commentService.entity.Comment;
import com.knowit.commentService.entity.EmailRequest;
import com.knowit.commentService.entity.User;
import com.knowit.commentService.kafka.KafkaProducer;
import com.knowit.commentService.service.CommentService;
import com.knowit.commentService.service.UserClient;

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
    
    @Autowired
    UserClient userClient;
    
    @Autowired
    KafkaProducer kafkaProducer;

    @PostMapping("/projects/{projectId}/tasks/{taskId}/comments")
    public ResponseEntity<Object> createCommentOnTask(
            @PathVariable int projectId,
            @PathVariable int taskId,
            @RequestBody Comment comment
    ) throws JsonProcessingException{
        Comment savedComment = commentService.createCommentOnTask(
                projectId,
                taskId,
                comment
        );
        int userId = comment.getUserId();
        User user = userClient.getUserById((long)userId).getBody();
        EmailRequest emailRequest = new EmailRequest();
        emailRequest.setTo(user.getGmail());
        emailRequest.setSubject("comment is successully given to task");
        emailRequest.setMassage(comment.toString());
        kafkaProducer.sendMail(emailRequest);
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
