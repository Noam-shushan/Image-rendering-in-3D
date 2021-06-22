package geometries;

import primitives.*;

import java.util.List;

import static primitives.Util.*;

/**
 * this class represent an infinite Tube by radius and ray
 *
 * @author Noam Shushan
 */
public class Tube extends Geometry {
    /**
     * the ray of the tube
     */
    final Ray _axisRay;
    /**
     * the radius of the sphere
     * most be greater then zero
     */
    final double _radius;

    /**
     * Constructor for Tube
     * @param axisRay the ray of the tube
     * @param radius the radius of the tube
     * @throws IllegalArgumentException if radius is lower then 0
     */
    public Tube(double radius, Ray axisRay) {
        if(radius < 0){
            throw new IllegalArgumentException("radius most be greater then zero");
        }

        _radius = radius;
        _axisRay = axisRay;
    }

    /**
     * normal of Tube
     * calculation of the normal: <br/>
     * t = v*(p - p0) <br/>
     * O = p0 + t*v <br/>
     * N = normalize(p - O)
     * @param point the point to get the normal from
     * @return vector normal to to the tube from the point
     * @throws IllegalArgumentException if the point is equal to the O in the
     * formula above because we get vector zero
     */
    @Override
    public Vector getNormal(Point3D point) {
        Point3D p0 = _axisRay.getP0();
        Vector v = _axisRay.getDir();

        Vector p_p0 = point.subtract(p0);
        double t = v.dotProduct(p_p0);

        Point3D O = p0.add(p_p0.scale(t));
        if (O.equals(point))
            throw new IllegalArgumentException("point cannot be equal to O");

        Vector N = point.subtract(O);

        return N.normalize();
    }

    /**
     * find intersections with tube
     * @param ray ray intersecting with the tube
     * @return intersection points
     */
    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray) {
        Vector vAxis = _axisRay.getDir();
        Vector v = ray.getDir();
        Point3D p0 = ray.getP0();

        // At^2+Bt+C=0
        double a = 0;
        double b = 0;
        double c = 0;

        double vVa = alignZero(v.dotProduct(vAxis));
        Vector vVaVa;
        Vector vMinusVVaVa;
        if (isZero(vVa)) {
            // the ray is orthogonal to the axis
            vMinusVVaVa = v;
        } else {
            vVaVa = vAxis.scale(vVa);
            try {
                vMinusVVaVa = v.subtract(vVaVa);
            } catch (IllegalArgumentException e1) { // the rays is parallel to axis
                return null;
            }
        }
        // A = (v-(v*va)*va)^2
        a = vMinusVVaVa.lengthSquared();

        double squareRadius = _radius * _radius;

        Vector deltaP = null;
        try {
            deltaP = p0.subtract(_axisRay.getP0());
        } catch (IllegalArgumentException e1) { // the ray begins at axis P0
            if (isZero(vVa)){
                // the ray is orthogonal to Axis
                return List.of(new GeoPoint(this, ray.getPoint(_radius)));
            }
            return List.of(new GeoPoint(this, ray.getPoint(Math.sqrt(squareRadius / vMinusVVaVa.lengthSquared()))));
        }

        double dPVAxis = alignZero(deltaP.dotProduct(vAxis));
        Vector dPVaVa;
        Vector dPMinusdPVaVa;
        if (isZero(dPVAxis)){
            dPMinusdPVaVa = deltaP;
        } else {
            dPVaVa = vAxis.scale(dPVAxis);
            try {
                dPMinusdPVaVa = deltaP.subtract(dPVaVa);
            } catch (IllegalArgumentException e1) {
                return List.of(new GeoPoint(this,ray.getPoint(Math.sqrt(squareRadius / a))));
            }
        }

        // B = 2(v - (v*va)*va) * (dp - (dp*va)*va))
        b = 2 * alignZero(vMinusVVaVa.dotProduct(dPMinusdPVaVa));
        c = dPMinusdPVaVa.lengthSquared() - squareRadius;

        // A*t^2 + B*t + C = 0 - lets resolve it
        double discr = alignZero(b * b - 4 * a * c);
        if (discr <= 0){
            return null; // the ray is outside or tangent to the tube
        }

        double doubleA = 2 * a;
        double tm = alignZero(-b / doubleA);
        double th = Math.sqrt(discr) / doubleA;
        if (isZero(th)){
            return null; // the ray is tangent to the tube
        }

        double t1 = alignZero(tm - th);
        double t2 = alignZero(tm + th); // always: t2 > t1
        if (t2 <= 0){
            return null;
        }

        try {
            return t1 > 0 ? List.of(new GeoPoint(this, ray.getPoint(t1)),
                    new GeoPoint(this, ray.getPoint(t2)))
                    : List.of(new GeoPoint(this, ray.getPoint(t2)));
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
