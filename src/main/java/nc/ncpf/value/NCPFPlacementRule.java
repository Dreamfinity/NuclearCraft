package nc.ncpf.value;

import nc.ncpf.element.NCPFElement;

import java.util.*;

public class NCPFPlacementRule {
	
	public NCPFPlacementRuleType type;
	public NCPFElement block;
	public int min;
	public int max;
	public final List<NCPFPlacementRule> rules = new ArrayList<>();
}
