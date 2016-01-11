package hello.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.interceptor.CustomizableTraceInterceptor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import javax.sql.DataSource;


@Configuration
@EnableAspectJAutoProxy
@EnableAutoConfiguration
public class ApplicationConfiguration {

    @Value("$(spring.datasource.driver-class-name)")
    private String driverClassName;
    @Value("$(spring.datasource.url)")
    private String url;
    @Value("$(spring.database.username)")
    private String userName;
    @Value("$(spring.database.password)")
    private String password;
    @Value("$(hibernate.hbm2ddl.auto)")
    private String hibernateDDLAutoConfig;


    @Bean
    public CustomizableTraceInterceptor customizableTraceInterceptor() {
        CustomizableTraceInterceptor customizableTraceInterceptor = new CustomizableTraceInterceptor();
        customizableTraceInterceptor.setEnterMessage("Intercepted");
        customizableTraceInterceptor.setExitMessage("out of bounds");

        return customizableTraceInterceptor;
    }

    @Bean
    public Advisor traceAdvisor() {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("execution(public * org.springframework.data.repository.Repository+.*(..))");
        return new DefaultPointcutAdvisor(pointcut, customizableTraceInterceptor());
    }

    /*@Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory()
    {
        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(Boolean.TRUE);
        vendorAdapter.setShowSql(Boolean.TRUE);

        factory.setDataSource(dataSource());
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPackagesToScan("hello.entity");

        Properties jpaProperties = new Properties();
        jpaProperties.put("hibernate.hbm2ddl.auto", "validate");
        jpaProperties.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        factory.setJpaProperties(jpaProperties);

       // factory.afterPropertiesSet();
        factory.setLoadTimeWeaver(new InstrumentationLoadTimeWeaver());
        return factory;
    }*/

    @Bean
    public DataSource dataSource() {
        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setDriverClassName("org.postgresql.Driver");
        basicDataSource.setUrl("jdbc:postgresql://localhost:5432/p1");
        basicDataSource.setUsername("postgres");
        basicDataSource.setPassword("password");
        basicDataSource.setDefaultQueryTimeout(6000);
        basicDataSource.setMaxIdle(5);
        basicDataSource.setMinIdle(3);
        basicDataSource.setMaxOpenPreparedStatements(30);
        basicDataSource.setMaxTotal(30);
        return basicDataSource;
    }


}
