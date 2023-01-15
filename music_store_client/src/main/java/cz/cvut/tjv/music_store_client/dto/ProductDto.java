package cz.cvut.tjv.music_store_client.dto;

import jakarta.validation.constraints.*;

public class ProductDto {


    @Min(0)
    private int id;

    @NotBlank
    private String product_name;

    @DecimalMin("10")
    @DecimalMax("100000")
    private Integer price;
    @DecimalMin("5")
    @DecimalMax("90")
    private Integer discount;

    public ProductDto(int id, String product_name, Integer price, Integer discount) {
        this.id = id;
        this.product_name = product_name;
        this.price = price;
        this.discount = discount;
    }
    public ProductDto(){};
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }
}
