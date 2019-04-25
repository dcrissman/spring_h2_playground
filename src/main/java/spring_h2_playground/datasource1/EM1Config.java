package spring_h2_playground.datasource1;

import java.util.HashMap;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import spring_h2_playground.model.user.UserDao;

@Configuration
@EnableJpaRepositories(
        basePackages = "spring_h2_playground.datasource1",
        entityManagerFactoryRef = "userEntityManager1",
        transactionManagerRef = "ds1TransactionManager"
        )
public class EM1Config {

    @Bean(name = "ds1")
    @ConfigurationProperties("spring.datasource1")
    public DataSource dataSource1() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "userEntityManager1")
    public LocalContainerEntityManagerFactoryBean userEntityManager1(@Qualifier("ds1") DataSource ds1) {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(ds1);
        em.setPackagesToScan(new String[]{"spring_h2_playground.model.user"});

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(true);

        em.setJpaVendorAdapter(vendorAdapter);
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", "create-drop");
        properties.put("hibernate.dialect", "org.hibernate.dialect.Oracle10gDialect");
        em.setJpaPropertyMap(properties);

        return em;
    }

    @Bean(name = "ds1TransactionManager")
    @Primary
    public PlatformTransactionManager ds1TransactionManager(@Qualifier("userEntityManager1") LocalContainerEntityManagerFactoryBean ds1em) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(ds1em.getObject());
        return transactionManager;
    }

    @Bean(name = "userDao1")
    public UserDao userDao1(@Qualifier("userEntityManager1") EntityManager mgr) {
        return new UserDao1(mgr);
    }

}
