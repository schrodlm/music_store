package cz.cvut.fit.tjv.music_store.domain;

/*
 This class represents user data object in the database
 - it is an account of a user who is engaging in our website
 */

import cz.cvut.fit.tjv.music_store.api.ProductController;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name = "users")
public class StoreUser implements DomainEntity<Integer> {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "username")
    private String username;
    @Column(name = "name")
    private String name;
    @Column(name = "surname")
    private String surname;

    @Column(name = "address")
    private String address;

    @Column(name = "email")
    private String email;
    @Column(name = "credit_card")
    private String credit_card;

    @Column(name="password")
    private String password;

    @Column(name="role")
    private String role;

    /*
        Relations
     */

    @OneToMany(mappedBy = "buyer",  cascade = CascadeType.REMOVE)
    Collection<Order> userOrders;

    @ManyToMany(cascade = CascadeType.REMOVE)
    Collection<Product> likedProducts;



    /*
        Constructors
     */
    public StoreUser(int id, String username, String password, String role, String name, String surname, String address, String email, String credit_card) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.email = email;
        this.credit_card = credit_card;
    }

    public StoreUser(){};

    @Override
    public Integer getId() {return this.id;}

    public String getUsername() {return username;}

    public String getName() {return name;}

    public String getSurname() {return surname;}

    public String getAddress() {return address;}

    public String getEmail() {return email;}

    public String getCredit_card() {return credit_card;}

    public Collection<Order> getUserOrders() {return userOrders;}

    public Collection<Product> getLikedProducts() {return likedProducts;}

    /*
        Setters
     */
    @Override
    public void setId(Integer id_user) {this.id = id_user;}

    public void setUsername(String username) {this.username = username;}

    public void setName(String name) {this.name = name;}

    public void setSurname(String surname) {this.surname = surname;}

    public void setAddress(String address) {this.address = address;}

    public void setEmail(String email) {this.email = email;}

    public void setCredit_card(String credit_card) {this.credit_card = credit_card;}

    public void setUserOrders(Collection<Order> userOrders) {this.userOrders = userOrders;}

    public void setLikedProducts(Collection<Product> likedProducts) {this.likedProducts = likedProducts;}


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
}
