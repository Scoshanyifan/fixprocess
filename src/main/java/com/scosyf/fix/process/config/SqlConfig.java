package com.scosyf.fix.process.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.sql.SQLException;

/**
 * 配置不同的数据源即可使用jdbc（sqlserver / mysql）
 *
 * @project: bucks
 * @author: kunbu
 * @create: 2019-09-25 13:29
 **/
@Configuration
public class SqlConfig {

    private static final Logger logger = LoggerFactory.getLogger(SqlConfig.class);

    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;
    @Value("${spring.datasource.username}")
    private String userName;
    @Value("${spring.datasource.password}")
    private String password;
    @Value("${spring.datasource.url}")
    private String url;

    @Bean(name = "jdbcTemplate")
    public JdbcTemplate jdbcTemplate() {

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driverClassName);
        //动态端口
        dataSource.setUrl(url);
        dataSource.setUsername(userName);
        dataSource.setPassword(password);

        try {
            logger.debug(">>> dataSource:{}", dataSource.getConnection());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new JdbcTemplate(dataSource);
    }

}
