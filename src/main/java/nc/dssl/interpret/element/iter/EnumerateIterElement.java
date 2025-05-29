package nc.dssl.interpret.element.iter;

import nc.dssl.interpret.*;
import nc.dssl.interpret.element.*;
import nc.dssl.interpret.element.primitive.IntElement;

import javax.annotation.Nonnull;

public class EnumerateIterElement extends IterElement {
	
	protected final IterElement internal;
	
	protected int index = 0;
	
	public EnumerateIterElement(Interpreter interpreter, IterElement internal) {
		super(interpreter);
		this.internal = internal;
	}
	
	@Override
	public boolean hasNext(TokenExecutor exec) {
		return internal.hasNext(exec);
	}
	
	@Override
	public @Nonnull Element next(TokenExecutor exec) {
		return new ListElement(interpreter, new IntElement(interpreter, index++), internal.next(exec));
	}
}
