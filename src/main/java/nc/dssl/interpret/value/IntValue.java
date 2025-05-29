package nc.dssl.interpret.value;

import nc.dssl.interpret.BuiltIn;

import javax.annotation.*;
import java.math.BigInteger;
import java.util.Objects;

public class IntValue extends PrimitiveValue<BigInteger> {
	
	public IntValue(@Nonnull BigInteger value) {
		super(value);
	}
	
	@Override
	public @Nullable BigInteger intValue(boolean explicit) {
		return raw;
	}
	
	@Override
	public @Nullable Boolean boolValue(boolean explicit) {
		return explicit ? !raw.equals(BigInteger.ZERO) : null;
	}
	
	@Override
	public @Nullable Double floatValue(boolean explicit) {
		return raw.doubleValue();
	}
	
	@Override
	public @Nullable Character charValue(boolean explicit) {
		return explicit ? (char) raw.intValue() : null;
	}
	
	@Override
	public @Nullable String stringValue(boolean explicit) {
		return explicit ? raw.toString() : null;
	}
	
	@Override
	public IntValue clone() {
		return new IntValue(raw);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(BuiltIn.INT, raw);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof IntValue other) {
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
