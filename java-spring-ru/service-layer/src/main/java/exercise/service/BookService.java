package exercise.service;

import exercise.dto.*;
import exercise.exception.ResourceNotFoundException;
import exercise.mapper.AuthorMapper;
import exercise.mapper.BookMapper;
import exercise.repository.AuthorRepository;
import exercise.repository.BookRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Service
public class BookService {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    AuthorRepository authorRepository;
    // BEGIN
    @Autowired
    BookMapper bookMapper;
    // BEGIN
    public List<BookDTO> findAll(){
        return bookRepository.findAll().stream().map(bookMapper::map).toList();
    }

    public BookDTO findById(long id){
        return bookRepository.findById(id).stream().map(bookMapper::map).findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Sorry"));
    }

    public BookDTO create(BookCreateDTO createDTO){
        var author = bookMapper.map(createDTO);
        bookRepository.save(author);
        return bookMapper.map(author);
    }

    public void delete(long id){
        var author = bookRepository.findById(id).stream().findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Sorry"));
        bookRepository.delete(author);
    }

    public BookDTO update(long id, BookUpdateDTO updateDTO){
        var book = bookRepository.findById(id).stream().findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Sorry"));
        bookMapper.update(updateDTO, book);
        authorRepository.findById(book.getAuthor().getId()).stream().findFirst().ifPresent(book::setAuthor);
        bookRepository.save(book);
        return bookMapper.map(book);
    }
    // END
}
