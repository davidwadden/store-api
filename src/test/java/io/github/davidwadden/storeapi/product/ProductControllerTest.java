package io.github.davidwadden.storeapi.product;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.math.BigDecimal;

class ProductControllerTest {

    private WebTestClient webTestClient;
    private ProductController controller;

    @BeforeEach
    void setUp() {
        controller = new ProductController();
        webTestClient = WebTestClient
                .bindToController(controller)
                .configureClient()
                .build();
    }

    @DisplayName("lists all products")
    @Test
    void getAllProducts() {
        Product product = Product.newBuilder()
                .productId(1L)
                .name("ProductName")
                .productNumber("123-45X")
                .listPrice(BigDecimal.valueOf(100L))
                .stockCount(10)
                .build();

        webTestClient.get().uri("/products")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isOk()
                .expectHeader()
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBodyList(Product.class)
                .hasSize(1)
                .contains(product);

    }
}
