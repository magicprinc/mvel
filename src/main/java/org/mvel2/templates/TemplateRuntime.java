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

package org.mvel2.templates;

import org.jspecify.annotations.Nullable;
import org.mvel2.integration.VariableResolverFactory;
import org.mvel2.integration.impl.ImmutableDefaultFactory;
import org.mvel2.integration.impl.MapVariableResolverFactory;
import org.mvel2.templates.res.Node;
import org.mvel2.templates.util.TemplateTools;
import org.mvel2.templates.util.io.StandardOutputStream;
import org.mvel2.util.ExecutionStack;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import static org.mvel2.templates.TemplateCompiler.compileTemplate;

/**
 * This is the root of the template runtime, and contains various utility methods for executing templates.
 */
public final class TemplateRuntime {
  private final char[] template;
  private @Nullable TemplateRegistry namedTemplateRegistry;
  private final Node rootNode;
  private final String baseDir;
  private ExecutionStack relPath;


  TemplateRuntime (char[] template, @Nullable TemplateRegistry namedTemplateRegistry, Node rootNode, String baseDir) {
    this.template = template;
    this.namedTemplateRegistry = namedTemplateRegistry;
    this.rootNode = rootNode;
    this.baseDir = baseDir;
  }

  public static Object eval (File file, @Nullable Object ctx, VariableResolverFactory vars, @Nullable TemplateRegistry registry) {
    return execute(compileTemplate(TemplateTools.readInFile(file)), ctx, vars, registry);
  }

  public static Object eval (InputStream instream) {
    return eval(instream, null, new ImmutableDefaultFactory(), null);
  }

  public static Object eval (InputStream instream, @Nullable Object ctx) {
    return eval(instream, ctx, new ImmutableDefaultFactory(), null);
  }

  public static Object eval (InputStream instream, @Nullable Object ctx, VariableResolverFactory vars) {
    return eval(instream, ctx, vars, null);
  }

  public static Object eval (InputStream instream, @Nullable Object ctx, Map<String,Object> vars) {
    return eval(instream, ctx, new MapVariableResolverFactory(vars), null);
  }

  public static Object eval (InputStream instream, @Nullable Object ctx, Map<String,Object> vars, TemplateRegistry registry) {
    return execute(compileTemplate(TemplateTools.readStream(instream)), ctx, new MapVariableResolverFactory(vars), registry);
  }

  public static Object eval(InputStream instream, @Nullable Object ctx, VariableResolverFactory vars, @Nullable TemplateRegistry registry) {
    return execute(compileTemplate(TemplateTools.readStream(instream)), ctx, vars, registry);
  }

  public static void eval(InputStream instream, Object ctx, VariableResolverFactory vars, TemplateRegistry register, OutputStream stream) {
    execute(compileTemplate(TemplateTools.readStream(instream)), ctx, vars, register, stream);
  }

  public static Object eval(String template, Map<String,Object> vars) {
    return execute(compileTemplate(template), null, new MapVariableResolverFactory(vars));
  }

  public static void eval(String template, Map<String,Object> vars, OutputStream stream) {
    execute(compileTemplate(template), null, new MapVariableResolverFactory(vars), null, stream);
  }

  public static Object eval(String template, Object ctx) {
    return execute(compileTemplate(template), ctx);
  }

  public static Object eval(String template, Object ctx, Map<String,Object> vars) {
    return execute(compileTemplate(template), ctx, new MapVariableResolverFactory(vars));
  }

  public static void eval(String template, Object ctx, Map<String,Object> vars, OutputStream stream) {
    execute(compileTemplate(template), ctx, new MapVariableResolverFactory(vars), null, stream);
  }

  public static Object eval(String template, Object ctx, VariableResolverFactory vars) {
    return execute(compileTemplate(template), ctx, vars);
  }

  public static void eval(String template, Object ctx, VariableResolverFactory vars, Appendable stream) {
    execute(compileTemplate(template), ctx, vars, null, stream);
  }

  public static void eval(String template, Object ctx, VariableResolverFactory vars, OutputStream stream) {
    execute(compileTemplate(template), ctx, vars, null, stream);
  }

  public static Object eval(String template, Map<String,Object> vars, TemplateRegistry registry) {
    return execute(compileTemplate(template), null, new MapVariableResolverFactory(vars), registry);
  }

  public static void eval(String template, Map<String,Object> vars, TemplateRegistry registry, Appendable stream) {
    execute(compileTemplate(template), null, new MapVariableResolverFactory(vars), registry, stream);
  }

  public static void eval(String template, Map<String,Object> vars, TemplateRegistry registry, OutputStream stream) {
    execute(compileTemplate(template), null, new MapVariableResolverFactory(vars), registry, stream);
  }

  public static Object eval(String template, Object ctx, Map<String,Object> vars, TemplateRegistry registry) {
    return execute(compileTemplate(template), ctx, new MapVariableResolverFactory(vars), registry);
  }

  public static void eval(String template, Object ctx, Map<String,Object> vars, TemplateRegistry registry, OutputStream stream) {
    execute(compileTemplate(template), ctx, new MapVariableResolverFactory(vars), registry, stream);
  }

  public static Object eval(String template, Object ctx, VariableResolverFactory vars, TemplateRegistry registry) {
    return execute(compileTemplate(template), ctx, vars, registry);
  }

  public static void eval(String template, Object ctx, VariableResolverFactory vars, TemplateRegistry registry, Appendable to) {
    execute(compileTemplate(template), ctx, vars, registry, to);
  }

  public static void eval(String template, Object ctx, VariableResolverFactory vars, TemplateRegistry registry, OutputStream stream) {
    execute(compileTemplate(template), ctx, vars, registry, stream);
  }


  public static Object execute (CompiledTemplate compiled) {
    return execute(compiled.getRoot(), compiled.getTemplate(), new StringBuilder(), null, new ImmutableDefaultFactory(), null);
  }

  public static void execute (CompiledTemplate compiled, OutputStream stream) {
    execute(compiled.getRoot(), compiled.getTemplate(), new StandardOutputStream(stream), null, new ImmutableDefaultFactory(), null);
  }

  public static Object execute(CompiledTemplate compiled, Object context) {
    return execute(compiled.getRoot(), compiled.getTemplate(), new StringBuilder(), context, new ImmutableDefaultFactory(), null);
  }

  public static void execute(CompiledTemplate compiled, Object context, OutputStream stream) {
    execute(compiled.getRoot(), compiled.getTemplate(), new StandardOutputStream(stream), context, new ImmutableDefaultFactory(), null);
  }

  public static Object execute(CompiledTemplate compiled, Map<String,Object> vars) {
    return execute(compiled.getRoot(), compiled.getTemplate(), new StringBuilder(), null, new MapVariableResolverFactory(vars), null);
  }

  public static void execute(CompiledTemplate compiled, Map<String,Object> vars, OutputStream stream) {
    execute(compiled.getRoot(), compiled.getTemplate(), new StandardOutputStream(stream), null, new MapVariableResolverFactory(vars), null);
  }

  public static Object execute(CompiledTemplate compiled, Object context, Map<String,Object> vars) {
    return execute(compiled.getRoot(), compiled.getTemplate(), new StringBuilder(), context, new MapVariableResolverFactory(vars), null);
  }

  public static void execute(CompiledTemplate compiled, Object context, Map<String,Object> vars, OutputStream stream) {
    execute(compiled.getRoot(), compiled.getTemplate(), new StandardOutputStream(stream), context, new MapVariableResolverFactory(vars), null);
  }

  public static Object execute(CompiledTemplate compiled, Object context, TemplateRegistry registry) {
    return execute(compiled.getRoot(), compiled.getTemplate(), new StringBuilder(), context, null, registry);
  }

  public static void execute(CompiledTemplate compiled, Object context, TemplateRegistry registry, OutputStream stream) {
    execute(compiled.getRoot(), compiled.getTemplate(), new StandardOutputStream(stream), context, null, registry);
  }

  public static Object execute(CompiledTemplate compiled, Object context, Map<String,Object> vars, TemplateRegistry registry) {
    return execute(compiled.getRoot(), compiled.getTemplate(), new StringBuilder(), context, new MapVariableResolverFactory(vars), registry);
  }

  public static void execute(CompiledTemplate compiled, Object context, Map<String,Object> vars, TemplateRegistry registry, OutputStream stream) {
    execute(compiled.getRoot(), compiled.getTemplate(), new StandardOutputStream(stream), context, new MapVariableResolverFactory(vars), registry);
  }

  public static Object execute(CompiledTemplate compiled, @Nullable Object context, VariableResolverFactory factory) {
    return execute(compiled.getRoot(), compiled.getTemplate(), new StringBuilder(), context, factory, null);
  }

  public static Object execute(CompiledTemplate compiled, @Nullable Object context, VariableResolverFactory factory, @Nullable TemplateRegistry registry) {
    return execute(compiled.getRoot(), compiled.getTemplate(), new StringBuilder(), context, factory, registry);
  }

  public static Object execute(CompiledTemplate compiled, Object context, VariableResolverFactory factory, String baseDir) {
    return execute(compiled.getRoot(), compiled.getTemplate(), new StringBuilder(), context, factory, null, baseDir);
  }

  public static Object execute(CompiledTemplate compiled, Object context, VariableResolverFactory factory, TemplateRegistry registry, String baseDir) {
    return execute(compiled.getRoot(), compiled.getTemplate(), new StringBuilder(), context, factory, registry, baseDir);
  }

  public static void execute (CompiledTemplate compiled, Object context, VariableResolverFactory factory, OutputStream stream) {
    execute(compiled.getRoot(), compiled.getTemplate(), new StandardOutputStream(stream), context, factory, null);
  }

  public static void execute (CompiledTemplate compiled, Object context, VariableResolverFactory factory, OutputStream stream, String baseDir) {
    execute(compiled.getRoot(), compiled.getTemplate(), new StandardOutputStream(stream), context, factory, null, baseDir);
  }

  public static Object execute(CompiledTemplate compiled, @Nullable Object context, VariableResolverFactory factory, @Nullable TemplateRegistry registry, OutputStream stream) {
    return execute(compiled.getRoot(), compiled.getTemplate(), new StandardOutputStream(stream), context, factory, registry);
  }


  public static Object execute (CompiledTemplate compiled, @Nullable Object context, VariableResolverFactory factory, @Nullable TemplateRegistry registry, Appendable to) {
    return execute(compiled.getRoot(), compiled.getTemplate(), to, context, factory, registry);
  }

  public static Object execute (CompiledTemplate compiled, Object context, VariableResolverFactory factory, TemplateRegistry registry, Appendable to, String basedir) {
    return execute(compiled.getRoot(), compiled.getTemplate(), to, context, factory, registry, basedir);
  }


  public static Object execute (
		Node root,
		char[] template,
		Appendable appender,
		@Nullable Object context,
		@Nullable VariableResolverFactory factory,
		@Nullable TemplateRegistry registry
	){
    return execute(root, template, appender, context, factory, registry, ".");
  }

  static Object execute (
		Node root,
		char[] template,
		Appendable appender,
		@Nullable Object context,
		@Nullable VariableResolverFactory factory,
		@Nullable TemplateRegistry registry,
		String baseDir
	){
    return new TemplateRuntime(template, registry, root, baseDir)
				.execute(appender, context, factory);
  }

  public Object execute (Appendable appender, @Nullable Object context, @Nullable VariableResolverFactory factory) {
    return rootNode.eval(this, appender, context, factory);
  }

  public Node getRootNode (){ return rootNode; }

  public char[] getTemplate (){ return template; }

  public @Nullable TemplateRegistry getNamedTemplateRegistry (){ return namedTemplateRegistry; }

  public void setNamedTemplateRegistry (TemplateRegistry namedTemplateRegistry) {
    this.namedTemplateRegistry = namedTemplateRegistry;
  }

  public ExecutionStack getRelPath () {
    if (relPath == null) {
      relPath = new ExecutionStack();
      relPath.push(baseDir);
    }
    return relPath;
  }
}