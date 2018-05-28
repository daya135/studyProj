package org.jzz.study.annotation;

import java.util.List;

public class PasswordUtils {
	@UseCase(id = 47, description = "password must contain at least one numeric")
	public boolean validatePassword(String password) {
		return (password.matches("\\w*\\d\\w*"));
	}
	
	@UseCase(id = 48)
	public String encryptPassword(String password) {
		return new StringBuffer(password).reverse().toString();
	}
	
	@UseCase(id = 49, description = "new passwords can't equal previously used ones")
	public boolean checkForNewPassword(List<String> prePasswords, String password) {
		return !prePasswords.contains(password);
	}
}
