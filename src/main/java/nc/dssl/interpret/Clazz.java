package nc.dssl.interpret;

import nc.dssl.*;
import nc.dssl.interpret.element.Element;
import nc.dssl.interpret.element.clazz.*;

import javax.annotation.*;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Clazz implements HierarchicalScope {
	
	public final @Nonnull String fullIdentifier;
	public final @Nonnull String shallowIdentifier;
	
	public final @Nonnull ClazzType type;
	
	protected Element elem;
	
	public final List<Clazz> supers;
	
	protected final DSSLHierarchy<String, Def> defHierarchy;
	protected final DSSLHierarchy<String, Macro> macroHierarchy;
	protected final DSSLHierarchy<String, Clazz> clazzHierarchy;
	
	public Clazz(Interpreter interpreter, @Nonnull String identifier, @Nonnull ClazzType type, Clazz... supers) {
		this(interpreter, null, identifier, type, null, new ArrayList<>(Arrays.asList(supers)));
	}
	
	public Clazz(Interpreter interpreter, @Nullable String prev, @Nonnull String extension, @Nonnull ClazzType type, @Nullable HierarchicalScope base, @Nullable ArrayList<Clazz> supers) {
		fullIdentifier = DSSLHelpers.extendedIdentifier(prev, extension);
		shallowIdentifier = extension;
		this.type = type;
		
		if (supers == null) {
			this.supers = new ArrayList<>();
		}
		else {
			if (supers.isEmpty()) {
				supers.add(interpreter.builtIn.objectClazz);
			}
			this.supers = supers.stream().distinct().collect(Collectors.toList());
		}
		
		defHierarchy = getHierarchy(base, HierarchicalScope::getDefHierarchy);
		macroHierarchy = getHierarchy(base, HierarchicalScope::getMacroHierarchy);
		clazzHierarchy = getHierarchy(base, HierarchicalScope::getClazzHierarchy);
	}
	
	protected <K, V> DSSLHierarchy<K, V> getHierarchy(@Nullable HierarchicalScope base, Function<HierarchicalScope, DSSLHierarchy<K, V>> function) {
		return (base == null ? new DSSLHierarchy<K, V>() : function.apply(base)).branch(DSSLHelpers.map(supers, function));
	}
	
	@SuppressWarnings("null")
	public @Nonnull Element clazzElement(Interpreter interpreter) {
		if (elem == null) {
			elem = new ClassElement(interpreter, this);
		}
		return elem;
	}
	
	@Override
	public @Nullable String scopeIdentifier() {
		return fullIdentifier;
	}
	
	@Override
	public boolean canShadow() {
		return type.canModify();
	}
	
	@Override
	public boolean canDelete() {
		return type.canModify();
	}
	
	@Override
	public DSSLHierarchy<String, Def> getDefHierarchy() {
		return defHierarchy;
	}
	
	@Override
	public DSSLHierarchy<String, Macro> getMacroHierarchy() {
		return macroHierarchy;
	}
	
	@Override
	public DSSLHierarchy<String, Clazz> getClazzHierarchy() {
		return clazzHierarchy;
	}
	
	public @Nonnull TokenResult instantiate(TokenExecutor exec) {
		if (!type.canInstantiate()) {
			throw new IllegalArgumentException(String.format("Can not instantiate instance of class \"%s\"!", fullIdentifier));
		}
		
		InstanceElement instance = new InstanceElement(exec.interpreter, this);
		TokenResult init = instance.magicAction(exec, "__init__");
		if (init == null) {
			exec.push(instance);
			return TokenResult.PASS;
		}
		else {
			return init;
		}
	}
	
	public boolean is(@Nonnull Clazz clazz) {
		return equals(clazz) || supers.stream().anyMatch(x -> x.is(clazz));
	}
	
	protected RuntimeException castError(@Nonnull Element elem) {
		return new IllegalArgumentException(String.format("Failed to cast %s \"%s\" to %s!", elem.typeName(), elem, fullIdentifier));
	}
	
	public @Nullable Element as(TokenExecutor exec, @Nonnull Element elem) {
		if (elem.clazz.is(this)) {
			return elem;
		}
		else {
			return null;
		}
	}
	
	public @Nonnull Element cast(TokenExecutor exec, @Nonnull Element elem) {
		Element implicit = as(exec, elem);
		if (implicit != null) {
			return implicit;
		}
		else {
			throw castError(elem);
		}
	}
}
