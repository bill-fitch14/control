package Utilities;

import javax.vecmath.Vector3d;

//import com.ajexperience.utils.DeepCopyUtil;

public final class Util
{
   public static final boolean DEBUG = true;
   public static final boolean PRINT = true;
   
   public final static double anglebetween( Vector3d vector1, Vector3d vector2){
//   	// get absolute angle between Y projected and Up 
//   	double absAngle = vector1.angle(vector2);  
//   	// magic formula 
//   	Vector3d cross = new Vector3d();
//   	cross.cross(vector1, vector2); 
//   	double dot = DotProduct(lookDirection, cross);  
//   	// set actual signed angle 
//   	return (dot >= 0) ?  absAngle : -absAngle;
   	return (float)Math.atan2(vector2.y, vector2.x) - (float)Math.atan2(vector1.y, vector1.x);
   }



}
