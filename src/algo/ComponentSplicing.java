package algo;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

import graph.Component;
import graph.ComponentGraph;
import graph.ContractedGraph;
import graph.MetaNode;
import graph.Node;

public class ComponentSplicing {

	private ContractedGraph contracted;

	public ComponentSplicing(ContractedGraph contracted) {
		this.contracted = contracted;
	}
	
	public ComponentGraph basicSplicing () {
		ComponentGraph components = new ComponentGraph();
		
		Set<MetaNode> nodes = new HashSet<>();
		nodes.addAll(this.contracted.nodes.values());
		
		// Nodes creation
		while (!nodes.isEmpty()) {
			MetaNode current = nodes.iterator().next();
			Component component = new Component();
			
			// In case of hub
			if (current.arity() > 2) {
				nodes.remove(current);
				
				component.addNode(current);
				current.componentId = component.idx;
				component.hub = true;
				
				components.nodes.put(component.id, component);
				this.indexMetaNode(current, component, components);
				continue;
			}
			
			// Otherwise
			component.hub = false;
			Queue<MetaNode> queue = new LinkedList<>();
			queue.add(current);
			while (!queue.isEmpty()) {
				current = queue.poll();
				nodes.remove(current);
				component.addNode(current);
				this.indexMetaNode(current, component, components);
				current.componentId = component.idx;
				
				for (Node nei : current.neighbors) {
					if (nei.arity() <= 2 &&  nodes.contains(nei))
						queue.add((MetaNode) nei);
				}
			}
			components.nodes.put(component.id, component);
		}
		
		return components;
	}
	
	public int enlargeComponents (ComponentGraph cg) {
		int nodesTransfered = 0;
		
		// Analysis each component that is a hub
		for (MetaNode cmn : cg.nodes.values()) {
			Component cont = (Component)cmn;
			if (!cont.hub)
				continue;
		
			// Get the only meta-node in the hub
			MetaNode mn = (MetaNode) cont.nodes.iterator().next();
			
			/* Find reads in the meta-node with only one meta-node neighbor.
			 * Transfer theses nodes to the corresponding meta-node */
			Set<Node> nodes = new HashSet<Node>();
			nodes.addAll(mn.nodes);
			for (Node node : nodes) {
				MetaNode extMn = null;
				for (Node nei : node.neighbors) {
					MetaNode neiMn = cg.contractedIndex.get(nei);
					// TODO : Look why there are null neighbors
					if (neiMn == null)
						continue;
					
					if (!neiMn.equals(mn)) {
						if (extMn == null)
							extMn = neiMn;
						else {
							extMn = null;
							break;
						}
					}
				}
				
				// If extMn != null then extMn is the only neighbor of the node
				if (extMn == null)
					continue;
				
				nodesTransfered += 1;
				extMn.nodes.add(node);
				mn.nodes.remove(node);
				this.indexMetaNode(extMn, cg.componentIndex.get(extMn), cg);
			}
		}
		
		return nodesTransfered;
	}
	
	private void indexMetaNode (MetaNode node, Component component, ComponentGraph graph) {
		graph.componentIndex.put(node, component);
		
		for (Node n : node.nodes)
			graph.contractedIndex.put(n, node);
	}
	
}
