package nc.dssl.interpret.element.primitive;

import nc.dssl.interpret.*;
import nc.dssl.interpret.element.Element;
import nc.dssl.interpret.value.*;

import javax.annotation.*;
import java.math.BigInteger;

public abstract class PrimitiveElement<RAW, VALUE extends PrimitiveValue<RAW>> extends Element {
	
	public final @Nonnull VALUE value;
	
	protected PrimitiveElement(Interpreter interpreter, @Nonnull Clazz clazz, @Nonnull VALUE value) {
		super(interpreter, clazz);
		this.value = value;
	}
	
	@Override
	public @Nullable IntElement asInt(TokenExecutor exec) {
		BigInteger intValue = value.intValue(false);
		return intValue == null ? null : new IntElement(interpreter, intValue);
	}
	
	@Override
	public @Nullable BoolElement asBool(TokenExecutor exec) {
		Boolean boolValue = value.boolValue(false);
		return boolValue == null ? null : new BoolElement(interpreter, boolValue);
	}
	
	@Override
	public @Nullable FloatElement asFloat(TokenExecutor exec) {
		Double floatValue = value.floatValue(false);
		return floatValue == null ? null : new FloatElement(interpreter, floatValue);
	}
	
	@Override
	public @Nullable CharElement asChar(TokenExecutor exec) {
		Character charValue = value.charValue(false);
		return charValue == null ? null : new CharElement(interpreter, charValue);
	}
	
	@Override
	public @Nullable StringElement asString(TokenExecutor exec) {
		String stringValue = value.stringValue(false);
		return stringValue == null ? null : new StringElement(interpreter, stringValue);
	}
	
	@Override
	public @Nonnull IntElement intCast(TokenExecutor exec) {
		BigInteger intValue = value.intValue(true);
		return intValue == null ? super.intCast(exec) : new IntElement(interpreter, intValue);
	}
	
	@Override
	public @Nonnull BoolElement boolCast(TokenExecutor exec) {
		Boolean boolValue = value.boolValue(true);
		return boolValue == null ? super.boolCast(exec) : new BoolElement(interpreter, boolValue);
	}
	
	@Override
	public @Nonnull FloatElement floatCast(TokenExecutor exec) {
		Double floatValue = value.floatValue(true);
		return floatValue == null ? super.floatCast(exec) : new FloatElement(interpreter, floatValue);
	}
	
	@Override
	public @Nonnull CharElement charCast(TokenExecutor exec) {
		Character charValue = value.charValue(true);
		return charValue == null ? super.charCast(exec) : new CharElement(interpreter, charValue);
	}
	
	@Override
	public @Nonnull StringElement stringCast(TokenExecutor exec) {
		String stringValue = value.stringValue(true);
		return stringValue == null ? super.stringCastInternal(exec) : new StringElement(interpreter, stringValue);
	}
	
	@Override
	public @Nonnull TokenResult onEqualTo(TokenExecutor exec, @Nonnull Element other) {
		if (other instanceof PrimitiveElement<?, ?> primitive) {
			Element elem = PrimitiveBinaryOpLogic.onEqualTo(interpreter, value, primitive.value);
			if (elem != null) {
				exec.push(elem);
				return TokenResult.PASS;
			}
		}
		return super.onEqualToInternal(exec, other);
	}
	
	@Override
	public @Nonnull TokenResult onNotEqualTo(TokenExecutor exec, @Nonnull Element other) {
		if (other instanceof PrimitiveElement<?, ?> primitive) {
			Element elem = PrimitiveBinaryOpLogic.onNotEqualTo(interpreter, value, primitive.value);
			if (elem != null) {
				exec.push(elem);
				return TokenResult.PASS;
			}
		}
		return super.onNotEqualToInternal(exec, other);
	}
	
	@Override
	public @Nonnull TokenResult onLessThan(TokenExecutor exec, @Nonnull Element other) {
		if (other instanceof PrimitiveElement<?, ?> primitive) {
			Element elem = PrimitiveBinaryOpLogic.onLessThan(interpreter, value, primitive.value);
			if (elem != null) {
				exec.push(elem);
				return TokenResult.PASS;
			}
		}
		return super.onLessThanInternal(exec, other);
	}
	
	@Override
	public @Nonnull TokenResult onLessOrEqual(TokenExecutor exec, @Nonnull Element other) {
		if (other instanceof PrimitiveElement<?, ?> primitive) {
			Element elem = PrimitiveBinaryOpLogic.onLessOrEqual(interpreter, value, primitive.value);
			if (elem != null) {
				exec.push(elem);
				return TokenResult.PASS;
			}
		}
		return super.onLessOrEqualInternal(exec, other);
	}
	
	@Override
	public @Nonnull TokenResult onMoreThan(TokenExecutor exec, @Nonnull Element other) {
		if (other instanceof PrimitiveElement<?, ?> primitive) {
			Element elem = PrimitiveBinaryOpLogic.onMoreThan(interpreter, value, primitive.value);
			if (elem != null) {
				exec.push(elem);
				return TokenResult.PASS;
			}
		}
		return super.onMoreThanInternal(exec, other);
	}
	
	@Override
	public @Nonnull TokenResult onMoreOrEqual(TokenExecutor exec, @Nonnull Element other) {
		if (other instanceof PrimitiveElement<?, ?> primitive) {
			Element elem = PrimitiveBinaryOpLogic.onMoreOrEqual(interpreter, value, primitive.value);
			if (elem != null) {
				exec.push(elem);
				return TokenResult.PASS;
			}
		}
		return super.onMoreOrEqualInternal(exec, other);
	}
	
	@Override
	public @Nonnull TokenResult onPlus(TokenExecutor exec, @Nonnull Element other) {
		if (other instanceof PrimitiveElement<?, ?> primitive) {
			Element elem = PrimitiveBinaryOpLogic.onPlus(interpreter, value, primitive.value);
			if (elem != null) {
				exec.push(elem);
				return TokenResult.PASS;
			}
		}
		return super.onPlusInternal(exec, other);
	}
	
	@Override
	public @Nonnull TokenResult onAnd(TokenExecutor exec, @Nonnull Element other) {
		if (other instanceof PrimitiveElement<?, ?> primitive) {
			Element elem = PrimitiveBinaryOpLogic.onAnd(interpreter, value, primitive.value);
			if (elem != null) {
				exec.push(elem);
				return TokenResult.PASS;
			}
		}
		return super.onAndInternal(exec, other);
	}
	
	@Override
	public @Nonnull TokenResult onOr(TokenExecutor exec, @Nonnull Element other) {
		if (other instanceof PrimitiveElement<?, ?> primitive) {
			Element elem = PrimitiveBinaryOpLogic.onOr(interpreter, value, primitive.value);
			if (elem != null) {
				exec.push(elem);
				return TokenResult.PASS;
			}
		}
		return super.onOrInternal(exec, other);
	}
	
	@Override
	public @Nonnull TokenResult onXor(TokenExecutor exec, @Nonnull Element other) {
		if (other instanceof PrimitiveElement<?, ?> primitive) {
			Element elem = PrimitiveBinaryOpLogic.onXor(interpreter, value, primitive.value);
			if (elem != null) {
				exec.push(elem);
				return TokenResult.PASS;
			}
		}
		return super.onXorInternal(exec, other);
	}
	
	@Override
	public @Nonnull TokenResult onMinus(TokenExecutor exec, @Nonnull Element other) {
		if (other instanceof PrimitiveElement<?, ?> primitive) {
			Element elem = PrimitiveBinaryOpLogic.onMinus(interpreter, value, primitive.value);
			if (elem != null) {
				exec.push(elem);
				return TokenResult.PASS;
			}
		}
		return super.onMinusInternal(exec, other);
	}
	
	@Override
	public @Nonnull TokenResult onConcat(TokenExecutor exec, @Nonnull Element other) {
		if (other instanceof PrimitiveElement<?, ?> primitive) {
			Element elem = PrimitiveBinaryOpLogic.onConcat(interpreter, value, primitive.value);
			if (elem != null) {
				exec.push(elem);
				return TokenResult.PASS;
			}
		}
		return super.onConcatInternal(exec, other);
	}
	
	@Override
	public @Nonnull TokenResult onLeftShift(TokenExecutor exec, @Nonnull Element other) {
		if (other instanceof PrimitiveElement<?, ?> primitive) {
			Element elem = PrimitiveBinaryOpLogic.onLeftShift(interpreter, value, primitive.value);
			if (elem != null) {
				exec.push(elem);
				return TokenResult.PASS;
			}
		}
		return super.onLeftShiftInternal(exec, other);
	}
	
	@Override
	public @Nonnull TokenResult onRightShift(TokenExecutor exec, @Nonnull Element other) {
		if (other instanceof PrimitiveElement<?, ?> primitive) {
			Element elem = PrimitiveBinaryOpLogic.onRightShift(interpreter, value, primitive.value);
			if (elem != null) {
				exec.push(elem);
				return TokenResult.PASS;
			}
		}
		return super.onRightShiftInternal(exec, other);
	}
	
	@Override
	public @Nonnull TokenResult onMultiply(TokenExecutor exec, @Nonnull Element other) {
		if (other instanceof PrimitiveElement<?, ?> primitive) {
			Element elem = PrimitiveBinaryOpLogic.onMultiply(interpreter, value, primitive.value);
			if (elem != null) {
				exec.push(elem);
				return TokenResult.PASS;
			}
		}
		return super.onMultiplyInternal(exec, other);
	}
	
	@Override
	public @Nonnull TokenResult onDivide(TokenExecutor exec, @Nonnull Element other) {
		if (other instanceof PrimitiveElement<?, ?> primitive) {
			Element elem = PrimitiveBinaryOpLogic.onDivide(interpreter, value, primitive.value);
			if (elem != null) {
				exec.push(elem);
				return TokenResult.PASS;
			}
		}
		return super.onDivideInternal(exec, other);
	}
	
	@Override
	public @Nonnull TokenResult onRemainder(TokenExecutor exec, @Nonnull Element other) {
		if (other instanceof PrimitiveElement<?, ?> primitive) {
			Element elem = PrimitiveBinaryOpLogic.onRemainder(interpreter, value, primitive.value);
			if (elem != null) {
				exec.push(elem);
				return TokenResult.PASS;
			}
		}
		return super.onRemainderInternal(exec, other);
	}
	
	@Override
	public @Nonnull TokenResult onPower(TokenExecutor exec, @Nonnull Element other) {
		if (other instanceof PrimitiveElement<?, ?> primitive) {
			Element elem = PrimitiveBinaryOpLogic.onPower(interpreter, value, primitive.value);
			if (elem != null) {
				exec.push(elem);
				return TokenResult.PASS;
			}
		}
		return super.onPowerInternal(exec, other);
	}
	
	@Override
	public @Nonnull TokenResult onIdivide(TokenExecutor exec, @Nonnull Element other) {
		if (other instanceof PrimitiveElement<?, ?> primitive) {
			Element elem = PrimitiveBinaryOpLogic.onIdivide(interpreter, value, primitive.value);
			if (elem != null) {
				exec.push(elem);
				return TokenResult.PASS;
			}
		}
		return super.onIdivideInternal(exec, other);
	}
	
	@Override
	public @Nonnull TokenResult onModulo(TokenExecutor exec, @Nonnull Element other) {
		if (other instanceof PrimitiveElement<?, ?> primitive) {
			Element elem = PrimitiveBinaryOpLogic.onModulo(interpreter, value, primitive.value);
			if (elem != null) {
				exec.push(elem);
				return TokenResult.PASS;
			}
		}
		return super.onModuloInternal(exec, other);
	}
	
	@Override
	public abstract @Nonnull TokenResult onNot(TokenExecutor exec);
	
	@Override
	protected Object formattedInternal(TokenExecutor exec) {
		return value.raw;
	}
	
	@Override
	public Object formatted(TokenExecutor exec) {
		return formattedInternal(exec);
	}
	
	@Override
	public @Nonnull StringElement __str__(TokenExecutor exec) {
		String stringValue = value.stringValue(true);
		return stringValue == null ? super.__str__(exec) : new StringElement(interpreter, stringValue);
	}
	
	@Override
	public @Nonnull TokenResult __eq__(TokenExecutor exec, @Nonnull Element other) {
		return onEqualTo(exec, other);
	}
	
	@Override
	public @Nonnull TokenResult __ne__(TokenExecutor exec, @Nonnull Element other) {
		return onNotEqualTo(exec, other);
	}
	
	@Override
	public @Nonnull TokenResult __lt__(TokenExecutor exec, @Nonnull Element other) {
		return onLessThan(exec, other);
	}
	
	@Override
	public @Nonnull TokenResult __le__(TokenExecutor exec, @Nonnull Element other) {
		return onLessOrEqual(exec, other);
	}
	
	@Override
	public @Nonnull TokenResult __gt__(TokenExecutor exec, @Nonnull Element other) {
		return onMoreThan(exec, other);
	}
	
	@Override
	public @Nonnull TokenResult __ge__(TokenExecutor exec, @Nonnull Element other) {
		return onMoreOrEqual(exec, other);
	}
	
	@Override
	public @Nonnull TokenResult __add__(TokenExecutor exec, @Nonnull Element other) {
		return onPlus(exec, other);
	}
	
	@Override
	public @Nonnull TokenResult __and__(TokenExecutor exec, @Nonnull Element other) {
		return onAnd(exec, other);
	}
	
	@Override
	public @Nonnull TokenResult __or__(TokenExecutor exec, @Nonnull Element other) {
		return onOr(exec, other);
	}
	
	@Override
	public @Nonnull TokenResult __xor__(TokenExecutor exec, @Nonnull Element other) {
		return onXor(exec, other);
	}
	
	@Override
	public @Nonnull TokenResult __sub__(TokenExecutor exec, @Nonnull Element other) {
		return onMinus(exec, other);
	}
	
	@Override
	public @Nonnull TokenResult __concat__(TokenExecutor exec, @Nonnull Element other) {
		return onConcat(exec, other);
	}
	
	@Override
	public @Nonnull TokenResult __lshift__(TokenExecutor exec, @Nonnull Element other) {
		return onLeftShift(exec, other);
	}
	
	@Override
	public @Nonnull TokenResult __rshift__(TokenExecutor exec, @Nonnull Element other) {
		return onRightShift(exec, other);
	}
	
	@Override
	public @Nonnull TokenResult __mul__(TokenExecutor exec, @Nonnull Element other) {
		return onMultiply(exec, other);
	}
	
	@Override
	public @Nonnull TokenResult __div__(TokenExecutor exec, @Nonnull Element other) {
		return onDivide(exec, other);
	}
	
	@Override
	public @Nonnull TokenResult __rem__(TokenExecutor exec, @Nonnull Element other) {
		return onRemainder(exec, other);
	}
	
	@Override
	public @Nonnull TokenResult __pow__(TokenExecutor exec, @Nonnull Element other) {
		return onPower(exec, other);
	}
	
	@Override
	public @Nonnull TokenResult __floordiv__(TokenExecutor exec, @Nonnull Element other) {
		return onIdivide(exec, other);
	}
	
	@Override
	public @Nonnull TokenResult __mod__(TokenExecutor exec, @Nonnull Element other) {
		return onModulo(exec, other);
	}
	
	@Override
	public @Nonnull TokenResult __not__(TokenExecutor exec) {
		return onNot(exec);
	}
	
	@Override
	public @Nonnull String toString(TokenExecutor exec) {
		return value.toString();
	}
}
