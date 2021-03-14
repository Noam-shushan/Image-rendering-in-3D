package geometries;

import primitives.*;

/**
 * this is a class represent a Triangle
 * @author Noam Shushan
 */
public class Triangle extends Polygon {
    /**
     * Constructor for Triangle class
     * @param p1
     * @param p2
     * @param p3
     */
    public Triangle(Point3D p1, Point3D p2, Point3D p3) {
        super(p1, p2, p3);
    }
}