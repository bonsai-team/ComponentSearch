package graph;

public class Component extends MetaNode {
	
	private static int nextIdx = 1;
	
	public int idx;
	public boolean hub;

	public Component() {
		super();
		this.idx = nextIdx++;
	}
	
	public Component(MetaNode node) {
		super(node);
		this.idx = nextIdx++;
	}

}
