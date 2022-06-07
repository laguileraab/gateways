package com.msoft.gateways.utils;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.Pattern;

@Pattern(
  regexp = "^([0-1]?\\d?\\d|2[0-4]\\d|25[0-5])(\\.([0-1]?\\d?\\d|2[0-4]\\d|25[0-5])){3}$"
)
@Target({ METHOD, FIELD, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = {})
@Documented
@ReportAsSingleViolation
public @interface Ipv4 {
  String message() default "must be ipv4";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
