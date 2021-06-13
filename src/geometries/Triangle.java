package geometries;

import primitives.*;
import static primitives.Util.*;

import java.util.List;

/**
 * this class represent a Triangle by 3 points
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
     * find intersections point with the triangle
     * v1 = p1 - p0 <br/>
     * v2 = p2 - p0 <br/>
     * v3 = p3 - p0 <br/>
     * n1 = normalize(v1xv2) <br/>
     * n2 = normalize(v2xv3) <br/>
     * n3 = normalize(v3xv1) <br/>
     * let v be the direction of the rey
     * if v*ni (1 <= i < 3) is have the sing (+/-)
     * there is intersection points with the triangle
     * @param ray ray that cross the triangle
     * @return list of intersection points that were found
     */
    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray){
        Point3D p0 = ray.getP0();
        Vector v = ray.getDir();

        var result = _plane.findGeoIntersections(ray);

        // if there is no intersections with the plane is a fortiori (kal&homer)
        // that there is no intersections with the triangle
        if(result == null){
            return null;
        }

        Vector v1 = this._vertices.get(0).subtract(p0),
                v2 = this._vertices.get(1).subtract(p0),
                v3 = this._vertices.get(2).subtract(p0);

        Vector n1 = v1.crossProduct(v2).normalize(),
                n2 = v2.crossProduct(v3).normalize(),
                n3 = v3.crossProduct(v1).normalize();

        double x1 = alignZero(v.dotProduct(n1)),
                x2 = alignZero(v.dotProduct(n2)),
                x3 = alignZero(v.dotProduct(n3));

        boolean allNegative = x1 < 0 && x2 < 0 && x3 < 0;
        boolean allPositive = x1 > 0 && x2 > 0 && x3 > 0;

        if(allNegative || allPositive){
            return List.of(new GeoPoint(this, result.get(0).point)); // return the intersections with the plane that the triangle is on
        }

        return null;
    }
}