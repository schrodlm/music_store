package cz.cvut.fit.tjv.music_store.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;

/*
 This class represents Order (basically a list of Products that User bought) data object in the database
 - it holds information about specific Order
 */

public class Order implements  DomainEntity<Integer> {

    private Integer id_order;
    private User Buyer;
    private ArrayList<Product> Bought_Items = new ArrayList<>();
    private String invoice;
    private Integer cost;
    private String order_status;
    private LocalDateTime date_of_order = LocalDateTime.now();

    @Override
    public Integer getId_user() {
        return null;
    }


}
