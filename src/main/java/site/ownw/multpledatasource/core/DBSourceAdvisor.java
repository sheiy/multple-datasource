package site.ownw.multpledatasource.core;

import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractBeanFactoryPointcutAdvisor;
import org.springframework.lang.NonNull;

public class DBSourceAdvisor extends AbstractBeanFactoryPointcutAdvisor {
    @Override
    @NonNull
    public Pointcut getPointcut() {
        return new DBSourcePointcut();
    }
}
