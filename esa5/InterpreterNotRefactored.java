package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InterpreterNotRefactored {

	public static List<String> interpret(List<String> list) {
		if (!validateSyntax(getText(list))) {
			return Arrays.asList("Invalid Input");
		}
		List<String> wmd = new ArrayList<>(list);
		for (int i1 = 0; i1 < wmd.size(); i1++) {
			if (wmd.get(i1).matches("[\\*\\/]")) {
				double v1 = Double.parseDouble(wmd.remove(i1 - 1));
				double v2 = Double.parseDouble(wmd.remove(i1));
				String o = wmd.get(i1 - 1);
				String result = "" + calculate(o, v1, v2);
				wmd.set(i1 - 1, result);
				i1--;
			}
		}

		if (wmd.contains("NaN")) {
			return wmd;
		}

		List<Double> num = new ArrayList<>();
		for (String s : wmd) {
			if (s.matches("(\\-)?[0-9]+((\\.){1}[0-9]+)?")) {
				num.add(Double.parseDouble(s));
			}
		}

		List<String> ops = new ArrayList<>();
		for (String s : wmd) {
			if (s.matches("[\\+\\-\\*\\/]")) {
				ops.add(s);
			}
		}

		double result = num.get(0);
		for (int i = 0; i < ops.size(); i++) {
			result = calculate(ops.get(i), result, num.get(i + 1));
		}
		return Arrays.asList(result + "");
	}

	public static double calculate(String input, double d1, double d2) {
		if (input.contains("+")) {
			return d1 + d2;
		}
		if (input.contains("-")) {
			return d1 - d2;
		}
		if (input.contains("*")) {
			return d1 * d2;
		}
		if (input.contains("/")) {
			return d2 != 0
					? d1 / d2
					: Double.NaN;
		}
		throw new IllegalArgumentException();
	}

	public static boolean validateSyntax(String input) {
		return input.matches("(\\-)?[0-9]+((\\.){1}[0-9]+)?([\\+\\-\\*\\/]{1}(\\-)?[0-9]+((\\.){1}[0-9]+)?)*");
	}

	public static String getText(List<String> inputs) {
		StringBuilder sb = new StringBuilder();
		for (String s : inputs) {
			sb.append(s);
		}
		return sb.toString();
	}
}
