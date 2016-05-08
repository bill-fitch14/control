package mytrack;

public class E1_TNodeNames {

	private String Reverse = null;
	private String Station = null;
	private String Crossing = null;
	private String NodeName = null;
	private String ReversingAllowed = null;
	private String Node = null;

	public E1_TNodeNames(String nodeName, String reverse, String station,
			String crossing,String reversingAllowed, String node) {
		NodeName = nodeName;
		Reverse = reverse;
		Station = station;
		Crossing = crossing;
		ReversingAllowed = reversingAllowed;
		Node  = node;
	}

	public E1_TNodeNames(String nodeName, String reverse) {
		NodeName = nodeName;
		Reverse = reverse;
	}

	public E1_TNodeNames() {
		// TODO Auto-generated constructor stub
	}

	public String getNodeName() {
		return NodeName;
	}

	public String getReverse() {
		return Reverse;
	}

	public String getStation() {
		return Station;
	}

	public String getCrossing() {
		return Crossing;
	}
	
	public String getDisplayNode() {
		return Node;
	}
	
	public String getReversingAllowed() {
		return ReversingAllowed;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TNodeNames [getNodeName()=");
		builder.append(getNodeName());
		builder.append(", getReverse()=");
		builder.append(getReverse());
		builder.append(", getStation()=");
		builder.append(getStation());
		builder.append(", getCrossing()=");
		builder.append(getCrossing());
		builder.append(", getReversingAllowed()=");
		builder.append(getReversingAllowed());
		builder.append(", getNode()=");
		builder.append(getDisplayNode());
		builder.append("]");
		return builder.toString();
	}


}
