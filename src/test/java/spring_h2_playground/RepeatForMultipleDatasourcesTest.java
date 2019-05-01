package spring_h2_playground;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.HashMap;
import java.util.stream.Stream;

import javax.sql.DataSource;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import spring_h2_playground.datasource1.UserDao1TransactionManaged;
import spring_h2_playground.model.user.User;
import spring_h2_playground.model.user.UserDao;

public class RepeatForMultipleDatasourcesTest {

    private static Stream<Arguments> arguments() {
        LocalContainerEntityManagerFactoryBean oracleEM = userEntityManager(
                dataSource("jdbc:h2:mem:testdbOracle;MODE=ORACLE"),
                "org.hibernate.dialect.Oracle10gDialect");
        oracleEM.afterPropertiesSet();

        LocalContainerEntityManagerFactoryBean postgresqlEM = userEntityManager(
                dataSource("jdbc:h2:mem:testdbPostgresql;MODE=POSTGRESQL"),
                "org.hibernate.dialect.PostgreSQLDialect");
        postgresqlEM.afterPropertiesSet();

        LocalContainerEntityManagerFactoryBean mariaEM = userEntityManager(
                dataSource("jdbc:h2:mem:testdbMariaDB;MODE=MySQL"),
                "org.hibernate.dialect.MariaDBDialect");
        mariaEM.afterPropertiesSet();

        return Stream.of(
                Arguments.of(new UserDao1TransactionManaged(
                        oracleEM.getObject().createEntityManager(), transactionManager(oracleEM))),
                Arguments.of(new UserDao1TransactionManaged(
                        postgresqlEM.getObject().createEntityManager(), transactionManager(postgresqlEM))),
                Arguments.of(new UserDao1TransactionManaged(
                        mariaEM.getObject().createEntityManager(), transactionManager(mariaEM))));
    }

    private static DataSource dataSource(String url) {
        return DataSourceBuilder
                .create()
                .url(url)
                .driverClassName("org.h2.Driver")
                .username("sa")
                .password("password")
                .build();
    }

    private static LocalContainerEntityManagerFactoryBean userEntityManager(DataSource ds, String dialect) {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(ds);
        em.setPackagesToScan(new String[]{"spring_h2_playground.model.user"});

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(true);

        em.setJpaVendorAdapter(vendorAdapter);
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", "create-drop");
        properties.put("hibernate.dialect", dialect);
        em.setJpaPropertyMap(properties);

        return em;
    }

    private static PlatformTransactionManager transactionManager(LocalContainerEntityManagerFactoryBean em) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(em.getObject());
        return transactionManager;
    }

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

    @ParameterizedTest
    @MethodSource("arguments")
    public void test(UserDao dao) {
        int id = dao.persist(fakeUser());

        assertUser(dao.getUser(id));
    }

}
