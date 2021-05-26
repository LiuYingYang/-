package com.medusa.basemall.aop.annotation;

import com.medusa.basemall.constant.ScopeType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface LookTimeAspect {

	int scope() default ScopeType.ARGUMENTS;
}
