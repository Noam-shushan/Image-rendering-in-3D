package renderer;

import static geometries.Intersectable.GeoPoint;
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
        var intersections  = _scene.geometries.findGeoIntersections(ray);

        if(intersections == null){
            return _scene.background;
        }

        var closestPoint = ray.findClosestGeoPoint(intersections);
        if(closestPoint == null){
            return _scene.ambientLight.getIntensity();
        }

        return calcColor(closestPoint);
    }

    /**
     * Calculates the ambient light
     * @param geoPoint Later we will want to get the color at a certain geoPoint
     * @return the ambient light
     */
    public Color calcColor(GeoPoint geoPoint){
        Color iA = _scene.ambientLight.getIntensity(),
                iE = geoPoint.geometry.getEmission();
        Color iL = iA.add(iE);

        for(var ls : _scene.lights){
            iL = iL.add(ls.getIntensity(geoPoint.point));
        }
        //TODO

        return iL;
    }
}
