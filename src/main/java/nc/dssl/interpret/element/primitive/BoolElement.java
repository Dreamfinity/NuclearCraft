package nc.dssl.interpret.element.primitive;

import nc.dssl.interpret.*;
import nc.dssl.interpret.element.Element;
import nc.dssl.interpret.value.BoolValue;

import javax.annotation.Nonnull;
import java.util.Objects;

public class BoolElement extends PrimitiveElement<Boolean, BoolValue> {
	
	public BoolElement(Interpreter interpreter, @Nonnull Boolean rawValue) {
		super(interpreter, interpreter.builtIn.boolClazz, new BoolValue(rawValue));
	}
	
	@Override
	public @Nonnull BoolElement boolCast(TokenExecutor exec) {
		return this;
	}
	
	@Override
	public @Nonnull TokenResult onNot(TokenExecutor exec) {
		exec.push(new BoolElement(interpreter, !primitiveBool()));
		return TokenResult.PASS;
	}
	
	public boolean primitiveBool() {
		return value.raw;
	}
	
	@Override
	public @Nonnull Element clone() {
		return new BoolElement(interpreter, value.raw);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(BuiltIn.BOOL, value);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof BoolElement other) {
			return value.equals(other.value);
		}
		return false;
	}
}
