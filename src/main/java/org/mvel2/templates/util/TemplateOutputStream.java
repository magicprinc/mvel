package org.mvel2.templates.util;

///  todo {@link Appendable} + Unicode tests
public interface TemplateOutputStream extends Appendable {

	@Override
	TemplateOutputStream append (CharSequence c);

  TemplateOutputStream append (char[] c);
}