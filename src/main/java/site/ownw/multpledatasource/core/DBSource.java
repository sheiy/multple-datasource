package site.ownw.multpledatasource.core;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface DBSource {

    String value() default "";
}
