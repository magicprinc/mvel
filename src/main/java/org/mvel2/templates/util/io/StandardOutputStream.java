package org.mvel2.templates.util.io;

import org.jspecify.annotations.Nullable;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UncheckedIOException;
import java.nio.charset.Charset;

import static java.nio.charset.StandardCharsets.*;

/// @see Appendable
/// @see StringBuilder
/// @see java.io.OutputStreamWriter
public class StandardOutputStream implements Appendable {
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
	public StandardOutputStream append (CharSequence c) {
    try {
			outputStream.write(c.toString().getBytes(charset));
      return this;
    } catch (IOException e){
      throw new UncheckedIOException("failed to write to stream: "+c, e);
    }
  }

	@Override
	public StandardOutputStream append (CharSequence csq, int start, int end) throws IOException {
		try {
			outputStream.write(csq.subSequence(start,end).toString().getBytes(charset));
			return this;
		} catch (IOException e){
			throw new UncheckedIOException("failed to write to stream", e);
		}
	}

	@Deprecated
	@Override
	public StandardOutputStream append (char c) throws IOException {
		try {
			outputStream.write(String.valueOf(c).getBytes(charset));
			return this;
		} catch (IOException e){
			throw new UncheckedIOException("failed to write to stream", e);
		}
	}

	public void close () throws IOException {
		outputStream.close();
	}

	/// special meaning: no result of accumulating
	@Override public @Nullable String toString (){ return null; }
}