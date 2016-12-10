package mytrack;

/*
 * Java 3D Tutorial example. 
 *
 * http://www.daltonfilho.com
 *
 * Copyright (C) 2005 Dalton Filho
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */



import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.StandardCopyOption;

import org.apache.commons.io.FileUtils;
//import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;

import javax.swing.JFrame;
import javax.vecmath.*;
import javax.media.j3d.*;

import com.sun.j3d.loaders.IncorrectFormatException;
import com.sun.j3d.loaders.ParsingErrorException;
import com.sun.j3d.loaders.Scene;
import com.sun.j3d.loaders.objectfile.ObjectFile;
import com.sun.j3d.utils.universe.SimpleUniverse;


/**
 * This class creates a very simple window in which a 3D canvas occupies the 
 * entire window and displays a 3D model loaded from disk. You will have to 
 * change the line where the path of the model is set.
 *
 * @author Dalton Filho
 * @since 12/28/2005
 */
public class SimpleModelView extends JFrame {

    
    // INSTANCE ****************************************************************

    /** The canvas where the object is rendered. */
    private Canvas3D canvas;

    /** Simplifies the configuration of the scene. */
    private SimpleUniverse universe;

    /** The root node of the scene. */
    private BranchGroup root;
    
    // INITIALIZATION **********************************************************

    /**
     * Creates a window with a 3D canvas on its center.
     *
     * @throws IOException if some error occur while loading the model
     */
    public SimpleModelView() throws IOException {
        configureWindow();
        configureCanvas();
        configureUniverse();
        addModelToUniverse();
        addLightsToUniverse();
        //root.compile();
        universe.addBranchGraph(root);
    }

    /**
     * Defines basic properties of this window.
     */
    private void configureWindow() {
        setTitle("Basic Java3D Program");
        setSize(640, 480);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int locationX = (screenSize.width - getWidth()) / 2;
        int locationY = (screenSize.height - getHeight()) / 2;
        setLocation(locationX,locationY);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * Defines basic properties of the canvas. 
     */
    private void configureCanvas() {
        canvas = new Canvas3D(SimpleUniverse.getPreferredConfiguration());
        canvas.setDoubleBufferEnable(true);
        getContentPane().add(canvas, BorderLayout.CENTER);
    }

    /** 
     * Defines basic properties of the universe.
     */
    private void configureUniverse() {
        universe = new SimpleUniverse(canvas);
        universe.getViewingPlatform().setNominalViewingTransform();
    }

    /**
     * Loads a model from disk and assign the root node of the scene
     *
     * @throws IOException if it's impossible to find the 3D model
     */
    private void addModelToUniverse() throws IOException {
        Scene scene = getSceneFromFile("C:/Users/bill/Documents/models/shunter.obj"); 
        root = scene.getSceneGroup();
    }

    /** 
     * Adds a dramatic blue light... 
     */
    private void addLightsToUniverse() {
        Bounds influenceRegion = new BoundingSphere();
        Color3f lightColor = new Color3f(Color.RED);
        Vector3f lightDirection = new Vector3f(-1F, -1F, -1F);
        DirectionalLight light = new DirectionalLight(lightColor, lightDirection);
        light.setInfluencingBounds(influenceRegion);
        root.addChild(light);
    }

    // ACCESS ******************************************************************

    /**
     * Loads a scene from a Wavefront .obj model.
     *
     * @param location the path of the model
     * @return Scene the scene contained on the model
     * @throws IOException if some error occur while loading the model
     */
    public static Scene getSceneFromFile(String location) throws IOException {
        ObjectFile file = new ObjectFile(ObjectFile.RESIZE);
        return file.load(new FileReader(location));
//        Class currentClass = new Object() { }.getClass().getEnclosingClass();
//    	InputStream in = currentClass.getClass().getResourceAsStream(location); 
//
//    	File targetFile = new File("C:/tmp");
//        java.nio.file.Files.copy(
//        	      in, 
//        	      targetFile.toPath(), 
//        	      StandardCopyOption.REPLACE_EXISTING);
//        	 
//        	    IOUtils.closeQuietly(in);
//    
//    	return file.load(new FileReader(targetFile));

    }

    


    // MAIN ********************************************************************

    public static void main(String[] args) {
        try {
            new SimpleModelView().setVisible(true);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    
}


