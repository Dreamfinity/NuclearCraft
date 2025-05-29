package nc.dssl.interpret.value;

import nc.dssl.interpret.BuiltIn;

import javax.annotation.*;
import java.math.BigInteger;
import java.util.Objects;

public class BoolValue extends PrimitiveValue<Boolean> {
	
	public BoolValue(@Nonnull Boolean value) {
		super(value);
	}
	
	@Override
	public @Nullable BigInteger intValue(boolean explicit) {
		return explicit ? (raw ? BigInteger.ONE : BigInteger.ZERO) : null;
	}
	
	@Override
	public @Nullable Boolean boolValue(boolean explicit) {
		return raw;
	}
	
	@Override
	public @Nullable Double floatValue(boolean explicit) {
		return null;
	}
	
	@Override
	public @Nullable Character charValue(boolean explicit) {
		return null;
	}
	
	@Override
	public @Nullable String stringValue(boolean explicit) {
		return explicit ? raw.toString() : null;
	}
	
	@Override
	public BoolValue clone() {
		return new BoolValue(raw);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(BuiltIn.BOOL, raw);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof BoolValue other) {
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
