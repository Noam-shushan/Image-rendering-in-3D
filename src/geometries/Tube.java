package geometries;

import primitives.*;

import java.util.List;

import static primitives.Util.*;

/**
 * this class represent a Tube
 *
 * @author Noam Shushan
 */
public class Tube extends RadialGeometry implements Geometry {
    final Ray _axisRay;

    /**
     * Constructor for Tube
     * @param axisRay the ray of the tube
     * @param radius the radius of the tube
     */
    public Tube(double radius, Ray axisRay) {
        super(radius);
        _axisRay = axisRay;
    }

    /**
     * normal of Tube
     * calculation of the normal:
     * t = v*(p - p0)
     * O = p0 + t*v
     * N = normalize(P - O)
     *
     * @param point should be null for flat geometries
     * @return the normal to the geometry
     */
    @Override
    public Vector getNormal(Point3D point) {
        Vector p_p0 = point.subtract(_axisRay.getP0());
        double t = _axisRay.getDir().dotProduct(p_p0);

        Point3D O = _axisRay.getP0().add(p_p0.scale(t));
        if (O.equals(point))
            throw new IllegalArgumentException("point cannot be equal to O");

        Vector N = point.subtract(O);

        return N.normalize();
    }

    /**
     * implemented by Dan zilberstein
     *
     * @param ray ray intersecting with the tube
     * @return intersection points
     */
    @Override
    public List<Point3D> findIntersections(Ray ray) {
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
        if (vVa == 0) // the ray is orthogonal to the axis
            vMinusVVaVa = v;
        else {
            vVaVa = vAxis.scale(vVa);
            try {
                vMinusVVaVa = v.subtract(vVaVa);
            } catch (IllegalArgumentException e1) { // the rays is parallel to axis
                return null;
            }
        }
        // A = (v-(v*va)*va)^2
        a = vMinusVVaVa.lengthSquared();

        Vector deltaP = null;
        try {
            deltaP = p0.subtract(_axisRay.getP0());
        } catch (IllegalArgumentException e1) { // the ray begins at axis P0
            if (vVa == 0) // the ray is orthogonal to Axis
                return List.of(ray.getPoint(_radius));

            double t = alignZero(Math.sqrt(_radius * _radius / vMinusVVaVa.lengthSquared()));
            return t == 0 ? null : List.of(ray.getPoint(t));
        }

        double dPVAxis = alignZero(deltaP.dotProduct(vAxis));
        Vector dPVaVa;
        Vector dPMinusdPVaVa;
        if (dPVAxis == 0)
            dPMinusdPVaVa = deltaP;
        else {
            dPVaVa = vAxis.scale(dPVAxis);
            try {
                dPMinusdPVaVa = deltaP.subtract(dPVaVa);
            } catch (IllegalArgumentException e1) {
                double t = alignZero(Math.sqrt(_radius * _radius / a));
                return t == 0 ? null : List.of(ray.getPoint(t));
            }
        }

        // B = 2(v - (v*va)*va) * (dp - (dp*va)*va))
        b = 2 * alignZero(vMinusVVaVa.dotProduct(dPMinusdPVaVa));
        c = dPMinusdPVaVa.lengthSquared() - _radius * _radius;

        // A*t^2 + B*t + C = 0 - lets resolve it
        double discr = alignZero(b * b - 4 * a * c);
        if (discr <= 0) return null; // the ray is outside or tangent to the tube

        double doubleA = 2 * a;
        double tm = alignZero(-b / doubleA);
        double th = Math.sqrt(discr) / doubleA;
        if (isZero(th)) return null; // the ray is tangent to the tube

        double t1 = alignZero(tm + th);
        if (t1 <= 0) // t1 is behind the head
            return null; // since th must be positive (sqrt), t2 must be non-positive as t1

        double t2 = alignZero(tm - th);

        // if both t1 and t2 are positive
        if (t2 > 0)
            return List.of(ray.getPoint(t1), ray.getPoint(t2));
        else // t2 is behind the head
            return List.of(ray.getPoint(t1));
    }
}
