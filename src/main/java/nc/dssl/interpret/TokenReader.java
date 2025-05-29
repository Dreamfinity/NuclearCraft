package nc.dssl.interpret;

import nc.dssl.node.Token;

import javax.annotation.Nonnull;

public abstract class TokenReader {
	
	public final Interpreter interpreter;
	protected final TokenIterator iterator;
	protected final TokenReader prev;
	
	protected TokenReader(Interpreter interpreter, TokenIterator iterator) {
		this.interpreter = interpreter;
		this.iterator = iterator;
		this.prev = null;
	}
	
	protected TokenReader(TokenIterator iterator, TokenReader prev) {
		interpreter = prev.interpreter;
		this.iterator = iterator;
		this.prev = prev;
	}
	
	public @Nonnull TokenResult iterate() {
		while (iterator.hasNext()) {
			@Nonnull TokenResult readResult = read(iterator.next());
			if (readResult == TokenResult.PASS) {
				continue;
			}
			return readResult;
		}
		return TokenResult.PASS;
	}
	
	protected abstract @Nonnull TokenResult read(@Nonnull Token token);
}
