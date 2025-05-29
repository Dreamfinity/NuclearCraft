package nc.dssl.interpret.element;

import nc.dssl.interpret.*;

import javax.annotation.Nonnull;
import java.util.Objects;

public class ModuleElement extends Element {
	
	public final @Nonnull Clazz internal;
	
	public ModuleElement(Interpreter interpreter, @Nonnull String identifier) {
		super(interpreter, interpreter.builtIn.moduleClazz);
		Clazz internal = interpreter.builtIn.moduleMap.get(identifier);
		if (internal == null) {
			throw new IllegalArgumentException(String.format("Core module \"%s\" not found!", identifier));
		}
		this.internal = internal;
	}
	
	protected ModuleElement(Interpreter interpreter, @Nonnull Clazz internal) {
		super(interpreter, interpreter.builtIn.moduleClazz);
		this.internal = internal;
	}
	
	@Override
	public @Nonnull Element clone() {
		return new ModuleElement(interpreter, internal);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(BuiltIn.MODULE, internal);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ModuleElement other) {
			return internal.equals(other.internal);
		}
		return false;
	}
	
	@Override
	public @Nonnull String toString(TokenExecutor exec) {
		return "$" + internal.fullIdentifier;
	}
}
