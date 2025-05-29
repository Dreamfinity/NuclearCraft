package nc.dssl.interpret.value;

import nc.dssl.DSSLHelpers;
import nc.dssl.interpret.Interpreter;
import nc.dssl.interpret.element.Element;
import nc.dssl.interpret.element.primitive.*;

import javax.annotation.Nonnull;

public class PrimitiveBinaryOpLogic {
	
	public static Element onEqualTo(Interpreter interpreter, @Nonnull PrimitiveValue<?> value0, @Nonnull PrimitiveValue<?> value1) {
		return new BoolElement(interpreter, value0.equals(value1));
	}
	
	public static Element onNotEqualTo(Interpreter interpreter, @Nonnull PrimitiveValue<?> value0, @Nonnull PrimitiveValue<?> value1) {
		return new BoolElement(interpreter, !value0.equals(value1));
	}
	
	public static Element onLessThan(Interpreter interpreter, @Nonnull PrimitiveValue<?> value0, @Nonnull PrimitiveValue<?> value1) {
		if (value0 instanceof IntValue intValue0) {
			if (value1 instanceof IntValue intValue1) {
				return new BoolElement(interpreter, intValue0.raw.compareTo(intValue1.raw) < 0);
			}
			else if (value1 instanceof FloatValue floatValue1) {
				return new BoolElement(interpreter, intValue0.floatValue(true).compareTo(floatValue1.raw) < 0);
			}
		}
		else if (value0 instanceof BoolValue boolValue0 && value1 instanceof BoolValue boolValue1) {
			return new BoolElement(interpreter, boolValue0.raw.compareTo(boolValue1.raw) < 0);
		}
		else if (value0 instanceof FloatValue floatValue0) {
			if (value1 instanceof IntValue intValue1) {
				return new BoolElement(interpreter, floatValue0.raw.compareTo(intValue1.floatValue(true)) < 0);
			}
			else if (value1 instanceof FloatValue floatValue1) {
				return new BoolElement(interpreter, floatValue0.raw.compareTo(floatValue1.raw) < 0);
			}
		}
		else if (value0 instanceof CharValue charValue0 && value1 instanceof CharValue charValue1) {
			return new BoolElement(interpreter, charValue0.raw.compareTo(charValue1.raw) < 0);
		}
		else if (value0 instanceof StringValue stringValue0 && value1 instanceof StringValue stringValue1) {
			return new BoolElement(interpreter, stringValue0.raw.compareTo(stringValue1.raw) < 0);
		}
		return null;
	}
	
	public static Element onLessOrEqual(Interpreter interpreter, @Nonnull PrimitiveValue<?> value0, @Nonnull PrimitiveValue<?> value1) {
		if (value0 instanceof IntValue intValue0) {
			if (value1 instanceof IntValue intValue1) {
				return new BoolElement(interpreter, intValue0.raw.compareTo(intValue1.raw) <= 0);
			}
			else if (value1 instanceof FloatValue floatValue1) {
				return new BoolElement(interpreter, intValue0.floatValue(true).compareTo(floatValue1.raw) <= 0);
			}
		}
		else if (value0 instanceof BoolValue boolValue0 && value1 instanceof BoolValue boolValue1) {
			return new BoolElement(interpreter, boolValue0.raw.compareTo(boolValue1.raw) <= 0);
		}
		else if (value0 instanceof FloatValue floatValue0) {
			if (value1 instanceof IntValue intValue1) {
				return new BoolElement(interpreter, floatValue0.raw.compareTo(intValue1.floatValue(true)) <= 0);
			}
			else if (value1 instanceof FloatValue floatValue1) {
				return new BoolElement(interpreter, floatValue0.raw.compareTo(floatValue1.raw) <= 0);
			}
		}
		else if (value0 instanceof CharValue charValue0 && value1 instanceof CharValue charValue1) {
			return new BoolElement(interpreter, charValue0.raw.compareTo(charValue1.raw) <= 0);
		}
		else if (value0 instanceof StringValue stringValue0 && value1 instanceof StringValue stringValue1) {
			return new BoolElement(interpreter, stringValue0.raw.compareTo(stringValue1.raw) <= 0);
		}
		return null;
	}
	
	public static Element onMoreThan(Interpreter interpreter, @Nonnull PrimitiveValue<?> value0, @Nonnull PrimitiveValue<?> value1) {
		if (value0 instanceof IntValue intValue0) {
			if (value1 instanceof IntValue intValue1) {
				return new BoolElement(interpreter, intValue0.raw.compareTo(intValue1.raw) > 0);
			}
			else if (value1 instanceof FloatValue floatValue1) {
				return new BoolElement(interpreter, intValue0.floatValue(true).compareTo(floatValue1.raw) > 0);
			}
		}
		else if (value0 instanceof BoolValue boolValue0 && value1 instanceof BoolValue boolValue1) {
			return new BoolElement(interpreter, boolValue0.raw.compareTo(boolValue1.raw) > 0);
		}
		else if (value0 instanceof FloatValue floatValue0) {
			if (value1 instanceof IntValue intValue1) {
				return new BoolElement(interpreter, floatValue0.raw.compareTo(intValue1.floatValue(true)) > 0);
			}
			else if (value1 instanceof FloatValue floatValue1) {
				return new BoolElement(interpreter, floatValue0.raw.compareTo(floatValue1.raw) > 0);
			}
		}
		else if (value0 instanceof CharValue charValue0 && value1 instanceof CharValue charValue1) {
			return new BoolElement(interpreter, charValue0.raw.compareTo(charValue1.raw) > 0);
		}
		else if (value0 instanceof StringValue stringValue0 && value1 instanceof StringValue stringValue1) {
			return new BoolElement(interpreter, stringValue0.raw.compareTo(stringValue1.raw) > 0);
		}
		return null;
	}
	
	public static Element onMoreOrEqual(Interpreter interpreter, @Nonnull PrimitiveValue<?> value0, @Nonnull PrimitiveValue<?> value1) {
		if (value0 instanceof IntValue intValue0) {
			if (value1 instanceof IntValue intValue1) {
				return new BoolElement(interpreter, intValue0.raw.compareTo(intValue1.raw) >= 0);
			}
			else if (value1 instanceof FloatValue floatValue1) {
				return new BoolElement(interpreter, intValue0.floatValue(true).compareTo(floatValue1.raw) >= 0);
			}
		}
		else if (value0 instanceof BoolValue boolValue0 && value1 instanceof BoolValue boolValue1) {
			return new BoolElement(interpreter, boolValue0.raw.compareTo(boolValue1.raw) >= 0);
		}
		else if (value0 instanceof FloatValue floatValue0) {
			if (value1 instanceof IntValue intValue1) {
				return new BoolElement(interpreter, floatValue0.raw.compareTo(intValue1.floatValue(true)) >= 0);
			}
			else if (value1 instanceof FloatValue floatValue1) {
				return new BoolElement(interpreter, floatValue0.raw.compareTo(floatValue1.raw) >= 0);
			}
		}
		else if (value0 instanceof CharValue charValue0 && value1 instanceof CharValue charValue1) {
			return new BoolElement(interpreter, charValue0.raw.compareTo(charValue1.raw) >= 0);
		}
		else if (value0 instanceof StringValue stringValue0 && value1 instanceof StringValue stringValue1) {
			return new BoolElement(interpreter, stringValue0.raw.compareTo(stringValue1.raw) >= 0);
		}
		return null;
	}
	
	public static Element onPlus(Interpreter interpreter, @Nonnull PrimitiveValue<?> value0, @Nonnull PrimitiveValue<?> value1) {
		if (value0 instanceof IntValue intValue0) {
			if (value1 instanceof IntValue intValue1) {
				return new IntElement(interpreter, intValue0.raw.add(intValue1.raw));
			}
			else if (value1 instanceof FloatValue floatValue1) {
				return new FloatElement(interpreter, intValue0.floatValue(true) + floatValue1.raw);
			}
		}
		else if (value0 instanceof FloatValue floatValue0) {
			if (value1 instanceof IntValue intValue1) {
				return new FloatElement(interpreter, floatValue0.raw + intValue1.floatValue(true));
			}
			else if (value1 instanceof FloatValue floatValue1) {
				return new FloatElement(interpreter, floatValue0.raw + floatValue1.raw);
			}
		}
		else if (value0 instanceof StringValue stringValue0 && value1 instanceof StringValue stringValue1) {
			return new StringElement(interpreter, stringValue0.raw + stringValue1.raw);
		}
		return null;
	}
	
	public static Element onAnd(Interpreter interpreter, @Nonnull PrimitiveValue<?> value0, @Nonnull PrimitiveValue<?> value1) {
		if (value0 instanceof IntValue intValue0 && value1 instanceof IntValue intValue1) {
			return new IntElement(interpreter, intValue0.raw.and(intValue1.raw));
		}
		else if (value0 instanceof BoolValue boolValue0 && value1 instanceof BoolValue boolValue1) {
			return new BoolElement(interpreter, boolValue0.raw && boolValue1.raw);
		}
		return null;
	}
	
	public static Element onOr(Interpreter interpreter, @Nonnull PrimitiveValue<?> value0, @Nonnull PrimitiveValue<?> value1) {
		if (value0 instanceof IntValue intValue0 && value1 instanceof IntValue intValue1) {
			return new IntElement(interpreter, intValue0.raw.or(intValue1.raw));
		}
		else if (value0 instanceof BoolValue boolValue0 && value1 instanceof BoolValue boolValue1) {
			return new BoolElement(interpreter, boolValue0.raw || boolValue1.raw);
		}
		return null;
	}
	
	public static Element onXor(Interpreter interpreter, @Nonnull PrimitiveValue<?> value0, @Nonnull PrimitiveValue<?> value1) {
		if (value0 instanceof IntValue intValue0 && value1 instanceof IntValue intValue1) {
			return new IntElement(interpreter, intValue0.raw.xor(intValue1.raw));
		}
		else if (value0 instanceof BoolValue boolValue0 && value1 instanceof BoolValue boolValue1) {
			return new BoolElement(interpreter, boolValue0.raw ^ boolValue1.raw);
		}
		return null;
	}
	
	public static Element onMinus(Interpreter interpreter, @Nonnull PrimitiveValue<?> value0, @Nonnull PrimitiveValue<?> value1) {
		if (value0 instanceof IntValue intValue0) {
			if (value1 instanceof IntValue intValue1) {
				return new IntElement(interpreter, intValue0.raw.subtract(intValue1.raw));
			}
			else if (value1 instanceof FloatValue floatValue1) {
				return new FloatElement(interpreter, intValue0.floatValue(true) - floatValue1.raw);
			}
		}
		else if (value0 instanceof FloatValue floatValue0) {
			if (value1 instanceof IntValue intValue1) {
				return new FloatElement(interpreter, floatValue0.raw - intValue1.floatValue(true));
			}
			else if (value1 instanceof FloatValue floatValue1) {
				return new FloatElement(interpreter, floatValue0.raw - floatValue1.raw);
			}
		}
		return null;
	}
	
	public static Element onConcat(Interpreter interpreter, @Nonnull PrimitiveValue<?> value0, @Nonnull PrimitiveValue<?> value1) {
		return new StringElement(interpreter, value0.stringValue(true) + value1.stringValue(true));
	}
	
	public static Element onLeftShift(Interpreter interpreter, @Nonnull PrimitiveValue<?> value0, @Nonnull PrimitiveValue<?> value1) {
		if (value0 instanceof IntValue intValue0 && value1 instanceof IntValue intValue1) {
			return new IntElement(interpreter, intValue0.raw.shiftLeft(intValue1.raw.intValue()));
		}
		return null;
	}
	
	public static Element onRightShift(Interpreter interpreter, @Nonnull PrimitiveValue<?> value0, @Nonnull PrimitiveValue<?> value1) {
		if (value0 instanceof IntValue intValue0 && value1 instanceof IntValue intValue1) {
			return new IntElement(interpreter, intValue0.raw.shiftRight(intValue1.raw.intValue()));
		}
		return null;
	}
	
	public static Element onMultiply(Interpreter interpreter, @Nonnull PrimitiveValue<?> value0, @Nonnull PrimitiveValue<?> value1) {
		if (value0 instanceof IntValue intValue0) {
			if (value1 instanceof IntValue intValue1) {
				return new IntElement(interpreter, intValue0.raw.multiply(intValue1.raw));
			}
			else if (value1 instanceof FloatValue floatValue1) {
				return new FloatElement(interpreter, intValue0.floatValue(true) * floatValue1.raw);
			}
		}
		else if (value0 instanceof FloatValue floatValue0) {
			if (value1 instanceof IntValue intValue1) {
				return new FloatElement(interpreter, floatValue0.raw * intValue1.floatValue(true));
			}
			else if (value1 instanceof FloatValue floatValue1) {
				return new FloatElement(interpreter, floatValue0.raw * floatValue1.raw);
			}
		}
		return null;
	}
	
	public static Element onDivide(Interpreter interpreter, @Nonnull PrimitiveValue<?> value0, @Nonnull PrimitiveValue<?> value1) {
		if (value0 instanceof IntValue intValue0) {
			if (value1 instanceof IntValue intValue1) {
				return new IntElement(interpreter, intValue0.raw.divide(intValue1.raw));
			}
			else if (value1 instanceof FloatValue floatValue1) {
				return new FloatElement(interpreter, intValue0.floatValue(true) / floatValue1.raw);
			}
		}
		else if (value0 instanceof FloatValue floatValue0) {
			if (value1 instanceof IntValue intValue1) {
				return new FloatElement(interpreter, floatValue0.raw / intValue1.floatValue(true));
			}
			else if (value1 instanceof FloatValue floatValue1) {
				return new FloatElement(interpreter, floatValue0.raw / floatValue1.raw);
			}
		}
		return null;
	}
	
	public static Element onRemainder(Interpreter interpreter, @Nonnull PrimitiveValue<?> value0, @Nonnull PrimitiveValue<?> value1) {
		if (value0 instanceof IntValue intValue0) {
			if (value1 instanceof IntValue intValue1) {
				return new IntElement(interpreter, intValue0.raw.remainder(intValue1.raw));
			}
			else if (value1 instanceof FloatValue floatValue1) {
				return new FloatElement(interpreter, intValue0.floatValue(true) % floatValue1.raw);
			}
		}
		else if (value0 instanceof FloatValue floatValue0) {
			if (value1 instanceof IntValue intValue1) {
				return new FloatElement(interpreter, floatValue0.raw % intValue1.floatValue(true));
			}
			else if (value1 instanceof FloatValue floatValue1) {
				return new FloatElement(interpreter, floatValue0.raw % floatValue1.raw);
			}
		}
		return null;
	}
	
	public static Element onPower(Interpreter interpreter, @Nonnull PrimitiveValue<?> value0, @Nonnull PrimitiveValue<?> value1) {
		if (value0 instanceof IntValue intValue0) {
			if (value1 instanceof IntValue intValue1) {
				return new IntElement(interpreter, intValue0.raw.pow(intValue1.raw.intValueExact()));
			}
			else if (value1 instanceof FloatValue floatValue1) {
				return new FloatElement(interpreter, Math.pow(intValue0.floatValue(true), floatValue1.raw));
			}
		}
		else if (value0 instanceof FloatValue floatValue0) {
			if (value1 instanceof IntValue intValue1) {
				return new FloatElement(interpreter, Math.pow(floatValue0.raw, intValue1.floatValue(true)));
			}
			else if (value1 instanceof FloatValue floatValue1) {
				return new FloatElement(interpreter, Math.pow(floatValue0.raw, floatValue1.raw));
			}
		}
		return null;
	}
	
	public static Element onIdivide(Interpreter interpreter, @Nonnull PrimitiveValue<?> value0, @Nonnull PrimitiveValue<?> value1) {
		if (value0 instanceof IntValue intValue0) {
			if (value1 instanceof IntValue intValue1) {
				return new IntElement(interpreter, intValue0.raw.divide(intValue1.raw));
			}
			else if (value1 instanceof FloatValue floatValue1) {
				return new IntElement(interpreter, DSSLHelpers.bigIntFromDouble(intValue0.floatValue(true) / floatValue1.raw));
			}
		}
		else if (value0 instanceof FloatValue floatValue0) {
			if (value1 instanceof IntValue intValue1) {
				return new IntElement(interpreter, DSSLHelpers.bigIntFromDouble(floatValue0.raw / intValue1.floatValue(true)));
			}
			else if (value1 instanceof FloatValue floatValue1) {
				return new IntElement(interpreter, DSSLHelpers.bigIntFromDouble(floatValue0.raw / floatValue1.raw));
			}
		}
		return null;
	}
	
	public static Element onModulo(Interpreter interpreter, @Nonnull PrimitiveValue<?> value0, @Nonnull PrimitiveValue<?> value1) {
		if (value0 instanceof IntValue intValue0) {
			if (value1 instanceof IntValue intValue1) {
				return new IntElement(interpreter, DSSLHelpers.mod(intValue0.raw, intValue1.raw));
			}
			else if (value1 instanceof FloatValue floatValue1) {
				return new FloatElement(interpreter, DSSLHelpers.mod(intValue0.floatValue(true), floatValue1.raw));
			}
		}
		else if (value0 instanceof FloatValue floatValue0) {
			if (value1 instanceof IntValue intValue1) {
				return new FloatElement(interpreter, DSSLHelpers.mod(floatValue0.raw, intValue1.floatValue(true)));
			}
			else if (value1 instanceof FloatValue floatValue1) {
				return new FloatElement(interpreter, DSSLHelpers.mod(floatValue0.raw, floatValue1.raw));
			}
		}
		return null;
	}
}
