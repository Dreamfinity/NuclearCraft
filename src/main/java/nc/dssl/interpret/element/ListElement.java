package nc.dssl.interpret.element;

import nc.dssl.interpret.*;
import nc.dssl.interpret.element.iter.IterElement;
import nc.dssl.interpret.element.primitive.*;

import javax.annotation.*;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.*;

public class ListElement extends Element {
	
	public final List<Element> value;
	
	public <T extends Element> ListElement(Interpreter interpreter, Consumer<Consumer<T>> forEach) {
		super(interpreter, interpreter.builtIn.listClazz);
		value = new ArrayList<>();
		forEach.accept(value::add);
	}
	
	public <T extends Element> ListElement(Interpreter interpreter, Iterable<T> elems) {
		this(interpreter, elems::forEach);
	}
	
	public <T extends Element> ListElement(Interpreter interpreter, Iterator<T> elems) {
		this(interpreter, elems::forEachRemaining);
	}
	
	public <T extends Element> ListElement(Interpreter interpreter, Stream<T> elems) {
		this(interpreter, elems::forEachOrdered);
	}
	
	@SafeVarargs
	public <T extends Element> ListElement(Interpreter interpreter, @Nonnull T... elems) {
		super(interpreter, interpreter.builtIn.listClazz);
		value = new ArrayList<>();
		Collections.addAll(value, elems);
	}
	
	@Override
	public @Nonnull StringElement stringCast(TokenExecutor exec) {
		return new StringElement(interpreter, toString(exec));
	}
	
	@Override
	public @Nonnull ListElement listCast(TokenExecutor exec) {
		return this;
	}
	
	@Override
	public @Nonnull SetElement setCast(TokenExecutor exec) {
		return new SetElement(interpreter, value.stream().map(x -> x.toKey(exec)));
	}
	
	@Override
	public @Nonnull IterElement iterator(TokenExecutor exec) {
		return new IterElement(interpreter) {
			
			final Iterator<Element> internal = value.iterator();
			
			@Override
			public boolean hasNext(TokenExecutor exec) {
				return internal.hasNext();
			}
			
			@SuppressWarnings("null")
			@Override
			public @Nonnull Element next(TokenExecutor exec) {
				return internal.next();
			}
		};
	}
	
	@Override
	public @Nonnull Element iter(TokenExecutor exec) {
		return iterator(exec);
	}
	
	@Override
	public void unpack(TokenExecutor exec) {
		for (@Nonnull Element elem : value) {
			exec.push(elem);
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
		for (@Nonnull Element e : value) {
			if (elem.dynEqualTo(exec, e)) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public void push(TokenExecutor exec, @Nonnull Element elem) {
		value.add(elem);
	}
	
	@Override
	public void insert(TokenExecutor exec, @Nonnull Element elem0, @Nonnull Element elem1) {
		value.add(methodIndex(exec, elem0, "insert", 1), elem1);
	}
	
	@Override
	public void remove(TokenExecutor exec, @Nonnull Element elem) {
		value.remove(methodIndex(exec, elem, "remove"));
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
	public void pushAll(TokenExecutor exec, @Nonnull Element elem) {
		@Nullable Iterable<Element> iterable = elem.internalIterable(exec);
		if (iterable == null) {
			throw new IllegalArgumentException(String.format("Built-in method \"pushAll\" requires %s element as argument!", BuiltIn.ITERABLE));
		}
		for (@Nonnull Element e : iterable) {
			value.add(e);
		}
	}
	
	@Override
	public void insertAll(TokenExecutor exec, @Nonnull Element elem0, @Nonnull Element elem1) {
		@Nullable Iterable<Element> iterable = elem1.internalIterable(exec);
		if (iterable == null) {
			throw new IllegalArgumentException(String.format("Built-in method \"insertAll\" requires %s element as second argument!", BuiltIn.ITERABLE));
		}
		
		int index = methodIndex(exec, elem0, "insertAll", 1);
		for (@Nonnull Element e : iterable) {
			value.add(index++, e);
		}
	}
	
	@SuppressWarnings("null")
	@Override
	public void removeAll(TokenExecutor exec, @Nonnull Element elem) {
		@Nullable Iterable<Element> iterable = elem.internalIterable(exec);
		if (iterable == null) {
			throw new IllegalArgumentException(String.format("Built-in method \"removeAll\" requires %s element of non-negative integers as argument!", BuiltIn.ITERABLE));
		}
		for (@Nonnull Element e : iterable) {
			IntElement intElem = e.asInt(exec);
			if (intElem == null) {
				throw new IllegalArgumentException(String.format("Built-in method \"removeAll\" requires %s element of non-negative integers as argument!", BuiltIn.ITERABLE));
			}
			value.set(intElem.primitiveInt(), null);
		}
		value.removeIf(Objects::isNull);
	}
	
	@SuppressWarnings("null")
	@Override
	public @Nonnull Element pop(TokenExecutor exec) {
		return value.remove(value.size() - 1);
	}
	
	@Override
	public void clear(TokenExecutor exec) {
		value.clear();
	}
	
	@SuppressWarnings("null")
	@Override
	public @Nonnull Element get(TokenExecutor exec, @Nonnull Element elem) {
		return value.get(methodIndex(exec, elem, "get"));
	}
	
	@Override
	public void set(TokenExecutor exec, @Nonnull Element elem0, @Nonnull Element elem1) {
		value.set(methodIndex(exec, elem0, "set", 1), elem1);
	}
	
	@Override
	public void removeValue(TokenExecutor exec, @Nonnull Element elem) {
		int size = value.size();
		for (int i = 0; i < size; ++i) {
			if (elem.dynEqualTo(exec, value.get(i))) {
				value.remove(i);
				return;
			}
		}
	}
	
	@Override
	public @Nonnull Element slice(TokenExecutor exec, @Nonnull Element elem0, @Nonnull Element elem1) {
		return new ListElement(interpreter, value.subList(methodIndex(exec, elem0, "slice", 1), methodIndex(exec, elem1, "slice", 2)));
	}
	
	@SuppressWarnings("null")
	@Override
	public @Nonnull Element fst(TokenExecutor exec) {
		return value.get(0);
	}
	
	@SuppressWarnings("null")
	@Override
	public @Nonnull Element snd(TokenExecutor exec) {
		return value.get(1);
	}
	
	@SuppressWarnings("null")
	@Override
	public @Nonnull Element last(TokenExecutor exec) {
		return value.get(value.size() - 1);
	}
	
	@Override
	public @Nonnull Element indexOf(TokenExecutor exec, @Nonnull Element elem) {
		int size = value.size();
		for (int i = 0; i < size; ++i) {
			if (elem.dynEqualTo(exec, value.get(i))) {
				return new IntElement(interpreter, i);
			}
		}
		return interpreter.builtIn.nullElement;
	}
	
	@Override
	public @Nonnull Element lastIndexOf(TokenExecutor exec, @Nonnull Element elem) {
		for (int i = value.size() - 1; i >= 0; --i) {
			if (elem.dynEqualTo(exec, value.get(i))) {
				return new IntElement(interpreter, i);
			}
		}
		return interpreter.builtIn.nullElement;
	}
	
	@Override
	public void reverse(TokenExecutor exec) {
		Collections.reverse(value);
	}
	
	@Override
	@SuppressWarnings("all")
	public void sort(TokenExecutor exec) {
		Collections.sort(value, (x, y) -> x.dynCompareTo(exec, y));
	}
	
	@Override
	@SuppressWarnings("all")
	public void sortBy(TokenExecutor exec, @Nonnull Element elem) {
		if (!(elem instanceof BlockElement block)) {
			throw new IllegalArgumentException(String.format("Built-in method \"sortBy\" requires \"%s %s -> %s\" %s element as argument!", BuiltIn.OBJECT, BuiltIn.OBJECT, BuiltIn.INT, BuiltIn.BLOCK));
		}
		
		Collections.sort(value, (x, y) -> {
			exec.push(x);
			exec.push(y);
			block.invoke(exec);
			
			IntElement result = exec.pop().asInt(exec);
			if (result == null) {
				throw new IllegalArgumentException(String.format("Built-in method \"sortBy\" requires \"%s %s -> %s\" %s element as argument!", BuiltIn.OBJECT, BuiltIn.OBJECT, BuiltIn.INT, BuiltIn.BLOCK));
			}
			
			return result.primitiveInt();
		});
	}
	
	@Override
	public void shuffle(TokenExecutor exec) {
		Collections.shuffle(value);
	}
	
	@Override
	public @Nonnull Element clone(TokenExecutor exec) {
		return new ListElement(interpreter, value.stream().map(x -> x.dynClone(exec)));
	}
	
	@Override
	public int hash(TokenExecutor exec) {
		int hash = BuiltIn.LIST.hashCode();
		for (@Nonnull Element elem : value) {
			hash = 31 * hash + elem.dynHash(exec);
		}
		return hash;
	}
	
	@Override
	public @Nonnull String debug(TokenExecutor exec) {
		return "[...]";
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
		return new ListElement(interpreter, value.stream().map(Element::clone));
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(BuiltIn.LIST, value);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ListElement other) {
			return value.equals(other.value);
		}
		return false;
	}
	
	@SuppressWarnings("null")
	@Override
	public @Nonnull String toString(TokenExecutor exec) {
		return value.stream().map(x -> x.innerString(exec, this)).collect(Collectors.joining(", ", "[", "]"));
	}
}
