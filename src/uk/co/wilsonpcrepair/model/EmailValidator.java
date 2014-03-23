package uk.co.wilsonpcrepair.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidator {
	
	private static Pattern pattern = null;
	private static Matcher matcher = null;
	
	private static final String EMAIL_PATTERN = 
			"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" +
			"[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	
	public static boolean validate(String email){
		pattern = Pattern.compile(EMAIL_PATTERN);
		
		matcher = pattern.matcher(email);
		return matcher.matches();
	}

}
