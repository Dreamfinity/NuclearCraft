package nc.dssl.interpret;

import nc.dssl.interpret.token.BlockToken;
import nc.dssl.node.*;

import javax.annotation.Nonnull;
import java.util.*;

public class TokenCollector extends TokenReader {
	
	protected final Deque<List<Token>> listStack = new ArrayDeque<>();
	
	protected TokenCollector(Interpreter interpreter, TokenIterator iterator) {
		super(interpreter, iterator);
		listStack.push(new ArrayList<>());
	}
	
	@Override
	protected @Nonnull TokenResult read(@Nonnull Token token) {
		if (token instanceof TLBrace) {
			listStack.push(new ArrayList<>());
		}
		else if (token instanceof TRBrace) {
			int stackSize = listStack.size();
			if (stackSize < 1) {
				throw new IllegalArgumentException("Encountered unexpected \"}\" token!");
			}
			else if (stackSize == 1) {
				return TokenResult.BREAK;
			}
			else {
				BlockToken block = new BlockToken(listStack.pop());
				listStack.peek().add(block);
			}
		}
		else {
			listStack.peek().add(token);
		}
		return TokenResult.PASS;
	}
}
