package spring_h2_playground.datasource1;

import org.springframework.data.jpa.repository.JpaRepository;

import spring_h2_playground.model.user.User;

public interface UserRepository1 extends JpaRepository<User, Integer> {

}
