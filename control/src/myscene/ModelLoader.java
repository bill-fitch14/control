package myscene;
// ModelLoader.java
// Andrew Davison, September 2006, ad@fivedots.coe.psu.ac.th

/* The ModelLoader getModel() method expects a OBJ filename, for
   files in the Models/ subdirectory, and an optional y-axis 
   translation. The method returns a TransformGroup which can
   be used with other TGs to further position, rotate, or scale
   the model.

   List of models with their optional y-axis offsets:
     penguin.obj, sword.obj 1, humanoid.obj 0.8
     hand.obj 1, barbell.obj 0.25, longBox.obj, cone.obj 0.4
     heli.obj 0, matCube.obj 0.5, cubeSphere.obj 0.9

   The loaded model is scaled so it doesn't exceed MAX_SIZE units
   along any axis. This model's size is obtained from its bounding
   box.
 */


import java.io.*;
import java.net.*;

import javax.media.j3d.*;
import javax.vecmath.*;
import com.sun.j3d.loaders.*;
import com.sun.j3d.loaders.objectfile.*;



public class ModelLoader
{

	private static boolean DEBUG = false;
	private static void print(String x){
		if (DEBUG ){
			System.out.print(x);
		}
	}
	private static final String MODELS_DIR = "Models/";

	private static final double MAX_SIZE = 1.0;   
	// max size of model along any dimension

	private ObjectFile objFileloader;


	public ModelLoader()
	{  
		objFileloader = new ObjectFile();
		objFileloader.setFlags (ObjectFile.RESIZE | ObjectFile.TRIANGULATE | ObjectFile.STRIPIFY);
	} 


	public TransformGroup getModel(String fnm)
	{  return getModel(fnm, 0);  }


	public TransformGroup getModel(String fnm, double yMove)
	// returns tg --> model
	{
		// load the model
		BranchGroup modelBG = loadModel(fnm);
		if (modelBG == null)
			return null;

		double scaleFactor = getScaling(modelBG);


		// scale and move the model along the y-axis
		Transform3D t3d = new Transform3D();
		t3d.setScale(scaleFactor);
		t3d.setTranslation( new Vector3d(0,yMove,0));

		TransformGroup tg = new TransformGroup(t3d);

		tg.addChild(modelBG); 
		return tg;
	}  // end of getModel()


	private BranchGroup loadModel(String modelFnm)
	// load the OBJ model stored in modelFnm
	{
		String fnm = MODELS_DIR + modelFnm;
		print("Loading OBJ model from " + fnm);

		File file = new java.io.File(fnm);
		if (!file.exists()) {
			System.out.println("Could not find " + fnm);
			return null;
		}

		/* Convert the filename to a URL, so the OBJ file can 
       find MTL and image files in the Models/ subdirectory
       at runtime. */
		URL url = null;
		try {
			url = file.toURI().toURL();
		}
		catch(Exception e) {
			System.out.println(e);
			return null;
		}

		// read in the geometry from the file
		Scene scene = null;
		try {
			scene = objFileloader.load(url);
		}
		catch (FileNotFoundException e) {
			System.out.println("Could not find " + fnm);
			return null;
		}
		catch (ParsingErrorException e) {
			System.out.println("Could not parse the contents of " + fnm);
			System.out.println(e);
			return null;
		}
		catch (IncorrectFormatException e) {
			System.out.println("Incorrect format in " + fnm);
			System.out.println(e);
			return null;
		}

		// return the model's BranchGroup
		if(scene != null)
			return scene.getSceneGroup();
		else
			return null;
	}  // end of loadModel()



	private double getScaling(BranchGroup modelBG)
	// check the model's size and scale if too big
	{
		double scaleFactor = 1.0;
		BoundingBox boundBox = new BoundingBox( modelBG.getBounds());

		Point3d lower = new Point3d();
		boundBox.getLower(lower);
		// System.out.println("lower: " + lower);

		Point3d upper = new Point3d();
		boundBox.getUpper(upper);
		// System.out.println("upper: " + upper);

		// calculate model scaling
		double maxDim = getMaxDimension(lower, upper);
		//if (maxDim > MAX_SIZE) {
			scaleFactor = MAX_SIZE/maxDim;
			//System.out.println("Applying scaling factor: " + scaleFactor);
		//}

		return scaleFactor;
	}  // end of getScaling()


	private double getMaxDimension(Point3d lower, Point3d upper)
	// calculate the max dimension
	{
		double max = 0;
		if ((upper.x - lower.x) > max) 
			max = upper.x - lower.x;
		if ((upper.y - lower.y) > max) 
			max = upper.y - lower.y;
		if ((upper.z - lower.z) > max) 
			max = upper.z - lower.z;
		return max;
	}  // end of getMaxDimension()




} // end of ModelLoader class