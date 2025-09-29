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

package org.mvel2.templates.util;

import org.jspecify.annotations.Nullable;
import org.mvel2.templates.TemplateError;
import org.mvel2.templates.res.Node;
import org.mvel2.templates.res.TerminalNode;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.Arrays;
import java.util.HexFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.nio.charset.StandardCharsets.*;
import static org.mvel2.util.ParseTools.balancedCapture;

public final class TemplateTools {

  public static Node getLastNode(Node node) {
    Node n = node;
    while (true){
      if (n.getNext() instanceof TerminalNode)
					return n;
      n = n.getNext();
    }
  }

  public static int captureToEOS (char[] expression, int cursor) {
    int length = expression.length;
    while (cursor != length) {
      switch (expression[cursor]) {
        case '(':
        case '[':
        case '{':
          cursor = balancedCapture(expression, cursor, expression[cursor]);
          break;

        case ';':
        case '}':
          return cursor;

      }
      cursor++;
    }

    return cursor;
  }

  public static String readInFile (String file) throws TemplateError {
    return readInFile(new File(file));
  }

  public static String readInFile (File file) throws TemplateError {
    try {
      try (var is = new FileInputStream(file)){
				return readStream(is);
			}
    } catch (FileNotFoundException e){
      throw new TemplateError("cannot include template '" + file.getName() + "': file not found.", e);
    } catch (IOException e){
      throw new TemplateError("unknown I/O exception while including '" + file.getName() + "' (stacktrace nested)", e);
    }
  }

  public static String readStream (InputStream is) throws TemplateError {
    try {
			byte[] buf = is.readAllBytes();
      return asStr(buf);
    } catch (NullPointerException e) {
      if (is == null)
        	throw new TemplateError("null input stream", e);
      else
        	throw e;// inside InputStream 🤷‍♀️
    } catch (IOException e){
      throw new TemplateError("unknown I/O exception while including (stacktrace nested)", e);
    }
  }

	@SuppressWarnings({"deprecation", "ImplicitDefaultCharsetUsage"})
	public static String asStr (byte[] b) {
		if (b == null || b.length <= 0)
				return "";
		try {
			return new String(b, UTF_8);
		} catch (Throwable e){
			var log = Logger.getLogger(TemplateTools.class.getName());
			log.log(Level.WARNING, "asStr: failed to convert byte[] to UTF-8 str: (%s) %s".formatted(b.length, HexFormat.of().formatHex(b)), e);
			return new String(b, 0/*hiByte*/);// fallback to Latin1
		}
	}

	public static Appendable append (Appendable to, @Nullable Object data) throws UncheckedIOException {
		try {
			return to.append(String.valueOf(data));
		} catch (NullPointerException ignore){
			return null;
		} catch (IOException e){
			throw new UncheckedIOException("failed to write to Appendable: "+ to +", data: "+ data, e);
		}
	}

	/// @see java.util.Objects#toString(Object, String)
	public static String str (@Nullable Object data) {
		return data != null ? data.toString() : "";
	}
	public static String str (@Nullable Object @Nullable [] data) {
		return data != null ? Arrays.toString(data) : "";
	}
	public static String str (char @Nullable [] data) {
		return data != null ? Arrays.toString(data) : "";
	}


	public static void close (@Nullable AutoCloseable closeable) {
		if (closeable == null)
				return;
		try {
			closeable.close();
		} catch (Throwable e){
			var log = Logger.getLogger(TemplateTools.class.getName());
			log.log(Level.WARNING, "close: failed to close AutoCloseable (%s) %s".formatted(closeable.getClass().getName(), closeable), e);
		}
	}
}