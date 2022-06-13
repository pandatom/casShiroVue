package com.pandatom.example.config;

import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @Description: TODO
 * @author: changxiong
 * @date: 2022年03月25日 10:39 AM
 */
@Configuration
public class DatasourceConfig {

    @Value("${spring.datasource.druid.url}")
    private String url;
    @Value("${spring.datasource.druid.driver-class-name}")
    private String driver;
    @Value("${spring.datasource.druid.username}")
    private String username;
    @Value("${spring.datasource.druid.password}")
    private String password;

    @Bean
    public DataSource datasource() {
        return DataSourceBuilder.create()
                .driverClassName(driver)
                .url(url)
                .username(username)
                .password(password)
                .build();
    }
}