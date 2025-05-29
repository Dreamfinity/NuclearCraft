package nc.dssl.interpret.element.iter;

import nc.dssl.interpret.*;
import nc.dssl.interpret.element.Element;

import javax.annotation.Nonnull;

public class TakeIterElement extends IterElement {
	
	protected final IterElement internal;
	protected long take;
	
	public TakeIterElement(Interpreter interpreter, IterElement internal, long take) {
		super(interpreter);
		this.internal = internal;
		this.take = take;
	}
	
	@Override
	public boolean hasNext(TokenExecutor exec) {
		return take > 0 && internal.hasNext(exec);
	}
	
	@Override
	public @Nonnull Element next(TokenExecutor exec) {
		--take;
		return internal.next(exec);
	}
}
