package renderer;

import static geometries.Intersectable.GeoPoint;

import elements.LightSource;
import geometries.Plane;
import primitives.*;

import static primitives.Util.*;

import scene.Scene;

import java.util.LinkedList;
import java.util.List;

/**
 * Rey tracer
 * calculate the color ray-geometry intersection
 * @author Noam Shushan
 */
public class RayTracerBasic extends RayTracerBase {

    /**
     * The max level of the recursion attending to get <br/>
     * reflection and transparency of the geometries that goes to other geometries
     */
    private static final int MAX_CALC_COLOR_LEVEL = 4;

    /**
     * The minimal effect of a color factor for transparency and reflection <br/>
     * Below that there are no longer any color differences
     */
    private static final double MIN_CALC_COLOR_K = 0.001d;

    private static final double MAX_DISTANCE_FOR_REFLECTION = 1000d;

    /**
     * starting value of the effect of a color factor for transparency and reflection
     */
    private static final double INITIAL_K = 1d;

    /**
     * radius of bean of rays around main ray of reflection and transparency
     */
    private double _beanRadiusForGlossy;

    /**
     * image improvements for glossy of the emission color of the geometry
     */
    private boolean _glossy = false;

    /**
     * image improvements for diffuse of the emission color of the geometry
     */
    private boolean _diffuse = false;

    /**
     * the amount of rays in bean
     */
    private int _numOfRaysInBean = 1;

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
     * Add image improvements for diffuse of the emission color of the geometry
     * @return this RayTracer
     */
    public RayTracerBasic setDiffuse(){
        _diffuse = true;
        return this;
    }

    /**
     * Add image improvements for glossy of the emission color of the geometry
     * @return this RayTracer
     */
    public RayTracerBasic setGlossy(){
        _glossy = true;
        return this;
    }

    /**
     * setter for the radius of bean to create around main ray af reflection and transparency
     * @param beanRadiusForGlossy the new radius
     * @throws IllegalArgumentException if the radius <= 0
     * @return this rayTracer
     */
    public RayTracerBasic setBeanRadiusForGlossy(double beanRadiusForGlossy) {
        if(beanRadiusForGlossy <= 0){
            throw new IllegalArgumentException("bean radius can not be lower or equal to 0");
        }

        _beanRadiusForGlossy = beanRadiusForGlossy;
        return this;
    }

    /**
     * setter for the number of rays in bean
     * @param numOfRaysInBean the new number of rays in bean
     * @throws IllegalArgumentException if numOfRaysInBean <= 0
     * @return this render
     */
    public RayTracerBasic setNumOfRaysInBean(int numOfRaysInBean) {
        if(numOfRaysInBean <= 0) {
            throw new IllegalArgumentException("Number of rays must be greater then 0");
        }
        _numOfRaysInBean = numOfRaysInBean;
        return this;
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
     * @param k Represents influencing factors of transparency and reflection
     * @return the total color at the point including the specular and diffusive
     */
    private Color calcLocalEffects(GeoPoint intersection, Ray ray, double k) {
        // the direction of the ray from the camera
        Vector v = ray.getDir();
        // most of the reflected light goes in the direction of the normal to the geometry on the point
        Vector n = intersection.normal;
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
                double ktr = transparency(lightSource, l, intersection);
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

        double kr = material.kR;
        double kkr = k * kr;

        if (kkr > MIN_CALC_COLOR_K) { // Recursion stop conditions
            // create reflected bean to calculate the color of reflection influences of the geometry
            var bean = constructReflectedBean(geoPoint, inRay);
            color =  color.add(calcGlobalEffectOfBean(bean, level, kr, kkr));
        }

        double kt = material.kT;
        double kkt = k * kt;
        if (kkt > MIN_CALC_COLOR_K) { // Recursion stop conditions
            // create refracted bean to calculate the color of transparency influences of the geometry
            var bean = constructRefractedBean(geoPoint, inRay);
            color = color.add(calcGlobalEffectOfBean(bean, level, kt, kkt));
        }

        return color;
    }

    /**
     * calc global effects Of bean of rays
     * @param bean the bean of rays
     * @param level the level of the recursion
     * @param kx Represents influencing factors of transparency and reflection, can be kT or kS
     * @param kkx effect of a color factor for transparency and reflection
     * @return the average color of the bean
     */
    private Color calcGlobalEffectOfBean(List<Ray> bean, int level,  double kx, double kkx){
        List<Color> colors = new LinkedList<>();

        // add the color at the intersection point of each ray in the bean
        for(var ray : bean) {
            GeoPoint geoPoint = findClosestIntersection(ray);
            if(geoPoint != null) {
                colors.add(calcColor(geoPoint, ray, level - 1, kkx).scale(kx));
            }
        }

        if(colors.size() < 1){
            return _scene.background;
        }
        if(colors.size() == 1){
            return colors.get(0);
        }
        return Color.average(colors);
    }

    /**
     * Produces a transparency bean of rays that starts from
     * the point where the ray hit from the camera and
     * goes in the direction almost like the original beam
     * @param geoPoint the point where the ray hit from the camera
     * @param inRay the ray from the camera
     * @return transparency ray
     */
    private List<Ray> constructRefractedBean(GeoPoint geoPoint, Ray inRay) {
        Vector n = geoPoint.normal;
        Vector v = inRay.getDir();
        Ray mainRefractedRay = new Ray(geoPoint.point, v, n);

        if(_diffuse){
            // create bean of rays around the main refracted ray
            // if the angle between the normal of the geometry and the reflected ray
            // is positive it mean that the ray is under the geometry and we don't take this ray
            // in the consideration
            return mainRefractedRay.createBeanForGlossy(_numOfRaysInBean, 1,
                    dir -> n.dotProduct(v) * dir.dotProduct(n) < 0);
        }
        else { // not bean but single ray
            return List.of(mainRefractedRay);
        }
    }

    /**
     * Produces a reflection bean that starts from
     * the point where the ray struck from the camera and goes diagonally to the point
     * @param geoPoint the point where the ray hit from the camera
     * @param inRay the ray from the camera
     * @return a reflection ray
     */
    private List<Ray> constructReflectedBean(GeoPoint geoPoint, Ray inRay) {
        Vector v = inRay.getDir();
        Vector n = geoPoint.normal;
        double nv = alignZero(v.dotProduct(n));
        // r = v - 2*(v * n) * n
        Vector r = v.subtract(n.scale(2d * nv)).normalized();

        // create reflected ray to calculate the color of reflection of the geometry
        Ray mainReflectedRay = new Ray(geoPoint.point, r, n);
        if(_glossy){
            // create bean of rays around the reflected ray
            // if the angle between the normal of the geometry and the reflected ray
            // is negative it mean that the ray is blow the geometry and we don't take this ray
            // in the consideration
            return mainReflectedRay.createBeanForGlossy(_numOfRaysInBean, 1,
                    dir -> nv * dir.dotProduct(n) > 0);
        }
        else{ // not bean but single ray
            return List.of(mainReflectedRay);
        }
    }

    /**
     * Scattering radius for bean of random rays
     * @param distance the distance between the geometry and the reflection body
     * @return the scattering radius of the bean
     */
    private double scatteringRadius(double distance) {
        // if the geometry is very close to the glossy geometry
        // there is no effect of creating a bean of rays
        if(isZero(distance) || distance >= MAX_DISTANCE_FOR_REFLECTION){
            return 0; // in this case the point generator will not create bean but a single ray
        }

        return random(5, 10);
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

    /**
     * At each point of intersection A of a ray from the camera with geometry
     * we create a new ray that is sent from A to the light source to
     * check if another geometry is blocking us.
     * If so it means that there is a shadow on A
     * and therefore the point A should be painted in shadow colors.
     * @param lightSource light source of the scene
     * @param l the direction of the light to the point where its strikes
     * @param geoPoint the geometry and the lighted point at him
     * @return The total amount of transparency in order to get a more accurate shadow as a number between 0 and 1.
     * 1 means that there is no shadow. 0 means full shadow.
     */
    private double transparency(LightSource lightSource, Vector l, GeoPoint geoPoint){
        Vector lightDirection = l.scale(-1); // from point to light source
        // create a new ray that is sent from point to the light source
        Ray lightRay = new Ray(geoPoint.point, lightDirection, geoPoint.normal);
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
