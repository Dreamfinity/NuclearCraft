package nc.dssl.interpret.element.iter;

import nc.dssl.interpret.*;
import nc.dssl.interpret.element.Element;

import javax.annotation.*;

public class MapWhileIterElement extends IterElement {
	
	protected final IterElement internal;
	protected final Invokable invokable;
	
	protected @Nullable Element next = null;
	protected boolean end = false;
	
	public MapWhileIterElement(Interpreter interpreter, IterElement internal, Invokable invokable) {
		super(interpreter);
		this.internal = internal;
		this.invokable = invokable;
	}
	
	protected void prepare(TokenExecutor exec) {
		if (next == null && !end) {
			if (internal.hasNext(exec)) {
				exec.push(internal.next(exec));
				invokable.invoke(exec);
				
				@Nonnull Element result = exec.pop();
				if (!result.equals(interpreter.builtIn.nullElement)) {
					next = result;
					return;
				}
			}
			
			end = true;
		}
	}
	
	@Override
	public boolean hasNext(TokenExecutor exec) {
		prepare(exec);
		return !end;
	}
	
	@SuppressWarnings("null")
	@Override
	public @Nonnull Element next(TokenExecutor exec) {
		prepare(exec);
		@Nonnull Element elem = next;
		next = null;
		return elem;
	}
}
