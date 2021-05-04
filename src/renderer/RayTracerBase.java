package renderer;

import primitives.*;
import scene.Scene;

/**
 * abstract class to rey tracing
 */
public abstract class RayTracerBase {
    /**
     * the scene we trace
     */
    protected Scene _scene;

    /**
     * Constructor for the RayTracerBase base
     * @param scene the scene
     */
    public RayTracerBase(Scene scene) {
        _scene = scene;
    }

    /**
     * Trace the ray and calculates the color
     * @param ray the ray that came out of the camera
     * @return the color of the object that the ray is interact with
     */
    public abstract Color traceRay(Ray ray);
}
