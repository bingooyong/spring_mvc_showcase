package org.n3r.web.thymeleaf.expression;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 设定类为FreeMarker的提供函数。
 * 
 * @author BingooHuang
 *
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SmallFunctionTag {
    /**
     * 函数前缀名称。
     */
    String value() default "";
}
