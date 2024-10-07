package exercise.controller;

import java.util.List;

import exercise.dto.TaskCreateDTO;
import exercise.dto.TaskDTO;
import exercise.dto.TaskUpdateDTO;
import exercise.mapper.TaskMapper;
import exercise.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import exercise.exception.ResourceNotFoundException;
import exercise.repository.TaskRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/tasks")
public class TasksController {
    // BEGIN

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TaskMapper taskMapper;

    @GetMapping("/{id}")
    public TaskDTO show(@PathVariable long id){
        return taskRepository.findById(id).map(taskMapper::map).orElseThrow(() -> new ResourceNotFoundException("Sorry"));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id, HttpServletResponse response){
        taskRepository.findById(id).ifPresent(task -> response.setStatus(204));
        taskRepository.deleteById(id);
    }

    @GetMapping
    public List<TaskDTO> showAll(){
        return taskRepository.findAll().stream().map(taskMapper::map).toList();
    }

    @PutMapping("/{id}")
    public TaskDTO update(@PathVariable long id, @Valid @RequestBody TaskUpdateDTO body){
        var task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sorry"));
        var user = userRepository.findById(task.getAssignee().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Sorry"));
        user.deleteTask(task);
        taskMapper.update(body, task);
        user = userRepository.findById(task.getAssignee().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Sorry"));
        user.addTask(task);
        return taskMapper.map(taskRepository.save(task));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TaskDTO create(@Valid @RequestBody TaskCreateDTO data){
        var task = taskMapper.map(data);
        taskRepository.save(task);
        return taskMapper.map(task);
    }
    // END
}
