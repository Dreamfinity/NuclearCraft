package nc.dssl.interpret.value;

import nc.dssl.DSSLHelpers;
import nc.dssl.interpret.BuiltIn;

import javax.annotation.*;
import java.math.BigInteger;
import java.util.Objects;

public class FloatValue extends PrimitiveValue<Double> {
	
	public FloatValue(@Nonnull Double value) {
		super(value);
	}
	
	@Override
	public @Nullable BigInteger intValue(boolean explicit) {
		return explicit ? DSSLHelpers.bigIntFromDouble(raw) : null;
	}
	
	@Override
	public @Nullable Boolean boolValue(boolean explicit) {
		return null;
	}
	
	@Override
	public @Nullable Double floatValue(boolean explicit) {
		return raw;
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
	public FloatValue clone() {
		return new FloatValue(raw);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(BuiltIn.FLOAT, raw);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof FloatValue other) {
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
