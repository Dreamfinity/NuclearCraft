package nc.ncpf.element.legacy;

import nc.ncpf.element.NCPFElement;

import java.util.*;

public class NCPFLegacyBlock extends NCPFElement {
	
	public String name;
	public Integer metadata;
	public final Map<String, Object> blockstate = new HashMap<>();
	public String nbt;
	
	public NCPFLegacyBlock() {
		super("legacy_block");
	}
}
