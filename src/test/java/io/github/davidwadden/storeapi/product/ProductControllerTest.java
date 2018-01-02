package io.github.davidwadden.storeapi.product;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

import java.math.BigDecimal;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

class ProductControllerTest {

    @Mock
    private ProductRepository mockRepository;
    private ProductController controller;
    private WebTestClient webTestClient;

    @BeforeEach
    void setUp() {
        initMocks(this);
        controller = new ProductController(mockRepository);
        webTestClient = WebTestClient
            .bindToController(controller)
            .configureClient()
            .build();
    }

    @DisplayName("lists all products")
    @Test
    void getAllProducts() {
        Product product1 = Product.newBuilder()
            .productId(1L)
            .name("ProductName")
            .productNumber("123-45X")
            .listPrice(BigDecimal.valueOf(100L))
            .stockCount(10)
            .build();
        Product product2 = Product.newBuilder()
            .productId(2L)
            .name("ProductTwo")
            .productNumber("123-12X")
            .listPrice(BigDecimal.valueOf(2.1d))
            .stockCount(2)
            .build();

        doReturn(Flux.just(product1, product2))
            .when(mockRepository)
            .findAll();

        webTestClient.get().uri("/products")
            .accept(MediaType.APPLICATION_JSON_UTF8)
            .exchange()
            .expectStatus().isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .expectBodyList(Product.class)
            .hasSize(2)
            .contains(product1, product2);

        verify(mockRepository).findAll();
    }
}
