package myscene;

import javax.media.j3d.Texture2D;
import com.sun.j3d.utils.image.TextureLoader;
import javax.vecmath.Color3f; 
import javax.media.j3d.Material;
import javax.media.j3d.Texture;

import java.awt.Color;
import java.awt.Component;

import javax.media.j3d.Appearance;

public class Apperance {

	


	private static final Color3f SPECULAR_LIGHT_COLOR = new Color3f(Color.WHITE);
	private static final Color3f AMBIENT_LIGHT_COLOR = new Color3f(Color.LIGHT_GRAY);
	private static final Color3f EMISSIVE_LIGHT_COLOR = new Color3f(Color.BLACK);

//	Now you can create a method that returns an Apperance based on a given Color:




	public static Appearance getAppearance(Color color) {
	    Appearance app = new Appearance();
	    app.setMaterial(getMaterial(color));
	    return app;
	}

	static Material getMaterial(Color color) {
	    return new Material(AMBIENT_LIGHT_COLOR, 
	                        EMISSIVE_LIGHT_COLOR, 
	                        new Color3f(color), 
	                        SPECULAR_LIGHT_COLOR, 
	                        100F);
	}

//	It's possible to use an image as a texture, but there are some constraints: the image must be equal in width and height and must be a power of 2. If you have ever used Swing, you know you have to pass an instance of Component to MediaTracker if you want to track the loading of an image. Loading a texture uses a similar process:




	Appearance getAppearance(String path, Component canvas, int dimension) {
	    Appearance appearance = new Appearance();
	    appearance.setTexture(getTexture(path, canvas, dimension));
	    return appearance;
	}

	Texture getTexture(String path, Component canvas, int dimension) {
	    TextureLoader textureLoader = new TextureLoader(path, canvas);

	    Texture2D texture = new Texture2D(Texture2D.BASE_LEVEL, 
	                                      Texture2D.RGB, 
	                                      dimension, 
	                                      dimension); 

	    texture.setImage(0, textureLoader.getImage());

	    return texture;
	}   
}
