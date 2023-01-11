package cz.cvut.fit.tjv.music_store.domain;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

/*
 This class represents Order (basically a list of Products that User bought) data object in the database
 - it holds information about specific Order
 */
@Entity
@Table(name = "orders")
public class Order implements  DomainEntity<Integer> {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "invoice")
    private String invoice;
    @Column(name = "cost")
    private Integer cost;
    private String order_status;
    @Column(name = "date_of_order")
    private LocalDateTime date_of_order = LocalDateTime.now();


    /*
        Relations
     */
    @ManyToOne
    private StoreUser Buyer;
    @ManyToMany
    private Collection<Product> Bought_Items;

    /*
        Constructor
    */
    public Order(int id, StoreUser buyer, ArrayList<Product> bought_Items, String invoice, Integer cost, String order_status, LocalDateTime date_of_order) {
        this.id = id;
        Buyer = buyer;
        Bought_Items = bought_Items;
        this.invoice = invoice;
        this.cost = cost;
        this.order_status = order_status;
        this.date_of_order = date_of_order;
    }

    public Order(){};

    /*
        Getters
     */
    @Override
    public Integer getId() {return this.id;}

    public StoreUser getBuyer() {return Buyer;}

    public Collection<Product> getBought_Items() {return Bought_Items;}

    public String getInvoice() {return invoice;}

    public Integer getCost() {return cost;}

    public String getOrder_status() {return order_status;}

    public LocalDateTime getDate_of_order() {return date_of_order;}


    /*
        Setters
     */

    public void setId(Integer id_order) {this.id = id_order;}

    public void setBuyer(StoreUser buyer) {Buyer = buyer;}

    public void setBought_Items(Collection <Product> bought_Items) {Bought_Items = bought_Items;}

    public void setInvoice(String invoice) {this.invoice = invoice;}

    public void setCost(Integer cost) {this.cost = cost;}

    public void setOrder_status(String order_status) {this.order_status = order_status;}

    public void setDate_of_order(LocalDateTime date_of_order) {this.date_of_order = date_of_order;}
}
