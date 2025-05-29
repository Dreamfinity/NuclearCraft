package nc.dssl.interpret.element;

import nc.dssl.DSSLHelpers;
import nc.dssl.interpret.*;
import nc.dssl.node.Token;

import javax.annotation.Nonnull;
import java.util.*;

public class BlockElement extends Element implements Invokable {
	
	public final List<Token> tokens;
	
	public BlockElement(Interpreter interpreter, List<Token> tokens) {
		super(interpreter, interpreter.builtIn.blockClazz);
		this.tokens = tokens;
	}
	
	public TokenExecutor executor(TokenExecutor exec) {
		return new TokenExecutor(exec.interpreter.hooks.getBlockIterator(exec, this), exec, true);
	}
	
	@Override
	public @Nonnull TokenResult invoke(TokenExecutor exec) {
		return executor(exec).iterate();
	}
	
	@SuppressWarnings("null")
	@Override
	public @Nonnull Element clone() {
		List<Token> tokensClone = new ArrayList<>(tokens.size());
		for (@Nonnull Token token : tokens) {
			tokensClone.add((Token) token.clone());
		}
		return new BlockElement(interpreter, tokensClone);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(BuiltIn.BLOCK, tokens);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof BlockElement other) {
			return tokens.equals(other.tokens);
		}
		return false;
	}
	
	@Override
	public @Nonnull String toString(TokenExecutor exec) {
		return DSSLHelpers.tokenListToString(tokens);
	}
}
