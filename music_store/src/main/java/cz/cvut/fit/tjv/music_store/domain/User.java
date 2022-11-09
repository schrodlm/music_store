package cz.cvut.fit.tjv.music_store.domain;

/*
 This class represents user data object in the database
 - it is an account of a user who is engaging in our website
 */

public class User implements DomainEntity<Integer> {

    public User(int id_user, String username, String name, String surname, String address, String email, String credit_card) {
        this.id_user = id_user;
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.email = email;
        this.credit_card = credit_card;
    }

    final private int id_user;
    final private String username;
    final private String name;
    final private String surname;
    final public String address;
    final private String email;
    final private String credit_card;



    @Override
    public Integer getId_user() {return this.id_user;}

    public String getName() {return this.name;}


}
