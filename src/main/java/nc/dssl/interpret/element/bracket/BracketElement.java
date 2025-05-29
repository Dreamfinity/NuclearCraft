package nc.dssl.interpret.element.bracket;

import nc.dssl.interpret.Interpreter;
import nc.dssl.interpret.element.Element;

public abstract class BracketElement extends Element {
	
	protected BracketElement(Interpreter interpreter) {
		super(interpreter, interpreter.builtIn.bracketClazz);
	}
	
}
