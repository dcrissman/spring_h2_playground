package spring_h2_playground;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import spring_h2_playground.datasource1.UserRepository1;
import spring_h2_playground.datasource2.UserRepository2;
import spring_h2_playground.model.user.User;
import spring_h2_playground.model.user.UserDao;

@ExtendWith(SpringExtension.class)
@EnableAutoConfiguration
@ComponentScan
@PropertySource("classpath:application.properties")
public class MainTest {

    @Autowired
    @Qualifier("userDao1")
    private UserDao dao1;

    @Autowired
    private UserRepository1 repo1;

    @Autowired
    @Qualifier("userDao2")
    private UserDao dao2;

    @Autowired
    private UserRepository2 repo2;

    @Autowired
    @Qualifier("userDao1TransactionManaged")
    private UserDao dao1TransactionManaged;

    @Autowired
    @Qualifier("userDao2TransactionManaged")
    private UserDao dao2TransactionManaged;

    private User fakeUser() {
        User user = new User();
        user.setAge(4);
        user.setName("Name");
        user.setEmail("my@email.com");

        return user;
    }

    private void assertUser(User user) {
        assertNotNull(user);
        assertEquals(4, user.getAge());
        assertEquals("Name", user.getName());
        assertEquals("my@email.com", user.getEmail());
    }

    @Test
    public void testUserRepository1() {
        int id = repo1.save(fakeUser()).getId();

        assertUser(repo1.findById(id).get());
    }

    @Test
    public void testUserRepository2() {
        int id = repo2.save(fakeUser()).getId();

        assertUser(repo2.findById(id).get());
    }

    @Test
    public void testDao1() {
        int id = dao1.persist(fakeUser());

        assertUser(dao1.getUser(id));
    }

    @Test
    public void testDao2() {
        int id = dao2.persist(fakeUser());

        assertUser(dao2.getUser(id));
    }

    @Test
    public void testDao1TransactionManaged() {
        int id = dao1TransactionManaged.persist(fakeUser());

        assertUser(dao1TransactionManaged.getUser(id));
    }

    @Test
    public void testDao2TransactionManaged() {
        int id = dao2TransactionManaged.persist(fakeUser());

        assertUser(dao2TransactionManaged.getUser(id));
    }

}
