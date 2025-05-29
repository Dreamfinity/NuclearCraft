package nc.dssl.interpret;

import nc.dssl.DSSLHelpers;
import nc.dssl.lexer.Lexer;
import nc.dssl.node.*;

import javax.annotation.Nonnull;
import java.io.PushbackReader;

public class LexerIterator extends TokenIterator {
	
	protected final Lexer lexer;
	
	public LexerIterator(@Nonnull String str) {
		this(DSSLHelpers.stringLexer(str));
	}
	
	public LexerIterator(PushbackReader reader) {
		this(new Lexer(reader));
	}
	
	public LexerIterator(Lexer lexer) {
		super();
		this.lexer = lexer;
	}
	
	@Override
	public void onStart() {
		curr = getNextChecked();
	}
	
	@Override
	public boolean validNext() {
		return !(curr instanceof EOF);
	}
	
	@Override
	protected Token getNext() {
		return DSSLHelpers.getLexerNext(lexer);
	}
}
