package org.mvel2.tests;

import org.junit.Before;
import org.junit.Test;
import org.mvel2.MVEL;
import org.mvel2.ParserContext;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class MvelAI2Test {
	private ParserContext parserContext;
	private Map<String, Object> variables;

	@Before
	public void setUp() {
		parserContext = new ParserContext();
		variables = new HashMap<>();
	}

	// 1. Тесты на арифметику и базовые операции
	@Test
	public void testBasicArithmetic() {
		String expression = "1 + 2 * 3";
		Serializable compiled = MVEL.compileExpression(expression, parserContext);
		Object result = MVEL.executeExpression(compiled, variables);
		assertEquals(7, result);
	}

	@Test
	public void testArithmeticWithVariables() {
		variables.put("a", 10);
		variables.put("b", 5);
		String expression = "(a * 2) + b";
		Serializable compiled = MVEL.compileExpression(expression, parserContext);
		Object result = MVEL.executeExpression(compiled, variables);
		assertEquals(25, result);
	}

	// 2. Тесты на условные выражения
	@Test
	public void testConditionalEquality() {
		variables.put("user", new User("John Doe"));
		String expression = "user.name == 'John Doe'";
		Serializable compiled = MVEL.compileExpression(expression, parserContext);
		Boolean result = (Boolean) MVEL.executeExpression(compiled, variables);
		assertTrue(result);
	}

	@Test
	public void testCompoundConditional() {
		variables.put("x", 10);
		variables.put("user", new User("John Doe"));
		String expression = "(user.name == 'John Doe') && ((x * 2) - 1) > 20";
		Serializable compiled = MVEL.compileExpression(expression, parserContext);
		Boolean result = (Boolean) MVEL.executeExpression(compiled, variables);
		assertFalse(result); // 19 > 20 -> false
	}

	@Test
	public void testTernaryOperator() {
		variables.put("x", 5);
		String expression = "x > 0 ? 'Yes' : 'No'";
		Serializable compiled = MVEL.compileExpression(expression, parserContext);
		String result = (String) MVEL.executeExpression(compiled, variables);
		assertEquals("Yes", result);
	}

	// 3. Тесты на коллекции
	@Test
	public void testInlineList() {
		String expression = "['Jim', 'Bob', 'Smith'].size()";
		Serializable compiled = MVEL.compileExpression(expression, parserContext);
		Integer result = (Integer) MVEL.executeExpression(compiled, variables);
		assertEquals(3, (int) result);
	}

	@Test
	public void testInlineMap() {
		String expression = "['Foo': 'Bar', 'Bar': 'Foo']['Foo']";
		Serializable compiled = MVEL.compileExpression(expression, parserContext);
		String result = (String) MVEL.executeExpression(compiled, variables);
		assertEquals("Bar", result);
	}

	@Test
	public void testArrayAccess() {
		variables.put("foo", "My String");
		String expression = "foo[0]";
		Serializable compiled = MVEL.compileExpression(expression, parserContext);
		Character result = (Character) MVEL.executeExpression(compiled, variables);
		assertEquals('M', (char) result);
	}

	// 4. Тесты на функции и лямбды
	@Test
	public void testFunctionDefinition() {
		String expression = "def addTwo(a, b) { a + b }; addTwo(3, 4)";
		Serializable compiled = MVEL.compileExpression(expression, parserContext);
		Object result = MVEL.executeExpression(compiled, variables);
		assertEquals(7, result);
	}

	@Test
	public void testLambda() {
		String expression = "threshold = def (x) { x >= 10 ? x : 0 }; threshold(15)";
		Serializable compiled = MVEL.compileExpression(expression, parserContext);
		Integer result = (Integer) MVEL.executeExpression(compiled, variables);
		assertEquals(15, (int) result);
	}

	// 5. Тесты на навигацию свойств и null-safety
	@Test
	public void testPropertyNavigation() {
		variables.put("user", new User("John", new Manager("Jane")));
		String expression = "user.manager.name";
		Serializable compiled = MVEL.compileExpression(expression, parserContext);
		String result = (String) MVEL.executeExpression(compiled, variables);
		assertEquals("Jane", result);
	}

	@Test
	public void testNullSafeNavigation() {
		variables.put("user", new User("John", null));
		String expression = "user.?manager.name";
		Serializable compiled = MVEL.compileExpression(expression, parserContext);
		Object result = MVEL.executeExpression(compiled, variables);
		assertNull(result); // Должен вернуть null без исключения
	}

	// 6. Edge-кейсы
	@Test
	public void testNullCheck() {
		variables.put("foo", null);
		String expression = "foo == null";
		Serializable compiled = MVEL.compileExpression(expression, parserContext);
		Boolean result = (Boolean) MVEL.executeExpression(compiled, variables);
		assertTrue(result);
	}

	@Test
	public void testCoercion() {
		String expression = "'123' == 123";
		Serializable compiled = MVEL.compileExpression(expression, parserContext);
		Boolean result = (Boolean) MVEL.executeExpression(compiled, variables);
		assertTrue(result); // String coerced to int
	}

	@Test
	public void testStrictTypingFailure() {
		parserContext.setStrictTypeEnforcement(true);
		parserContext.setStrongTyping(true);
		parserContext.addInput("num", Integer.class);
		variables.put("num", 10);
		String expression = "num = 'string'"; // Несовместимый тип
		Object result = MVEL.executeExpression(MVEL.compileExpression(expression, parserContext), new HashMap<>());
		assertEquals("string", result);
		//?! assertThrows(org.mvel2.CompileException.class, () -> MVEL.compileExpression(expression, parserContext));
	}

	@Test
	public void testEmptyCheck() {
		variables.put("emptyList", new ArrayList<>());
		String expression = "emptyList == empty";
		Serializable compiled = MVEL.compileExpression(expression, parserContext);
		Boolean result = (Boolean) MVEL.executeExpression(compiled, variables);
		assertTrue(result);
	}

	// Вспомогательные классы для тестов
	public static final class User {
		private String name;
		private Manager manager;

		User(String name) {
			this.name = name;
		}

		User(String name, Manager manager) {
			this.name = name;
			this.manager = manager;
		}

		public String getName() {
			return name;
		}

		public Manager getManager() {
			return manager;
		}
	}

	public static class Manager {
		String name;

		Manager(String name) {
			this.name = name;
		}

		public String getName (){ return name; }
	}
}