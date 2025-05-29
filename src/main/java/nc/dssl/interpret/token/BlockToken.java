package nc.dssl.interpret.token;

import nc.dssl.DSSLHelpers;
import nc.dssl.node.*;

import javax.annotation.Nonnull;
import java.util.*;

public class BlockToken extends Token {
	
	public final List<Token> tokens;
	
	public BlockToken(List<Token> tokens) {
		this.tokens = tokens;
		if (!tokens.isEmpty()) {
			@SuppressWarnings("null") Token first = tokens.get(0);
			setLine(first.getLine());
			setPos(first.getPos());
		}
	}
	
	@Override
	public void apply(Switch sw) {
		for (Token token : tokens) {
			token.apply(sw);
		}
	}
	
	@SuppressWarnings("null")
	@Override
	public Object clone() {
		List<Token> tokensClone = new ArrayList<>();
		for (@Nonnull Token token : tokens) {
			tokensClone.add((Token) token.clone());
		}
		return new BlockToken(tokensClone);
	}
	
	@Override
	public @Nonnull String toString() {
		return DSSLHelpers.tokenListToString(tokens);
	}
}
