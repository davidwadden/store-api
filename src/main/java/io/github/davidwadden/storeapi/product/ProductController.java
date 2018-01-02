package io.github.davidwadden.storeapi.product;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequestMapping("/products")
@RestController
public class ProductController {

    private final ProductRepository repository;

    public ProductController(ProductRepository repository) {
        this.repository = repository;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Flux<Product> getAllProducts() {
        return repository.findAll();
    }

    @GetMapping(path = "/{productId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Mono<Product> getProduct(@PathVariable Long productId) {
        return repository.findById(productId);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Mono<Product> createProduct(@RequestBody Product product) {
        return repository.save(product);
    }
}
