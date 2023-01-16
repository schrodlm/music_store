package cz.cvut.fit.tjv.music_store.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Collection;
import java.util.Optional;

@ApiModel(value = "User", description = "This entity represents registered user of the store")
public class StoreUserDto {
    @ApiModelProperty(value = "Identification of the store user")
    @JsonIgnore
    private int id;

    @ApiModelProperty(value = "Second identification of the user", example = "johndoe")
    private String username;
    @ApiModelProperty(value = "First name of the user", example="John")
    private String name;
    @ApiModelProperty(value = "Last name of the user", example = "Doe")
    private String surname;
    @ApiModelProperty(value = "Password of the user, it is encrypted (bcrypt)", example = "password123" )
    private String password;
    @ApiModelProperty(value = "Role of the user that gives user authorization in client", example = "USER")
    private String role;
    @ApiModelProperty(value = "Address of the user", example = "Address 31, City")
    private String address;
    @ApiModelProperty(value = "Email of the user", example = "johndoe@email.com")
    private String email;
    @ApiModelProperty(value = "Credit card information of the user", example = "3411 4422 4221 4454")
    private String credit_card;
    @ApiModelProperty(value = "Liked products by user - consists of products id", example = "[1,2,3]")
    private Collection<Integer> likedProducts;




    
    public Collection<Integer> getLikedProducts() {
        return likedProducts;
    }

    public void setLikedProducts(Collection<Integer> likedProducts) {
        this.likedProducts = likedProducts;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }


    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCredit_card() {
        return credit_card;
    }

    public void setCredit_card(String credit_card) {
        this.credit_card = credit_card;
    }
}
