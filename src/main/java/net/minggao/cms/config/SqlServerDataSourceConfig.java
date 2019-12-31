package net.minggao.cms.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class SqlServerDataSourceConfig {
//	@Autowired
//	private EntityManagerFactory emf;

    @Bean(name = "DataSource.mysql")
    @Qualifier("sqlServerDataSource")
    @ConfigurationProperties(prefix="spring.datasource") //.mssql
    public DataSource getMyDataSource(){
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "JdbcTemplate")
    public JdbcTemplate secondaryJdbcTemplate(
            @Qualifier("sqlServerDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
    
    /*@Bean(name="sessionFactory")
    public SessionFactory sessionFactory(HibernateEntityManagerFactory hemf) {
        return hemf.getSessionFactory();
    }*/
}
