package org.mvel2.util;

import junit.framework.TestCase;

///@see ReflectionUtil
public class ReflectionUtilTest extends TestCase {

	public void testGetSetter () {
		assertEquals("getFoo", ReflectionUtil.getGetter("foo"));
		assertEquals("getX", ReflectionUtil.getGetter("x"));
	}

	public void testGetGetter () {
		assertEquals("setFoo", ReflectionUtil.getSetter("foo"));
		assertEquals("setX", ReflectionUtil.getSetter("x"));
	}

	public void testGetIsGetter () {
		assertEquals("isFoo", ReflectionUtil.getIsGetter("foo"));
		assertEquals("isX", ReflectionUtil.getIsGetter("x"));
	}

	public void testGetPropertyFromAccessor () {
		assertEquals("", ReflectionUtil.getPropertyFromAccessor(""));
		assertEquals("get", ReflectionUtil.getPropertyFromAccessor("get"));
		assertEquals("foo", ReflectionUtil.getPropertyFromAccessor("getFoo"));
		assertEquals("x", ReflectionUtil.getPropertyFromAccessor("getX"));

		assertEquals("", ReflectionUtil.getPropertyFromAccessor(""));
		assertEquals("set", ReflectionUtil.getPropertyFromAccessor("set"));
		assertEquals("foo", ReflectionUtil.getPropertyFromAccessor("setFoo"));
		assertEquals("x", ReflectionUtil.getPropertyFromAccessor("setX"));

		assertEquals("", ReflectionUtil.getPropertyFromAccessor(""));
		assertEquals("is", ReflectionUtil.getPropertyFromAccessor("is"));
		assertEquals("foo", ReflectionUtil.getPropertyFromAccessor("isFoo"));
		assertEquals("x", ReflectionUtil.getPropertyFromAccessor("isX"));
	}

	public void testToNonPrimitiveType () {
		assertSame(Integer.class, ReflectionUtil.toNonPrimitiveType(int.class));
		assertSame(Long.class, ReflectionUtil.toNonPrimitiveType(long.class));
		assertSame(Float.class, ReflectionUtil.toNonPrimitiveType(float.class));
		assertSame(Double.class, ReflectionUtil.toNonPrimitiveType(double.class));
		assertSame(Byte.class, ReflectionUtil.toNonPrimitiveType(byte.class));
		assertSame(Short.class, ReflectionUtil.toNonPrimitiveType(short.class));
		assertSame(Character.class, ReflectionUtil.toNonPrimitiveType(char.class));
		assertSame(Boolean.class, ReflectionUtil.toNonPrimitiveType(boolean.class));
		assertSame(Object.class, ReflectionUtil.toNonPrimitiveType(Object.class));
	}
}