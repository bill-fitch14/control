package myscene;
// GroundShape.java
// Andrew Davison, September 2006, ad@fivedots.coe.psu.ac.th

/* A transparent GIF (stored in fnm) is displayed in a QuadArray.

   The center of the quad's base is at (0,0,0), so is resting on 
   the ground. It has sides of screenSize, and is always oriented 
   towards the viewer.

   The intention is to load scenery which can be represented by 2D
   images that always face the viewer, such as trees, plants, etc.

   Min and mag filtering is used to improve the GIFs appearance 
   when viewed close to and from far away.
*/

import javax.vecmath.*;
import javax.media.j3d.*;

import mytrack.U4_Constants;

import com.sun.j3d.utils.geometry.*;
import com.sun.j3d.utils.image.*;


public class GroundShape extends OrientedShape3D
{
  private static final int NUM_VERTS = 4;
  //private static final String IMAGES_DIR = "images/";
  private static final String IMAGES_DIR = 
	  //"C:/Users/user/EclipseWorkspace/java3dbill/images/";
  		U4_Constants.projectlocationUnix + "/images/";


  public GroundShape(String fnm) 
  {  this(fnm, 1);  }


  public GroundShape(String fnm, float screenSize) 
  { 
    // rotate about the y-axis to follow the viewer
	setAlignmentAxis( 0.0f, 1.0f, 0.0f);

    createGeometry(screenSize);
    createAppearance(IMAGES_DIR + fnm);
  } // end of GroundShape()


  private void createGeometry(float sz)
  {
    QuadArray plane = new QuadArray(NUM_VERTS, 
							GeometryArray.COORDINATES |
							GeometryArray.TEXTURE_COORDINATE_2 );

    // the base is centered at (0,0,0), size screenSize, 
    // facing +z axis
    Point3f p1 = new Point3f(-sz/2, 0.0f, 0.0f);
    Point3f p2 = new Point3f(sz/2, 0.0f, 0.0f);
    Point3f p3 = new Point3f(sz/2, sz, 0.0f);
    Point3f p4 = new Point3f(-sz/2, sz, 0.0f);

    // anti-clockwise from bottom left
    plane.setCoordinate(0, p1);
    plane.setCoordinate(1, p2);
    plane.setCoordinate(2, p3);
    plane.setCoordinate(3, p4);

    TexCoord2f q = new TexCoord2f();
    q.set(0.0f, 0.0f);    
    plane.setTextureCoordinate(0, 0, q);
    q.set(1.0f, 0.0f);   
    plane.setTextureCoordinate(0, 1, q);
    q.set(1.0f, 1.0f);    
    plane.setTextureCoordinate(0, 2, q);
    q.set(0.0f, 1.0f);   
    plane.setTextureCoordinate(0, 3, q);  

    setGeometry(plane);
  }  // end of createGeometry()


  private void createAppearance(String fnm)
  {
    Appearance app = new Appearance();

    // blended transparency so texture can be irregular
    TransparencyAttributes tra = new TransparencyAttributes();
    tra.setTransparencyMode( TransparencyAttributes.BLENDED );
    app.setTransparencyAttributes( tra );

    Texture2D tex = loadTexture(fnm);
    app.setTexture(tex);      // set the texture
    setAppearance(app);
  }  // end of createAppearance()


  private Texture2D loadTexture(String fn)
  // load image from file fn as a texture
  {
    TextureLoader texLoader = new TextureLoader(fn, null);
    Texture2D texture = (Texture2D) texLoader.getTexture();
    Object a;
	if (texture == null)
    	a=null;
      //1//2//3//99System.out.print("Cannot load texture from " + fn);
    else {
      //1//2//3//99System.out.print("Loaded texture from " + fn);

      // remove edge texels, so no seams between texture faces
      texture.setBoundaryModeS(Texture.CLAMP_TO_EDGE);
      texture.setBoundaryModeT(Texture.CLAMP_TO_EDGE);

      // smoothing for texture enlargement/shrinking
      texture.setMinFilter(Texture2D.BASE_LEVEL_LINEAR); //shrink texels
      texture.setMagFilter(Texture2D.BASE_LEVEL_LINEAR); // enlarge
      texture.setEnable(true);
    }
    return texture;
  }  // end of loadTexture()

} // end of GroundShape class