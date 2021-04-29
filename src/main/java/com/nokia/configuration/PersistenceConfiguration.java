package com.nokia.configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
		basePackages = "com.nokia",
		entityManagerFactoryRef = "entityManagerFactory",
		transactionManagerRef = "transactionManager"
		)
public class PersistenceConfiguration {
	private final String PACKAGE_SCAN = "com.nokia";

	@Primary
	@Bean(name = "mysqlDataSource")
	@ConfigurationProperties("app.datasource.mysql")
	public DataSource mysqlDataSource() {
		return DataSourceBuilder.create().type(HikariDataSource.class).build();
	}
	@Bean(name = "mariadbADataSource")
	@ConfigurationProperties("app.datasource.mariadb")
	public DataSource mariadbDataSource() {
		return DataSourceBuilder.create().type(HikariDataSource.class).build();
	}

	@Bean(name = "multiRoutingDataSource")
	public DataSource multiRoutingDataSource() {
		Map<Object, Object> targetDataSources = new HashMap<>();
		targetDataSources.put(DBTypeEnum.MySQL, mysqlDataSource());
		targetDataSources.put(DBTypeEnum.MariaDB, mariadbDataSource());

		MultiRoutingDataSource multiRoutingDataSource = new MultiRoutingDataSource();
		multiRoutingDataSource.setDefaultTargetDataSource(mysqlDataSource());
		multiRoutingDataSource.setTargetDataSources(targetDataSources);
		return multiRoutingDataSource;
	}
	@Bean(name = "entityManagerFactory")// - multiEntityManager
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(multiRoutingDataSource());
		em.setPackagesToScan(PACKAGE_SCAN);
		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		em.setJpaVendorAdapter(vendorAdapter);
		em.setJpaProperties(hibernateProperties());
		return em;
	}
	@Bean(name = "transactionManager") // transactionManager - multiTransactionManager 
	public PlatformTransactionManager multiTransactionManager() {
		JpaTransactionManager transactionManager
		= new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(
				entityManagerFactory().getObject());
		return transactionManager;
	}
	@Primary
	@Bean(name = "dbSessionFactory")
	public LocalSessionFactoryBean dbSessionFactory() {
		LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
		sessionFactoryBean.setDataSource(multiRoutingDataSource());
		sessionFactoryBean.setPackagesToScan(PACKAGE_SCAN);
		sessionFactoryBean.setHibernateProperties(hibernateProperties());
		return sessionFactoryBean;
	}
	private Properties hibernateProperties() {
		Properties properties = new Properties();
		properties.put("hibernate.show_sql", true);
		properties.put("hibernate.format_sql", true);
		return properties;
	}

}
