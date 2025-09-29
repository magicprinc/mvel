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

import org.jspecify.annotations.Nullable;

/**
 *
 * @author Mike Brock
 */
public final class StringAppender implements CharSequence, Appendable {
	final StringBuilder sb;

  public StringAppender() {
    sb = new StringBuilder(99);
  }

  public StringAppender (int capacity) {
    sb = new StringBuilder(capacity);
  }

  public StringAppender (char[] s) {
    this();
		sb.append(s);
  }

  public StringAppender (CharSequence s) {
		this();
		sb.append(s);
  }


  public StringAppender append (char[] chars) {
		if (chars != null)
			sb.append(chars);
    return this;
  }

  public StringAppender append (char[] chars, int start, int length) {
    sb.append(chars, start, length);
    return this;
  }

  @Override
	public StringAppender append (@Nullable CharSequence s) {
		if (s != null)
				sb.append(s);
    return this;
  }

	@Override
	public StringAppender append (CharSequence cs, int start, int len) {
		if (cs != null && start < cs.length())
				sb.append(cs, start, start+len);// start..end
		return this;
	}

	@Override
	public StringAppender append (char c) {
		sb.append(c);
    return this;
  }

	@Deprecated(forRemoval = true)
	public StringAppender append (byte c) {
		sb.append((char)c);
		return this;
	}

  @Override public int length (){ return sb.length(); }

  public char[] toChars () {
		return ArrayTools.toCharArray(sb);
  }

  @Override public String toString (){ return sb.toString(); }

  @SuppressWarnings("Since15")
	public void getChars (int start, int end, char[] target, int offset) {
    sb.getChars(start, end, target, offset);
  }

  public void reset() {
    sb.setLength(0);
  }

  @Override public char charAt (int index){ return sb.charAt(index); }

  public CharSequence substring (int start, int end) {
    return sb.substring(start, end);
  }

  @Override
	public CharSequence subSequence(int start, int end) {
    return sb.subSequence(start, end);
  }
}