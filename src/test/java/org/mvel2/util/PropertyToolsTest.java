package org.mvel2.util;

import junit.framework.TestCase;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.Collections;

/// @see PropertyTools
public class PropertyToolsTest extends TestCase {
	@Test
	public void testGetGetter() {
		Method getter = PropertyTools.getGetter(ClassA.class, "resource");
		assertEquals("isResource", getter.getName());
	}

	public static class ClassA {

		private boolean resource;

		public boolean isResource() {
			return resource;
		}

		public boolean isresource() {
			return resource;
		}

		public boolean getResource() {
			return resource;
		}

		public boolean getresource() {
			return resource;
		}

		public boolean resource() {
			return resource;
		}

		public void setResource(boolean resource) {
			this.resource = resource;
		}
	}

	@Test
	public void testIsEmpty () {
		assertTrue(PropertyTools.isEmpty(null));
		assertTrue(PropertyTools.isEmpty(""));
		assertTrue(PropertyTools.isEmpty("null"));
		assertTrue(PropertyTools.isEmpty(Collections.emptyList()));
		assertTrue(PropertyTools.isEmpty(Collections.emptyMap()));

		assertFalse(PropertyTools.isEmpty(42));
		assertFalse(PropertyTools.isEmpty(Collections.singleton(42)));
		assertFalse(PropertyTools.isEmpty(Collections.singletonMap(1,2)));
	}
}