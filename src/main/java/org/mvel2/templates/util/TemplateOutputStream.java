package org.mvel2.templates.util;

///  todo {@link Appendable} + Unicode tests + Qute examples +? Vert.x MVEL Template examples? + Click access examples, or accessor
public interface TemplateOutputStream extends Appendable {

	@Override
	TemplateOutputStream append (CharSequence c);

  TemplateOutputStream append (char[] c);

	@Override
	Appendable append (CharSequence csq, int start, int end);

	@Deprecated  @Override
	default TemplateOutputStream append (char c) {
		return append(new char[]{c});
	}
}