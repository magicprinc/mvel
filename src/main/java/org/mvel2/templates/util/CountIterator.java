package org.mvel2.templates.util;

import java.util.Iterator;

/**
 * User: christopherbrock
 * Date: 10-Aug-2010
 * Time: 6:42:20 PM
 */
public class CountIterator implements Iterator {
  int cursor;
  final int countTo;

  public CountIterator(int countTo) {
    this.countTo = countTo;
  }

  @Override
	public boolean hasNext() {
    return cursor < countTo;
  }

  @Override
	public Object next() {
    return cursor++;
  }

  @Override public void remove (){}

	@Override
	public String toString () {
		return "CountIterator: %d of %d".formatted(cursor, countTo);
	}
}