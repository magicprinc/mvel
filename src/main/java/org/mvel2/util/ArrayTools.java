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

public final class ArrayTools {
	public static final char[] EMPTY_CHAR = new char[0];
	public static final String[] EMPTY_STR = new String[0];
	/// EMPTYARG
	public static final Object[] EMPTY_OBJ = new Object[0];
	public static final Class<?>[] EMPTY_CLASS = new Class[0];


	/// @see ParseTools#find(char[], int, int, char)
  public static int findFirst (char c, int start, int offset, char[] array) {
		return ParseTools.find(array, start, offset, c);
  }

	/// @see ParseTools#findLast
  public static int findLast (char c, int start, int offset, char[] array) {
		return ParseTools.findLast(array, start, offset, c);
  }

	public static char[] toCharArray (StringBuilder sb) {
		int len = sb.length();
		var chars = new char[len];
		sb.getChars(0, len, chars, 0);
		return chars;
	}
}