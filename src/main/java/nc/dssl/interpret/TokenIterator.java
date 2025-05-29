package nc.dssl.interpret;

import nc.dssl.DSSLHelpers;
import nc.dssl.node.*;

import javax.annotation.Nonnull;
import java.util.Iterator;

public abstract class TokenIterator implements Iterator<Token> {
	
	protected @Nonnull Token prev = new InvalidToken(""), curr = new InvalidToken("");
	protected boolean start = true, requireSeparator = false;
	
	public TokenIterator() {
	
	}
	
	@Override
	public boolean hasNext() {
		if (start) {
			onStart();
			start = false;
		}
		return validNext();
	}
	
	@Override
	public @Nonnull Token next() {
		boolean notSeparator = !DSSLHelpers.isSeparator(curr);
		if (requireSeparator && notSeparator) {
			throw new IllegalArgumentException(String.format("Encountered tokens \"%s\" and \"%s\" not separated by comment or whitespace!", prev.getText(), curr.getText()));
		}
		else {
			requireSeparator = notSeparator;
			prev = curr;
			curr = getNextChecked();
			return prev;
		}
	}
	
	public abstract void onStart();
	
	public abstract boolean validNext();
	
	protected abstract Token getNext();
	
	protected final @Nonnull Token getNextChecked() {
		Token next = getNext();
		if (next == null) {
			throw new IllegalArgumentException("Encountered null token!");
		}
		else {
			return next;
		}
	}
}
