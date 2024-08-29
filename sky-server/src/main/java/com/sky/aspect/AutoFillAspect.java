package com.sky.aspect;

import com.sky.annotation.AutoFill;
import com.sky.constant.AutoFillConstant;
import com.sky.context.BaseContext;
import com.sky.enumeration.OperationType;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import java.lang.reflect.Method;
import java.time.LocalDateTime;

//切面类
@Aspect
//纳入IOC容器管理
@Component
@Slf4j
public class AutoFillAspect {
    //切点,作用于com.sky.mapper包下所有类中带有@AutoFill注解的方法
    //切点表达式
    @Pointcut("execution(* com.sky.mapper..*.*(..))&&@annotation(com.sky.annotation.AutoFill)")
    public void autoFillPointcut() {}
    //前置通知，在目标方法执行前执行赋值操作
    @Before("autoFillPointcut()")
    public void autoFill(JoinPoint jpoint) {
        log.info("执行自动填充参数");
        //获取当前被拦截的方法上的数据库操作类型
        MethodSignature signature =(MethodSignature) jpoint.getSignature();//方法签名
        AutoFill autoFill = signature.getMethod().getAnnotation(AutoFill.class);//方法注解对象
        OperationType operationType = autoFill.value();//数据库操作类型
        //获取被拦截目标方法的参数
        Object[] args = jpoint.getArgs();
        if (args == null || args.length == 0) {
            return;
        }
        Object target = args[0];
        //准备赋值数据
        LocalDateTime now = LocalDateTime.now();
        Long currentId = BaseContext.getCurrentId();

        //根据不同的数据库操作，给参数属性赋值
        if (operationType == OperationType.INSERT) {
            //给参数属性赋值
            try {
                Method setCreateUser = target.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_USER, Long.class);
                Method setCreateTime = target.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_TIME, LocalDateTime.class);
                Method setUpdateUser = target.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER, Long.class);
                Method setUpdateTime = target.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
                //通过反射给参数属性赋值
                setCreateUser.invoke(target, currentId);
                setCreateTime.invoke(target, now);
                setUpdateUser.invoke(target, currentId);
                setUpdateTime.invoke(target, now);
            } catch (Exception e) {
                log.error("自动填充参数失败", e);
            }
        } else if (operationType == OperationType.UPDATE) {
            //给参数属性赋值
            try {
                Method setUpdateUser = target.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER, Long.class);
                Method setUpdateTime = target.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
                //通过反射给参数属性赋值
                setUpdateUser.invoke(target, currentId);
                setUpdateTime.invoke(target, now);
            } catch (Exception e) {
                log.error("自动填充参数失败", e);
            }
        }
    }
}
