package com.ht.util;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * Excel实体BEAN的属性注解
 * 
 * @author Alvin 2012-7-24
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ExcelAnnotation {

	String name();// Excel列名

	short width();// Excel列宽

	short align();// 水平对齐
	
	int id();// Excel列ID
	
}

