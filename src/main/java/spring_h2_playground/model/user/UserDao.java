package spring_h2_playground.model.user;

import javax.persistence.EntityManager;

import org.springframework.transaction.annotation.Transactional;

@Transactional
public class UserDao {

    private final EntityManager mgr;

    public UserDao(EntityManager mgr) {
        this.mgr = mgr;
    }

    public User getUser(int id) {
        return mgr.find(User.class, id);
    }

    public int persist(User user) {
        mgr.persist(user);

        return user.getId();
    }

}
