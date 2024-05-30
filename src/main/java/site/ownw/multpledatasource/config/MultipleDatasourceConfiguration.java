package site.ownw.multpledatasource.config;

import javax.sql.DataSource;
import org.aopalliance.intercept.Interceptor;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.aop.Advisor;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;
import site.ownw.multpledatasource.core.DBSourceAdvisor;
import site.ownw.multpledatasource.core.DBSourceInterceptor;
import site.ownw.multpledatasource.core.MultipleDatasource;

@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(MultipleDatasourceProperties.class)
@AutoConfiguration(before = {DataSourceAutoConfiguration.class, MybatisAutoConfiguration.class})
public class MultipleDatasourceConfiguration {

    @Bean
    public DataSource multipleDatasource(MultipleDatasourceProperties multipleDatasourceProperties) {
        return new MultipleDatasource(multipleDatasourceProperties);
    }

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public Interceptor dbsourceInterceptor() {
        return new DBSourceInterceptor();
    }

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public Advisor advisor(Interceptor dbsourceInterceptor) {
        final DBSourceAdvisor advisor = new DBSourceAdvisor();
        advisor.setAdvice(dbsourceInterceptor);
        return advisor;
    }
}
