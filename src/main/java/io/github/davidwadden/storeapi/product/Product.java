package io.github.davidwadden.storeapi.product;

import com.datastax.driver.core.DataType;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@JsonDeserialize(builder = Product.Builder.class)
@Table
public class Product {

    @PrimaryKey("product_id")
    @CassandraType(type = DataType.Name.UUID)
    private UUID productId;

    @NotNull
    @Size(min = 2, max = 50)
    @Column("name")
    private String name;

    @NotNull
    @Size(min = 2, max = 50)
    @Column("product_number")
    private String productNumber;

    @NotNull
    @Column("list_price")
    private BigDecimal listPrice;

    @NotNull
    @Column("stock_count")
    private Integer stockCount;

    // no-arg constructor for JPA
    private Product() {}

    private Product(Builder builder) {
        setProductId(builder.productId);
        setName(builder.name);
        setProductNumber(builder.productNumber);
        setListPrice(builder.listPrice);
        setStockCount(builder.stockCount);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public UUID getProductId() {
        return productId;
    }

    public void setProductId(UUID productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProductNumber() {
        return productNumber;
    }

    public void setProductNumber(String productNumber) {
        this.productNumber = productNumber;
    }

    public BigDecimal getListPrice() {
        return listPrice;
    }

    public void setListPrice(BigDecimal listPrice) {
        this.listPrice = listPrice;
    }

    public Integer getStockCount() {
        return stockCount;
    }

    public void setStockCount(Integer stockCount) {
        this.stockCount = stockCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(productId, product.productId) &&
            Objects.equals(name, product.name) &&
            Objects.equals(productNumber, product.productNumber) &&
            Objects.equals(listPrice, product.listPrice) &&
            Objects.equals(stockCount, product.stockCount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, name, productNumber, listPrice, stockCount);
    }

    @Override
    public String toString() {
        return "Product{" +
            "productId=" + productId +
            ", name='" + name + '\'' +
            ", productNumber='" + productNumber + '\'' +
            ", listPrice=" + listPrice +
            ", stockCount=" + stockCount +
            '}';
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static final class Builder {
        private UUID productId;
        private @NotNull @Size(min = 2, max = 50) String name;
        private @NotNull @Size(min = 2, max = 50) String productNumber;
        private @NotNull BigDecimal listPrice;
        private @NotNull Integer stockCount;

        private Builder() {}

        public Builder productId(UUID productId) {
            this.productId = productId;
            return this;
        }

        public Builder name(@NotNull @Size(min = 2, max = 50) String name) {
            this.name = name;
            return this;
        }

        public Builder productNumber(@NotNull @Size(min = 2, max = 50) String productNumber) {
            this.productNumber = productNumber;
            return this;
        }

        public Builder listPrice(@NotNull BigDecimal listPrice) {
            this.listPrice = listPrice;
            return this;
        }

        public Builder stockCount(@NotNull Integer stockCount) {
            this.stockCount = stockCount;
            return this;
        }

        public Product build() {
            return new Product(this);
        }
    }
}
