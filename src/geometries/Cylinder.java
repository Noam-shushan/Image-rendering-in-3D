package geometries;

import primitives.*;

import java.util.List;

import static primitives.Util.alignZero;

/**
 * this a class represent a final Cylinder by radius, ray and height
 *
 * @author Noam Shushan
 */
public class Cylinder extends Tube{
    /**
     * the height of the Cylinder
     * most be greater then zero
     */
    final double _height;
    private final Plane _base1; // the first base
    private final Plane _base2; // the second base

    /**
     * Constructor of the Cylinder
     * We will represent cylinder by ray, radius and height
     * @param axisRay the ray of the Tube
     * @param radius the radius of the Tube
     * @param height the height of the cylinder
     * @throws IllegalArgumentException if radius < 0 or the height < 0
     */
    public Cylinder(double radius, Ray axisRay, double height) {
        super(radius ,axisRay);

        if(height < 0){
            throw new IllegalArgumentException("height most be greater then zero");
        }

        _height = height;

         Vector v = _axisRay.getDir();
        _base1 = new Plane(_axisRay.getP0(), v);
        _base2= new Plane(_axisRay.getPoint(_height), v);
    }

    /**
     * get ths normal from some point on the cylinder
     * @param point the point to get the normal from
     * @return vector normal to to the cylinder from the point
     */
    @Override
    public Vector getNormal(Point3D point) {
        Point3D p0 = _axisRay.getP0();
        Vector N = _axisRay.getDir();

        // find the vector on the lower base
        Vector p0_p = point.subtract(p0);
        if(p0_p.dotProduct(N) == 0){ // the vectors is orthogonal to each other
            if(p0_p.length() <= _radius){ // the point is on the base of the cylinder
                return N;
            }
        }

        // find the vector on the upper base
        Vector vN = N.scale(N.dotProduct(p0_p));
        if(p0_p.equals(vN)){
            return N;
        }
        else {
            Vector p0_p_vN = p0_p.subtract(vN);
            return p0_p_vN.length() == _radius ? p0_p_vN.normalize() : N;
        }
    }
    /**
     * find intersections with cylinder
     * @param ray ray intersecting with the cylinder
     * @return list of intersections can be max of 2 points
     */
    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray){
        Vector vAxis = _axisRay.getDir();
        Vector v = ray.getDir();
        Point3D p0 = ray.getP0();
        Point3D pC = _axisRay.getP0();
        Point3D p1;
        Point3D p2;

        // intersections of the ray with infinite cylinder {without the bases)
        var intersections = super.findGeoIntersections(ray);
        double vAxisV = alignZero(vAxis.dotProduct(v)); // cos(angle between ray directions)

        if (intersections == null) { // no intersections with the infinite cylinder
            try {
                vAxis.crossProduct(v); // if ray and axis are parallel - throw exception
                return null; // they are not parallel - the ray is outside the cylinder
            } catch (Exception e) {}

            // The rays are parallel
            Vector vP0PC;
            try {
                vP0PC = pC.subtract(p0); // vector from P0 to Pc (O1)
            } catch (Exception e) { // the rays start at the same point
                // check whether the ray goes into the cylinder
                return vAxisV > 0 ? //
                        List.of(new GeoPoint(this, ray.getPoint(_height))) : null;
            }

            double t1 = alignZero(vP0PC.dotProduct(v)); // project Pc (O1) on the ray
            p1 = ray.getPoint(t1); // intersection point with base1

            // Check the distance between the rays
            if (alignZero(p1.distance(pC) - _radius) >= 0){
                return null;
            }

            // intersection points with base2
            double t2 = alignZero(vAxisV > 0 ? t1 + _height : t1 - _height);
            p2 = ray.getPoint(t2);
            // the ray goes through the bases - test bases vs. ray head and return points
            // accordingly
            if (t1 > 0 && t2 > 0){
                return List.of(new GeoPoint(this, p1), new GeoPoint(this, p2));
            }

            if (t1 > 0){
                return List.of(new GeoPoint(this, p1));
            }
            if (t2 > 0){
                return List.of(new GeoPoint(this, p2));
            }
            return null;
        }

        // Ray - infinite cylinder intersection points
        p1 = intersections.get(0).point;
        p2 = intersections.get(1).point;

        // get projection of the points on the axis vs. base1 and update the
        // points if both under base1 or they are from different sides of base1
        double p1OnAxis = alignZero(p1.subtract(pC).dotProduct(vAxis));
        double p2OnAxis = alignZero(p2.subtract(pC).dotProduct(vAxis));
        var pTemp = _base1.findIntersections(ray);
        if(pTemp != null){
            if (p1OnAxis < 0 && p2OnAxis < 0){
                p1 = null;
            }
            else if (p1OnAxis < 0){
                p1 = _base1.findIntersections(ray).get(0);
            }
            else{
                /* p2OnAxis < 0 */
                p2 = _base1.findIntersections(ray).get(0);
            }

        }

        // get projection of the points on the axis vs. base2 and update the
        // points if both above base2 or they are from different sides of base2
        double p1OnAxisMinusH = alignZero(p1OnAxis - _height);
        double p2OnAxisMinusH = alignZero(p2OnAxis - _height);
        if (p1OnAxisMinusH > 0 && p2OnAxisMinusH > 0)
            p1 = null;
        else if (p1OnAxisMinusH > 0)
            p1 = _base2.findIntersections(ray).get(0);
        else
            /* p2OnAxisMinusH > 0 */ p2 = _base2.findIntersections(ray).get(0);

        // Check the points and return list of points accordingly
        if (p1 != null && p2 != null){
            return List.of(new GeoPoint(this, p1), new GeoPoint(this, p2));
        }
        if (p1 != null){
            return List.of(new GeoPoint(this, p1));
        }
        if (p2 != null){
            return List.of(new GeoPoint(this, p2));
        }

        return null;
    }

    @Override
    public String toString() {
        return "height = " + _height +
                ", axisRay = " + _axisRay +
                ", radius = " + _radius;
    }
}
