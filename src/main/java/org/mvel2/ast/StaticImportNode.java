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

package org.mvel2.ast;

import org.jspecify.annotations.Nullable;
import org.mvel2.CompileException;
import org.mvel2.ParserContext;
import org.mvel2.integration.VariableResolverFactory;

import java.lang.reflect.Method;

import static java.lang.reflect.Modifier.isStatic;
import static org.mvel2.util.ArrayTools.findLast;

/**
 * @author Christopher Brock
 */
public class StaticImportNode extends ASTNode {
  private final Class<?> declaringClass;
  private final String methodName;
  private transient @Nullable Method method;

  public StaticImportNode(char[] expr, int start, int offset, ParserContext pCtx) {
    super(pCtx);
    try {
      this.expr = expr;
      this.start = start;
      this.offset = offset;
      int mark;

      declaringClass = Class.forName(
				new String(expr, start, (mark = findLast('.', start, offset, this.expr = expr)) - start).trim(),
				true,
				getClassLoader()
			);

      methodName = new String(expr, ++mark, offset - (mark - start)).trim();

      if (resolveMethod() == null)
        	throw new CompileException("Can't find method for static import: "
            + declaringClass.getName() + "." + methodName, expr, start);
    }
    catch (Exception e){
      throw new CompileException("unable to import class", expr, start, e);
    }
  }

  private @Nullable Method resolveMethod () {
    for (Method meth : declaringClass.getMethods()){
      if (isStatic(meth.getModifiers()) && methodName.equals(meth.getName()))
        	return method = meth;
    }
		for (Method meth : declaringClass.getDeclaredMethods()){
			if (isStatic(meth.getModifiers()) && methodName.equals(meth.getName()))
				return method = meth;
		}
    return null;
  }

  public @Nullable Method getMethod() {
    return method;
  }

  @Override
	public @Nullable Object getReducedValueAccelerated (Object ctx, Object thisValue, VariableResolverFactory factory) {
    factory.createVariable(methodName, method == null ? method = resolveMethod() : method);
    return null;
  }

  @Override
	public @Nullable Object getReducedValue(Object ctx, Object thisValue, VariableResolverFactory factory) {
    return getReducedValueAccelerated(ctx, thisValue, factory);
  }
}