package cz.cvut.fit.tjv.music_store.domain;

import java.awt.*;

/*
 This class represents Product data object in the database
 - it is a product we are selling in our store
 */
public class Product implements DomainEntity<Integer> {
    public Product(Integer id_product, String product_name, Integer price, Integer discount, Image product_image) {
        this.id_product = id_product;
        this.product_name = product_name;
        this.price = price;
        this.discount = discount;
        this.product_image = product_image;
    }

    private Integer id_product;
    public String product_name;
    private Integer price;
    private Integer discount;
    private Image product_image;

    @Override
    public Integer getId_user() {
        return null;
    }


}
