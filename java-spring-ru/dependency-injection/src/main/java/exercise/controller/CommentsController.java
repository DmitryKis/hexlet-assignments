package exercise.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.util.List;

import exercise.model.Comment;
import exercise.repository.CommentRepository;
import exercise.exception.ResourceNotFoundException;

// BEGIN
@RestController
@RequestMapping("/comments")
public class CommentsController {

    @Autowired
    CommentRepository commentRepository;

    @GetMapping
    public List<Comment> showAll() {
        return commentRepository.findAll();
    }

    @GetMapping("/{id}")
    public Comment show(@PathVariable long id) {
        return commentRepository.findById(id).stream().findFirst().orElseThrow(() -> new ResourceNotFoundException("No exist"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Comment create(@RequestBody Comment body) {
        commentRepository.save(body);
        return body;
    }

    @PutMapping("/{id}")
    public Comment update(@PathVariable long id, @RequestBody Comment body) {
        var comment = commentRepository.findById(id).get();
        comment.setBody(body.getBody());
        comment.setPostId(body.getPostId());
        commentRepository.save(comment);
        return comment;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        commentRepository.deleteById(id);
    }
}
// END
