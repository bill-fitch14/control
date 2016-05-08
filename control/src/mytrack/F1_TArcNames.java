package mytrack;


public class F1_TArcNames {

	String nodeFromName = null;				//N1 or N1reverse , i.e. can be a reverse node, i.e the trains can change direction
	String nodeFromDir = null;
	String nodeToName = null;				//F or B for forward or back (was 1 or -1)
	String nodeToDir = null;
	String[] segElements = null;
	
	private String stopPoints = "1";		// of the form 1,5,10
	
	F3_TArc tArc = null;
		
	public F1_TArcNames() {
	}

	public F1_TArcNames(E1_TNodeNames nodeFrom, E1_TNodeNames nodeTo, String nodeFromDir,
			String nodeToDir, String[] segElements) {
		this.nodeFromName = nodeFrom.getNodeName();
		this.nodeToName = nodeTo.getNodeName();
		this.nodeFromDir = nodeFromDir;
		this.nodeToDir = nodeToDir;
		this.segElements = segElements;
	}
	
	public void set_TArcNames(String nodeFromName,  String nodeFromDir, String nodeToName,
			String nodeToDir, String[] segElements) {
		this.nodeFromName = nodeFromName;
		this.nodeToName = nodeToName;
		this.nodeFromDir = nodeFromDir;
		this.nodeToDir = nodeToDir;
		this.segElements = segElements;
	}
	
	public void set_StopPoints(String nodeFromName, String nodeFromDir,
			String nodeToName, String nodeToDir, String[] segElements, String stopPoints) {
		this.nodeFromName = nodeFromName;
		this.nodeToName = nodeToName;
		this.nodeFromDir = nodeFromDir;
		this.nodeToDir = nodeToDir;
		this.segElements = segElements;
		this.stopPoints = stopPoints;
	}
	
	public String get_items_as_String() {
		StringBuilder builder = new StringBuilder();
		builder.append(nodeFromName);
		builder.append(", ");
		builder.append(nodeFromDir);
		builder.append(", ");
		builder.append(nodeToName);
		builder.append(", ");
		builder.append(nodeToDir);
		builder.append(", ");
		builder.append(segElements);
		builder.append("]");
		return builder.toString();
	}



	String get_taStr(){
		//<param name="TAStr">of the form N11N2reverse-1</param>
		//changed to N1FN2reverseB

		String taStr = nodeFromName.concat("").concat(nodeFromDir).concat("").
						concat(nodeFromName).concat("").concat(nodeFromDir);
		return taStr;
	}

	public void setnodeToDir(String nodeToDir) {
		this.nodeToDir = nodeToDir;
	}

	public void setsegElements(String[] segElements) {
		this.segElements = segElements;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TArcNames [nodeFrom=");
		builder.append(nodeFromName);
		builder.append(", nodeFromDir=");
		builder.append(nodeFromDir);
		builder.append(", nodeTo=");
		builder.append(nodeToName);
		builder.append(", nodeToDir=");
		builder.append(nodeToDir);
		builder.append(", segElements=");
		for (String S: segElements){
			builder.append(S + " " );
		}
		builder.append("length " + segElements.length + " ");
		builder.append("]");
		
		return builder.toString();
	}

	public String getNodeFromName() {
		return nodeFromName;
	}

	public String getNodeFromDir() {
		return nodeFromDir;
	}

	public String getNodeToName() {
		return nodeToName;
	}

	public String getNodeToDir() {
		return nodeToDir;
	}

	public String[] getSegElements() {
		return segElements;
	}

	public void setNodeFromName(String nodeFromName) {
		this.nodeFromName = nodeFromName;
	}

	public void setNodeFromDir(String nodeFromDir) {
		this.nodeFromDir = nodeFromDir;
	}

	public void setNodeToName(String nodeToName) {
		this.nodeToName = nodeToName;
	}

	public void setNodeToDir(String nodeToDir) {
		this.nodeToDir = nodeToDir;
	}

	public void setSegElements(String[] segElements) {
		this.segElements = segElements;
	}

	public int getNodeToDirection() {
		return Nodedir(this.nodeToDir);
	}

	public int getNodeFromDirection() {
		return Nodedir(this.nodeFromDir);
	}	
	int Nodedir(String ND){
		if( ND.equals("F")){
			return 1;
		}
		else
		{
			return -1;
		}
	}

	public String getStopPoints() {
		return stopPoints;
	}

	public F3_TArc gettArc() {
		return tArc;
	}

	public void settArc(F3_TArc tArc) {
		this.tArc = tArc;
	}


}
