package com.knowit.commentService.service;

import com.knowit.commentService.entity.Comment;
import com.knowit.commentService.exception.CommentNotFoundException;
import com.knowit.commentService.exception.NotCommentOwnerException;
import com.knowit.commentService.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    public Comment createCommentOnTask(int projectId, int taskId, Comment comment){
        comment.setProjectId(projectId);
        comment.setTaskId(taskId);
        comment.setTimestamp(LocalDateTime.now());
        return commentRepository.save(comment);
    }

    public Comment createCommentOnProject(int projectId, Comment comment){
        comment.setProjectId(projectId);
        comment.setTaskId(null);
        comment.setTimestamp(LocalDateTime.now());
        return commentRepository.save(comment);
    }

    public List<Comment> getAllCommentsOnTask(int projectId, int taskId){
        return commentRepository.getAllCommentsOnTask(projectId, taskId);
    }

    public List<Comment> getAllCommentsOnProject(int projectId){
        return commentRepository.getAllCommentsOnProject(projectId);
    }

    public void updateCommentByCommentId(
            int commentId,
            int userId,
            Comment comment
    ){
        Optional<Comment> optionalComment = commentRepository.findById(commentId);
        if(optionalComment.isEmpty()){
            throw new CommentNotFoundException(
                    "Comment with id "+commentId+" not found"
            );
        }
        Comment commentEntity = optionalComment.get();
        if(userId != commentEntity.getUserId()){
            throw new NotCommentOwnerException(
                    "You cannot update comment beacause you are not owner of comment"
            );
        }
        commentEntity.setText(comment.getText());
        commentEntity.setTimestamp(LocalDateTime.now());
        commentRepository.save(commentEntity);
    }

    public void deleteCommentByCommentId(int commentId, int userId){
        Optional<Comment> optionalComment = commentRepository.findById(commentId);
        if(optionalComment.isEmpty()){
            throw new CommentNotFoundException(
                    "Comment with id "+commentId+" not found"
            );
        }
        Comment comment = optionalComment.get();
        if(userId != comment.getUserId()){
            throw new NotCommentOwnerException(
                    "You cannot delete comment beacause you are not owner of comment"
            );
        }
        commentRepository.delete(comment);
    }
}
