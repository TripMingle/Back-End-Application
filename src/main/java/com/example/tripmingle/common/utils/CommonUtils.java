package com.example.tripmingle.common.utils;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.StringJoiner;

import org.springframework.stereotype.Component;

@Component
public class CommonUtils {

	private double epsilon = 0.000000000001;

	public String convertListToString(List<String> list) {
		StringJoiner joiner = new StringJoiner(",");
		for (String s : list) {
			joiner.add(s);
		}
		return joiner.toString();
	}

	public List<String> convertStringToArray(String str) {
		if (str == null || str.isEmpty()) {
			return Collections.emptyList();
		}
		return Arrays.asList(str.split(","));
	}

	public boolean isEndDatePassed(LocalDate endDate) {
		if (endDate.equals(null)) {
			return false;
		}

		LocalDate currentDate = LocalDate.now();

		return !currentDate.isBefore(endDate);
	}

	public double convertIntToDouble(int input) {
		return (double)input;
	}

	public int convertDoubleToInt(double input) {
		input += epsilon;
		return (int)input;
	}

}
