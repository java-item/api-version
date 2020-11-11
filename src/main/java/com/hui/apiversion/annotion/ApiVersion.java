package com.hui.apiversion.annotion;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
/**
 * 版本控制器
 */
public @interface ApiVersion {
    // 标识版本号
    int value() default 1;
}
