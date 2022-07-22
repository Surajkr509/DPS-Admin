package com.dps_admin.utils;

import java.util.ArrayList;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.springframework.stereotype.Component;

import com.dps_admin.model.Admin;


@Component
public class BeanValidator {

	public Validator getValidator() {
		return Validation.buildDefaultValidatorFactory().getValidator();
	}

	public ArrayList<String> userValidate(Admin reqData) {
		ArrayList<String> arrayList = new ArrayList<>();
		Set<ConstraintViolation<Admin>> constraintViolations = getValidator().validate(reqData);
		for (ConstraintViolation<Admin> constraintViolation : constraintViolations) {
			if (constraintViolation.getPropertyPath().toString().equals("name")) {
				arrayList.add(constraintViolation.getMessage());
			}
			if (constraintViolation.getPropertyPath().toString().equals("email")) {
				arrayList.add(constraintViolation.getMessage());
			}
			if (constraintViolation.getPropertyPath().toString().equals("mobileNo")) {
				arrayList.add(constraintViolation.getMessage());
			}
		}
		System.err.println("BeanValidation ErrorList:::"+arrayList);
		return arrayList;
	}
	
	
	
	
	
}

