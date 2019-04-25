package spring_h2_playground.datasource2;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;

import spring_h2_playground.model.user.User;
import spring_h2_playground.model.user.UserDao;

@Transactional("ds2TransactionManager")
public class UserDao2 implements UserDao {

    private final static Logger LOGGER = LoggerFactory.getLogger(UserDao2.class);

    private final EntityManager mgr;

    public UserDao2(@Qualifier("userEntityManager2") EntityManager mgr) {
        this.mgr = mgr;
    }

    @Override
    public User getUser(int id) {
        LOGGER.info("getUser " + id);

        return mgr.find(User.class, id);
    }

    @Override
    public int persist(User user) {
        LOGGER.info("persist " + user);

        mgr.persist(user);

        return user.getId();
    }

}
