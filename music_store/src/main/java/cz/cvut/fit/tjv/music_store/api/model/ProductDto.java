package cz.cvut.fit.tjv.music_store.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/*

    This class is temporarily created when JSON wants to refer to our Entities, this is more readable
    and easier to represent in JSON format. After it was used, it gets deleted.

    For instance, it gets rid of showing data of joined tables, not necessary

 */
@ApiModel(value = "Product", description = "This entity represents products sold by the store")
public class ProductDto {

    @ApiModelProperty(value = "Identification of the product")
    private int id;
    @ApiModelProperty(value = "Name of the products", example = "Karel Kril LP Vinyl")
    private String product_name;
    @ApiModelProperty(value = "Current price of the products (in CZK)", example="100")
    private Integer price;
    @ApiModelProperty(value = "Discount that will later be deducted from the price property (in %), value between 1 - 99", example = "30")
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
