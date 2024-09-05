package com.sky.annotation;

import com.sky.enumeration.OperationType;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 公共字段自动填充注解
 * */
//注解的作用范围为方法
@Target(ElementType.METHOD)
//注解的生命周期为运行时
@Retention(RetentionPolicy.RUNTIME)
public @interface AutoFill {
    OperationType value();
}
