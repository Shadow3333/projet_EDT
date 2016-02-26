package business.model.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Validator for <code>Email</code> interface
 * @author DUBUIS Michael
 *
 */
public class EmailConstraintValidator
implements ConstraintValidator<Email, String> {
	private static final String EMAIL_PATTERN = 
			"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
					+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	private Pattern pattern;

	public final void initialize(Email annotation) {
		pattern = Pattern.compile(EMAIL_PATTERN);
	}

	public boolean isValid(String value, ConstraintValidatorContext context) {
		Matcher matcher = pattern.matcher(value);
		return matcher.matches();
	}

}