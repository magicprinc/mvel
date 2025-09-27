package org.mvel2.util;

import org.mvel2.integration.VariableResolverFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * @author Mike Brock
 */
public final class StaticFieldStub implements StaticStub {
	private final Object cachedValue;

  public StaticFieldStub (Field field) {
		field.trySetAccessible();
		if (!field.isAccessible() || (field.getModifiers() & Modifier.STATIC) == 0){
      throw new RuntimeException("not an accessible static field: " + field.getDeclaringClass().getName() +'.'+ field.getName());
    }

    try {
      cachedValue = field.get(null);
    } catch (Exception e){// IllegalAccessException
      throw new RuntimeException("error accessing static field: "+ field, e);
    }
  }

  @Override
	public Object call (Object ctx, Object thisCtx, VariableResolverFactory factory, Object[] parameters) {
    return cachedValue;
  }
}