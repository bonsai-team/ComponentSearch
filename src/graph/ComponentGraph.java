package graph;

import java.util.HashMap;
import java.util.Map;

public class ComponentGraph extends ContractedGraph {

	public Map<MetaNode, Component> componentIndex;
	public Map<Node, MetaNode> contractedIndex;
	
	public ComponentGraph() {
		super();
		this.componentIndex = new HashMap<>();
		this.contractedIndex = new HashMap<>();
	}
	
}
