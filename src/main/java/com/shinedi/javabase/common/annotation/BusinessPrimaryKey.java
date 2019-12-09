package com.shinedi.javabase.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author 蔡仁浩
 * @e-mail cairenhao1314@sina.com
 * @desc  业务主键标识  (一个类只能有一个业务主键)
 * @since 2019年10月23日 19:16
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface BusinessPrimaryKey {
}
