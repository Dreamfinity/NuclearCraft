package nc.dssl.interpret.element.iter;

import nc.dssl.interpret.*;
import nc.dssl.interpret.element.Element;

import javax.annotation.Nonnull;

public class MapIterElement extends IterElement {
	
	protected final IterElement internal;
	protected final Invokable invokable;
	
	public MapIterElement(Interpreter interpreter, IterElement internal, Invokable invokable) {
		super(interpreter);
		this.internal = internal;
		this.invokable = invokable;
	}
	
	@Override
	public boolean hasNext(TokenExecutor exec) {
		return internal.hasNext(exec);
	}
	
	@Override
	public @Nonnull Element next(TokenExecutor exec) {
		exec.push(internal.next(exec));
		invokable.invoke(exec);
		return exec.pop();
	}
}
