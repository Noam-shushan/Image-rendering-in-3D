package geometries;

import primitives.*;
import static primitives.Util.*;

import java.util.List;

/**
 * this class represent a Triangle
 * @author Noam Shushan
 */
public class Triangle extends Polygon {
    /**
     * Constructor for Triangle class
     * @param p1 first point
     * @param p2 second point
     * @param p3 third point
     */
    public Triangle(Point3D p1, Point3D p2, Point3D p3) {
        super(p1, p2, p3);
    }

    /**
     * @param ray ray that cross the geometry
     * @return list of intersection points that were found
     */
    @Override
    public List<Point3D> findIntersections(Ray ray) {
        Point3D p0 = ray.getP0();
        Vector v = ray.getDir();

        Vector v1 = this._vertices.get(0).subtract(p0),
                v2 = this._vertices.get(1).subtract(p0),
                v3 = this._vertices.get(2).subtract(p0);

        Vector n1 = v1.crossProduct(v2).normalize(),
                n2 = v2.crossProduct(v3).normalize(),
                n3 = v3.crossProduct(v1).normalize();

        var temp = List.of(
                alignZero(n1.dotProduct(v)),
                alignZero(n2.dotProduct(v)),
                alignZero(n3.dotProduct(v)));
        boolean allNegative = temp.stream().allMatch(x -> x < 0);
        boolean allPositive = temp.stream().allMatch(x -> x > 0);

        if(allNegative || allPositive){
            return _plane.findIntersections(ray);
        }

        return null;
    }
}