package nc.dssl.interpret.value;

import nc.dssl.interpret.BuiltIn;

import javax.annotation.*;
import java.math.BigInteger;
import java.util.Objects;

public class CharValue extends PrimitiveValue<Character> {
	
	public CharValue(@Nonnull Character value) {
		super(value);
	}
	
	@Override
	public @Nullable BigInteger intValue(boolean explicit) {
		return explicit ? BigInteger.valueOf(raw) : null;
	}
	
	@Override
	public @Nullable Boolean boolValue(boolean explicit) {
		return null;
	}
	
	@Override
	public @Nullable Double floatValue(boolean explicit) {
		return null;
	}
	
	@Override
	public @Nullable Character charValue(boolean explicit) {
		return raw;
	}
	
	@Override
	public @Nullable String stringValue(boolean explicit) {
		return explicit ? raw.toString() : null;
	}
	
	@Override
	public CharValue clone() {
		return new CharValue(raw);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(BuiltIn.CHAR, raw);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof CharValue other) {
			return raw.equals(other.raw);
		}
		return false;
	}
	
	@SuppressWarnings("null")
	@Override
	public @Nonnull String toString() {
		return raw.toString();
	}
}
