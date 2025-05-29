package nc.ncpf.element;

import java.util.*;

public class NCPFListElement extends NCPFElement {
	
	public final List<NCPFElement> elements = new ArrayList<>();
	
	public NCPFListElement() {
		super("list");
	}
}
