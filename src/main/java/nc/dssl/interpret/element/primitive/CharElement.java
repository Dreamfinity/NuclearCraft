package nc.dssl.interpret.element.primitive;

import nc.dssl.interpret.*;
import nc.dssl.interpret.element.Element;
import nc.dssl.interpret.value.CharValue;
import org.apache.commons.lang3.StringEscapeUtils;

import javax.annotation.Nonnull;
import java.util.Objects;

public class CharElement extends PrimitiveElement<Character, CharValue> {
	
	public CharElement(Interpreter interpreter, @Nonnull Character rawValue) {
		super(interpreter, interpreter.builtIn.charClazz, new CharValue(rawValue));
	}
	
	@Override
	public @Nonnull CharElement charCast(TokenExecutor exec) {
		return this;
	}
	
	@Override
	public @Nonnull TokenResult onNot(TokenExecutor exec) {
		throw unaryOpError("!");
	}
	
	public char primitiveChar() {
		return value.raw;
	}
	
	@Override
	public @Nonnull String debug(TokenExecutor exec) {
		return "'" + StringEscapeUtils.escapeJava(value.raw.toString()) + "'";
	}
	
	@Override
	public @Nonnull Element clone() {
		return new CharElement(interpreter, value.raw);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(BuiltIn.CHAR, value);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof CharElement other) {
			return value.equals(other.value);
		}
		return false;
	}
}
