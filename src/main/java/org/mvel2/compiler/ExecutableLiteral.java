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

import org.jspecify.annotations.Nullable;
import org.mvel2.ast.Safe;
import org.mvel2.integration.VariableResolverFactory;

/**
 * @author Christopher Brock
 */
public class ExecutableLiteral implements ExecutableStatement, Safe {
  private final Object literal;
  private int integer32;
  private boolean intOptimized;

  public ExecutableLiteral(Object literal) {
    if ((this.literal = literal) instanceof Integer) this.integer32 = (Integer) literal;
  }

  public ExecutableLiteral(int literal) {
    this.literal = this.integer32 = literal;
    this.intOptimized = true;
  }

  public int getInteger32() {
    return integer32;
  }

  public void setInteger32(int integer32) {
    this.integer32 = integer32;
  }

  @Override
	public Object getValue(Object staticContext, VariableResolverFactory factory) {
    return literal;
  }

  @Override
	public void setKnownIngressType (Class<?> type) {

  }

  @Override
	public void setKnownEgressType (Class<?> type) {

  }

  @Override
	public @Nullable Class<?> getKnownIngressType() {
    return null;
  }

  @Override
	public Class<?> getKnownEgressType() {
    return this.literal == null ? Object.class : this.literal.getClass();
  }

  @Override
	public boolean isConvertableIngressEgress() {
    return false;
  }

  @Override
	public void computeTypeConversionRule() {

  }

  @Override
	public Object getValue(Object ctx, Object elCtx, VariableResolverFactory variableFactory) {
    return literal;
  }


  public Object getLiteral() {
    return literal;
  }

  @Override
	public boolean intOptimized() {
    return intOptimized;
  }


  @Override
	public @Nullable Object setValue(Object ctx, Object elCtx, VariableResolverFactory variableFactory, Object value) {
    // not implemented
    return null;
  }

  @Override
	public boolean isLiteralOnly() {
    return true;
  }

  @Override
	public boolean isEmptyStatement() {
    return false;
  }

  @Override
	public boolean isExplicitCast() {
    return false;
  }
}