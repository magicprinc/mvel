package org.mvel2.tests;

import org.junit.Test;
import org.mvel2.MVEL;
import org.mvel2.ParserContext;
import org.mvel2.integration.impl.CachedMapVariableResolverFactory;
import org.mvel2.integration.impl.CachingMapVariableResolverFactory;
import org.mvel2.util.ParseTools;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/// https://gist.github.com/gowrishankarin/390e53cc64d934330bb4
public class MVELExpressionSampleTest {
	@Test
	public void example () {
		var vars = new HashMap<String,Object>();
		vars.put("foobar", 120);
		Boolean result = (Boolean) MVEL.eval("foobar > 99", vars);
		assertTrue(result);

		result = (Boolean) MVEL.eval("foobar > 99 && foobar < 150", vars);
		assertTrue(result);

		vars.put("foobar", 70);
		result = (Boolean) MVEL.eval("foobar < 0.8 * 100 ", vars);
		assertTrue(result);// Hurrah less than 80% threshold

		vars.put("foobar", 70);
		result = (Boolean) MVEL.eval("foobar == 100 ", vars);

		assertFalse(result);

		// Multi Variable

		vars.put("foobar", 120);
		vars.put("rhs1", 99);
		result = (Boolean) MVEL.eval("foobar > rhs1", vars);

		assertTrue(result);

		vars.put("rhs2", 150);
		result = (Boolean) MVEL.eval("foobar > rhs1 && foobar < rhs2", vars);
		assertTrue(result);// Hurrah AND Operator

		vars.put("foobar", 70);
		vars.put("rhs1", 0.8f);
		vars.put("rhs2", 100);
		result = (Boolean) MVEL.eval("foobar < rhs1 * rhs2 ", vars);
		assertTrue(result);

		vars.put("foobar", 70);
		vars.put("rhs1", 100);

		result = (Boolean) MVEL.eval("foobar == rhs1 ", vars);
		assertFalse(result);

		vars.put("rhs1", 70);
		result = (Boolean) MVEL.eval("foobar == rhs1 ", vars);
		assertTrue(result);

		var pctx = new ParserContext();
		pctx.stronglyTyped();
		pctx.withInput("contentinstance", ContentInstance.class);

		MVEL.compileExpression("content = contentinstance.content", pctx);
		MVEL.compileExpression("contentInstanceId = contentinstance.contentInstanceId", pctx);
		MVEL.compileExpression("creationTime = contentinstance.creationTime", pctx);

		Map<String, Class> inputs = pctx.getVariables();
		assertEquals("{content=class java.lang.String, contentInstanceId=int, creationTime=long}", inputs.toString());
	}
	public static final class ContentInstance {
		public String content = "foo";
		public int contentInstanceId = 42;
		public long creationTime;
	}

	/// @see org.mvel2.tests.core.ASMConsistencyTest#testListGet
	@Test
	public void testBug355 () throws IOException {
		Object expr = MVEL.compileExpression(ParseTools.loadFromFile(new File("samples/scripts/issue355.mvel")));
		MVEL.executeExpression(expr, new CachingMapVariableResolverFactory(new HashMap()));

		System.out.println("---eval---");

		char[] script = ParseTools.loadFromFile(new File("samples/scripts/issue355.mvel"));
		MVEL.eval(script, this, new CachedMapVariableResolverFactory(new HashMap()));

		MVEL.evalFile(new File("samples/scripts/issue355.mvel"), new CachedMapVariableResolverFactory(new HashMap()));
	}

	/// https://github.com/mvel/mvel/issues/380
	@Test
	public void testBug380 () {
		var context = new ParserContext();
		context.setStrictTypeEnforcement(true);
		context.setStrongTyping(true);

		Serializable compiled = MVEL.compileExpression("String a = 'b' return a.length();");
		Object result = MVEL.executeExpression(compiled, context, new CachedMapVariableResolverFactory(new HashMap<>()));
		assertEquals("b", result);

		compiled = MVEL.compileExpression("String a = 'b'; return a.length();");
		result = MVEL.executeExpression(compiled, context, new CachedMapVariableResolverFactory(new HashMap<>()));
		assertEquals(1, result);

		result = MVEL.eval("String a = 'b' return a.length();", new HashMap<>());
		assertEquals("b", result);

		result = MVEL.eval("String a = 'b'; return a.length();", new HashMap<>());
		assertEquals(1, result);
	}
}