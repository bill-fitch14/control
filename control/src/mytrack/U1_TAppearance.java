package mytrack;

import java.awt.Color;
import java.io.File;

import javax.media.j3d.*;
import javax.vecmath.*;

import com.sun.j3d.utils.image.TextureLoader;

public final class U1_TAppearance {
	
	//private final static String TEX_FNM = "images/steel.jpg";
	//private final static String TEX_FNM = "C:/Users/user/EclipseWorkspace/java3dbill/images/steel.jpg";
	private final static String TEX_FNM = U4_Constants.projectlocationUnix + "/images/steel.jpg";

	private Texture2D _Texture;
	
	private Appearance _Appearance = new Appearance();

	private Material material;
	
	  public U1_TAppearance(Color col)
	  // shiny metallic appearance, combining a material and texture
	  {
	    //_Appearance = new Appearance();

	    // combine texture with material and lighting of underlying surface
	    TextureAttributes ta = new TextureAttributes();
	    ta.setTextureMode( TextureAttributes.MODULATE );
	    _Appearance.setTextureAttributes( ta );

	    // shiny metal material
	    Color3f alumDiffuse = new Color3f(0.37f, 0.37f, 0.37f);
	    //Color3f black = new Color3f(0.0f, 0.0f, 0.0f);
	    Color3f emissiveColour = new Color3f(col);
	    Color3f alumSpecular = new Color3f(0.89f, 0.89f, 0.89f);
	    
	    material = new Material(alumDiffuse, emissiveColour, alumDiffuse, alumSpecular, 17);
	    material.setCapability(Material.ALLOW_COMPONENT_WRITE);
	       // sets ambient, emissive, diffuse, specular, shininess
	    material.setLightingEnable(true);
	    _Appearance.setMaterial(material);
		File dir1 = new File (".");
	     File dir2 = new File ("..");
	     try {
//	      System.out.print ("Current dir : " + dir1.getCanonicalPath());
//	      System.out.print ("Parent  dir : " + dir2.getCanonicalPath());
	       }
	     catch(Exception e) {
	       e.printStackTrace();
	     }
	    
	    Texture2D tex = loadTexture(TEX_FNM);  // used by both grabbers

	    // apply texture to the shape
	    //if (_Texture == null)
	      _Appearance.setTexture(tex);
	  }  // end of makeAppearance();
	  
	  public void SetEmissiveColor(Color color){
		  Color3f color3f = new Color3f(color);
		  material.setEmissiveColor(color3f);
	  }
	  
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
	      texture.setEnable(true);
	    }
	    return texture;
	  }  // end of loadTexture()

	public Texture2D get_Texture() {
		return _Texture;
	}

	public Appearance get_Appearance() {
		return _Appearance;
	}
}
