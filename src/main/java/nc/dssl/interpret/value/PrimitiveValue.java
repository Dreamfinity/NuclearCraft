package nc.dssl.interpret.value;

import javax.annotation.*;
import java.math.BigInteger;

public abstract class PrimitiveValue<T> {
	
	public final @Nonnull T raw;
	
	protected PrimitiveValue(@Nonnull T raw) {
		this.raw = raw;
	}
	
	public abstract @Nullable BigInteger intValue(boolean explicit);
	
	public abstract @Nullable Boolean boolValue(boolean explicit);
	
	public abstract @Nullable Double floatValue(boolean explicit);
	
	public abstract @Nullable Character charValue(boolean explicit);
	
	public abstract @Nullable String stringValue(boolean explicit);
	
	@Override
	public abstract PrimitiveValue<T> clone();
	
	@Override
	public abstract int hashCode();
	
	@Override
	public abstract boolean equals(Object obj);
	
	@Override
	public abstract @Nonnull String toString();
}
