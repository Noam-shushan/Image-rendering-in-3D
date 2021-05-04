package renderer;

import elements.Camera;
import primitives.Color;
import primitives.Ray;
import scene.Scene;

import java.util.MissingResourceException;

/**
 * This class gets all the objects needed to create an image in our 3D graphic model
 * @author Naom Shushan
 */
public class Render {
    /**
     * Handle the painting and producing the final image
     */
    ImageWriter _imageWriter = null;
    /**
     * Handle the geometric shape and the colors
     */
    Scene _scene = null;
    /**
     * Perspective on the scene
     */
    Camera _camera = null;
    /**
     * trace the ray of each pixel and prodos the color
     */
    RayTracerBase _rayTracer = null;

    /**
     * Produces an image with graphic shapes
     * Paint them
     * @throws UnsupportedOperationException if missing some resource in order to create the image
     */
    public void renderImage(){
        try {
            if (_imageWriter == null) {
                throw new MissingResourceException("missing resource", ImageWriter.class.getName(), "");
            }
            if (_scene == null) {
                throw new MissingResourceException("missing resource", Scene.class.getName(), "");
            }
            if (_camera == null) {
                throw new MissingResourceException("missing resource", Camera.class.getName(), "");
            }
            if (_rayTracer == null) {
                throw new MissingResourceException("missing resource", RayTracerBase.class.getName(), "");
            }

            int Nx = _imageWriter.getNx(), Ny = _imageWriter.getNy();

            for (int i = 0; i < Ny; i++) {
                for (int j = 0; j < Nx; j++) {
                    Ray ray = _camera.constructRayThroughPixel(Nx, Ny, j, i); // create ray through the pixel
                    Color pixelColor = _rayTracer.traceRay(ray); // calculates the color
                    _imageWriter.writePixel(j, i, pixelColor); // paint the pixel
                }
            }

        } catch(MissingResourceException e){
            throw new UnsupportedOperationException("Not implemented yet" + e.getClassName());
        }
    }

    /**
     * Produces a pixel-sized matrix of the view plane and colors rows and columns
     * @param interval the space between the rows and columns
     * @param color the color of the grid
     * @throws MissingResourceException if image writer for the render is missing
     */
    public void printGrid(int interval, Color color){
        if(_imageWriter == null){
            throw new MissingResourceException("missing resource", ImageWriter.class.getName(), "");
        }

        int Nx = _imageWriter.getNx(), Ny = _imageWriter.getNy();

        for(int i = 0; i < Nx; i++){
            for(int j = 0; j < Ny; j++){
                if(i % interval == 0 || j % interval == 0){
                    _imageWriter.writePixel(j, i, color);
                }
            }
        }
    }

    /**
     * produces unoptimized png file of the image according
     * to pixel color matrix in the directory of the project
     * @throws MissingResourceException if image writer for the render is missing
     */
    public void writeToImage(){
        if(_imageWriter == null){
            throw new MissingResourceException("missing resource", ImageWriter.class.getName(), "");
        }
        _imageWriter.writeToImage();
    }

    /**
     * setter to the image writer
     * @param imageWriter the new image writer
     * @return this render
     */
    public Render setImageWriter(ImageWriter imageWriter) {
        _imageWriter = imageWriter;
        return this;
    }

    /**
     * setter to the scene
     * @param scene the new scene
     * @return this render
     */
    public Render setScene(Scene scene) {
        _scene = scene;
        return this;
    }
    /**
     * setter to the camera
     * @param camera the new camera
     * @return this render
     */
    public Render setCamera(Camera camera) {
        _camera = camera;
        return this;
    }
    /**
     * setter to the rayTracer
     * @param rayTracer the new rayTracer
     * @return this render
     */
    public Render setRayTracer(RayTracerBase rayTracer) {
        _rayTracer = rayTracer;
        return this;
    }
}
