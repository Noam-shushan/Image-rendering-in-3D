package renderer;

import elements.Camera;
import primitives.Color;

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
     * the amount of rays in bean
     */
    private int _numOfRaysInBean = 1;

    /**
     * number of threads
     */
    private int _threadsCount = 0;
    private static final int SPARE_THREADS = 2; // Spare threads if trying to use all the cores
    boolean print = false; // printing progress percentage

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

            if (_threadsCount == 0)
                for (int i = 0; i < Ny; ++i)
                    for (int j = 0; j < Nx; ++j)
                        castRay(Nx, Ny, j, i);
            else
                renderImageThreaded();

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
        var rays = _camera.constructBeanOfRaysThroughPixel(nX, nY, col, row, _numOfRaysInBean);
        Color pixelColor = Color.BLACK;
        // calculates the color
        for(var r : rays){
            pixelColor = pixelColor.add(_rayTracer.traceRay(r));
        }
        pixelColor = pixelColor.reduce(rays.size()); // the average of the color from the bean
        _imageWriter.writePixel(col, row, pixelColor);
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
                while (thePixel.nextPixel(pixel))
                    castRay(nX, nY, pixel.col, pixel.row);
            });
        }
        // Start threads
        for (Thread thread : threads)
            thread.start();

        // Print percents on the console
        thePixel.print();

        // Ensure all threads have finished
        for (Thread thread : threads)
            try {
                thread.join();
            } catch (Exception e) {
            }

        if (print)
            System.out.print("\r100%");
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
     * setter for the number of rays in bean
     * @param numOfRaysInBean the new number of rays in bean
     * @return this render
     */
    public Render setNumOfRaysInBean(int numOfRaysInBean) {
        _numOfRaysInBean = numOfRaysInBean;
        return this;
    }

    /**
     * Set multi-threading <br>
     * - if the parameter is 0 - number of cores less 2 is taken
     *
     * @param threads number of threads
     * @return the Render object itself
     */
    public Render setMultithreading(int threads) {
        if (threads < 0)
            throw new IllegalArgumentException("Multithreading parameter must be 0 or higher");
        if (threads != 0)
            this._threadsCount = threads;
        else {
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
