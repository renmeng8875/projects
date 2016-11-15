package com.yy.ent.platform.demo.web.interceptor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Permissions
 *
 * @author suzhihua
 * @date 2015/7/23.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Permissions {
    PermissionsEnum[] value() default PermissionsEnum.LOGIN;
}