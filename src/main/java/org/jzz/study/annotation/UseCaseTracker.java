package org.jzz.study.annotation;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UseCaseTracker {
	public static void trackUserCases(List<Integer> useCases, Class<?> cl) {
		for(Method method : cl.getDeclaredMethods()) {
			UseCase uc = method.getAnnotation(UseCase.class);
			if (uc != null) {
				System.out.println("Found Use Case:" + uc.id() + " " + uc.description());
				useCases.remove(Integer.valueOf(uc.id()));
			}
		}
		for (int i : useCases) {
			System.out.println("Warnning: Missing use case-" + i);
		}
	}
	
	public static void main(String[] args) {
		List<Integer> useCases = new ArrayList<Integer>();
		Collections.addAll(useCases, 47, 48, 49, 50);
		trackUserCases(useCases, PasswordUtils.class);
	}
}
