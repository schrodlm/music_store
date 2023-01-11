package cz.cvut.fit.tjv.music_store.api.model;

/*

    This class is temporarily created when JSON wants to refer to our Entities, this is more readable
    and easier to represent in JSON format. After it was used, it gets deleted.

    For instance, it gets rid of showing data of joined tables, not necessary

 */

public class ProductDto {

    private int id;
    private String product_name;
    private Integer price;
    private Integer discount;

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
