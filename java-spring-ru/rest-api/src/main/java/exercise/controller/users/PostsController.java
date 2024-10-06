package exercise.controller.users;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import exercise.model.Post;
import exercise.Data;

// BEGIN
@RestController
@RequestMapping("/api/users/{id}/posts")
public class PostsController {

    private List<Post> posts = Data.getPosts();

    @GetMapping
    public List<Post> getPosts(@PathVariable int id){
        return posts.stream().filter(post -> post.getUserId() == id).toList();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Post> createPostWithinUserId(@PathVariable int id, @RequestBody Post body){
        body.setUserId(id);
        posts.add(body);
        return ResponseEntity.status(201).body(body);
    }
}
// END
