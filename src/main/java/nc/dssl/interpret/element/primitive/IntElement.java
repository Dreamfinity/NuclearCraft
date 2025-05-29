package nc.dssl.interpret.element.primitive;

import nc.dssl.DSSLHelpers;
import nc.dssl.interpret.*;
import nc.dssl.interpret.element.Element;
import nc.dssl.interpret.value.IntValue;

import javax.annotation.Nonnull;
import java.math.BigInteger;
import java.util.Objects;

public class IntElement extends PrimitiveElement<BigInteger, IntValue> {
	
	public IntElement(Interpreter interpreter, BigInteger rawValue) {
		super(interpreter, interpreter.builtIn.intClazz, new IntValue(DSSLHelpers.checkNonNull(rawValue)));
	}
	
	public IntElement(Interpreter interpreter, long rawValue) {
		this(interpreter, BigInteger.valueOf(rawValue));
	}
	
	@Override
	public @Nonnull IntElement intCast(TokenExecutor exec) {
		return this;
	}
	
	@Override
	public @Nonnull TokenResult onNot(TokenExecutor exec) {
		exec.push(new IntElement(interpreter, value.raw.not()));
		return TokenResult.PASS;
	}
	
	public int primitiveInt() {
		return value.raw.intValueExact();
	}
	
	public long primitiveLong() {
		return value.raw.longValueExact();
	}
	
	@Override
	public @Nonnull Element clone() {
		return new IntElement(interpreter, value.raw);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(BuiltIn.INT, value);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof IntElement other) {
			return value.equals(other.value);
		}
		return false;
	}
}
