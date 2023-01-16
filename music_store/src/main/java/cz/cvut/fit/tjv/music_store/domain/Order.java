package cz.cvut.fit.tjv.music_store.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

/*
 This class represents Order (basically a list of Products that User bought) data object in the database
 - it holds information about specific Order
 */
@Entity
@Table(name = "orders")
@ApiModel(value = "ExampleRequest", description = "Example request model")
public class Order implements  DomainEntity<Integer> {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "cost")
    @Min(10)
    @Max(1000000)
    @ApiModelProperty(value = "Name of the user", example = "John Doe")
    private Integer cost;
    private String status;
    @Column(name = "date_of_order")
    @DateTimeFormat(pattern = "dd.MM.yyyy. HH:mm a")
    private LocalDateTime date = LocalDateTime.now();


    /*
        Relations
     */
    @ManyToOne
    private StoreUser buyer;
    @ManyToMany
    private Collection<Product> boughtItems;

    /*
        Constructor
    */
    public Order(int id, StoreUser buyer, ArrayList<Product> boughtItems, Integer cost, String order_status, LocalDateTime date_of_order) {
        this.id = id;
        this.buyer = buyer;
        this.boughtItems = boughtItems;
        this.cost = cost;
        this.status = order_status;
        this.date = date_of_order;
    }

    public Order(){};


    /*
        Setters
     */
    @Override
    public void setId(Integer id_order) {this.id = id_order;}

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public StoreUser getBuyer() {
        return buyer;
    }

    public void setBuyer(StoreUser buyer) {
        this.buyer = buyer;
    }

    public Collection<Product> getBoughtItems() {
        return boughtItems;
    }

    public void setBoughtItems(Collection<Product> boughtItems) {
        this.boughtItems = boughtItems;
    }
}
