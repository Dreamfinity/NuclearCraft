package nc.dssl.interpret.element;

import nc.dssl.DSSLHelpers;
import nc.dssl.interpret.*;

import javax.annotation.*;
import java.util.*;

public class LabelElement extends Element {
	
	protected final @Nonnull Scope scope;
	
	public final @Nonnull String fullIdentifier;
	protected final @Nonnull String shallowIdentifier;
	
	public LabelElement(Interpreter interpreter, @Nonnull Scope scope, @Nonnull String identifier) {
		this(interpreter, scope, DSSLHelpers.extendedIdentifier(scope.scopeIdentifier(), identifier), identifier);
	}
	
	protected LabelElement(Interpreter interpreter, @Nonnull Scope scope, @Nonnull String fullIdentifier, @Nonnull String shallowIdentifier) {
		super(interpreter, interpreter.builtIn.labelClazz);
		this.scope = scope;
		this.fullIdentifier = fullIdentifier;
		this.shallowIdentifier = shallowIdentifier;
	}
	
	protected LabelElement(Interpreter interpreter, @Nonnull LabelElement prev, @Nonnull String extension) {
		super(interpreter, interpreter.builtIn.labelClazz);
		Def def;
		@Nonnull Element elem;
		@Nullable Scope nextScope;
		if ((def = prev.getDef()) != null && (nextScope = (elem = def.elem).getMemberScope(MemberAccessType.STATIC)) != null) {
			scope = nextScope;
			fullIdentifier = elem.extendedIdentifier(extension, MemberAccessType.STATIC);
		}
		else if ((nextScope = prev.getClazz()) != null) {
			scope = nextScope;
			fullIdentifier = DSSLHelpers.extendedIdentifier(prev.fullIdentifier, extension);
		}
		else {
			throw new IllegalArgumentException(String.format("Scope \"%s\" not accessible for member \"%s\"!", prev.fullIdentifier, extension));
		}
		shallowIdentifier = extension;
	}
	
	public Def getDef() {
		return scope.getDef(shallowIdentifier);
	}
	
	public void setDef(@Nonnull Element value, boolean shadow) {
		if (shadow && !scope.canShadow() && scope.hasDef(shallowIdentifier, false)) {
			throw shadowError("variable");
		}
		scope.setDef(shallowIdentifier, value, shadow);
	}
	
	public Macro getMacro() {
		return scope.getMacro(shallowIdentifier);
	}
	
	public void setMacro(@Nonnull BlockElement block) {
		if (!scope.canShadow() && scope.hasMacro(shallowIdentifier, false)) {
			throw shadowError("macro");
		}
		scope.setMacro(shallowIdentifier, block);
	}
	
	public Clazz getClazz() {
		return scope.getClazz(shallowIdentifier);
	}
	
	public void setClazz(@Nonnull ClazzType type, @Nullable HierarchicalScope base, @Nonnull ArrayList<Clazz> supers) {
		if (!scope.canShadow() && scope.hasClazz(shallowIdentifier, false)) {
			throw shadowError("class");
		}
		scope.setClazz(interpreter, shallowIdentifier, type, base, supers);
	}
	
	protected RuntimeException shadowError(@Nonnull String type) {
		return new IllegalArgumentException(String.format("Can not shadow %s \"%s\" in %s!", type, shallowIdentifier, scope.scopeIdentifier()));
	}
	
	public @Nonnull TokenResult delete() {
		if (scope.removeDef(shallowIdentifier) != null) {
			if (!scope.canDelete()) {
				throw deleteError("variable");
			}
			return TokenResult.PASS;
		}
		else if (scope.removeMacro(shallowIdentifier) != null) {
			if (!scope.canDelete()) {
				throw deleteError("macro");
			}
			return TokenResult.PASS;
		}
		else if (scope.removeClazz(shallowIdentifier) != null) {
			if (!scope.canDelete()) {
				throw deleteError("class");
			}
			return TokenResult.PASS;
		}
		throw DSSLHelpers.defError(fullIdentifier);
	}
	
	protected RuntimeException deleteError(@Nonnull String type) {
		return new IllegalArgumentException(String.format("Can not delete %s \"%s\" in %s!", type, shallowIdentifier, scope.scopeIdentifier()));
	}
	
	public @Nonnull LabelElement extended(@Nonnull String extension) {
		return new LabelElement(interpreter, this, extension);
	}
	
	@Override
	public @Nonnull Element clone() {
		return new LabelElement(interpreter, scope, fullIdentifier, shallowIdentifier);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(BuiltIn.LABEL, scope, fullIdentifier, shallowIdentifier);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof LabelElement other) {
			return scope.equals(other.scope) && fullIdentifier.equals(other.fullIdentifier) && shallowIdentifier.equals(other.shallowIdentifier);
		}
		return false;
	}
	
	@Override
	public @Nonnull String toString(TokenExecutor exec) {
		return "/" + fullIdentifier;
	}
}
