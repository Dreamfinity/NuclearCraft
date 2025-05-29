package nc.dssl.interpret.element.iter;

import nc.dssl.interpret.*;
import nc.dssl.interpret.element.Element;

import javax.annotation.Nonnull;

public class ChainIterElement extends IterElement {
	
	protected final IterElement first, second;
	protected boolean start = true;
	
	public ChainIterElement(Interpreter interpreter, IterElement first, IterElement second) {
		super(interpreter);
		this.first = first;
		this.second = second;
	}
	
	protected void prepare(TokenExecutor exec) {
		if (start && !first.hasNext(exec)) {
			start = false;
		}
	}
	
	@Override
	public boolean hasNext(TokenExecutor exec) {
		prepare(exec);
		return (start ? first : second).hasNext(exec);
	}
	
	@Override
	public @Nonnull Element next(TokenExecutor exec) {
		prepare(exec);
		return (start ? first : second).next(exec);
	}
}
