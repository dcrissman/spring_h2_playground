package spring_h2_playground.datasource2;

import java.util.HashMap;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import spring_h2_playground.model.user.UserDao;

@Configuration
@EnableJpaRepositories(
        basePackages = "spring_h2_playground.datasource2",
        entityManagerFactoryRef = "userEntityManager2",
        transactionManagerRef = "ds2TransactionManager"
        )
public class EM2Config {

    @Bean(name = "ds2")
    @ConfigurationProperties("spring.datasource2")
    public DataSource dataSource2() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "userEntityManager2")
    public LocalContainerEntityManagerFactoryBean userEntityManager2(@Qualifier("ds2") DataSource ds2) {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(ds2);
        em.setPackagesToScan(new String[]{"spring_h2_playground.model.user"});

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", "create-drop");
        properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        em.setJpaPropertyMap(properties);

        return em;
    }

    @Bean(name = "ds2TransactionManager")
    public PlatformTransactionManager ds2TransactionManager(@Qualifier("userEntityManager2") LocalContainerEntityManagerFactoryBean ds2em) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(ds2em.getObject());
        return transactionManager;
    }

    @Bean(name = "userDao2")
    public UserDao userDao2(@Qualifier("userEntityManager2") EntityManager mgr) {
        return new UserDao2(mgr);
    }

}
