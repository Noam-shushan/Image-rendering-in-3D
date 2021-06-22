package renderer;

import elements.Camera;
import primitives.*;
import primitives.Ray;

import java.util.LinkedList;
import java.util.List;
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
     * Perspective on the scene
     */
    Camera _camera = null;

    /**
     * trace the ray of each pixel and prodos the color
     */
    RayTracerBase _rayTracer = null;

    /**
     * number of threads
     */
    private int _threadsCount = 0;

    /**
     * Spare threads if trying to use all the cores
     */
    private static final int SPARE_THREADS = 3;
    /**
     * flag of printing progress percentage
     */
    boolean print = false;

    /**
     * the amount of rays in bean for anti aliasing improvement
     */
    private int _antiAliasing  = 1;

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
            if (_camera == null) {
                throw new MissingResourceException("missing resource", Camera.class.getName(), "");
            }
            if (_rayTracer == null) {
                throw new MissingResourceException("missing resource", RayTracerBase.class.getName(), "");
            }

            int Nx = _imageWriter.getNx(), Ny = _imageWriter.getNy();

            if (_threadsCount == 0){
                for (int i = 0; i < Ny; ++i){
                    for (int j = 0; j < Nx; ++j){
                        castRay(Nx, Ny, j, i);
                    }
                }
            }
            else{
                renderImageThreaded();
            }
        } catch(MissingResourceException e){
            throw new UnsupportedOperationException("missing resources in order to create the image"
                    + e.getClassName());
        }
    }

    /**
     * Cast ray from camera in order to color a pixel
     * @param nX resolution on X axis (number of pixels in row)
     * @param nY resolution on Y axis (number of pixels in column)
     * @param col pixel's column number (pixel index in row)
     * @param row pixel's row number (pixel index in column)
     */
    private void castRay(int nX, int nY, int col, int row) {
        Color pixelColor;
        if(_antiAliasing == 1){
            Ray ray = _camera.constructRayThroughPixel(nX, nY, col, row);
            pixelColor = _rayTracer.traceRay(ray);
        }
        else{
            var rays = _camera.constructBeanOfRaysThroughPixel(nX, nY, col, row, _antiAliasing);
            pixelColor = castBean(rays);
        }

        _imageWriter.writePixel(col, row, pixelColor);
    }

    /**
     * cast bean of rays
     * @param bean bean of rays
     * @return the average of the colors from the bean
     */
    private Color castBean(List<Ray> bean){
        List<Color> colors = new LinkedList<>();
        // calculates the colors
        for(var rey : bean){
            colors.add(_rayTracer.traceRay(rey));
        }
        return Color.average(colors);
    }

    /**
     * This function renders image's pixel color map from the scene included with
     * the Renderer object - with multi-threading
     */
    private void renderImageThreaded() {
        final int nX = _imageWriter.getNx();
        final int nY = _imageWriter.getNy();
        final Pixel thePixel = new Pixel(nY, nX, this)
                .setRender(this);
        // Generate threads
        Thread[] threads = new Thread[_threadsCount];

        for (int i = _threadsCount - 1; i >= 0; --i) {
            threads[i] = new Thread(() -> {
                Pixel pixel = new Pixel()
                        .setRender(this);
                while (thePixel.nextPixel(pixel)){
                    castRay(nX, nY, pixel.col, pixel.row);
                }
            });
        }
        // Start threads
        for (Thread thread : threads){
            thread.start();
        }

        // Print percents on the console
        thePixel.print();

        // Ensure all threads have finished
        for (Thread thread : threads){
            try {
                thread.join();
            } catch (Exception e) { }
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
        if(_imageWriter == null) {
            throw new MissingResourceException("missing resource", ImageWriter.class.getName(), "");
        }
        _imageWriter.writeToImage();
    }

    /**
     * setter for the number of rays in bean for anti aliasing improvement
     * @param numOfRaysInBean the new number of rays in bean
     * @throws IllegalArgumentException if numOfRaysInBean is lower then 0
     * @return this render
     */
    public Render setAntiAliasing(int numOfRaysInBean) {
        if(numOfRaysInBean <= 0) {
            throw new IllegalArgumentException("Number of rays must be greater then 0");
        }
        _antiAliasing = numOfRaysInBean;
        return this;
    }

    /**
     * Set multi-threading <br>
     * - if the parameter is 0 - number of cores less 2 is taken
     *
     * @param threads number of threads
     * @throws IllegalArgumentException if threads is lower then 0
     * @return the Render object itself
     */
    public Render setMultithreading(int threads) {
        if (threads < 0) {
            throw new IllegalArgumentException("Multithreading parameter must be 0 or higher");
        }
        if (threads != 0) {
            _threadsCount = threads;
        } else {
            int cores = Runtime.getRuntime().availableProcessors() - SPARE_THREADS;
            this._threadsCount = cores <= 2 ? 1 : cores;
        }
        return this;
    }

    /**
     * Set debug printing on
     *
     * @return the Render object itself
     */
    public Render setDebugPrint() {
        print = true;
        return this;
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
