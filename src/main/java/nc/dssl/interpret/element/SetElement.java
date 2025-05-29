package nc.dssl.interpret.element;

import nc.dssl.interpret.*;
import nc.dssl.interpret.element.iter.IterElement;
import nc.dssl.interpret.element.primitive.StringElement;

import javax.annotation.*;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.*;

public class SetElement extends Element {
	
	public final Set<ElementKey> value;
	
	public <T extends Element> SetElement(TokenExecutor exec, Consumer<Consumer<T>> forEach) {
		super(exec.interpreter, exec.interpreter.builtIn.setClazz);
		value = new HashSet<>();
		forEach.accept(x -> value.add(x.toKey(exec)));
	}
	
	public <T extends Element> SetElement(TokenExecutor exec, Iterable<T> elems) {
		this(exec, elems::forEach);
	}
	
	public <T extends Element> SetElement(TokenExecutor exec, Iterator<T> elems) {
		this(exec, elems::forEachRemaining);
	}
	
	public <T extends Element> SetElement(TokenExecutor exec, Stream<T> elems) {
		this(exec, elems::forEach);
	}
	
	public SetElement(Interpreter interpreter, Set<ElementKey> set) {
		super(interpreter, interpreter.builtIn.setClazz);
		value = set;
	}
	
	public SetElement(Interpreter interpreter, Stream<ElementKey> keys) {
		this(interpreter, keys.collect(Collectors.toSet()));
	}
	
	@Override
	public @Nonnull StringElement stringCast(TokenExecutor exec) {
		return new StringElement(interpreter, toString(exec));
	}
	
	@Override
	public @Nonnull ListElement listCast(TokenExecutor exec) {
		return new ListElement(interpreter, value.stream().map(x -> x.elem));
	}
	
	@Override
	public @Nonnull SetElement setCast(TokenExecutor exec) {
		return this;
	}
	
	@Override
	public @Nonnull IterElement iterator(TokenExecutor exec) {
		return new IterElement(interpreter) {
			
			final Iterator<ElementKey> internal = value.iterator();
			
			@Override
			public boolean hasNext(TokenExecutor exec) {
				return internal.hasNext();
			}
			
			@SuppressWarnings("null")
			@Override
			public @Nonnull Element next(TokenExecutor exec) {
				return internal.next().elem;
			}
		};
	}
	
	@Override
	public @Nonnull Element iter(TokenExecutor exec) {
		return iterator(exec);
	}
	
	@Override
	public void unpack(TokenExecutor exec) {
		for (@Nonnull ElementKey key : value) {
			exec.push(key.elem);
		}
	}
	
	@Override
	public int size(TokenExecutor exec) {
		return value.size();
	}
	
	@Override
	public boolean isEmpty(TokenExecutor exec) {
		return value.isEmpty();
	}
	
	@Override
	public boolean contains(TokenExecutor exec, @Nonnull Element elem) {
		return value.contains(elem.toKey(exec));
	}
	
	@Override
	public void add(TokenExecutor exec, @Nonnull Element elem) {
		value.add(elem.toKey(exec));
	}
	
	@Override
	public void remove(TokenExecutor exec, @Nonnull Element elem) {
		value.remove(elem.toKey(exec));
	}
	
	@Override
	public boolean containsAll(TokenExecutor exec, @Nonnull Element elem) {
		@Nullable Iterable<Element> iterable = elem.internalIterable(exec);
		if (iterable == null) {
			throw new IllegalArgumentException(String.format("Built-in method \"containsAll\" requires %s element as argument!", BuiltIn.ITERABLE));
		}
		for (@Nonnull Element e : iterable) {
			if (!contains(exec, e)) {
				return false;
			}
		}
		return true;
	}
	
	@Override
	public void addAll(TokenExecutor exec, @Nonnull Element elem) {
		@Nullable Iterable<Element> iterable = elem.internalIterable(exec);
		if (iterable == null) {
			throw new IllegalArgumentException(String.format("Built-in method \"addAll\" requires %s element as argument!", BuiltIn.ITERABLE));
		}
		for (@Nonnull Element e : iterable) {
			add(exec, e);
		}
	}
	
	@Override
	public void removeAll(TokenExecutor exec, @Nonnull Element elem) {
		@Nullable Iterable<Element> iterable = elem.internalIterable(exec);
		if (iterable == null) {
			throw new IllegalArgumentException(String.format("Built-in method \"removeAll\" requires %s element as argument!", BuiltIn.ITERABLE));
		}
		for (@Nonnull Element e : iterable) {
			remove(exec, e);
		}
	}
	
	@Override
	public void clear(TokenExecutor exec) {
		value.clear();
	}
	
	@Override
	public @Nonnull String debug(TokenExecutor exec) {
		return "(|...|)";
	}
	
	@Override
	public @Nonnull StringElement __str__(TokenExecutor exec) {
		return stringCast(exec);
	}
	
	@Override
	public @Nonnull StringElement __debug__(TokenExecutor exec) {
		return new StringElement(interpreter, debug(exec));
	}
	
	@Override
	public @Nonnull Element clone() {
		return new SetElement(interpreter, value.stream().map(ElementKey::clone));
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(BuiltIn.SET, value);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof SetElement other) {
			return value.equals(other.value);
		}
		return false;
	}
	
	@SuppressWarnings("null")
	@Override
	public @Nonnull String toString(TokenExecutor exec) {
		return value.stream().map(x -> x.elem.innerString(exec, this)).collect(Collectors.joining(", ", "(|", "|)"));
	}
}
