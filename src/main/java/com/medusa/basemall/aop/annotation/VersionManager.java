package com.medusa.basemall.aop.annotation;

import com.medusa.basemall.constant.VersionNumber;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface VersionManager {


	/**
	 * enable设置为false 第三方调用接口不给予拦截,如微信回调等
	 * @return
	 */
	boolean enable() default true;

	int versoinNumber() default VersionNumber.BASICVERSION;
}
