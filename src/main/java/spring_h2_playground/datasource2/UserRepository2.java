package spring_h2_playground.datasource2;

import org.springframework.data.jpa.repository.JpaRepository;

import spring_h2_playground.model.user.User;

public interface UserRepository2 extends JpaRepository<User, Integer> {

}
