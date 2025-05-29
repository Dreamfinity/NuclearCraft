package nc.dssl.interpret.element.clazz;

import nc.dssl.interpret.*;
import nc.dssl.interpret.element.*;
import nc.dssl.interpret.element.iter.IterElement;
import nc.dssl.interpret.element.primitive.StringElement;

import javax.annotation.*;
import java.util.*;

public class InstanceElement extends Element implements Scope {
	
	protected final Map<String, Def> defMap;
	protected final Map<String, Macro> macroMap;
	protected final Map<String, Clazz> clazzMap;
	
	public final @Nonnull String scopeIdentifier;
	
	public InstanceElement(Interpreter interpreter, @Nonnull Clazz clazz) {
		this(interpreter, clazz, new HashMap<>(), new HashMap<>(), new HashMap<>());
	}
	
	protected InstanceElement(Interpreter interpreter, @Nonnull Clazz clazz, Map<String, Def> defMap, Map<String, Macro> macroMap, Map<String, Clazz> clazzMap) {
		super(interpreter, clazz);
		this.defMap = defMap;
		this.macroMap = macroMap;
		this.clazzMap = clazzMap;
		scopeIdentifier = toString();
	}
	
	@Override
	public @Nullable String scopeIdentifier() {
		return scopeIdentifier;
	}
	
	@Override
	public boolean canShadow() {
		return true;
	}
	
	@Override
	public boolean canDelete() {
		return true;
	}
	
	@Override
	public boolean hasDef(@Nonnull String identifier, boolean shallow) {
		return defMap.containsKey(identifier);
	}
	
	@Override
	public Def getDef(@Nonnull String identifier) {
		return defMap.get(identifier);
	}
	
	@Override
	public void setDef(@Nonnull String identifier, @Nonnull Element value, boolean shadow) {
		if (shadow) {
			checkCollision(identifier);
		}
		defMap.put(identifier, new Def(identifier, value));
	}
	
	@Override
	public Def removeDef(@Nonnull String identifier) {
		return defMap.remove(identifier);
	}
	
	@Override
	public boolean hasMacro(@Nonnull String identifier, boolean shallow) {
		return macroMap.containsKey(identifier);
	}
	
	@Override
	public Macro getMacro(@Nonnull String identifier) {
		return macroMap.get(identifier);
	}
	
	@Override
	public void setMacro(@Nonnull String identifier, @Nonnull Invokable invokable) {
		checkCollision(identifier);
		macroMap.put(identifier, new Macro(identifier, invokable));
	}
	
	@Override
	public Macro removeMacro(@Nonnull String identifier) {
		return macroMap.remove(identifier);
	}
	
	@Override
	public boolean hasClazz(@Nonnull String shallowIdentifier, boolean shallow) {
		return clazzMap.containsKey(shallowIdentifier);
	}
	
	@Override
	public Clazz getClazz(@Nonnull String shallowIdentifier) {
		return clazzMap.get(shallowIdentifier);
	}
	
	@Override
	public void setClazz(Interpreter interpreter, @Nonnull String shallowIdentifier, @Nonnull ClazzType type, @Nullable HierarchicalScope base, @Nonnull ArrayList<Clazz> supers) {
		checkCollision(shallowIdentifier);
		clazzMap.put(shallowIdentifier, new Clazz(interpreter, scopeIdentifier, shallowIdentifier, type, base, supers));
	}
	
	@Override
	public Clazz removeClazz(@Nonnull String shallowIdentifier) {
		return clazzMap.remove(shallowIdentifier);
	}
	
	@Override
	public @Nullable IterElement iterator(TokenExecutor exec) {
		TokenResult result = memberAction(exec, "iter", false);
		return result == null ? null : (IterElement) exec.pop();
	}
	
	protected <T> void addToScopeMap(TokenExecutor exec, Map<String, T> source, Map<ElementKey, Element> target) {
		for (String key : source.keySet()) {
			target.put(new StringElement(interpreter, key).toKey(exec), new LabelElement(interpreter, this, key));
		}
	}
	
	@Override
	public void addToScopeMap(TokenExecutor exec, @Nonnull Map<ElementKey, Element> map) {
		addToScopeMap(exec, defMap, map);
		addToScopeMap(exec, macroMap, map);
		addToScopeMap(exec, clazzMap, map);
	}
	
	@Override
	public @Nullable Scope getMemberScope(@Nonnull MemberAccessType access) {
		return access.equals(MemberAccessType.STATIC) ? this : clazz;
	}
	
	@Override
	public @Nonnull Element clone() {
		return new InstanceElement(interpreter, clazz, new HashMap<>(defMap), new HashMap<>(macroMap), new HashMap<>(clazzMap));
	}
	
	@Override
	public int hashCode() {
		return Objects.hash("instance", clazz, defMap, macroMap, clazzMap);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof InstanceElement other) {
			return clazz.equals(other.clazz) && defMap.equals(other.defMap) && macroMap.equals(other.macroMap) && clazzMap.equals(other.clazzMap);
		}
		return false;
	}
}
