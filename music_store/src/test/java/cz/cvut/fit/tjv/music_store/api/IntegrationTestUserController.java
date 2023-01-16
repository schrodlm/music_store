package cz.cvut.fit.tjv.music_store.api;

import cz.cvut.fit.tjv.music_store.bussiness.StoreUserService;
import cz.cvut.fit.tjv.music_store.domain.StoreUser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;

@SpringBootTest
public class IntegrationTestUserController {

    @Autowired
    StoreUserController userController;

    @Autowired
    StoreUserService userService;



    @Test
    @Transactional
    @Rollback
    @DirtiesContext
    public void testReadAll()
    {
        StoreUser user = new StoreUser(1,"Test", "password", "USER", "Name", "LastName", "address", "email", "credit_card" );
        userService.create(user);

        var users2 = userController.readAll();
        Assertions.assertEquals(users2.size(), 1);

    }
    @Test
    @Transactional
    @Rollback
    @DirtiesContext
    public void testReadOneByUsername()
    {
        StoreUser user = new StoreUser(1,"Test", "password", "USER", "Name", "LastName", "address", "email", "credit_card" );
        userService.create(user);

        Assertions.assertEquals(userService.readByUsername("Test").orElseThrow(), user);
    }

    @Test
    @Transactional
    @Rollback
    @DirtiesContext
    public void testReadOneById()
    {


        StoreUser user = new StoreUser(1,"Test", "password", "USER", "Name", "LastName", "address", "email", "credit_card" );
        userService.create(user);

        Assertions.assertEquals(userService.readById(1).orElseThrow(), user);
    }

    @Test
    @Transactional
    @Rollback
    @DirtiesContext

    public void testUpdate()
    {
        StoreUser originalUser = new StoreUser(1,"Test", "password", "USER", "Name", "LastName", "address", "email", "credit_card" );
        userService.create(originalUser);

        StoreUser user = new StoreUser(1,"Test1", "password", "USER", "Name", "LastName", "address", "email", "credit_card" );
        userService.update(user, 1);

        Assertions.assertEquals(userService.readById(1).orElseThrow(), user);
    }

    @Test
    @Transactional
    @Rollback
    @DirtiesContext
    public void testDelete()
    {
        StoreUser user = new StoreUser(1,"Test", "password", "USER", "Name", "LastName", "address", "email", "credit_card" );
        userService.create(user);
        userService.deleteById(1);

        Assertions.assertEquals(userController.readAll().size(), 0);
    }
}
