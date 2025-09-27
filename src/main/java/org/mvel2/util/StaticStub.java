package org.mvel2.util;

import org.mvel2.integration.VariableResolverFactory;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;

/**
 * @author Mike Brock
 */
@FunctionalInterface
public interface StaticStub extends Serializable {
  Object call (Object ctx, Object thisCtx, VariableResolverFactory factory, Object[] parameters) throws IllegalAccessException, InvocationTargetException;
}