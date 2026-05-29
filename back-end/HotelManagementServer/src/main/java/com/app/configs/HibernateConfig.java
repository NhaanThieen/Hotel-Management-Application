package com.app.configs;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.util.Properties;
import javax.sql.DataSource;
import static org.hibernate.cfg.JdbcSettings.DIALECT;
import static org.hibernate.cfg.JdbcSettings.SHOW_SQL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

@Configuration
@PropertySource("classpath:database.properties")
public class HibernateConfig {

    @Autowired
    private Environment env;

    @Bean
    // Datasource là 1 connection pool manager, nhiệm vụ là tạo sẵn các connection tới db. Quản lý các connection.
    // -> Tạo ra Datasource rất tốn chi phí, do nó phải tạo kèm thêm các connection tới db. Nên ta cần cho nó 
    // là Bean để Singleton
    public DataSource datasource() {
        
        // Sử dụng connection pool hikari
        HikariConfig config = new HikariConfig();
        config.setDriverClassName(env.getProperty("hibernate.connection.driverClass"));
        config.setJdbcUrl(env.getProperty("hibernate.connection.url"));
        config.setUsername(env.getProperty("hibernate.connection.username"));
        config.setPassword(env.getProperty("hibernate.connection.password"));
        
        // Mở sẵn 20 connection
        config.setMaximumPoolSize(env.getProperty("hikari.maximum.poolsize", Integer.class));
        // Ít người dùng thì luôn mở 2 connection
        config.setMinimumIdle(env.getProperty("hikari.minium.idle", Integer.class));
        // Connection nào rãnh quá 30s thì hủy để tiết kiệm tài nguyên
        config.setIdleTimeout(env.getProperty("hikari.timeout.idle", Integer.class));
        return new HikariDataSource(config);
    }

    // Đây chỉ là thông tin thêm của hibernate, không nặng như datasource nên không cần bean.
    private Properties hibernateProperties() {
        Properties props = new Properties();
        props.put(DIALECT, env.getProperty("hibernate.dialect"));
        props.put(SHOW_SQL, env.getProperty("hibernate.showSql"));
        props.put("hibernate.default_batch_fetch_size", env.getProperty("hibernate.default_batch_fetch_size"));
        return props;

    }

    // Tạo ra SessionFactory -> Bean, chỉ cần 1 SessionFactory singleton là đủ.
    @Bean
    public LocalSessionFactoryBean getSessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();

        sessionFactory.setPackagesToScan(new String[]{"com.app.pojo"});

        // Set Session Factory sử dụng Connection Pool Manager này
        // Khi nào tạo Session thì cứ vào Datasource để lấy connection dùng, sau đó trả lại
        sessionFactory.setDataSource(datasource());

        sessionFactory.setHibernateProperties(hibernateProperties());
        return sessionFactory;
    }

    // Giao việc đóng mở transaction (kèm theo session) của hibernate cho Spring quản lý
    // chỗ nào cần dùng transaction chỉ cần khai báo @Transactional lên là được
    // Spring tự động mở Session và tự động mở Transaction
    @Bean
    public HibernateTransactionManager transactionManager() {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(getSessionFactory().getObject());
        return transactionManager;
    }
}
