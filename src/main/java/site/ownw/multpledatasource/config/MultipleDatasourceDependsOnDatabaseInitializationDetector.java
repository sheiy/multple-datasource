package site.ownw.multpledatasource.config;

import java.util.Collections;
import java.util.Set;
import org.springframework.boot.sql.init.dependency.AbstractBeansOfTypeDependsOnDatabaseInitializationDetector;
import site.ownw.multpledatasource.core.MultipleDatasource;

public class MultipleDatasourceDependsOnDatabaseInitializationDetector
        extends AbstractBeansOfTypeDependsOnDatabaseInitializationDetector {

    @Override
    protected Set<Class<?>> getDependsOnDatabaseInitializationBeanTypes() {
        return Collections.singleton(MultipleDatasource.class);
    }
}
