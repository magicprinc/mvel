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

package org.mvel2.compiler;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.OptionalLong;

public final class BlankLiteral implements Serializable {
  public static final BlankLiteral INSTANCE = new BlankLiteral();

  private BlankLiteral (){}

  @Override
	public boolean equals (Object obj) {
    if (obj == null)
      return true;

		else if (obj instanceof Double n)
			return isZeroSafe(n);
		else if (obj instanceof Float n)
			return isZeroSafe(n);

		else if (obj instanceof Boolean b)
			return !b;

		else if (obj instanceof BigInteger n)
			return n.signum() == 0;
		else if (obj instanceof BigDecimal n) // bd.compareTo(BigDecimal.ZERO) == 0
			return n.signum() == 0;

		else if (obj instanceof Number n)
			return n.longValue() == 0;

    else if (obj instanceof Collection<?>c)
      return c.isEmpty();
    else if (obj.getClass().isArray())
      return Array.getLength(obj) == 0;
    else if (obj instanceof Map<?,?> m)
      return m.isEmpty();

		else if (obj instanceof Optional<?> x)
			return x.isEmpty();
		else if (obj instanceof OptionalInt x)
			return x.isEmpty();
		else if (obj instanceof OptionalLong x)
			return x.isEmpty();
		else if (obj instanceof OptionalDouble x)
			return x.isEmpty();

		var s = obj.toString().trim();
		return s.length() == 0;
  }

	static boolean isZeroSafe (double value) {
		if (Double.isNaN(value)) return false;
		if (Double.isInfinite(value)) return false;
		return Math.abs(value) < 1e-10;// EPSILON
	}

	static boolean isZeroSafe (float value) {
		if (Double.isNaN(value)) return false;
		if (Double.isInfinite(value)) return false;
		return Math.abs(value) < 1e-5f;// EPSILON
	}

	@Override public int hashCode (){ return 0; }

	@Override public String toString (){ return ""; }
}