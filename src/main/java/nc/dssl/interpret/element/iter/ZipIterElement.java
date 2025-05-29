package nc.dssl.interpret.element.iter;

import nc.dssl.interpret.*;
import nc.dssl.interpret.element.*;

import javax.annotation.Nonnull;

public class ZipIterElement extends IterElement {
	
	protected final IterElement first, second;
	
	public ZipIterElement(Interpreter interpreter, IterElement first, IterElement second) {
		super(interpreter);
		this.first = first;
		this.second = second;
	}
	
	@Override
	public boolean hasNext(TokenExecutor exec) {
		return first.hasNext(exec) && second.hasNext(exec);
	}
	
	@Override
	public @Nonnull Element next(TokenExecutor exec) {
		return new ListElement(interpreter, first.next(exec), second.next(exec));
	}
}
