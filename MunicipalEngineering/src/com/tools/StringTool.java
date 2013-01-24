package com.tools;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringTool {
	public static boolean isNumber(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher match = pattern.matcher(str);
		if (match.matches())
			return true;
		return false;
	}
}
