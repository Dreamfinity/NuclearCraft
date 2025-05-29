package nc.dssl.interpret.element.iter;

import nc.dssl.interpret.*;
import nc.dssl.interpret.element.*;

import javax.annotation.*;

public class ChunksIterElement extends IterElement {
	
	protected final IterElement internal;
	protected final long size;
	
	protected @Nullable ListElement next = null;
	protected boolean end = false;
	
	public ChunksIterElement(Interpreter interpreter, IterElement internal, long size) {
		super(interpreter);
		this.internal = internal;
		this.size = size;
	}
	
	protected void prepare(TokenExecutor exec) {
		if (next == null && !end) {
			long count = 0;
			while (internal.hasNext(exec)) {
				if (count++ >= size) {
					next = new ListElement(interpreter);
					return;
				}
				next.value.add(internal.next(exec));
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
