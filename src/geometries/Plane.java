package geometries;

import primitives.Point3D;
import primitives.Vector;

public class Plane {

    final Point3D _q0;
    final Vector _normal;

    public Plane(Point3D q0, Vector normal) {
        _q0 = q0;
        _normal = normal;
    }

    public Plane(Point3D vertex1, Point3D vertex2, Point3D vertex3) {
        _q0 = vertex1;
        _normal = null;
    }

    public Vector getNormal() {
        //TODO
        return _normal;
    }
}
