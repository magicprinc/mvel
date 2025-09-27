package org.mvel2.integration;

@FunctionalInterface
public interface Listener {
  void onEvent (Object context, String contextName, VariableResolverFactory variableFactory, Object value);
}