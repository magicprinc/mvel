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

package org.mvel2.templates.res;

import org.mvel2.MVEL;
import org.mvel2.integration.VariableResolverFactory;
import org.mvel2.templates.TemplateError;
import org.mvel2.templates.TemplateRuntime;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import static java.nio.charset.StandardCharsets.*;
import static org.mvel2.templates.util.TemplateTools.append;
import static org.mvel2.templates.util.TemplateTools.captureToEOS;
import static org.mvel2.templates.util.TemplateTools.close;

public class IncludeNode extends Node {
//    private char[] includeExpression;
//    private char[] preExpression;
  int includeStart;
  int includeOffset;

  int preStart;
  int preOffset;

  public IncludeNode(int begin, String name, char[] template, int start, int end) {
    this.begin = begin;
    this.name = name;
    this.contents = template;
    this.cStart = start;
    this.cEnd = end - 1;
    this.end = end;
    //this.contents = subset(template, this.cStart = start, (this.end = this.cEnd = end) - start - 1);

    int mark = captureToEOS(contents, 0);
    includeStart = cStart;
    includeOffset = mark - cStart;
    preStart = ++mark;
    preOffset = cEnd - mark;

    //this.includeExpression = subset(contents, 0, mark = captureToEOS(contents, 0));
//        if (mark != contents.length) this.preExpression = subset(contents, ++mark, contents.length - mark);
  }

  @Override
	public Object eval (TemplateRuntime runtime, Appendable appender, Object ctx, VariableResolverFactory factory) {
    String file = MVEL.eval(contents, includeStart, includeOffset, ctx, factory, String.class);

    if (preOffset != 0)
      	MVEL.eval(contents, preStart, preOffset, ctx, factory);

    if (next != null)
      return next.eval(runtime, append(appender, TemplateRuntime.eval(readInFile(runtime, file), ctx, factory)), ctx, factory);
    else
      return append(appender, MVEL.eval(readInFile(runtime, file), ctx, factory));
  }

  @Override
	public boolean demarcate(Node terminatingNode, char[] template) {
    return false;
  }


	static String readInFile (TemplateRuntime runtime, String fileName) {
    File file = new File(runtime.getRelPath().peek() +"/"+ fileName);
		BufferedReader in = null;
    try {
      in = new BufferedReader(new InputStreamReader(new FileInputStream(file), UTF_8));

      runtime.getRelPath().push(file.getParent());

			var sb = new StringBuilder(99);

			String currentLine;
			boolean onFirstLine = true;
			while ((currentLine = in.readLine()) != null){
				if (onFirstLine)
						onFirstLine = false;
				else
						sb.append('\n');
				sb.append(currentLine);
			}

      runtime.getRelPath().pop();

      return sb.toString();

    } catch (FileNotFoundException e) {
      throw new TemplateError("cannot include template '" + fileName + "': file not found.", e);
    } catch (IOException e) {
      throw new TemplateError("unknown I/O exception while including '" + fileName + "' (stacktrace nested)", e);
    } finally {
			close(in);
		}
  }
}