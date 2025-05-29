package nc.dssl.interpret.element.primitive;

import nc.dssl.interpret.*;
import nc.dssl.interpret.element.Element;
import nc.dssl.interpret.value.FloatValue;

import javax.annotation.Nonnull;
import java.math.BigDecimal;
import java.util.Objects;

public class FloatElement extends PrimitiveElement<Double, FloatValue> {
	
	public FloatElement(Interpreter interpreter, @Nonnull Double rawValue) {
		super(interpreter, interpreter.builtIn.floatClazz, new FloatValue(rawValue));
	}
	
	@Override
	public @Nonnull FloatElement floatCast(TokenExecutor exec) {
		return this;
	}
	
	@Override
	public @Nonnull TokenResult onNot(TokenExecutor exec) {
		throw unaryOpError("!");
	}
	
	public double primitiveFloat() {
		return value.raw;
	}
	
	public @Nonnull BigDecimal bigFloat() {
		return BigDecimal.valueOf(primitiveFloat());
	}
	
	@Override
	public @Nonnull Element clone() {
		return new FloatElement(interpreter, value.raw);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(BuiltIn.FLOAT, value);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof FloatElement other) {
			return value.equals(other.value);
		}
		return false;
	}
}
