package nc.ncpf.element;

import java.util.*;

public class NCPFBlockTag extends NCPFElement {
	
	public String name;
	public final Map<String, Object> blockstate = new HashMap<>();
	public String nbt;
	
	public NCPFBlockTag() {
		super("block_tag");
	}
}
