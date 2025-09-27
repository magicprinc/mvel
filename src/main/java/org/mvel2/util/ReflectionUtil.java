/**
 * MVEL 2.0
 * Copyright (C) 2007 The Codehaus
 * Mike Brock, Dhanji Prasanna, John Graham, Mark Proctor
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.mvel2.util;

import java.util.Map;

import static java.lang.Character.toLowerCase;
import static java.lang.Character.toUpperCase;

/**
 * Utilities for working with reflection.
 */
public class ReflectionUtil {

  /**
   * This new method 'slightly' outperforms the old method, it was
   * essentially a perfect example of me wasting my time and a
   * premature optimization.  But what the hell...
   *
   * @param s -
   * @return String
   */
  public static String getSetter (String s) {
    return "set" + toUpperCase(s.charAt(0)) + s.substring(1);
  }

  public static String getGetter (String s) {
    return "get" + toUpperCase(s.charAt(0)) + s.substring(1);
  }

  public static String getIsGetter (String s) {
    return "is" + toUpperCase(s.charAt(0)) + s.substring(1);
  }

  public static String getPropertyFromAccessor (String s) {
    if (s.length() > 3 && s.charAt(1) == 'e' && s.charAt(2) == 't'){// get or set
			char c = s.charAt(0);
			if (c == 'g' || c == 's')
	        return toLowerCase(s.charAt(3)) + s.substring(4);// getF→oo

		} else if (s.length() > 2 && s.startsWith("is")){
      return toLowerCase(s.charAt(2)) + s.substring(3);
    }
    return s;
  }

	static final Map<Class<?>,Class<?>> toNonPrimitiveType = Map.of(
		int.class,   Integer.class,
		long.class,  Long.class,
		double.class, Double.class,
		float.class, Float.class,
		short.class, Short.class,
		byte.class, Byte.class,
		char.class, Character.class,
		boolean.class, Boolean.class
	);

  public static Class<?> toNonPrimitiveType (Class<?> c) {
		Class<?> n = toNonPrimitiveType.get(c);
		return n != null ? n
				: c;//if (!c.isPrimitive()) return c;
	}

  public static Class<?> toNonPrimitiveArray(Class<?> c) {
    if (!c.isArray() || !c.getComponentType().isPrimitive()) return c;
    if (c == int[].class) return Integer[].class;
    if (c == long[].class) return Long[].class;
    if (c == double[].class) return Double[].class;
    if (c == float[].class) return Float[].class;
    if (c == short[].class) return Short[].class;
    if (c == byte[].class) return Byte[].class;
    if (c == char[].class) return Character[].class;
    return Boolean[].class;
  }

  public static Class<?> toPrimitiveArrayType(Class<?> c) {
    if (!c.isPrimitive()) throw new RuntimeException(c + " is not a primitive type");
    if (c == int.class) return int[].class;
    if (c == long.class) return long[].class;
    if (c == double.class) return double[].class;
    if (c == float.class) return float[].class;
    if (c == short.class) return short[].class;
    if (c == byte.class) return byte[].class;
    if (c == char.class) return char[].class;
    return boolean[].class;
  }

  public static boolean isAssignableFrom(Class<?> from, Class<?> to) {
    return from.isAssignableFrom(to) || areBoxingCompatible(from, to);
  }

  private static boolean areBoxingCompatible (Class<?> c1, Class<?> c2) {
    return c1.isPrimitive() ? isPrimitiveOf(c2, c1)
				: (c2.isPrimitive() && isPrimitiveOf(c1, c2));
  }

  private static boolean isPrimitiveOf(Class<?> boxed, Class<?> primitive) {
		Class<?> n = toNonPrimitiveType(primitive);
		return n == boxed;
  }
}