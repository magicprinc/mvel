package org.mvel2.tests;

import org.junit.Test;
import org.mvel2.MVEL;
import org.mvel2.ParserContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;


/**
 * Unit tests for MVEL (MVFLEX Expression Language)
 * Library: https://github.com/mvel/mvel
 * Maven: org.mvel:mvel2:2.5.2.Final
 */
public class MvelAITest {

	@Test
	public void testBasicExpression() {
		String expression = "10 + 5 * 2";
		Object result = MVEL.eval(expression);
		assertEquals(20, result);
	}

	@Test
	public void testVariableContext() {
		Map<String, Object> vars = new HashMap<>();
		vars.put("x", 10);
		vars.put("y", 5);

		Object result = MVEL.eval("x + y", vars);
		assertEquals(15, result);
	}

	@Test
	public void testStringOperations() {
		Map<String, Object> vars = Map.of("name", "John");
		Object result = MVEL.eval("name + ' Doe'", vars);
		assertEquals("John Doe", result);
	}

	@Test
	public void testPropertyAccess() {
		record Person(String name, int age) {}

		Map<String, Object> vars = Map.of("person", new Person("Alice", 30));
		Object result = MVEL.eval("person.name", vars);
		assertEquals("Alice", result);
	}

	@Test
	public void testListOperations() {
		List<Integer> numbers = List.of(1, 2, 3, 4, 5);
		Map<String, Object> vars = Map.of("numbers", numbers);

		Object result = MVEL.eval("numbers[2]", vars);
		assertEquals(3, result);
	}

	@Test
	public void testMapAccess() {
		Map<String, String> data = Map.of("key1", "value1", "key2", "value2");
		Map<String, Object> vars = Map.of("data", data);

		Object result = MVEL.eval("data['key1']", vars);
		assertEquals("value1", result);
	}

	@Test
	public void testConditionalExpression() {
		Map<String, Object> vars = Map.of("age", 25);
		Object result = MVEL.eval("age >= 18 ? 'adult' : 'minor'", vars);
		assertEquals("adult", result);
	}

	@Test
	public void testBooleanLogic() {
		Map<String, Object> vars = Map.of("x", 10, "y", 20);
		Object result = MVEL.eval("x > 5 && y < 30", vars);
		assertEquals(true, result);
	}

	@Test
	public void testMethodInvocation() {
		Map<String, Object> vars = Map.of("text", "hello world");
		Object result = MVEL.eval("text.toUpperCase()", vars);
		assertEquals("HELLO WORLD", result);
	}

	@Test
	public void testCollectionProjection() {
		List<Product> products = List.of(
			new Product("Laptop", 999.99),
			new Product("Mouse", 25.50),
			new Product("Keyboard", 75.00)
		);

		Map<String, Object> vars = Map.of("products", products);
		@SuppressWarnings("unchecked")
		List<String> result = (List<String>) MVEL.eval("(name in products)", vars);

		assertEquals(List.of("Laptop", "Mouse", "Keyboard"), result);
	}
	public static class Product {
		String name;
		double price;

		public Product (String name, double price) {
			this.name = name;
			this.price = price;
		}

		public String getName (){ return name; }
		public double getPrice (){ return price; }
	}

//	@Test
//	public void testCollectionFiltering() {
//		List<Integer> numbers = List.of(1, 2, 3, 4, 5, 6);
//		Map<String, Object> vars = Map.of("numbers", numbers);
//
//		@SuppressWarnings("unchecked")
//		List<Integer> result = (List<Integer>) MVEL.eval("(numbers.?{ this > 3 })", vars);
//		assertEquals(List.of(4, 5, 6), result);
//	}

	@Test
	public void testInlineList() {
		Object result = MVEL.eval("[1, 2, 3, 4]");
		assertEquals(List.of(1, 2, 3, 4), result);
	}

	@Test
	public void testInlineMap() {
		@SuppressWarnings("unchecked")
		Map<String, Object> result = (Map<String, Object>) MVEL.eval("['name': 'John', 'age': 30]");
		assertEquals("John", result.get("name"));
		assertEquals(30, result.get("age"));
	}

	@Test
	public void testCompiledExpression() {
		// Compile once, execute multiple times for better performance
		var compiled = MVEL.compileExpression("x * 2 + y");

		Map<String, Object> vars1 = Map.of("x", 5, "y", 3);
		assertEquals(13, MVEL.executeExpression(compiled, vars1));

		Map<String, Object> vars2 = Map.of("x", 10, "y", 5);
		assertEquals(25, MVEL.executeExpression(compiled, vars2));
	}

	@Test
	public void testNullSafeNavigation() {
		User user = new User("Bob", null);
		Map<String, Object> vars = Map.of("user", user);

		Object result = MVEL.eval("user.?address.?city", vars);
		assertNull(result);
	}

	public static final class Address {
		private final String city;
		public Address (String city) { this.city = city; }
		public String getCity () { return city; }
	}

	public static final class User {
		private final String name;
		private final Address address;

		public User (String name, Address address) {
			this.name = name;
			this.address = address;
		}

		public String getName () { return name; }

		public Address getAddress () { return address; }
	}

	@Test
	public void testTypeCoercion() {
		Map<String, Object> vars = Map.of("number", "42");
		Object result = MVEL.eval("number + 8", vars);
		assertEquals("428", result); // String concatenation
	}

	@Test
	public void testImportsAndStaticMethods() {
		ParserContext context = new ParserContext();
		context.addImport(Math.class);

		var compiled = MVEL.compileExpression("Math.sqrt(16)", context);
		Object result = MVEL.executeExpression(compiled);
		assertEquals(4.0, result);
	}

	@Test
	public void testContainsOperator() {
		List<String> fruits = List.of("apple", "banana", "orange");
		Map<String, Object> vars = Map.of("fruits", fruits);

		Object result = MVEL.eval("fruits contains 'banana'", vars);
		assertEquals(true, result);
	}

//	@Test
//	public void testRegexMatching() {
//		Map<String, Object> vars = Map.of("email", "test@example.com");
//		Object result = MVEL.eval("email ~= '[a-z]+@[a-z]+\\.[a-z]+'", vars);
//		assertEquals(true, result);
//	}
}