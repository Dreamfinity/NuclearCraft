package nc.dssl.interpret.element.bracket;

import nc.dssl.interpret.*;
import nc.dssl.interpret.element.Element;
import nc.dssl.interpret.element.primitive.BoolElement;

import javax.annotation.Nonnull;
import java.util.Objects;

public class RangeLBracketElement extends LBracketElement {
	
	public RangeLBracketElement(Interpreter interpreter) {
		super(interpreter);
	}
	
	@Override
	public @Nonnull TokenResult onEqualTo(TokenExecutor exec, @Nonnull Element other) {
		exec.push(new BoolElement(interpreter, interpreter.builtIn.rangeLBracketElement.equals(other)));
		return TokenResult.PASS;
	}
	
	@Override
	public @Nonnull TokenResult onNotEqualTo(TokenExecutor exec, @Nonnull Element other) {
		exec.push(new BoolElement(interpreter, !interpreter.builtIn.rangeLBracketElement.equals(other)));
		return TokenResult.PASS;
	}
	
	@Override
	public @Nonnull TokenResult __eq__(TokenExecutor exec, @Nonnull Element other) {
		return onEqualTo(exec, other);
	}
	
	@Override
	public @Nonnull TokenResult __ne__(TokenExecutor exec, @Nonnull Element other) {
		return onNotEqualTo(exec, other);
	}
	
	@Override
	public @Nonnull Element clone() {
		return interpreter.builtIn.rangeLBracketElement;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash("(");
	}
	
	@Override
	@SuppressWarnings("all")
	public boolean equals(Object obj) {
		return obj == interpreter.builtIn.rangeLBracketElement;
	}
	
	@Override
	public @Nonnull String toString(TokenExecutor exec) {
		return "(";
	}
}
