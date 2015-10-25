package com.omnisource.utils;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

	public static final Pattern DASH_PATTERN = Pattern.compile("\\-");

	public static String generateGuid() {
		String guid = UUID.randomUUID().toString();
		Matcher dashMatcher = DASH_PATTERN.matcher(guid);
		guid = dashMatcher.replaceAll("");
		return guid;
	}
}
