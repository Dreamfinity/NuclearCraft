package nc.dssl.interpret;

import nc.dssl.DSSLHierarchy;
import nc.dssl.interpret.element.*;
import nc.dssl.interpret.element.primitive.StringElement;

import javax.annotation.*;
import java.util.*;

public interface HierarchicalScope extends Scope {
	
	@Override
	default boolean hasDef(@Nonnull String identifier, boolean shallow) {
		return getDefHierarchy().containsKey(identifier, shallow);
	}
	
	@Override
	default Def getDef(@Nonnull String identifier) {
		return getDefHierarchy().get(identifier, false);
	}
	
	default void setDef(@Nonnull String identifier, @Nonnull Def def, boolean shadow) {
		if (shadow) {
			checkCollision(identifier);
		}
		getDefHierarchy().put(identifier, def, shadow);
	}
	
	@Override
	default void setDef(@Nonnull String identifier, @Nonnull Element value, boolean shadow) {
		setDef(identifier, new Def(identifier, value), shadow);
	}
	
	@Override
	default Def removeDef(@Nonnull String identifier) {
		return getDefHierarchy().remove(identifier, false);
	}
	
	@Override
	default boolean hasMacro(@Nonnull String identifier, boolean shallow) {
		return getMacroHierarchy().containsKey(identifier, shallow);
	}
	
	@Override
	default Macro getMacro(@Nonnull String identifier) {
		return getMacroHierarchy().get(identifier, false);
	}
	
	default void setMacro(@Nonnull String identifier, @Nonnull Macro macro, boolean shadow) {
		checkCollision(identifier);
		getMacroHierarchy().put(identifier, macro, shadow);
	}
	
	@Override
	default void setMacro(@Nonnull String identifier, @Nonnull Invokable invokable) {
		setMacro(identifier, new Macro(identifier, invokable), true);
	}
	
	@Override
	default Macro removeMacro(@Nonnull String identifier) {
		return getMacroHierarchy().remove(identifier, false);
	}
	
	@Override
	default boolean hasClazz(@Nonnull String shallowIdentifier, boolean shallow) {
		return getClazzHierarchy().containsKey(shallowIdentifier, shallow);
	}
	
	@Override
	default Clazz getClazz(@Nonnull String shallowIdentifier) {
		return getClazzHierarchy().get(shallowIdentifier, false);
	}
	
	default void setClazz(@Nonnull String shallowIdentifier, @Nonnull Clazz clazz, boolean shadow) {
		checkCollision(shallowIdentifier);
		getClazzHierarchy().put(shallowIdentifier, clazz, shadow);
	}
	
	@Override
	default void setClazz(Interpreter interpreter, @Nonnull String shallowIdentifier, @Nonnull ClazzType type, @Nullable HierarchicalScope base, @Nonnull ArrayList<Clazz> supers) {
		setClazz(shallowIdentifier, new Clazz(interpreter, scopeIdentifier(), shallowIdentifier, type, base, supers), true);
	}
	
	@Override
	default Clazz removeClazz(@Nonnull String shallowIdentifier) {
		return getClazzHierarchy().remove(shallowIdentifier, false);
	}
	
	DSSLHierarchy<String, Def> getDefHierarchy();
	
	DSSLHierarchy<String, Macro> getMacroHierarchy();
	
	DSSLHierarchy<String, Clazz> getClazzHierarchy();
	
	default <T> void addToScopeMap(TokenExecutor exec, DSSLHierarchy<String, T> source, Map<ElementKey, Element> target) {
		source.forEach((k, v) -> target.put(new StringElement(exec.interpreter, k).toKey(exec), new LabelElement(exec.interpreter, this, k)), false);
	}
	
	@Override
	default void addToScopeMap(TokenExecutor exec, @Nonnull Map<ElementKey, Element> map) {
		addToScopeMap(exec, getDefHierarchy(), map);
		addToScopeMap(exec, getMacroHierarchy(), map);
		addToScopeMap(exec, getClazzHierarchy(), map);
	}
	
	@SuppressWarnings("null")
	default void putAll(@Nonnull HierarchicalScope from, boolean shadow, boolean shallow) {
		from.getDefHierarchy().forEach((k, v) -> setDef(k, v, shadow), shallow);
		from.getMacroHierarchy().forEach((k, v) -> setMacro(k, v, shadow), shallow);
		from.getClazzHierarchy().forEach((k, v) -> setClazz(k, v, shadow), shallow);
	}
}
