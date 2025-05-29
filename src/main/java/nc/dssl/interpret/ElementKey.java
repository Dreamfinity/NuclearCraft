package nc.dssl.interpret;

import nc.dssl.interpret.element.Element;

import javax.annotation.Nonnull;

public class ElementKey {
	
	public final TokenExecutor exec;
	public final @Nonnull Element elem;
	
	public ElementKey(TokenExecutor exec, @Nonnull Element elem) {
		this.exec = exec;
		this.elem = elem;
	}
	
	@Override
	@SuppressWarnings("all")
	public @Nonnull ElementKey clone() {
		return elem.dynClone(exec).toKey(exec);
	}
	
	@Override
	public int hashCode() {
		return elem.dynHash(exec);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Element other) {
			return elem.dynEqualTo(exec, other);
		}
		else if (obj instanceof ElementKey key) {
			return elem.dynEqualTo(exec, key.elem);
		}
		else {
			return false;
		}
	}
	
	@Override
	public @Nonnull String toString() {
		return elem.stringCast(exec).toString(exec);
	}
}
