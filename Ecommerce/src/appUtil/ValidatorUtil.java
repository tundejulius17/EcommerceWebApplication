package appUtil;

public class ValidatorUtil {

	// This checks the phone pattern.
	public static boolean checkPhone(String phone) {
		String phoneRegEx = "[0][1-9][0-9]{8}";

		if (phone.matches(phoneRegEx))
			return true;
		else
			return false;
	}

	// This checks the email pattern.
	public static boolean checkEmail(String email) {
		String emailRegEx = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";

		if (email.matches(emailRegEx))
			return true;
		else
			return false;
	}

	// This checks for the date input pattern.
	public static boolean checkDate(String dateString) {
		String dateRegEx = "([2][0][1-9][0-9])-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])";
		if (dateString.matches(dateRegEx))
			return true;
		else
			return false;
	}

	// This checks for empty or null input.
	public static boolean checkParam(String param) {
		if (param != null && (!param.equals("")))
			return true;
		else {
			return false;
		}
	}

}
