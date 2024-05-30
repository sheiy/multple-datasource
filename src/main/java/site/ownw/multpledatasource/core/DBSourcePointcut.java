package site.ownw.multpledatasource.core;

import java.lang.reflect.Method;
import org.springframework.aop.support.StaticMethodMatcherPointcut;

public class DBSourcePointcut extends StaticMethodMatcherPointcut {
    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        DBSource methodAnnotation = method.getAnnotation(DBSource.class);
        DBSource clsAnnotation = targetClass.getAnnotation(DBSource.class);
        return methodAnnotation != null || clsAnnotation != null;
    }
}
