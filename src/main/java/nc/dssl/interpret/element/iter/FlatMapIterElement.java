package nc.dssl.interpret.element.iter;

import nc.dssl.interpret.*;
import nc.dssl.interpret.element.Element;

import javax.annotation.*;

public class FlatMapIterElement extends IterElement {
	
	protected final IterElement internal;
	protected final Invokable invokable;
	
	protected @Nullable IterElement current = null;
	protected boolean end = false;
	
	public FlatMapIterElement(Interpreter interpreter, IterElement internal, Invokable invokable) {
		super(interpreter);
		this.internal = internal;
		this.invokable = invokable;
	}
	
	protected void prepare(TokenExecutor exec) {
		while (!end) {
			if (current != null) {
				if (current.hasNext(exec)) {
					return;
				}
				else {
					current = null;
				}
			}
			
			if (internal.hasNext(exec)) {
				exec.push(internal.next(exec));
				invokable.invoke(exec);
				
				@Nonnull Element result = exec.pop();
				if (!(result instanceof IterElement iter)) {
					throw new IllegalArgumentException(String.format("Built-in method \"flatMap\" requires \"%s -> %s\" %s element as argument!", BuiltIn.OBJECT, BuiltIn.ITER, BuiltIn.BLOCK));
				}
				current = iter;
			}
			else {
				end = true;
			}
		}
	}
	
	@Override
	public boolean hasNext(TokenExecutor exec) {
		prepare(exec);
		return !end;
	}
	
	@Override
	public @Nonnull Element next(TokenExecutor exec) {
		prepare(exec);
		return current.next(exec);
	}
}
