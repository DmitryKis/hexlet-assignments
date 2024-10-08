package exercise;

import exercise.model.Post;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@SpringBootApplication
@RestController
public class Application {
    // Хранилище добавленных постов
    private List<Post> posts = Data.getPosts();

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    // BEGIN
    @GetMapping("/posts")
    public ResponseEntity<List<Post>> getAllPosts() {
        return ResponseEntity.ok()
                .header("X-Total-Count", String.valueOf(posts.size()))
                .body(posts);
    }

    @PostMapping("/posts")
    public ResponseEntity<Object> createPost(@RequestBody Post post) {
        posts.add(post);
        return ResponseEntity.status(HttpStatusCode.valueOf(201)).body(post);
    }

    @GetMapping("/posts/{id}")
    public ResponseEntity<Optional<Post>> getPostByID(@PathVariable String id) {
        var post = posts.stream().filter(p -> p.getId().equals(id)).findFirst();
        if (post.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(post);
        }
    }

    @PutMapping("/posts/{id}")
    public ResponseEntity<Object> editPost(@PathVariable String id, @RequestBody Post data) {
        var post = posts.stream().filter(p -> p.getId().equals(id)).findFirst();
        if (post.isPresent()) {
            var currentPost = post.get();
            currentPost.setId(data.getId());
            currentPost.setTitle(data.getTitle());
            currentPost.setBody(data.getBody());
            return ResponseEntity.ok().body(currentPost);
        } else {
            return ResponseEntity.status(204).body(null);
        }
    }
    // END

    @DeleteMapping("/posts/{id}")
    public void destroy(@PathVariable String id) {
        posts.removeIf(p -> p.getId().equals(id));
    }
}
