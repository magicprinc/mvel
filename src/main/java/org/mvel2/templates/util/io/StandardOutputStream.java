package org.mvel2.templates.util.io;

import org.mvel2.templates.util.TemplateOutputStream;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UncheckedIOException;
import java.nio.charset.Charset;

import static java.nio.charset.StandardCharsets.*;

public class StandardOutputStream implements TemplateOutputStream {
  private final OutputStream outputStream;
  private final Charset charset;

  public StandardOutputStream (OutputStream outputStream) {
    this.outputStream = outputStream;
		this.charset = UTF_8;// very popular ~ default
  }

	public StandardOutputStream (OutputStream outputStream, Charset charset) {
		this.outputStream = outputStream;
		this.charset = charset != null ? charset : UTF_8;
	}

  @Override
	public TemplateOutputStream append (CharSequence c) {
    try {
			outputStream.write(c.toString().getBytes(charset));
      return this;
    } catch (IOException e){
      throw new UncheckedIOException("failed to write to stream: "+c, e);
    }
  }

  @Override
	public TemplateOutputStream append (char[] c) {
    try {
			outputStream.write(new String(c).getBytes(charset));
      return this;
    } catch (IOException e){
      throw new UncheckedIOException("failed to write to stream", e);
    }
  }

	public void close () throws IOException {
		outputStream.close();
	}

	/// special meaning: no result of accumulating
	@Override public String toString (){ return null; }
}