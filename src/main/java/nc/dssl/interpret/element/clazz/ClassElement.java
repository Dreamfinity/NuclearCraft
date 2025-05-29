package nc.dssl.interpret.element.clazz;

import nc.dssl.interpret.*;
import nc.dssl.interpret.element.*;

import javax.annotation.*;
import java.util.Objects;

public class ClassElement extends Element {
	
	public final @Nonnull Clazz internal;
	
	public ClassElement(Interpreter interpreter, @Nonnull Clazz internal) {
		super(interpreter, interpreter.builtIn.classClazz);
		this.internal = internal;
	}
	
	@Override
	public @Nullable Scope getMemberScope(@Nonnull MemberAccessType access) {
		return access.equals(MemberAccessType.STATIC) ? internal : clazz;
	}
	
	@Override
	public @Nonnull Element supers(TokenExecutor exec) {
		return new ListElement(interpreter, internal.supers.stream().map(x -> x.clazzElement(interpreter)));
	}
	
	@Override
	public @Nonnull Element clone() {
		return new ClassElement(interpreter, internal);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(BuiltIn.CLASS, internal);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ClassElement other) {
			return internal.equals(other.internal);
		}
		return false;
	}
	
	@Override
	public @Nonnull String toString(TokenExecutor exec) {
		return internal.fullIdentifier;
	}
}
