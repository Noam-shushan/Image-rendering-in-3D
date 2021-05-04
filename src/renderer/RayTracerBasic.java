package renderer;

import primitives.*;
import scene.Scene;

/**
 *
 */
public class RayTracerBasic extends RayTracerBase{

    /**
     * constructor for RayTracerBasic
     * @param scene the scene
     */
    public RayTracerBasic(Scene scene) {
        super(scene);
    }

    @Override
    public Color traceRay(Ray ray) {
        var intersections  = _scene.geometries.findIntersections(ray);

        if(intersections == null){
            return _scene.background;
        }

        var closestPoint = ray.findClosestPoint(intersections);
//        if(closestPoint == null){
//            return something
//        }
        return calcColor(closestPoint);
    }

    /**
     * Calculates the ambient light
     * @param point Later we will want to get the color at a certain point
     * @return the ambient light
     */
    public Color calcColor(Point3D point){
        return _scene.ambientLight.getIntensity();
    }
}
