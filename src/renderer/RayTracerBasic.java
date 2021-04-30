package renderer;

import primitives.*;
import scene.Scene;

/**
 *
 */
public class RayTracerBasic extends RayTracerBase{

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
//            return sometiong
//        }
        return calcColor(closestPoint);
    }

    /**
     *
     * @param point
     * @return
     */
    public Color calcColor(Point3D point){
        return _scene.ambientLight.getIntensity();
    }
}
