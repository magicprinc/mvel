package org.mvel2.tests.templates.tests.res;

import org.jspecify.annotations.Nullable;
import org.mvel2.integration.VariableResolverFactory;
import org.mvel2.templates.TemplateRuntime;
import org.mvel2.templates.res.Node;

import static org.mvel2.templates.util.TemplateTools.append;

public final class TestPluginNode extends Node {
  @Override
	public @Nullable Object eval(TemplateRuntime runtime, Appendable appender, Object ctx, VariableResolverFactory factory) {
    append(appender, "THIS_IS_A_TEST");
    return next != null ? next.eval(runtime, appender, ctx, factory) : null;
  }

  @Override
	public boolean demarcate(Node terminatingNode, char[] template) {
    return false;
  }
}