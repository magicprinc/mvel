package org.mvel2.util;

import org.mvel2.integration.VariableResolverFactory;

@FunctionalInterface
public interface CallableProxy {
  Object call (Object ctx, Object thisCtx, VariableResolverFactory factory, Object[] parameters);
}