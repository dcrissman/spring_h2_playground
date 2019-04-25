package spring_h2_playground;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

    @Test
    public void test() {
        User user = new User();
        user.setAge(4);
        user.setName("Name");
        user.setEmail("my@email.com");

        int id1 = repo1.save(user).getId();
        int id2 = repo2.save(user).getId();

        //int id1 = dao1.persist(user);
        //int id2 = dao2.persist(user);

        assertEquals(dao1.getUser(id1), dao2.getUser(id2));
        assertEquals(repo1.findById(id1), repo2.findById(id2));
    }

}
