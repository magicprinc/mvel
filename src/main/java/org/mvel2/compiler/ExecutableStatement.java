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
import org.mvel2.integration.VariableResolverFactory;

import java.io.Serializable;

public interface ExecutableStatement extends Accessor, Serializable, Cloneable {
	ExecutableStatement[] EMPTY_EXECUTABLE_STATEMENT = new ExecutableStatement[0];

  Object getValue (@Nullable Object staticContext, @Nullable VariableResolverFactory factory);

  void setKnownIngressType (Class<?> type);

  void setKnownEgressType (Class<?> type);

  Class<?> getKnownIngressType ();

  @Override
	Class<?> getKnownEgressType ();

  boolean isExplicitCast ();

  @SuppressWarnings("BooleanMethodIsAlwaysInverted")
	boolean isConvertableIngressEgress ();

  void computeTypeConversionRule ();

  boolean intOptimized ();

  boolean isLiteralOnly ();

  boolean isEmptyStatement ();
}