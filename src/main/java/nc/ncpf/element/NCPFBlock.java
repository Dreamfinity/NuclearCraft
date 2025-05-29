package nc.ncpf.element;

import java.util.*;

public class NCPFBlock extends NCPFElement {
	
	public String name;
	public final Map<String, Object> blockstate = new HashMap<>();
	public String nbt;
	
	public NCPFBlock() {
		super("block");
	}
}
