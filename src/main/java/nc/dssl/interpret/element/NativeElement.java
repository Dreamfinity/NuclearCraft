package nc.dssl.interpret.element;

import nc.dssl.interpret.*;
import org.apache.commons.lang3.SerializationUtils;

import javax.annotation.Nonnull;
import java.io.Serializable;
import java.util.Objects;

public class NativeElement extends Element {
	
	public final @Nonnull Object value;
	
	public NativeElement(Interpreter interpreter, @Nonnull Object value) {
		super(interpreter, interpreter.builtIn.nativeClazz);
		this.value = value;
	}
	
	@Override
	public @Nonnull Element clone() {
		if (value instanceof Serializable serializable) {
			return new NativeElement(interpreter, SerializationUtils.clone(serializable));
		}
		else {
			throw new IllegalArgumentException(String.format("Non-serializable %s element can not be cloned!", BuiltIn.NATIVE));
		}
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(BuiltIn.NATIVE, value);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof NativeElement other) {
			return value.equals(other.value);
		}
		return false;
	}
	
	@SuppressWarnings("null")
	@Override
	public @Nonnull String toString(TokenExecutor exec) {
		return String.valueOf(value);
	}
}
