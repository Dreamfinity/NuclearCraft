package nc.dssl.interpret;

import javax.annotation.Nonnull;

public class Macro {
	
	public final @Nonnull String identifier;
	public final @Nonnull Invokable invokable;
	
	public Macro(@Nonnull String identifier, @Nonnull Invokable invokable) {
		this.identifier = identifier;
		this.invokable = invokable;
	}
}
