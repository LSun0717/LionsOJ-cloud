package org.gzu.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Classname: AuthCheck
 * @Description: 自定义权限校验注解
 * @Author: lions
 * @Datetime: 12/29/2023 12:11 AM
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthCheck {

    /**
     * @Description: 判断必须拥有某个权限
     * @Return: 权限
     * @Author: lions
     * @Datetime: 12/29/2023 12:11 AM
     */
    String mustRole() default "";

}

