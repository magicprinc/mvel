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
import java.util.Collection;
import java.util.Map;

import static java.lang.String.valueOf;
import static org.mvel2.util.ParseTools.isNumeric;

public final class BlankLiteral implements Serializable {
  public static final BlankLiteral INSTANCE = new BlankLiteral();

  private BlankLiteral (){}

  @Override
	public boolean equals (Object obj) {
		final String s;
    if (obj == null || (s = valueOf(obj).trim()).length() == 0){
      return true;
    }
    if (isNumeric(obj)) {
      return "0".equals(s);
    }
    if (obj instanceof Collection<?>c){
      return c.isEmpty();
    }
    if (obj.getClass().isArray()) {
      return Array.getLength(obj) == 0;
    }
    if (obj instanceof Boolean b){
      return !b;
    }
    if (obj instanceof Map<?,?> m){
      return m.isEmpty();
    }
    return false;
  }

	@Override public int hashCode (){ return 0; }

	@Override public String toString (){ return ""; }
}