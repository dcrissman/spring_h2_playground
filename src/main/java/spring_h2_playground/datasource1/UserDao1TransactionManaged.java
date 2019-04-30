package spring_h2_playground.datasource1;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import spring_h2_playground.model.user.User;
import spring_h2_playground.model.user.UserDao;

public class UserDao1TransactionManaged implements UserDao {

    private final static Logger LOGGER = LoggerFactory.getLogger(UserDao1TransactionManaged.class);

    private final EntityManager mgr;
    private final PlatformTransactionManager transactionManager;

    public UserDao1TransactionManaged(
            @Qualifier("userEntityManager1") EntityManager mgr,
            @Qualifier("ds1TransactionManager") PlatformTransactionManager transactionManager) {
        this.mgr = mgr;
        this.transactionManager = transactionManager;
    }

    @Override
    public User getUser(int id) {
        LOGGER.info("getUser " + id);

        TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            User user = mgr.find(User.class, id);
            transactionManager.commit(status);
            return user;
        }
        catch (Exception e) {
            transactionManager.rollback(status);
            throw e;
        }
    }

    @Override
    public int persist(User user) {
        LOGGER.info("persist " + user);

        TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            mgr.persist(user);
            transactionManager.commit(status);
            return user.getId();
        }
        catch (Exception e) {
            transactionManager.rollback(status);
            throw e;
        }
    }

}
