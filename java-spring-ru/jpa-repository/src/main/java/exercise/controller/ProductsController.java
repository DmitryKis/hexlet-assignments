package exercise.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.data.domain.Sort;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import exercise.model.Product;
import exercise.repository.ProductRepository;
import exercise.exception.ResourceNotFoundException;

@RestController
@RequestMapping("/products")
public class ProductsController {

    @Autowired
    private ProductRepository productRepository;

    // BEGIN
    @GetMapping
    public List<Product> showWithFilter(@RequestParam(defaultValue = "-5") int min, @RequestParam(defaultValue = "-5") int max) {
        List<Product> list;
        if (min == -5 && max != -5){
            list = productRepository.findByPriceLessThan(max);
        } else if(min != -5 && max == -5){
            list = productRepository.findByPriceGreaterThan(min);
        } else if(min == -5 && max == -5){
            list = productRepository.findAll();
        } else {
            list = productRepository.findByPriceBetween(min, max);
        }
        return list.stream().sorted(Comparator.comparingInt(Product::getPrice)).toList();
    }
    // END

    @GetMapping(path = "/{id}")
    public Product show(@PathVariable long id) {

        var product =  productRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Product with id " + id + " not found"));

        return product;
    }
}
