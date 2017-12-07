package me.codetalk.mybatis.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.core.annotation.AliasFor;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Shardby {

	// 用于获取id的map key
	@AliasFor("key")
	String value() default "";
	
	int range() default 10000;
	
}
