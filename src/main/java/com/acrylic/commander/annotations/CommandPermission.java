package com.acrylic.commander.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface CommandPermission {

    String[] permissions();

    String failActionId() default "";

    String failedMessage() default "&cYou do not have permission to use this command.";

}
