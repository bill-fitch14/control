package mytrack;

import java.awt.Color;
import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;

import javax.media.j3d.BranchGroup;

import com.ajexperience.utils.DeepCopyException;
import com.ajexperience.utils.DeepCopyUtil;

/**
 * @author bill
 * 
 * 
 */
public class M62_train {

	private String trainCoupling;
	// train information
	private String trainStr;
	private int trainNo;
	private Color engineColor;
	private String strEngineColor;
	private int numberEngines;
	private float engineLength;
	private int numberTrucks;
	private float truckLength;
	private Integer[] truckNos;

	private List<M43_TruckData_Display> truckPositions;
	/*
	 * types in M43_TruckData_Display
	 * float objectLength;	
	 * String objectStr; 
	 * String objectText; 
	 * String objectType;
	 * 
	 */
	

	// private List<L3_TruckEngineObject_BG> truck_BG;
	private float[] lengths;
	private String[] truckType = new String[20];
	float[] distancesFromHeadToMidpoint;
	float[] distancesFromHeadToCoupling;

	private BranchGroup trainBranchGroup = new BranchGroup();
	private double trainSpeed = U4_Constants.simSpeedSetting;

	public M62_train(String trainStr, int trainNo, String strEngineColor,
			int numberEngines, float engineLength, int numberTrucks,
			float truckLength, Integer[] truckNos, String trainCoupling2) {
		this.trainStr = trainStr;
		this.trainNo = trainNo;
		this.strEngineColor = strEngineColor;
		this.engineLength = engineLength;
		this.numberEngines = numberEngines;
		this.numberTrucks = numberTrucks;
		this.truckLength = truckLength;
		this.truckNos = truckNos;
		this.trainCoupling = trainCoupling2;

		this.engineColor = getColor(strEngineColor);

		// set the lengths of each truck
		lengths = setLengthsOfTrain();

		// set the truck types

		setTruckTypes(numberEngines,numberTrucks);

		// set the distances from the engine
		distancesFromHeadToMidpoint = distancesFromHeadToMidpointOfTruck();
		distancesFromHeadToCoupling = distancesFromHeadToCoupling();

		// setTrainDisplay();

	}
	


	public void setTrainVariablesFromTruckPositions(){
		
		/*
		 * types in M43_TruckData_Display
		 * float objectLength;	
		 * String objectStr; 
		 * String objectText; 
		 * String objectType;
		 * 
		 */
		
		//99System.out.print("before" +this.toString());
		numberEngines = 0;
		numberTrucks = 0;
		for(M43_TruckData_Display tp:truckPositions){
			
			if(tp.objectType.equals("Engine")){
				numberEngines++;
				engineLength = tp.objectLength;
			}else if(tp.objectType.equals("Truck")){
				numberTrucks++;
				truckLength=tp.objectLength;
			}
//			private Color engineColor;
//			private String strEngineColor;
//			//private int numberEngines;
//			//private float engineLength;
//			//private int numberTrucks;
//			//private float truckLength;
//			private Integer[] truckNos;
		}
		//99System.out.print("after"+this.toString());
	}

	private Color getColor(String strColor) {
		// using reflection to get the color from a string representation of the
		// color
		Color color;
		try {
			Field field = Class.forName("java.awt.Color").getField(strColor);
			color = (Color) field.get(null);
		} catch (Exception e) {
			color = null; // Not defined}
		}
		return color;
	}

	// void setTrainDisplay(){
	//
	// //set first item display
	// M43_TruckData_Display truckData_Display = new M43_TruckData_Display(arc,
	// directionFacing, graph, startFraction);
	// //set rest of items display
	// set_truck_locations(truckData_Display);
	// }
	//

	public void checkforstop(M43_TruckData_Display stop, int truckNo) {

	}

	public void reset_truck_locations(int referenceTruckNo) {

		this.numberTrucks = this.getTruckPositions().size() - 1;
		lengths = setLengthsOfTrain();
		setTruckTypes(numberEngines, numberTrucks);
		distancesFromHeadToMidpoint = distancesFromHeadToMidpointOfTruck();

		// for all the items in the train
		for (int truckEngineNo = 0; truckEngineNo < this.getNumberEngines()
				+ this.getNumberTrucks(); truckEngineNo++) {
			// for (int truckEngineNo = 0; truckEngineNo < 2; truckEngineNo++){
			M43_TruckData_Display temp = getTruckPositions().get(truckEngineNo);
			temp.setvaluesfromEngine(referenceTruckNo,
					getTruckPositions().get(0));
			temp.setKeyValues(referenceTruckNo,
					getTruckPositions().get(referenceTruckNo));			
			if (temp.getOrientation().equals("same")) {
				if (this.getTrainCoupling().equals("tail")) {
					temp.positionTrucks(-(distancesFromHeadToMidpoint[truckEngineNo] - 0.5f*U4_Constants.enginelength*U4_Constants.scalefactor));
				} else {
					temp.positionTrucks(distancesFromHeadToMidpoint[truckEngineNo] - 0.5f*U4_Constants.enginelength*U4_Constants.scalefactor);
				}
			} else {
				if (this.getTrainCoupling().equals("tail")) {
					temp.positionTrucks((distancesFromHeadToMidpoint[truckEngineNo] - 0.5f*U4_Constants.enginelength*U4_Constants.scalefactor));
				} else {
					temp.positionTrucks(-(distancesFromHeadToMidpoint[truckEngineNo] - 0.5f*U4_Constants.enginelength*U4_Constants.scalefactor));
				}
			}

			// temp.positionTrucks(distancesFromGivenTruck(referenceTruckNo)[truckEngineNo]);
			temp.setPositionTangent();
			temp.updatePositionAndTangent(temp.getPosition(), temp.getTangent());
//			if (truckEngineNo == 0) {
//				temp.reset_BG(engineLength,
//						String.valueOf(truckNos[truckEngineNo]),
//						String.valueOf(truckNos[truckEngineNo]),
//						truckType[truckEngineNo], true);
//			} else {
//				temp.reset_BG(truckLength,
//						String.valueOf(truckNos[truckEngineNo]),
//						String.valueOf(truckNos[truckEngineNo]),
//						truckType[truckEngineNo], false);
//			}
		}
	}

	public void set_truck_locations(M43_TruckData_Display truckData_Display,
			K2_Route route, String arc, String directionFacing,
			D_MyGraph graph, String startFraction, String trainCoupling) {

		String startFractionCopy = startFraction;
		// set the lengths of each truck
		lengths = setLengthsOfTrain();

		// set the truck types
		setTruckTypes(numberEngines, numberTrucks);
		//99System.out.print(truckType[0]);
		int dir = setdirection(truckData_Display);

		// set the distances from the engine
		float[] distanceFromHead = distancesFromHeadToMidpointOfTruck();

		truckPositions = new LinkedList<M43_TruckData_Display>();
		// truck_BG = new LinkedList<L3_TruckEngineObject_BG>();
		//99System.out.print(truckType[0]);
		// for all the items in the train
		for (int truckEngineNo = 0; truckEngineNo < this.getNumberEngines()
				+ this.getNumberTrucks(); truckEngineNo++) {
			// if(truckEngineNo!=1 ){
			//99System.out.print(truckType[truckEngineNo]);
			String objectType = truckType[truckEngineNo];

			M43_TruckData_Display m43_Data = null;
			// if(truckEngineNo!=0){
			// m43_Data = (M43_TruckData_Display)
			// getheadOfTrainCopy(truckData_Display);
			startFraction = startFractionCopy;
			//99System.out.print(startFractionCopy);
			m43_Data = new M43_TruckData_Display(arc, directionFacing, graph,
					startFraction, trainCoupling);
			m43_Data.setArcPairList(route);
			// } else{
			// m43_Data = truckData_Display;
			// }

			String objectType1 = truckType[truckEngineNo];
			m43_Data.setObjectType(objectType1);

			////99//99System.out.print(objectType1);

			// truckPositions.add(m43_Data);
			// get the position of the truck or engine (seg and segment
			// fraction)
			//99System.out.print("distance from head " + truckEngineNo + "= "
			//99		+ dir * distanceFromHead[truckEngineNo]);

			m43_Data.moveWithinSegment3(distanceFromHead[truckEngineNo],trainCoupling);
			m43_Data.positionTrucks(distancesFromGivenTruck(0)[truckEngineNo]);

			truckPositions.add(m43_Data);

			// set position and tangent
			m43_Data.setPositionTangent();

			// set BG
			if (truckEngineNo == 0) {
				m43_Data.set_BG(engineLength,
						String.valueOf(truckNos[truckEngineNo]),
						String.valueOf(truckNos[truckEngineNo]),
						truckType[truckEngineNo]);
			} else {
				m43_Data.set_BG(truckLength,
						String.valueOf(truckNos[truckEngineNo]),
						String.valueOf(truckNos[truckEngineNo]),
						truckType[truckEngineNo]);
			}

			trainBranchGroup.addChild(m43_Data.get_BG());
			// }
			// //create a BG object and set the position and tangent
			// L3_TruckEngineObject_BG tl1 = new
			// L3_TruckEngineObject_BG(m43_Data.segment,
			// m43_Data.segmentFraction,
			// truckType[truckEngineNo], lengths[truckEngineNo],
			// Integer.toString(truckEngineNo));
			//
			// truck_BG.add(tl1);
			// m43_Data = null;
			// tl1 = null;
			// }
		}

	}

	/**
	 * @param tData
	 * @return
	 */
	private int setdirection(M41_TruckData_Position tData) {
		int dir;
		if (tData.getOrientation().equals("same")) {
			dir = -1;
		} else {
			dir = +1;
		}
		return dir;
	}

	/**
	 * @return
	 */
	private float[] distancesFromHeadToMidpointOfTruck() {
		float[] distanceFromHead = new float[20];
		for (int i = 0; i < lengths.length; i++) {
			for (int j = 0; j <= i; j++) {
				if (j == 0) {
					distanceFromHead[j] = 0 + 0.5f * lengths[j];
					// just add 1/2 length of engine
				} else {
					distanceFromHead[j] = distanceFromHead[j - 1] + 0.5f
							* lengths[j - 1] + 0.5f * lengths[j];
					// add 1/2 length of prev truck or engine + 0.5 length of
					// current truck
				}
			}
		}
		return distanceFromHead;
	}
	
	private float[] distancesFromHeadToCoupling() {
		float[] distanceFromHead = new float[20];
		for (int i = 0; i < lengths.length; i++) {
			for (int j = 0; j <= i; j++) {
				if (j == 0) {
					distanceFromHead[j] = 0 + lengths[j];
					// just add length of engine
				} else {
					distanceFromHead[j] = distanceFromHead[j - 1]  + lengths[j];
					// add length of truck 
				}
			}
		}
		return distanceFromHead;
	}

	private float[] distancesFromGivenTruck(int truckNo) {
		float[] distancesFromGivenTruck = new float[20];
		float distanceFromHeadToCouplingOfGivenTruck1 = 0;
		float[] distanceFromHeadToCouplingOfGivenTruck = new float[20];
		for (int i = 0; i < truckNo; i++) {
			distanceFromHeadToCouplingOfGivenTruck1 += lengths[i];
			distanceFromHeadToCouplingOfGivenTruck[i] = distanceFromHeadToCouplingOfGivenTruck1;
		}
		for (int i = 0; i < truckNo; i++) {
			distancesFromGivenTruck[i] = distancesFromHeadToMidpointOfTruck()[i]
					- distanceFromHeadToCouplingOfGivenTruck[i];
		}
		return distancesFromGivenTruck;
	}

	private void setTruckTypes(int numberEngines2, int numberTrucks2) {
		String[] truckType = new String[20];

		for (int i = 0; i<numberEngines2;i++ ){

				truckType[i] = "Engine";

		}
			
		for (int i = numberEngines2; i < lengths.length; i++) {

				truckType[i] = "Truck";
				//this.truckPositions.get(i+1).objectType="Truck";

		}
		// if(this.trainStr == "T0"){
		
		//this.truckPositions.get(1).objectType="Truck";
		// }
		this.truckType = truckType;

		// return truckType;
	}

	private float[] setLengthsOfTrain() {
		float[] lengths = new float[(int) (this.getNumberTrucks() + 1)];
		for (int i = 0; i < lengths.length; i++) {
			lengths[i] = this.getTruckLength();
		}
		lengths[0] = this.getEngineLength();
		return lengths;
	}

	public Object getheadOfTrainCopy(Object headOfTrainData) {

		try {
			DeepCopyUtil deepCopyUtil = new DeepCopyUtil();
			return deepCopyUtil.deepCopy(headOfTrainData);
		} catch (DeepCopyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// hopefully never used
		return headOfTrainData;
	}

	/**
	 * @return the trainStr
	 */
	public String getTrainStr() {
		return trainStr;
	}

	/**
	 * @param trainStr
	 *            the trainStr to set
	 */
	public void setTrainStr(String trainStr) {
		this.trainStr = trainStr;
	}

	/**
	 * @return the trainNo
	 */
	public int getTrainNo() {
		return trainNo;
	}

	/**
	 * @param trainNo
	 *            the trainNo to set
	 */
	public void setTrainNo(int trainNo) {
		this.trainNo = trainNo;
	}

	/**
	 * @return the engineColor
	 */
	public Color getEngineColor() {
		return engineColor;
	}

	/**
	 * @param engineColor
	 *            the engineColor to set
	 */
	public void setEngineColor(Color engineColor) {
		this.engineColor = engineColor;
	}

	/**
	 * @return the engineLength
	 */
	public float getEngineLength() {
		return engineLength;
	}

	/**
	 * @param engineLength
	 *            the engineLength to set
	 */
	public void setEngineLength(float engineLength) {
		this.engineLength = engineLength;
	}

	/**
	 * @return the numberTrucks
	 */
	public int getNumberTrucks() {
		return numberTrucks;
	}

	public int getNumberTrucks2() {
		int numberTrucks = truckPositions.size() - 1;
		return numberTrucks;
	}

	/**
	 * @param numberTrucks
	 *            the numberTrucks to set
	 */
	public void setNumberTrucks(int numberTrucks) {
		this.numberTrucks = numberTrucks;
	}

	/**
	 * @return the truckLength
	 */
	public float getTruckLength() {
		return truckLength;
	}

	/**
	 * @param truckLength
	 *            the truckLength to set
	 */
	public void setTruckLength(float truckLength) {
		this.truckLength = truckLength;
	}

	/**
	 * @return the truckNames
	 */
	public Integer[] getTruckNames() {
		return truckNos;
	}

	/**
	 * @param truckNames
	 *            the truckNames to set
	 */
	public void setTruckNames(Integer[] truckNames) {
		this.truckNos = truckNames;
	}

	public M43_TruckData_Display getTruckData(int i) {
		return truckPositions.get(i);
	}

	public int getNumberEngines() {
		return numberEngines;
	}

	public void setNumberEngines(int numberEngines) {
		this.numberEngines = numberEngines;
	}

	public BranchGroup getTrainBranchGroup() {
		return trainBranchGroup;
	}

	public List<M43_TruckData_Display> getTruckPositions() {
		return truckPositions;
	}

	/**
	 * @return the trainSpeed
	 */
	public double getTrainSpeed() {
		return trainSpeed;
	}

	/**
	 * @param trainSpeed
	 *            the trainSpeed to set
	 */
	public void setTrainSpeed(double trainSpeed) {
		this.trainSpeed = trainSpeed;
	}

	public String getTrainCoupling() {
		return trainCoupling;
	}

	public void setTrainCoupling(String trainCoupling) {
		this.trainCoupling = trainCoupling;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("M62_train [trainStr=");
		builder.append(trainStr);
		builder.append(", trainNo=");
		builder.append(trainNo);
		builder.append(", numberEngines=");
		builder.append(numberEngines);
		builder.append(", engineLength=");
		builder.append(engineLength);
		builder.append(", numberTrucks=");
		builder.append(numberTrucks);
		builder.append(", truckNos=");
		builder.append(truckNos);
		builder.append(", truckType=");
		builder.append(truckType);
		builder.append(", distancesFromHead=");
		builder.append(distancesFromHeadToMidpoint);
		builder.append("]");
		return builder.toString();
	}

	public void setTruckPositions(List<M43_TruckData_Display> truckPositions) {
		this.truckPositions = truckPositions;
	}

	public void setParametersfromTruckArray() {
		
		this.numberEngines = numberEngines;
		this.numberTrucks = numberTrucks;
	}
}
