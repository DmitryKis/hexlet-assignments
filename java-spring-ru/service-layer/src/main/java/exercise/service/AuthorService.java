package exercise.service;

import exercise.dto.*;
import exercise.exception.ResourceNotFoundException;
import exercise.mapper.AuthorMapper;
import exercise.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {

    @Autowired
    AuthorRepository authorRepository;
    @Autowired
    AuthorMapper authorMapper;
    // BEGIN
    public List<AuthorDTO> findAll(){
        return authorRepository.findAll().stream().map(authorMapper::map).toList();
    }

    public AuthorDTO findById(long id){
        return authorRepository.findById(id).stream().map(authorMapper::map).findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Sorry"));
    }

    public AuthorDTO create(AuthorCreateDTO createDTO){
        var author = authorMapper.map(createDTO);
        authorRepository.save(author);
        return authorMapper.map(author);
    }

    public void delete(long id){
        var author = authorRepository.findById(id).stream().findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Sorry"));
        authorRepository.delete(author);
    }

    public AuthorDTO update(long id, AuthorUpdateDTO updateDTO){
        var author = authorRepository.findById(id).stream().findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Sorry"));
        authorMapper.update(updateDTO, author);
        authorRepository.save(author);
        return authorMapper.map(author);
    }
    // END
}
