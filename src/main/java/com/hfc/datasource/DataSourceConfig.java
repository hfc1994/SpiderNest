package com.hfc.datasource;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * Created by user-hfc on 2019/1/3.
 */
@Configuration
@MapperScan(basePackages = "com.hfc.dao.mapper", sqlSessionFactoryRef = "spiderNestSqlSessionFactory")
public class DataSourceConfig {

    @Bean(name = "spiderNestDataSource")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.druid")
    public DataSource spiderNestDataSource() {
        return DruidDataSourceBuilder.create().build();
    }

    /**
     * session工厂
     */
    @Bean(name = "spiderNestSqlSessionFactory")
    public SqlSessionFactory spiderNestSqlSessionFactory(@Qualifier("spiderNestDataSource") DataSource dataSource)
            throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        return bean.getObject();
    }

    /**
     * 事务管理
     */
    @Bean(name = "spiderNestTransactionManager")
    public DataSourceTransactionManager spiderNextTransactionManager(@Qualifier("spiderNestDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "spiderNestSqlSessionTemplate")
    public SqlSessionTemplate spiderNestSqlSessionTemplate(@Qualifier("spiderNestSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
