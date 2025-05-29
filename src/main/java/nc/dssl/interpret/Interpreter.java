package nc.dssl.interpret;

import nc.dssl.interpret.element.Element;
import nc.dssl.lexer.Lexer;

import java.util.*;

public class Interpreter {
	
	public final List<String> args;
	
	public final BuiltIn builtIn;
	
	protected final TokenExecutor root;
	protected boolean halt = false;
	
	protected final Deque<Element> stack = new ArrayDeque<>();
	protected final List<String> printList = new ArrayList<>();
	
	public final Hooks hooks;
	public final boolean debug;
	
	public Interpreter(List<String> args, Hooks hooks, Lexer lexer, boolean debug) {
		this(args, hooks, new LexerIterator(lexer), debug);
	}
	
	public Interpreter(List<String> args, Hooks hooks, TokenIterator iterator, boolean debug) {
		this.args = args;
		builtIn = new BuiltIn(this);
		this.hooks = hooks;
		root = newExecutor(iterator);
		this.debug = debug;
	}
	
	public TokenExecutor newExecutor(TokenIterator iterator) {
		return new TokenExecutor(this, iterator);
	}
	
	public TokenResult run() {
		return root.iterate();
	}
}
