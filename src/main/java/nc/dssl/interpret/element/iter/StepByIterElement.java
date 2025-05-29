package nc.dssl.interpret.element.iter;

import nc.dssl.interpret.*;
import nc.dssl.interpret.element.Element;

import javax.annotation.*;

public class StepByIterElement extends IterElement {
	
	protected final IterElement internal;
	protected final long step;
	
	protected @Nullable Element next = null;
	protected boolean start = true, end = false;
	
	public StepByIterElement(Interpreter interpreter, IterElement internal, long step) {
		super(interpreter);
		this.internal = internal;
		this.step = step;
	}
	
	protected void prepare(TokenExecutor exec) {
		if (start) {
			start = false;
			if (internal.hasNext(exec)) {
				next = internal.next(exec);
				return;
			}
			
			end = true;
		}
		else if (next == null && !end) {
			long count = 0;
			while (internal.hasNext(exec)) {
				if (count++ >= step) {
					return;
				}
				next = internal.next(exec);
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
