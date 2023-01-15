package cz.cvut.tjv.music_store_client.dto;

import jakarta.validation.constraints.*;

import java.util.Collection;

public class UserDto {

    //id is not set anywhere, it doesn't need any constraints

    private int id;

    //username is set only once -> in registration form
    @Size(min=5, max=255)
    @NotBlank
    private String username;

    @NotBlank
    @Pattern(regexp = "^[A-Z][a-zA-Z]*$")
    private String name;
    @Pattern(regexp = "^[A-Z][a-zA-Z]*$")
    private String surname;

    @Size(min=5, max=255)
    @NotBlank
    private String password;

    @Pattern(regexp = "^(ADMIN|USER)$")
    private String role;

    private String address;
    private String email;
    private String credit_card;

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
