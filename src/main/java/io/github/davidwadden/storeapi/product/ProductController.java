package io.github.davidwadden.storeapi.product;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class ProductController {

    private final ProductRepository repository;

    public ProductController(ProductRepository repository) {
        this.repository = repository;
    }

    @GetMapping(path = "/products", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Flux<Product> getAllProducts() {
        return repository.findAll();
    }
}
