package nc.dssl.interpret.element.iter;

import nc.dssl.interpret.*;
import nc.dssl.interpret.element.Element;

import javax.annotation.Nonnull;

public class SkipIterElement extends IterElement {
	
	protected final IterElement internal;
	protected long skip;
	
	public SkipIterElement(Interpreter interpreter, IterElement internal, long skip) {
		super(interpreter);
		this.internal = internal;
		this.skip = skip;
	}
	
	protected void prepare(TokenExecutor exec) {
		while (skip-- > 0 && internal.hasNext(exec)) {
			internal.next(exec);
		}
	}
	
	@Override
	public boolean hasNext(TokenExecutor exec) {
		prepare(exec);
		return internal.hasNext(exec);
	}
	
	@Override
	public @Nonnull Element next(TokenExecutor exec) {
		prepare(exec);
		return internal.next(exec);
	}
}
