package nc.dssl.interpret;

import nc.dssl.interpret.element.Element;

import javax.annotation.Nonnull;

public class Def {
	
	public final @Nonnull String identifier;
	public final @Nonnull Element elem;
	
	public Def(@Nonnull String identifier, @Nonnull Element elem) {
		this.identifier = identifier;
		this.elem = elem;
	}
}
