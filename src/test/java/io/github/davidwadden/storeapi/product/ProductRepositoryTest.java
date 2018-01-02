package io.github.davidwadden.storeapi.product;

import io.github.davidwadden.storeapi.StoreApiApplication;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.cassandra.core.ReactiveCassandraTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
    classes = StoreApiApplication.class,
    webEnvironment = SpringBootTest.WebEnvironment.NONE
)
class ProductRepositoryTest {

    private final ProductRepository repository;
    private final ReactiveCassandraTemplate cassandraTemplate;

    @Autowired
    ProductRepositoryTest(ProductRepository repository, ReactiveCassandraTemplate cassandraTemplate) {
        this.repository = repository;
        this.cassandraTemplate = cassandraTemplate;
    }

    @DisplayName("lists all products")
    @Test
    void findAll() {
        Product productToSave = Product.newBuilder()
            .productId(UUID.randomUUID())
            .name("ProductName")
            .productNumber("123-45X")
            .listPrice(BigDecimal.valueOf(100L))
            .stockCount(10)
            .build();
        cassandraTemplate.insert(productToSave).block();

        Flux<Product> productFlux = repository.findAll();

        StepVerifier.create(productFlux)
            .assertNext(product -> assertThat(product).isEqualTo(productToSave))
            .verifyComplete();
    }

    @DisplayName("saves a product")
    @Test
    void save() {
        UUID productId = UUID.randomUUID();
        Product productToSave = Product.newBuilder()
            .productId(productId)
            .name("ProductName")
            .productNumber("123-45X")
            .listPrice(BigDecimal.valueOf(100L))
            .stockCount(10)
            .build();
        repository.save(productToSave).block();

        Product savedProduct = cassandraTemplate.selectOneById(productId, Product.class).block();

        assertThat(savedProduct).isEqualTo(productToSave);
    }
}
