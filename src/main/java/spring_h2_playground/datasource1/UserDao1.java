package spring_h2_playground.datasource1;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;

import spring_h2_playground.model.user.User;
import spring_h2_playground.model.user.UserDao;

@Transactional("ds1TransactionManager")
public class UserDao1 implements UserDao {

    private final static Logger LOGGER = LoggerFactory.getLogger(UserDao1.class);

    private final EntityManager mgr;

    public UserDao1(@Qualifier("userEntityManager1") EntityManager mgr) {
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
