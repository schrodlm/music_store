package cz.cvut.fit.tjv.music_store.domain;

import javax.persistence.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;

/*
 This class represents Product data object in the database
 - it is a product we are selling in our store
 */

@Entity
@Table(name = "products")
public class Product implements DomainEntity<Integer> {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "product_name")
    public String product_name;

    @Column(name = "price")
    private Integer price;
    @Column(name = "integer")
    private Integer discount;


    /*
        Relations
     */

    @ManyToMany
    private Collection<Order> inOrders;

    @ManyToMany
    private Collection<StoreUser> likedBy;


    /*
        Constructors
     */
    public Product(Integer id, String product_name, Integer price, Integer discount, Image product_image) {
        this.id = id;
        this.product_name = product_name;
        this.price = price;
        this.discount = discount;
    }

    public Product(){};


    /*
        Getters
     */
    @Override
    public Integer getId() {return id;}

    public String getProduct_name() {return product_name;}

    public Integer getPrice() {return price;}

    public Integer getDiscount() {return discount;}

    public Collection<Order> getInOrders() {return inOrders;}

    public Collection<StoreUser> getLikedBy() {return likedBy;}

    /*
        Setters
     */

    public void setId(Integer id_product) {this.id = id_product;}

    public void setProduct_name(String product_name) {this.product_name = product_name;}

    public void setPrice(Integer price) {this.price = price;}

    public void setDiscount(Integer discount) {this.discount = discount;}

    public void setInOrders(Collection<Order> inOrders) {this.inOrders = inOrders;}

    public void setLikedBy(Collection<StoreUser> likedBy) {this.likedBy = likedBy;}
}
