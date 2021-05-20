package renderer;

import static geometries.Intersectable.GeoPoint;

import elements.LightSource;
import primitives.*;

import static primitives.Util.*;

import scene.Scene;

import java.util.List;

/**
 * Rey tracer
 * calculate the color of point that interact with the ray
 * @author Noam Shushan
 */
public class RayTracerBasic extends RayTracerBase {

    private static final int MAX_CALC_COLOR_LEVEL = 10;
    private static final double MIN_CALC_COLOR_K = 0.001d;
    private static final double INITIAL_K = 1.0d;

    /**
     * constructor for RayTracerBasic
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
        var closestPoint = findClosestIntersection(ray);

        if (closestPoint == null) {
            return _scene.background;
        }

        return calcColor(closestPoint, ray);
    }

    /**
     * According to the pong model
     * This model is additive in which we connect all the components that will eventually
     * make up an image with background colors, self-colors and texture colors.
     * @param geoPoint the geometry and the lighted point at him
     * @param ray the ray that goes out of the camera
     * @return the color at the point
     */
    private Color calcColor(GeoPoint geoPoint, Ray ray) {
        return calcColor(geoPoint, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K)
                .add(_scene.ambientLight.getIntensity());
    }

    /**
     * overload methode of {@link renderer.RayTracerBasic#calcColor(GeoPoint, Ray)}
     * @param geoPoint the geometry and the lighted point at him
     * @param ray the ray that goes out of the camera
     * @param level the level of the recursion
     * @param k Represents influencing factors of transparency and reflection
     * @return the color at the point
     */
    private Color calcColor(GeoPoint geoPoint, Ray ray, int level, double k) {
        Color color = geoPoint.geometry.getEmission()
                .add(calcLocalEffects(geoPoint, ray, k));
        return 1 == level ? color : color.add(calcGlobalEffects(geoPoint, ray, level, k));
    }

    /**
     * Computer lighting effects as at a certain point on geometry
     * @param intersection the geometry and the lighted point at him
     * @param ray the ray from the camera
     * @return the total color at the point including the specular and diffusive
     */
    private Color calcLocalEffects(GeoPoint intersection, Ray ray, double k) {
        // the direction of the ray from the camera
        Vector v = ray.getDir();
        // most of the reflected light goes in the direction of the normal to the geometry on the point
        Vector n = intersection.geometry.getNormal(intersection.point);
        // the angle between n and v give as the the range of the diffusive and specularity
        double nv = alignZero(n.dotProduct(v));
        // The effects of light are not noticeable if our point of view is orthogonal to the object
        if (nv == 0) {
            return Color.BLACK;
        }
        // get the reduces factors of geometry to computer specular and diffusive effects
        Material material = intersection.geometry.getMaterial();
        double nShininess = material.nShininess;
        double kd = material.kD;
        double ks = material.kS;

        Color color = Color.BLACK;
        // Computer the total color at the point from all light sources
        for (var lightSource : _scene.lights) {
            Vector l = lightSource.getL(intersection.point);
            double nl = alignZero(n.dotProduct(l));

            if (nl * nv > 0) { // sign(nl) == sing(nv) to find out if the vectors l and n ar not negative directions
                // ktr is the amount of shadow as a number between 0 and 1
                double ktr = transparency(lightSource, l, intersection, n);
                if (ktr * k > MIN_CALC_COLOR_K) {
                    Color lightIntensity = lightSource.getIntensity(intersection.point).scale(ktr);
                    color = color
                            .add(calcDiffusive(kd, nl, lightIntensity),
                                    calcSpecular(ks, l, n, nl, v, nShininess, lightIntensity));
                }
            }
        }

        return color;
    }

    /**
     * Computer influences factors of transparency and reflection
     * @param geoPoint the geometry and the lighted point at him
     * @param inRay the ray that goes out of the camera
     * @param level the level of the recursion
     * @param k Represents influencing factors of transparency and reflection
     * @return the color with the transparency and reflection
     */
    private Color calcGlobalEffects(GeoPoint geoPoint, Ray inRay, int level, double k) {
        Color color = Color.BLACK;
        Material material = geoPoint.geometry.getMaterial();
        Vector n = geoPoint.geometry.getNormal(geoPoint.point);

        double kr = material.kR;
        double kkr = k * kr;

        if (kkr > MIN_CALC_COLOR_K) { // Recursion stop conditions
            // create reflected ray to calculate the color of reflection of the geometry
            Ray reflectedRay = constructReflectedRay(n, geoPoint.point, inRay);
            // Checks for intersections with the reflection ray
            GeoPoint reflectedPoint = findClosestIntersection(reflectedRay);
            if (reflectedPoint != null){
                color = color.add(calcColor(reflectedPoint, reflectedRay, level - 1, kkr).scale(kr));
            }
        }

        double kt = material.kT;
        double kkt = k * kt;
        if (kkt > MIN_CALC_COLOR_K) { // Recursion stop conditions
            // create refracted ray to calculate the color of transparency of the geometry
            Ray refractedRay = constructRefractedRay(n, geoPoint.point, inRay);
            // Checks for intersections with the transparency ray
            GeoPoint refractedPoint = findClosestIntersection(refractedRay);
            if (refractedPoint != null){
                color = color.add(calcColor(refractedPoint, refractedRay, level - 1, kkt).scale(kt));
            }
        }
        return color;
    }

    /**
     * Produces a transparency ray that starts from
     * the point where the ray hit from the camera and
     * goes in the direction almost like the original beam
     * @param n the normal on the point
     * @param point the point where the ray hit from the camera
     * @param inRay the ray from the camera
     * @return transparency ray
     */
    private Ray constructRefractedRay(Vector n, Point3D point, Ray inRay) {
        return new Ray(point, inRay.getDir(), n);
    }

    /**
     * Produces a reflection ray that starts from
     * the point where the ray struck from the camera and goes diagonally to the point
     * @param n the normal on the point
     * @param point the point where the ray hit from the camera
     * @param inRay the ray from the camera
     * @return a reflection ray
     */
    private Ray constructReflectedRay(Vector n, Point3D point, Ray inRay) {
        //𝒓 = 𝒗 − 𝟐 ∙ (𝒗 ∙ 𝒏) ∙ 𝒏
        Vector v = inRay.getDir();
        Vector r = null;
        try {
            double vn = v.dotProduct(n);
            r = v.subtract(n.scale(2d * vn)).normalized();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return new Ray(point, r, n);
    }

    /**
     * find closest intersection to the starting point of the ray
     * @param ray the ray that intersect with the geometries of the scene
     * @return the GeoPoint that is point is the closest point to the starting point of the ray
     */
    private GeoPoint findClosestIntersection(Ray ray) {
        if(ray == null){
            return null;
        }

        List<GeoPoint> points = _scene.geometries.findGeoIntersections(ray);
        return ray.findClosestGeoPoint(points);
    }

    private boolean unshaded(LightSource lightSource, Vector l, GeoPoint geoPoint, Vector n) {
        Vector lightDirection = l.scale(-1); // from point to light source
        Ray lightRay = new Ray(geoPoint.point, lightDirection, n);
        List<GeoPoint> intersections = _scene.geometries.findGeoIntersections(lightRay);

        if (intersections == null)
            return true;

        double lightDistance = lightSource.getDistance(geoPoint.point);
        for (GeoPoint gp : intersections) {
            if (alignZero(gp.point.distance(geoPoint.point) - lightDistance) <= 0)
                return false;
        }

        return true;
    }

    /**
     * At each point of intersection A of a ray from the camera with geometry
     * we create a new ray that is sent from A to the light source to
     * check if another geometry is blocking us.
     * If so it means that there is a shadow on A
     * and therefore the point A should be painted in shadow colors.
     * @param lightSource light source of the scene
     * @param l the direction of the light to the point where its strikes
     * @param geoPoint the geometry and the lighted point at him
     * @param n the direction of the normal to the geometry on the point
     * @return The total amount of transparency in order to get a more accurate shadow as a number between 0 and 1.
     * 1 means that there is no shadow. 0 means full shadow.
     */
    private double transparency(LightSource lightSource, Vector l, GeoPoint geoPoint, Vector n){
        Vector lightDirection = l.scale(-1); // from point to light source
        // create a new ray that is sent from point to the light source
        Ray lightRay = new Ray(geoPoint.point, lightDirection, n);
        // check if another geometry is blocking us by finding intersections
        var intersections = _scene.geometries.findGeoIntersections(lightRay);

        if (intersections == null){
            return 1d; // There is no shadow
        }

        // the distance from the light source to the point
        double lightDistance = lightSource.getDistance(geoPoint.point);
        double ktr = 1d;
        for (GeoPoint gp : intersections) {
            if (alignZero(gp.point.distance(geoPoint.point) - lightDistance) <= 0) {
                ktr *= gp.geometry.getMaterial().kT; // The transparency of each intersection
                if (ktr < MIN_CALC_COLOR_K){
                    return 0d; // full shadow
                }
            }
        }
        return ktr;
    }

    /**
     * Calculates the color of the specular light as part of a pong model
     * @param ks Percentage of energy going towards the part of the specular
     * @param l direction of the light to the point on the geometry
     * @param n the normal vector of the geometry
     * @param nl the angle between the normal and the direction from the light to the point
     * @param v the direction from the camera to the point
     * @param nShininess The amount of shininess that goes from the object in which the light strikes
     * @param lightIntensity light intensity from the light source
     * @return the color of the specular light
     */
    private Color calcSpecular(double ks, Vector l, Vector n, double nl, Vector v, double nShininess, Color lightIntensity) {
        Vector negV = v.scale(-1d);
        // vector r is the direction of thr reflection of the light from the point at the geometry
        Vector r = l.add(n.scale(-2d * nl));

        double vr = negV.dotProduct(r);

        return lightIntensity.scale(ks * Math.pow(Math.max(0, vr), nShininess));
    }

    /**
     * Calculates the color of the diffusive light as part of a pong model
     * @param kd Percentage of energy going towards the part of the diffusive
     * @param nl the angle between the normal and the direction from the light to the point
     * @param lightIntensity light intensity from the light source
     * @return the color of the diffusive light
     */
    private Color calcDiffusive(double kd, double nl, Color lightIntensity) {
        double factor = kd * Math.abs(nl);
        return lightIntensity.scale(factor);
    }
}
