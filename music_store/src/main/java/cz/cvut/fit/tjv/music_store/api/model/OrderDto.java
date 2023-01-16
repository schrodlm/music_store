package cz.cvut.fit.tjv.music_store.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import java.util.ArrayList;

@ApiModel(value = "Order", description = "This entity represent order of products created by user")
public class OrderDto {

    @ApiModelProperty(value = "Identification of the order, it is generated automatically", required = false)
    @JsonIgnore
    private int id;

    @ApiModelProperty(value = "Identification of the user that created that order", example = "1")
    private int buyer_id;

    @ApiModelProperty(value = "Identification of the products that user bought in that order", example = "[1,2,3]")
    private ArrayList<Integer> items_id;

    @ApiModelProperty(value = "Sum of all product prices included in that order", example = "100")
    private Integer cost;
    @ApiModelProperty(value = "This value can reach 4 states: [Waiting, Preparing, Shipped, Arrived] and is describing orders current state", example = "Waiting")
    private String order_status;
    @ApiModelProperty(value = "This value is date of the created order, it is generated automatically and is set to a time when order was created", example = "2022-11-03T12:45:30")
    private LocalDateTime date_of_order = LocalDateTime.now();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBuyer_id() {
        return buyer_id;
    }

    public void setBuyer_id(int buyer_id) {
        this.buyer_id = buyer_id;
    }

    public ArrayList<Integer> getItems_id() {
        return items_id;
    }

    public void setItems_id(ArrayList<Integer> items_id) {
        this.items_id = items_id;
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    public String getOrder_status() {
        return order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }

    public LocalDateTime getDate_of_order() {
        return date_of_order;
    }

    public void setDate_of_order(LocalDateTime date_of_order) {
        this.date_of_order = date_of_order;
    }
}
