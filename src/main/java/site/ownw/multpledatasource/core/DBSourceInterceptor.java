package site.ownw.multpledatasource.core;

import java.lang.reflect.Method;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class DBSourceInterceptor implements MethodInterceptor {
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        try {
            Method method = invocation.getMethod();
            Class<?> targetClass = method.getDeclaringClass();
            DBSource methodAnnotation = method.getAnnotation(DBSource.class);
            DBSource clsAnnotation = targetClass.getAnnotation(DBSource.class);
            if (methodAnnotation != null) {
                MultipleDatasourceContext.setCurrentDataSourceName(methodAnnotation.value());
            } else if (clsAnnotation != null) {
                MultipleDatasourceContext.setCurrentDataSourceName(clsAnnotation.value());
            }
            return invocation.proceed();
        } finally {
            MultipleDatasourceContext.clearCurrentDataSourceName();
        }
    }
}
