package com.blinkfox.fenix.specification.annotation;

import java.lang.annotation.*;

/**
 * OrGreater
 * @description OrGreater条件
 * @author YangWenpeng
 * @date 2019年6月6日 下午5:27:50
 * @version v1.0.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface OrGreater {

    /**
     * 
     * Equels
     * @description 字段名
     * @return
     * @author YangWenpeng
     * @date 2019年6月6日 下午5:27:50
     * @version v1.0.0
     */
    String value() default "";
}