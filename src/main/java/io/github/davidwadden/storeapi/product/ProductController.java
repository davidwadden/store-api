package io.github.davidwadden.storeapi.product;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.math.BigDecimal;

@RestController
public class ProductController {

    @GetMapping(path = "/products", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Flux<Product> getAllProducts() {
        Product product = Product.newBuilder()
                .productId(1L)
                .name("ProductName")
                .productNumber("123-45X")
                .listPrice(BigDecimal.valueOf(100L))
                .stockCount(10)
                .build();

        return Flux.just(product);
    }
}
