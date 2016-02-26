package business.model.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * This annotation is used to validate a Email
 * @author DUBUIS Michael
 *
 */
@Target({
	ElementType.METHOD,
	ElementType.FIELD,
	ElementType.ANNOTATION_TYPE,
	ElementType.CONSTRUCTOR,
	ElementType.PARAMETER
	})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = EmailConstraintValidator.class)
public @interface Email
{
    String message() default "This is not an email";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default {};
}

