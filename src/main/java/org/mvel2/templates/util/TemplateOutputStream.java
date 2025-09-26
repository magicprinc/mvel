package org.mvel2.templates.util;

public interface TemplateOutputStream {

	TemplateOutputStream append (CharSequence c);

  TemplateOutputStream append (char[] c);
}