package myscene;

// CheckerFloor.java
// Andrew Davison, June 2006, ad@fivedots.coe.psu.ac.th

/* The floor is a blue and green chessboard, with a small red square
   at the (0,0) position on the (X,Z) plane, and with numbers along
   the X- and Z- axes.
   Updated to use generic collections.
*/

import java.awt.*;
import javax.media.j3d.*;
import com.sun.j3d.utils.geometry.Text2D;
import javax.vecmath.*;

import mytrack.U4_Constants;

import java.util.ArrayList;


public class CheckerFloor
{
  private final static int x_FLOOR_LEN = 160;  // should be even
  private final static int y_FLOOR_LEN = 25;  // should be even

  // colours for floor, etc
  private final static Color3f blue = new Color3f(0.0f, 0.1f, 0.4f);
  private final static Color3f green = new Color3f(0.0f, 0.5f, 0.1f);
  private final static Color3f medRed = new Color3f(0.8f, 0.4f, 0.3f);
  private final static Color3f white = new Color3f(1.0f, 1.0f, 1.0f);

  private BranchGroup floorBG;


  public CheckerFloor()
  // create tiles, add origin marker, then the axes labels
  {
    ArrayList<Point3f> blueCoords = new ArrayList<Point3f>();
    ArrayList<Point3f> greenCoords = new ArrayList<Point3f>();
    floorBG = new BranchGroup();

    boolean isBlue;
    for(int y=-y_FLOOR_LEN/2; y <= (y_FLOOR_LEN/2)-1; y++) {
      isBlue = (y%2 == 0)? true : false;    // set colour for new row
      for(int x=-x_FLOOR_LEN/2; x <= (x_FLOOR_LEN/2)-1; x++) {
        if (isBlue)
          createCoords(x, y, blueCoords);
        else 
          createCoords(x, y, greenCoords);
        isBlue = !isBlue;
      }
    }
    floorBG.addChild( new ColouredTiles(blueCoords, blue) );
    floorBG.addChild( new ColouredTiles(greenCoords, green) );

    addOriginMarker();
    labelAxes();
  }  // end of CheckerFloor()


  private void createCoords(int x, int y, ArrayList<Point3f> coords)
  // Coords for a single blue or green square, 
  // its left hand corner at (x,y,0)
  {
    // points created in counter-clockwise order
//    Point3f p1 = new Point3f(x, 0.0f, z+1.0f);
//    Point3f p2 = new Point3f(x+1.0f, 0.0f, z+1.0f);
//    Point3f p3 = new Point3f(x+1.0f, 0.0f, z);
//    Point3f p4 = new Point3f(x, 0.0f, z);  
    Point3f p1 = new Point3f(x, y+1.0f, 0.0f);
    Point3f p2 = new Point3f(x+1.0f, y+1.0f, 0.0f);
    Point3f p3 = new Point3f(x+1.0f, y, 0.0f);
    Point3f p4 = new Point3f(x, y,0.0f);
    
    p1.scale(U4_Constants.scalefactor);
    p2.scale(U4_Constants.scalefactor);
    p3.scale(U4_Constants.scalefactor);
    p4.scale(U4_Constants.scalefactor);
    
    coords.add(p1); coords.add(p2); 
    coords.add(p3); coords.add(p4);
  }  // end of createCoords()


  private void addOriginMarker()
  // A red square centered at (0,0,0), of length 0.5
  {  // points created counter-clockwise, a bit above the floor
//    Point3f p1 = new Point3f(-0.25f, 0.01f, 0.25f);
//    Point3f p2 = new Point3f(0.25f, 0.01f, 0.25f);
//    Point3f p3 = new Point3f(0.25f, 0.01f, -0.25f);    
//    Point3f p4 = new Point3f(-0.25f, 0.01f, -0.25f);
    Point3f p1 = new Point3f(-0.25f, 0.25f, 0.01f);
    Point3f p2 = new Point3f(0.25f, 0.25f, 0.01f);
    Point3f p3 = new Point3f(0.25f, -0.25f, 0.01f);    
    Point3f p4 = new Point3f(-0.25f, -0.25f, 0.01f);
    
    p1.scale(U4_Constants.scalefactor);
    p2.scale(U4_Constants.scalefactor);
    p3.scale(U4_Constants.scalefactor);
    p4.scale(U4_Constants.scalefactor);    

    ArrayList<Point3f> oCoords = new ArrayList<Point3f>();
    oCoords.add(p1); oCoords.add(p2);
    oCoords.add(p3); oCoords.add(p4);

    floorBG.addChild( new ColouredTiles(oCoords, medRed) );
  } // end of addOriginMarker();


  private void labelAxes()
  // Place numbers along the X- and Z-axes at the integer positions
  {
    Vector3d pt = new Vector3d();
    for (int i=-x_FLOOR_LEN/2; i <= x_FLOOR_LEN/2; i++) {
      pt.x = i;
      pt.x *= U4_Constants.scalefactor;
      floorBG.addChild( makeText(pt,""+i) );   // along x-axis
    }

    pt.x = 0;
    for (int i=-y_FLOOR_LEN/2; i <= y_FLOOR_LEN/2; i++) {
      pt.y = i;
      pt.y *= U4_Constants.scalefactor;
      floorBG.addChild( makeText(pt,""+i) );   // along y-axis
      
    }
  }  // end of labelAxes()


  private TransformGroup makeText(Vector3d vertex, String text)
  // Create a Text2D object at the specified vertex
  {
    Text2D message = new Text2D(text, white, "SansSerif", (int) (130*U4_Constants.scalefactor), Font.BOLD );
       // 36 point bold Sans Serif

    TransformGroup tg = new TransformGroup();
    Transform3D t3d = new Transform3D();
    t3d.setTranslation(vertex);
    tg.setTransform(t3d);
    tg.addChild(message);
    return tg;
  } // end of getTG()


  public BranchGroup getBG()
  {  return floorBG;  }


}  // end of CheckerFloor class

