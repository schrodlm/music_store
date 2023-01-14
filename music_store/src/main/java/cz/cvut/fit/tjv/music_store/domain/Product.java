package cz.cvut.fit.tjv.music_store.domain;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

/*
 This class represents Product data object in the database
 - it is a product we are selling in our store
 */

@Entity
@Table(name = "products")
public class Product implements DomainEntity<Integer> {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "product_name")
    public String product_name;

    @Column(name = "price")
    @Min(10)
    @Max(1000000)
    private Integer price;
    @Column(name = "discount")
    private Integer discount;


    /*
        Relations
     */

    @ManyToMany(mappedBy = "Bought_Items", cascade = CascadeType.REMOVE)
    private Collection<Order> inOrders;

    @ManyToMany(mappedBy = "likedProducts",  cascade = CascadeType.REMOVE)
    private Collection<StoreUser> likedBy;


    /*
        Constructors
     */

    /**
     *
     * @param product_name      name of the product, cannot be null
     * @param price             price of the product, cannot be null
     * @param discount          discount in percents, can be null
     *                          -> likedBy & inOrders are always null when creating this Product entity
     */
    public Product(int id, String product_name, Integer price, Integer discount) {
        this.id = id;
        this.product_name = Objects.requireNonNull(product_name);
        this.price = Objects.requireNonNull(price);
        this.discount = discount;
    }

    public Product(){};


    /*
        Getters
     */
    @Override
    public Integer getId() {return this.id;}

    public String getProduct_name() {return product_name;}

    public Integer getPrice() {return price;}

    public Integer getDiscount() {return discount;}

    public Collection<Order> getInOrders() {return inOrders;}

    public Collection<StoreUser> getLikedBy() {return likedBy;}

    /*
        Setters
     */
    @Override
    public void setId(Integer id_product) {this.id = id_product;}

    public void setProduct_name(String product_name) {this.product_name = product_name;}

    public void setPrice(Integer price) {this.price = price;}

    public void setDiscount(Integer discount) {this.discount = discount;}

    public void setInOrders(Collection<Order> inOrders) {this.inOrders = inOrders;}

    public void setLikedBy(Collection<StoreUser> likedBy) {this.likedBy = likedBy;}
}
