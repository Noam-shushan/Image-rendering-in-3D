package renderer;

import static geometries.Intersectable.GeoPoint;

import elements.LightSource;
import primitives.*;

import static primitives.Util.*;

import scene.Scene;

/**
 * Rey tracer
 * calculate the color of point that interact with the ray
 * @author Noam Shushan
 */
public class RayTracerBasic extends RayTracerBase {

    /**
     * constructor for RayTracerBasic
     *
     * @param scene the scene
     */
    public RayTracerBasic(Scene scene) {
        super(scene);
    }

    /**
     * Trace the ray and calculates the color of the point that interact with the geometries of the scene
     * @param ray the ray that came out of the camera
     * @return the color of the object that the ray is interact with
     */
    @Override
    public Color traceRay(Ray ray) {
        var intersections = _scene.geometries.findGeoIntersections(ray);

        if (intersections == null) {
            return _scene.background;
        }
        // get the closest intersection point to the camera
        var closestPoint = ray.findClosestGeoPoint(intersections);
        if (closestPoint == null) {
            return _scene.ambientLight.getIntensity();
        }

        return calcColor(closestPoint, ray);
    }

    /**
     * According to the pong model
     * This model is additive in which we connect all the components that will eventually
     * make up an image with background colors, self-colors and texture colors.
     *
     * @param geoPoint the geometry and the lighted point at him
     * @return the color at the point
     */
    private Color calcColor(GeoPoint geoPoint, Ray ray) {
        return _scene.ambientLight.getIntensity()
                .add(geoPoint.geometry.getEmission(),
                        calcLocalEffects(geoPoint, ray));
    }

    /**
     * Computer lighting effects as at a certain point on geometry
     * @param intersection the geometry and the lighted point at him
     * @param ray the ray from the camera
     * @return the total color at the point including the specular and diffusive
     */
    private Color calcLocalEffects(GeoPoint intersection, Ray ray) {
        // the direction of the ray from the camera
        Vector v = ray.getDir();
        // most of the reflected light goes in the direction of the normal to the geometry on the point
        Vector n = intersection.geometry.getNormal(intersection.point);
        // the angle between n and v give as the the range of the diffusive and specularity
        double nv = alignZero(n.dotProduct(v));
        // The effects of light are not noticeable if our point of view is orthogonal to the object
        if (isZero(nv)) {
            return Color.BLACK;
        }
        // get the reduces factors of geometry to computer specular and diffusive effects
        Material material = intersection.geometry.getMaterial();
        double nShininess = material.nShininess, kd = material.kD, ks = material.kS;

        Color color = Color.BLACK;
        // Computer the total color at the point from all light sources
        for (LightSource lightSource : _scene.lights) {
            Vector l = lightSource.getL(intersection.point);
            double nl = alignZero(n.dotProduct(l));

            if (nl * nv > 0) { // sign(nl) == sing(nv) to find out if the vectors l and n ar not negative directions
                Color lightIntensity = lightSource.getIntensity(intersection.point);
                color = color
                        .add(calcDiffusive(kd, l, n, lightIntensity),
                                calcSpecular(ks, l, n, v, nShininess, lightIntensity));
            }
        }

        return color;
    }

    /**
     * Calculates the color of the specular light as part of a pong model
     * @param ks Percentage of energy going towards the part of the specular
     * @param l direction of the light to the point on the geometry
     * @param n the normal vector of the geometry
     * @param v the direction from the camera to the point
     * @param nShininess The amount of shininess that goes from the object in which the light strikes
     * @param lightIntensity light intensity from the light source
     * @return the color of the specular light
     */
    private Color calcSpecular(double ks, Vector l, Vector n, Vector v, double nShininess, Color lightIntensity) {
        Vector negV = v.scale(-1);
        double ln = l.dotProduct(n);
        // vector r is the direction of thr reflection of the light from the point at the geometry
        Vector r = l.add(n.scale(-2 * ln));
        double vr = negV.dotProduct(r);
        return lightIntensity.scale(ks * Math.pow(Math.max(0, vr), nShininess));
    }

    /**
     * Calculates the color of the diffusive light as part of a pong model
     * @param kd Percentage of energy going towards the part of the diffusive
     * @param l direction of the light to the point on the geometry
     * @param n the normal vector of the geometry
     * @param lightIntensity light intensity from the light source
     * @return the color of the diffusive light
     */
    private Color calcDiffusive(double kd, Vector l, Vector n, Color lightIntensity) {
        // the angle between the normal and the direction from the light to the point
        double factor = kd * Math.abs(l.dotProduct(n));
        return lightIntensity.scale(factor);
    }
}
