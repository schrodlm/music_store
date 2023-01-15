package cz.cvut.fit.tjv.music_store.api.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class OrderDto {

    private int id;
    private int buyer_id;
    private ArrayList<Integer> items_id;
    private Integer cost;
    private String order_status;
    @DateTimeFormat(pattern = "dd.MM.yyyy. HH:mm a")
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
