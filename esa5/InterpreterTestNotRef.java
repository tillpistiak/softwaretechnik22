package main;

import static main.InterpreterNotRefactored.calculate;
import static main.InterpreterNotRefactored.interpret;
import static main.InterpreterNotRefactored.validateSyntax;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Arrays;

import org.junit.Test;

public class InterpreterTestNotRef {

	@Test
	public void testCalculate() {
		double result = calculate("1+1", 1.0, 1.0);
		assertThat(result, is(2.0));

		result = calculate("1-1", 1.0, 1.0);
		assertThat(result, is(0.0));

		result = calculate("2*2", 2.0, 2.0);
		assertThat(result, is(4.0));

		result = calculate("4/2", 4.0, 2.0);
		assertThat(result, is(2.0));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCalculateException() {
		calculate("1ü1", 1.0, 1.0);
	}

	@Test
	public void testEinfacheOperationen() {
		String result = interpret(Arrays.asList("1", "+", "1")).get(0);
		assertThat(result, is("2.0"));

		result = interpret(Arrays.asList("1", "-", "1")).get(0);
		assertThat(result, is("0.0"));

		result = interpret(Arrays.asList("2", "*", "4")).get(0);
		assertThat(result, is("8.0"));

		result = interpret(Arrays.asList("4", "/", "2")).get(0);
		assertThat(result, is("2.0"));
	}

	@Test
	public void testAddition() {
		String result = interpret(Arrays.asList("1", "+", "1", "+", "1")).get(0);
		assertThat(result, is("3.0"));

		result = interpret(Arrays.asList("100", "+", "1234", "+", "10")).get(0);
		assertThat(result, is("1344.0"));

	}

	@Test
	public void testSubstraction() {
		String result = interpret(Arrays.asList("2", "-", "1")).get(0);
		assertThat(result, is("1.0"));

		result = interpret(Arrays.asList("10", "-", "1234", "-", "2")).get(0);
		assertThat(result, is("-1226.0"));

	}

	@Test
	public void testMultiplication() {
		String result = interpret(Arrays.asList("1", "*", "1", "*", "1")).get(0);
		assertThat(result, is("1.0"));

		result = interpret(Arrays.asList("10", "*", "1234", "*", "2")).get(0);
		assertThat(result, is("24680.0"));
	}

	@Test
	public void testDivision() {
		String result = interpret(Arrays.asList("1", "/", "0")).get(0);
		assertThat(result, is(Double.NaN + ""));

		result = interpret(Arrays.asList("8", "/", "2", "/", "2")).get(0);
		assertThat(result, is("2.0"));

	}

	@Test
	public void testComplexCalculations() {
		String result = interpret(Arrays.asList("2", "+", "3", "*", "4", "/", "8")).get(0);
		assertThat(result, is("3.5"));

		result = interpret(Arrays.asList("2", "*", "3", "+", "16", "/", "8", "-", "-4", "/", "2")).get(0);
		assertThat(result, is("10.0"));
	}

	@Test
	public void testSingleNumber() {
		String result = interpret(Arrays.asList("1")).get(0);
		assertThat(result, is("1.0"));
	}

	@Test
	public void testValidateSyntax() {
		boolean result = validateSyntax("1");
		assertThat(result, is(true));

		result = validateSyntax("1.0");
		assertThat(result, is(true));

		result = validateSyntax("1+1.0");
		assertThat(result, is(true));

		result = validateSyntax("-1+1");
		assertThat(result, is(true));

		result = validateSyntax("-1+-1");
		assertThat(result, is(true));

		result = validateSyntax("-1-1");
		assertThat(result, is(true));

		result = validateSyntax("1--1*2/5");
		assertThat(result, is(true));

		result = validateSyntax("");
		assertThat(result, is(false));

		result = validateSyntax("-1+1-");
		assertThat(result, is(false));

		result = validateSyntax("-1++1");
		assertThat(result, is(false));

		result = validateSyntax("1--1*2/*5");
		assertThat(result, is(false));

		result = validateSyntax("*1+1");
		assertThat(result, is(false));

		result = validateSyntax("1+1*");
		assertThat(result, is(false));

	}
}
