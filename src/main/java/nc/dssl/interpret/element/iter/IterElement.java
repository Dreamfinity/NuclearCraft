package nc.dssl.interpret.element.iter;

import nc.dssl.interpret.*;
import nc.dssl.interpret.element.*;
import nc.dssl.interpret.element.primitive.*;

import javax.annotation.*;
import java.util.*;

public abstract class IterElement extends Element {
	
	public IterElement(Interpreter interpreter) {
		super(interpreter, interpreter.builtIn.iterClazz);
	}
	
	public abstract boolean hasNext(TokenExecutor exec);
	
	public abstract @Nonnull Element next(TokenExecutor exec);
	
	public Iterator<Element> internalIterator(TokenExecutor exec) {
		return new Iterator<>() {
			
			@Override
			public boolean hasNext() {
				return IterElement.this.hasNext(exec);
			}
			
			@Override
			public @Nonnull Element next() {
				return IterElement.this.next(exec);
			}
		};
	}
	
	@SuppressWarnings("null")
	@Override
	public @Nonnull Element collectString(TokenExecutor exec) {
		Iterator<Element> iter = internalIterator(exec);
		StringBuilder sb = new StringBuilder();
		while (iter.hasNext()) {
			@Nonnull Element elem = iter.next();
			if (!(elem instanceof CharElement charElem)) {
				throw new IllegalArgumentException(String.format("Built-in method \"collectString\" requires \"() -> %s\" %s element as argument!", BuiltIn.CHAR, BuiltIn.ITER));
			}
			sb.append(charElem.primitiveChar());
		}
		return new StringElement(interpreter, sb.toString());
	}
	
	@Override
	public @Nonnull Element collectList(TokenExecutor exec) {
		return new ListElement(interpreter, internalIterator(exec));
	}
	
	@Override
	public @Nonnull Element collectSet(TokenExecutor exec) {
		return new SetElement(interpreter, internalStream(exec).map(x -> x.toKey(exec)));
	}
	
	@Override
	public @Nonnull Element collectDict(TokenExecutor exec) {
		Iterator<Element> iter = internalIterator(exec);
		Map<ElementKey, Element> map = new HashMap<>();
		while (iter.hasNext()) {
			@SuppressWarnings("null") @Nullable IterElement iterElem = iter.next().iterator(exec);
			if (iterElem == null) {
				throw new IllegalArgumentException(String.format("Built-in method \"collectDict\" requires \"() -> %s\" %s element as argument!", BuiltIn.ITERABLE, BuiltIn.ITER));
			}
			map.put(iterElem.next(exec).toKey(exec), iterElem.next(exec));
		}
		
		return new DictElement(interpreter, map);
	}
	
	@Override
	public @Nonnull Element stepBy(TokenExecutor exec, @Nonnull Element elem) {
		return new StepByIterElement(interpreter, this, methodLongIndex(exec, elem, "stepBy"));
	}
	
	@Override
	public @Nonnull Element chain(TokenExecutor exec, @Nonnull Element elem) {
		if (!(elem instanceof IterElement iter)) {
			throw new IllegalArgumentException(String.format("Built-in method \"chain\" requires %s element as argument!", BuiltIn.ITER));
		}
		return new ChainIterElement(interpreter, this, iter);
	}
	
	@Override
	public @Nonnull Element zip(TokenExecutor exec, @Nonnull Element elem) {
		if (!(elem instanceof IterElement iter)) {
			throw new IllegalArgumentException(String.format("Built-in method \"zip\" requires %s element as argument!", BuiltIn.ITER));
		}
		return new ZipIterElement(interpreter, this, iter);
	}
	
	@Override
	public @Nonnull Element map(TokenExecutor exec, @Nonnull Element elem) {
		if (!(elem instanceof BlockElement block)) {
			throw new IllegalArgumentException(String.format("Built-in method \"map\" requires %s element as argument!", BuiltIn.BLOCK));
		}
		return new MapIterElement(interpreter, this, block);
	}
	
	@Override
	public @Nonnull Element filter(TokenExecutor exec, @Nonnull Element elem) {
		if (!(elem instanceof BlockElement block)) {
			throw new IllegalArgumentException(String.format("Built-in method \"filter\" requires %s element as argument!", BuiltIn.BLOCK));
		}
		return new FilterIterElement(interpreter, this, block);
	}
	
	@Override
	public @Nonnull Element filterMap(TokenExecutor exec, @Nonnull Element elem) {
		if (!(elem instanceof BlockElement block)) {
			throw new IllegalArgumentException(String.format("Built-in method \"filterMap\" requires %s element as argument!", BuiltIn.BLOCK));
		}
		return new FilterMapIterElement(interpreter, this, block);
	}
	
	@Override
	public @Nonnull Element enumerate(TokenExecutor exec) {
		return new EnumerateIterElement(interpreter, this);
	}
	
	@Override
	public @Nonnull Element takeWhile(TokenExecutor exec, @Nonnull Element elem) {
		if (!(elem instanceof BlockElement block)) {
			throw new IllegalArgumentException(String.format("Built-in method \"takeWhile\" requires %s element as argument!", BuiltIn.BLOCK));
		}
		return new TakeWhileIterElement(interpreter, this, block);
	}
	
	@Override
	public @Nonnull Element mapWhile(TokenExecutor exec, @Nonnull Element elem) {
		if (!(elem instanceof BlockElement block)) {
			throw new IllegalArgumentException(String.format("Built-in method \"mapWhile\" requires %s element as argument!", BuiltIn.BLOCK));
		}
		return new MapWhileIterElement(interpreter, this, block);
	}
	
	@Override
	public @Nonnull Element skip(TokenExecutor exec, @Nonnull Element elem) {
		return new SkipIterElement(interpreter, this, methodLongIndex(exec, elem, "skip"));
	}
	
	@Override
	public @Nonnull Element take(TokenExecutor exec, @Nonnull Element elem) {
		return new TakeIterElement(interpreter, this, methodLongIndex(exec, elem, "take"));
	}
	
	@Override
	public @Nonnull Element flatten(TokenExecutor exec) {
		return new FlattenIterElement(interpreter, this);
	}
	
	@Override
	public @Nonnull Element flatMap(TokenExecutor exec, @Nonnull Element elem) {
		if (!(elem instanceof BlockElement block)) {
			throw new IllegalArgumentException(String.format("Built-in method \"flatMap\" requires %s element as argument!", BuiltIn.BLOCK));
		}
		return new FlatMapIterElement(interpreter, this, block);
	}
	
	@Override
	public @Nonnull Element chunks(TokenExecutor exec, @Nonnull Element elem) {
		return new ChunksIterElement(interpreter, this, methodLongIndex(exec, elem, "chunks"));
	}
	
	@Override
	public int count(TokenExecutor exec) {
		Iterator<Element> iter = internalIterator(exec);
		int count = 0;
		while (iter.hasNext()) {
			iter.next();
			++count;
		}
		return count;
	}
	
	@Override
	public void forEach(TokenExecutor exec, @Nonnull Element elem) {
		if (!(elem instanceof BlockElement block)) {
			throw new IllegalArgumentException(String.format("Built-in method \"forEach\" requires %s element as argument!", BuiltIn.BLOCK));
		}
		
		internalIterator(exec).forEachRemaining(x -> {
			exec.push(x);
			block.invoke(exec);
		});
	}
	
	@Override
	public boolean all(TokenExecutor exec) {
		Iterator<Element> iter = internalIterator(exec);
		while (iter.hasNext()) {
			@SuppressWarnings("null") BoolElement elem = iter.next().asBool(exec);
			if (elem == null) {
				throw new IllegalArgumentException(String.format("Built-in method \"all\" requires \"() -> %s\" %s element as argument!", BuiltIn.BOOL, BuiltIn.ITER));
			}
			if (!elem.primitiveBool()) {
				return false;
			}
		}
		return true;
	}
	
	@Override
	public boolean any(TokenExecutor exec) {
		Iterator<Element> iter = internalIterator(exec);
		while (iter.hasNext()) {
			@SuppressWarnings("null") BoolElement elem = iter.next().asBool(exec);
			if (elem == null) {
				throw new IllegalArgumentException(String.format("Built-in method \"any\" requires \"() -> %s\" %s element as argument!", BuiltIn.BOOL, BuiltIn.ITER));
			}
			if (elem.primitiveBool()) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public @Nonnull Element min(TokenExecutor exec) {
		@Nonnull Element curr = interpreter.builtIn.nullElement;
		Iterator<Element> iter = internalIterator(exec);
		while (iter.hasNext()) {
			@SuppressWarnings("null") @Nonnull Element elem = iter.next();
			if (curr.equals(interpreter.builtIn.nullElement) || elem.dynCompareTo(exec, curr) < 0) {
				curr = elem;
			}
		}
		return curr;
	}
	
	@Override
	public @Nonnull Element max(TokenExecutor exec) {
		@Nonnull Element curr = interpreter.builtIn.nullElement;
		Iterator<Element> iter = internalIterator(exec);
		while (iter.hasNext()) {
			@SuppressWarnings("null") @Nonnull Element elem = iter.next();
			if (curr.equals(interpreter.builtIn.nullElement) || elem.dynCompareTo(exec, curr) > 0) {
				curr = elem;
			}
		}
		return curr;
	}
	
	@Override
	public @Nonnull Element clone() {
		throw new IllegalArgumentException(String.format("Elements of type \"%s\" can not be cloned!", BuiltIn.ITER));
	}
	
	@Override
	public int hashCode() {
		return objectHashCode();
	}
	
	@Override
	@SuppressWarnings("all")
	public boolean equals(Object obj) {
		return objectEquals(obj);
	}
}
