package nc.ncpf.element.legacy;

import nc.ncpf.element.NCPFElement;

public class NCPFLegacyItem extends NCPFElement {
	
	public String name;
	public Integer metadata;
	public String nbt;
	
	public NCPFLegacyItem() {
		super("legacy_item");
	}
}
