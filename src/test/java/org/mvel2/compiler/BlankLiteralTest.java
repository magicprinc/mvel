package org.mvel2.compiler;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.OptionalLong;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mvel2.compiler.BlankLiteral.INSTANCE;

/// @see BlankLiteral
public class BlankLiteralTest {

	@Test
	@DisplayName("hashCode should always return 0")
	void hashCodeShouldReturnZero() {
		assertEquals(0, INSTANCE.hashCode());
	}

	@Test
	@DisplayName("toString should return empty string")
	void toStringShouldReturnEmptyString() {
		assertEquals("", INSTANCE.toString());
	}

	// Null and empty values
	@Test
	@DisplayName("Should equal null")
	void shouldEqualNull() {
		assertTrue(INSTANCE.equals(null));
	}

	@Test
	@DisplayName("Should equal empty string")
	void shouldEqualEmptyString() {
		assertTrue(INSTANCE.equals(""));
	}

	@Test
	@DisplayName("Should equal whitespace-only strings")
	void shouldEqualWhitespaceOnlyStrings() {
		assertTrue(INSTANCE.equals("   "));
		assertTrue(INSTANCE.equals("\t"));
		assertTrue(INSTANCE.equals("\n"));
		assertTrue(INSTANCE.equals(" \t\n "));
	}

	@Test
	@DisplayName("Should equal Integer 0")
	void shouldEqualIntegerZero() {
		assertTrue(INSTANCE.equals(Integer.valueOf(0)));
	}

	@Test
	@DisplayName("Should equal Long 0")
	void shouldEqualLongZero() {
		assertTrue(INSTANCE.equals(Long.valueOf(0L)));
	}

	@Test
	@DisplayName("Should equal Short 0")
	void shouldEqualShortZero() {
		assertTrue(INSTANCE.equals(Short.valueOf((short) 0)));
	}

	@Test
	@DisplayName("Should equal Byte 0")
	void shouldEqualByteZero() {
		assertTrue(INSTANCE.equals(Byte.valueOf((byte) 0)));
	}

	@Test
	@DisplayName("Should equal BigInteger 0")
	void shouldEqualBigIntegerZero() {
		assertTrue(INSTANCE.equals(BigInteger.ZERO));
	}

	@Test
	@DisplayName("Should equal BigDecimal 0")
	void shouldEqualBigDecimalZero() {
		assertTrue(INSTANCE.equals(BigDecimal.ZERO));
	}

	@Test
	@DisplayName("Should NOT equal string '0.0'")
	void shouldNotEqualStringZeroPointZero() {
		assertFalse(INSTANCE.equals("0.0"));
	}

	@Test
	void shouldFloats() {
		assertTrue(INSTANCE.equals(0.0));
		assertTrue(INSTANCE.equals(-0));
		assertTrue(INSTANCE.equals(0.0f));
		assertTrue(INSTANCE.equals(-0f));
	}

	@Test
	@DisplayName("Should NOT equal Float 0.0")
	void shouldNotEqualFloatZero() {
	}

	@Test
	@DisplayName("Should NOT equal non-zero numbers")
	void shouldNotEqualNonZeroNumbers() {
		assertFalse(INSTANCE.equals(Integer.valueOf(1)));
		assertFalse(INSTANCE.equals(Long.valueOf(-1)));
		assertFalse(INSTANCE.equals("1"));
		assertFalse(INSTANCE.equals("-0"));
	}

	// Collections
	@Test
	@DisplayName("Should equal empty Collection")
	void shouldEqualEmptyCollection() {
		assertTrue(INSTANCE.equals(new ArrayList<>()));
		assertTrue(INSTANCE.equals(new HashSet<>()));
		assertTrue(INSTANCE.equals(Collections.emptyList()));
		assertTrue(INSTANCE.equals(Collections.emptySet()));
	}

	@Test
	@DisplayName("Should NOT equal non-empty Collection")
	void shouldNotEqualNonEmptyCollection() {
		assertFalse(INSTANCE.equals(List.of("item")));
		assertFalse(INSTANCE.equals(Set.of("item")));
		assertFalse(INSTANCE.equals(Arrays.asList("a", "b")));
	}

	// Arrays
	@Test
	@DisplayName("Should equal empty array")
	void shouldEqualEmptyArray() {
		assertTrue(INSTANCE.equals(new Object[0]));
		assertTrue(INSTANCE.equals(new String[0]));
		assertTrue(INSTANCE.equals(new int[0]));
	}

	@Test
	@DisplayName("Should NOT equal non-empty array")
	void shouldNotEqualNonEmptyArray() {
		assertFalse(INSTANCE.equals(new Object[]{"item"}));
		assertFalse(INSTANCE.equals(new String[]{"item"}));
		assertFalse(INSTANCE.equals(new int[]{1, 2, 3}));
	}

	// Booleans
	@Test
	@DisplayName("Should equal Boolean FALSE")
	void shouldEqualBooleanFalse() {
		assertTrue(INSTANCE.equals(Boolean.FALSE));
	}

	@Test
	@DisplayName("Should NOT equal Boolean TRUE")
	void shouldNotEqualBooleanTrue() {
		assertFalse(INSTANCE.equals(Boolean.TRUE));
	}

	// Maps
	@Test
	@DisplayName("Should equal empty Map")
	void shouldEqualEmptyMap() {
		assertTrue(INSTANCE.equals(new HashMap<>()));
		assertTrue(INSTANCE.equals(Collections.emptyMap()));
	}

	@Test
	@DisplayName("Should NOT equal non-empty Map")
	void shouldNotEqualNonEmptyMap() {
		assertFalse(INSTANCE.equals(Map.of("key", "value")));
	}

	// Optionals
	@Test
	@DisplayName("Should equal empty Optional")
	void shouldEqualEmptyOptional() {
		assertTrue(INSTANCE.equals(Optional.empty()));
	}

	@Test
	@DisplayName("Should NOT equal non-empty Optional")
	void shouldNotEqualNonEmptyOptional() {
		assertFalse(INSTANCE.equals(Optional.of("value")));
		assertFalse(INSTANCE.equals(Optional.of(0)));
	}

	@Test
	@DisplayName("Should equal empty OptionalInt")
	void shouldEqualEmptyOptionalInt() {
		assertTrue(INSTANCE.equals(OptionalInt.empty()));
	}

	@Test
	@DisplayName("Should NOT equal non-empty OptionalInt")
	void shouldNotEqualNonEmptyOptionalInt() {
		assertFalse(INSTANCE.equals(OptionalInt.of(0)));
	}

	@Test
	@DisplayName("Should equal empty OptionalLong")
	void shouldEqualEmptyOptionalLong() {
		assertTrue(INSTANCE.equals(OptionalLong.empty()));
	}

	@Test
	@DisplayName("Should NOT equal non-empty OptionalLong")
	void shouldNotEqualNonEmptyOptionalLong() {
		assertFalse(INSTANCE.equals(OptionalLong.of(0L)));
	}

	@Test
	@DisplayName("Should equal empty OptionalDouble")
	void shouldEqualEmptyOptionalDouble() {
		assertTrue(INSTANCE.equals(OptionalDouble.empty()));
	}

	@Test
	@DisplayName("Should NOT equal non-empty OptionalDouble")
	void shouldNotEqualNonEmptyOptionalDouble() {
		assertFalse(INSTANCE.equals(OptionalDouble.of(0.0)));
	}

	// Other objects
	@Test
	@DisplayName("Should NOT equal random objects")
	void shouldNotEqualRandomObjects() {
		assertFalse(INSTANCE.equals(new Object()));
		assertFalse(INSTANCE.equals("hello"));
		assertFalse(INSTANCE.equals("   hello   "));
	}

	@Test
	@DisplayName("Should equal custom object with empty toString")
	void shouldEqualCustomObjectWithEmptyToString() {
		Object custom = new Object() {
			@Override
			public String toString() {
				return "";
			}
		};
		assertTrue(INSTANCE.equals(custom));
	}
}