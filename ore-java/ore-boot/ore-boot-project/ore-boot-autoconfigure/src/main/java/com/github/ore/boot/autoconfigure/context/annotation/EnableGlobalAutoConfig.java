package com.github.ore.boot.autoconfigure.context.annotation;

import org.springframework.context.annotation.Import;

import com.github.ore.boot.autoconfigure.context.GlobalAutoConfigure;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import(GlobalAutoConfigure.class)
public @interface EnableGlobalAutoConfig {
	
}
