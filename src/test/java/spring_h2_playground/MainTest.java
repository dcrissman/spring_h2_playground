package spring_h2_playground;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import spring_h2_playground.model.user.User;
import spring_h2_playground.model.user.UserDao;

@RunWith(SpringJUnit4ClassRunner.class)
@EnableAutoConfiguration
@ComponentScan
@PropertySource("classpath:application.properties")
public class MainTest {

    @Autowired
    @Qualifier("userDao1")
    private UserDao dao1;

    @Autowired
    @Qualifier("userDao2")
    private UserDao dao2;

    @Test
    public void test() {
        User user = new User();
        user.setAge(4);
        user.setName("Name");
        user.setEmail("my@email.com");

        int id1 = dao1.persist(user);
        int id2 = dao2.persist(user);

        assertEquals(dao1.getUser(id1), dao2.getUser(id2));
    }

}
