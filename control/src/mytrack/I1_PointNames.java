package mytrack;

public class I1_PointNames {
	
	private String nodeName;
	private String nodeDirection;
	private String curve;
	private String straight;
	private String nodeNumber;
	private String pointNumber;
	
	//private I3_TPoint_No point;
	
	public I1_PointNames(String nodeName, String nodeDirection, String curve, String straight, String nodeNumber, String pointNumber) {
		
		this.nodeName = nodeName;
		this.nodeDirection = nodeDirection;
		this.curve = curve;			
		this.setStraight(straight);
		this.pointNumber = pointNumber;
		this.nodeNumber = nodeNumber;
	
		//this.point = new I3_TPoint_No(nodeName, nodeDirection, curve, pointNumber);

	}
	
	private String PointName;

	public String getNodeName() {
		return nodeName;
	}
	public String getNodeDirection() {
		return nodeDirection;
	}
	public String getCurve() {
		return curve;
	}
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	public void setNodeDirection(String nodeDirection) {
		this.nodeDirection = nodeDirection;
	}
	public void setCurve(String curve) {
		this.curve = curve;
	}
	public String getPointNumber() {
		return pointNumber;
	}
	public String getNodeNumber() {
		return nodeNumber;
	}
//	public I3_TPoint_No getPoint() {
//		return point;
//	}
	public String getPointName() {
		return PointName;
	}
	/**
	 * @return the straight
	 */
	public String getStraight() {
		return straight;
	}
	/**
	 * @param straight the straight to set
	 */
	public void setStraight(String straight) {
		this.straight = straight;
	}


}
