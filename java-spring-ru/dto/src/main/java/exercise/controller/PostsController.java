package exercise.controller;

import exercise.model.Comment;
import exercise.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;

import exercise.model.Post;
import exercise.repository.PostRepository;
import exercise.exception.ResourceNotFoundException;
import exercise.dto.PostDTO;
import exercise.dto.CommentDTO;

// BEGIN
@RestController
@RequestMapping("/posts")
public class PostsController{

    @Autowired
    PostRepository postRepository;

    @Autowired
    CommentRepository commentRepository;

    @GetMapping
    public List<PostDTO> showAll(){
        return postRepository.findAll().stream().map(this::postToDTO).toList();
    }

    private PostDTO postToDTO(Post data){
        var dto = new PostDTO();
        dto.setId(data.getId());
        dto.setBody(data.getBody());
        dto.setTitle(data.getTitle());
        dto.setComments(commentRepository.findByPostId(data.getId())
                .stream().map(this::commentToDTO).toList());
        return dto;
    }

    private CommentDTO commentToDTO(Comment data){
        var dto = new CommentDTO();
        dto.setBody(data.getBody());
        dto.setId(data.getId());
        return dto;
    }

    @GetMapping("/{id}")
    public PostDTO show(@PathVariable long id){
        return postRepository.findById(id)
                .stream().map(this::postToDTO).findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Post with id " + id + " not found"));
    }
}
// END
