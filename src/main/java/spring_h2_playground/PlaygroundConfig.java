package spring_h2_playground;

import java.util.HashMap;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import spring_h2_playground.model.user.UserDao;

@Configuration
@EnableConfigurationProperties({PlaygroundConfig.Datasource1.class, PlaygroundConfig.Datasource2.class})
public class PlaygroundConfig {

    @Bean(name = "ds1")
    public DataSource dataSource1(Datasource1 config) {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(config.getDriverClassName());
        dataSource.setUrl(config.getUrl());
        dataSource.setUsername(config.getUser());
        dataSource.setPassword(config.getPassword());

        return dataSource;
    }

    @Bean(name = "ds1entitymanager")
    public LocalContainerEntityManagerFactoryBean userEntityManager1(@Qualifier("ds1") DataSource ds1, Datasource1 config) {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(ds1);
        em.setPackagesToScan(new String[]{"spring_h2_playground.model.user"});

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", config.getAuto());
        properties.put("hibernate.dialect", config.getDialect());
        em.setJpaPropertyMap(properties);

        return em;
    }

    @Bean(name = "userDao1")
    public UserDao userDao1(@Qualifier("ds1entitymanager") EntityManager mgr) {
        return new UserDao(mgr);
    }

    @Bean(name = "ds2")
    public DataSource dataSource2(Datasource2 config) {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(config.getDriverClassName());
        dataSource.setUrl(config.getUrl());
        dataSource.setUsername(config.getUser());
        dataSource.setPassword(config.getPassword());

        return dataSource;
    }

    @Bean(name = "ds2entitymanager")
    public LocalContainerEntityManagerFactoryBean userEntityManager2(@Qualifier("ds2") DataSource ds2, Datasource2 config) {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(ds2);
        em.setPackagesToScan(new String[]{"spring_h2_playground.model.user"});

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", config.getAuto());
        properties.put("hibernate.dialect", config.getDialect());
        em.setJpaPropertyMap(properties);

        return em;
    }

    @Bean(name = "userDao2")
    public UserDao userDao2(@Qualifier("ds2entitymanager") EntityManager mgr) {
        return new UserDao(mgr);
    }

    public static abstract class Datasource {

        private String url;
        private String user;
        private String password;
        private String driverClassName;
        private String auto;
        private String dialect;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getUser() {
            return user;
        }

        public void setUser(String user) {
            this.user = user;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getDriverClassName() {
            return driverClassName;
        }

        public void setDriverClassName(String driverClassName) {
            this.driverClassName = driverClassName;
        }

        public String getAuto() {
            return auto;
        }

        public void setAuto(String auto) {
            this.auto = auto;
        }

        public String getDialect() {
            return dialect;
        }

        public void setDialect(String dialect) {
            this.dialect = dialect;
        }

    }

    @ConfigurationProperties("spring.datasource1")
    public static class Datasource1 extends Datasource {

    }

    @ConfigurationProperties("spring.datasource2")
    public static class Datasource2 extends Datasource {

    }

}
