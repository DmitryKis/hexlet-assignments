package exercise.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import java.util.List;

import exercise.repository.ProductRepository;
import exercise.dto.ProductDTO;
import exercise.dto.ProductCreateDTO;
import exercise.dto.ProductUpdateDTO;
import exercise.exception.ResourceNotFoundException;
import exercise.mapper.ProductMapper;

@RestController
@RequestMapping("/products")
public class ProductsController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper productMapper;

    // BEGIN
    @GetMapping
    public List<ProductDTO> showAll(){
        return productRepository.findAll().stream().map(productMapper::map).toList();
    }

    @GetMapping("/{id}")
    public ProductDTO show(@PathVariable long id){
        return productRepository.findById(id).stream().map(productMapper::map).findFirst().orElseThrow(() -> new ResourceNotFoundException("Sorry"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDTO create(@RequestBody ProductCreateDTO body){
        productRepository.save(productMapper.map(body));
        return productMapper.map(productMapper.map(body));
    }

    @PutMapping("/{id}")
    public ProductDTO update(@PathVariable long id, @RequestBody ProductUpdateDTO updateBody){
        var product = productRepository.findById(id).stream().findFirst().orElseThrow(() -> new ResourceNotFoundException("Sorry"));
        productMapper.update(updateBody,product);
        productRepository.save(product);
        return productMapper.map(product);

    }

    // END
}
