package com.shinedi.javabase.common.annotation;

import java.lang.annotation.*;

/**
 * 标记不需要 token 认证的方法
 *
 * @author D-S
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SkipTokenAuthorization {
}
