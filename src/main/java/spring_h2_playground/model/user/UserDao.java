package spring_h2_playground.model.user;

public interface UserDao {

    User getUser(int id);

    int persist(User user);

}
