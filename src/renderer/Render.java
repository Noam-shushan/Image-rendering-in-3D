package renderer;

import elements.Camera;
import primitives.Color;
import primitives.Ray;
import scene.Scene;

import java.util.MissingResourceException;

/**
 *
 */
public class Render {
    ImageWriter _imageWriter = null;
    Scene _scene = null;
    Camera _camera = null;
    RayTracerBase _rayTracer = null;

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
                    Ray ray = _camera.constructRayThroughPixel(Nx, Ny, j, i);
                    Color pixelColor = _rayTracer.traceRay(ray);
                    _imageWriter.writePixel(j, i, pixelColor);
                }
            }

        } catch(MissingResourceException e){
            throw new UnsupportedOperationException("Not implemented yet" + e.getClassName());
        }
    }

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

    public void writeToImage(){
        if(_imageWriter == null){
            throw new MissingResourceException("missing resource", ImageWriter.class.getName(), "");
        }
        _imageWriter.writeToImage();
    }

    public Render setImageWriter(ImageWriter imageWriter) {
        _imageWriter = imageWriter;
        return this;
    }

    public Render setScene(Scene scene) {
        _scene = scene;
        return this;
    }

    public Render setCamera(Camera camera) {
        _camera = camera;
        return this;
    }

    public Render setRayTracer(RayTracerBase rayTracer) {
        _rayTracer = rayTracer;
        return this;
    }
}
