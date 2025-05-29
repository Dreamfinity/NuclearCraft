package nc.dssl.interpret;

import nc.dssl.interpret.element.Element;

import javax.annotation.*;
import java.util.*;
import java.util.function.Supplier;

public interface Scope {
	
	@Nullable
	String scopeIdentifier();
	
	boolean canShadow();
	
	boolean canDelete();
	
	default @Nullable TokenResult scopeAction(TokenExecutor exec, @Nonnull String identifier) {
		return exec.scopeAction(() -> getDef(identifier), () -> getMacro(identifier), () -> getClazz(identifier));
	}
	
	default @Nullable Supplier<TokenResult> scopeInvokable(TokenExecutor exec, @Nonnull String identifier) {
		return exec.scopeInvokable(() -> getDef(identifier), () -> getMacro(identifier), () -> getClazz(identifier));
	}
	
	default void checkCollision(@Nonnull String identifier) {
		if (hasDef(identifier, true)) {
			throw new IllegalArgumentException(String.format("Identifier \"%s\" already used for def!", identifier));
		}
		else if (hasMacro(identifier, true)) {
			throw new IllegalArgumentException(String.format("Identifier \"%s\" already used for macro!", identifier));
		}
		else if (hasClazz(identifier, true)) {
			throw new IllegalArgumentException(String.format("Identifier \"%s\" already used for class!", identifier));
		}
	}
	
	boolean hasDef(@Nonnull String identifier, boolean shallow);
	
	Def getDef(@Nonnull String identifier);
	
	void setDef(@Nonnull String identifier, @Nonnull Element value, boolean shadow);
	
	Def removeDef(@Nonnull String identifier);
	
	boolean hasMacro(@Nonnull String identifier, boolean shallow);
	
	Macro getMacro(@Nonnull String identifier);
	
	void setMacro(@Nonnull String identifier, @Nonnull Invokable invokable);
	
	Macro removeMacro(@Nonnull String identifier);
	
	boolean hasClazz(@Nonnull String shallowIdentifier, boolean shallow);
	
	Clazz getClazz(@Nonnull String shallowIdentifier);
	
	void setClazz(Interpreter interpreter, @Nonnull String shallowIdentifier, @Nonnull ClazzType type, @Nullable HierarchicalScope base, @Nonnull ArrayList<Clazz> supers);
	
	Clazz removeClazz(@Nonnull String shallowIdentifier);
	
	void addToScopeMap(TokenExecutor exec, @Nonnull Map<ElementKey, Element> map);
}
