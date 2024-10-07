package exercise.dto;

import java.util.List;

import exercise.model.Post;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import static jakarta.persistence.GenerationType.IDENTITY;

// BEGIN
@Getter
@Setter
public class PostDTO {

    private long id;
    private String title;
    private String body;

    private List<CommentDTO> comments;
}
// END
